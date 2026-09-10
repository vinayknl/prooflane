package nl.vinaykumar.prooflane.workflow;

import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.vinaykumar.prooflane.agent.VerifierAgent;
import nl.vinaykumar.prooflane.evidence.EvidenceService;
import nl.vinaykumar.prooflane.model.VerificationStatus;
import nl.vinaykumar.prooflane.model.WorkflowReceipt;
import nl.vinaykumar.prooflane.model.WorkflowRun;
import nl.vinaykumar.prooflane.model.WorkflowState;
import nl.vinaykumar.prooflane.model.WorkflowTransition;

import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

	private final EvidenceService evidenceService;
	private final VerifierAgent verifierAgent;
	private final VerificationRouter router;
	private final Clock clock;

	public WorkflowService(EvidenceService evidenceService, VerifierAgent verifierAgent, VerificationRouter router) {
		this(evidenceService, verifierAgent, router, Clock.systemUTC());
	}

	WorkflowService(EvidenceService evidenceService, VerifierAgent verifierAgent, VerificationRouter router, Clock clock) {
		this.evidenceService = evidenceService;
		this.verifierAgent = verifierAgent;
		this.router = router;
		this.clock = clock;
	}

	public WorkflowRun checkReadiness(Path repo) {
		var runId = UUID.randomUUID().toString();
		var startedAt = clock.instant();
		var transitions = new ArrayList<WorkflowTransition>();
		transition(transitions, WorkflowState.STARTED, "workflow started");

		var evidence = evidenceService.gather(repo);
		transition(transitions, WorkflowState.EVIDENCE_GATHERED, "local evidence gathered");

		var result = verifierAgent.verify(evidence);
		transition(transitions, WorkflowState.VERIFIED, "verifier returned " + result.status());

		var refreshed = result.status() == VerificationStatus.PASS ? evidenceService.gather(repo) : evidence;
		if (result.status() == VerificationStatus.PASS) {
			transition(transitions, WorkflowState.DELIVERY_REFRESHED, "delivery state refreshed after verifier pass");
		}

		var receipt = router.route(result, refreshed);
		var terminalState = terminalState(receipt);
		transition(transitions, terminalState, "router selected " + receipt.route());
		return new WorkflowRun(runId, repo.toAbsolutePath().normalize().toString(), startedAt, clock.instant(), terminalState, List.copyOf(transitions), receipt);
	}

	private void transition(List<WorkflowTransition> transitions, WorkflowState state, String reason) {
		transitions.add(new WorkflowTransition(state, Instant.now(clock), reason));
	}

	private WorkflowState terminalState(WorkflowReceipt receipt) {
		return switch (receipt.route()) {
			case "DELIVER" -> WorkflowState.DELIVERED;
			case "REPAIR" -> WorkflowState.NEEDS_REPAIR;
			case "BLOCKED" -> WorkflowState.BLOCKED;
			default -> throw new IllegalStateException("Unknown route: " + receipt.route());
		};
	}
}
