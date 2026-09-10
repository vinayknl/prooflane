package nl.vinaykumar.prooflane.workflow;

import java.nio.file.Path;

import nl.vinaykumar.prooflane.agent.VerifierAgent;
import nl.vinaykumar.prooflane.evidence.EvidenceService;
import nl.vinaykumar.prooflane.model.WorkflowReceipt;

import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

	private final EvidenceService evidenceService;
	private final VerifierAgent verifierAgent;
	private final VerificationRouter router;

	public WorkflowService(EvidenceService evidenceService, VerifierAgent verifierAgent, VerificationRouter router) {
		this.evidenceService = evidenceService;
		this.verifierAgent = verifierAgent;
		this.router = router;
	}

	public WorkflowReceipt checkReadiness(Path repo) {
		var evidence = evidenceService.gather(repo);
		var result = verifierAgent.verify(evidence);
		var refreshed = result.status().name().equals("PASS") ? evidenceService.gather(repo) : evidence;
		return router.route(result, refreshed);
	}
}
