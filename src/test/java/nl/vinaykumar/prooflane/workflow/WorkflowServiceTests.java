package nl.vinaykumar.prooflane.workflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import nl.vinaykumar.prooflane.agent.VerifierAgent;
import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.evidence.EvidenceService;
import nl.vinaykumar.prooflane.model.VerificationResult;
import nl.vinaykumar.prooflane.model.VerificationStatus;
import nl.vinaykumar.prooflane.model.WorkflowState;

import org.junit.jupiter.api.Test;

class WorkflowServiceTests {

	private final Path repo = Path.of("/tmp/example-repo");
	private final EvidencePacket evidence = new EvidencePacket(
			repo.toString(),
			0,
			"",
			0,
			"",
			"./mvnw test",
			0,
			"OK");

	@Test
	void passRecordsDeliveryRefreshAndDeliveredTerminalState() {
		var workflowService = workflowService(new VerificationResult(
				VerificationStatus.PASS,
				List.of("tests passed"),
				List.of(),
				"deliver"));

		var run = workflowService.checkReadiness(repo);

		assertThat(run.state()).isEqualTo(WorkflowState.DELIVERED);
		assertThat(run.receipt().route()).isEqualTo("DELIVER");
		assertThat(run.transitions()).extracting("state").containsExactly(
				WorkflowState.STARTED,
				WorkflowState.EVIDENCE_GATHERED,
				WorkflowState.VERIFIED,
				WorkflowState.DELIVERY_REFRESHED,
				WorkflowState.DELIVERED);
	}

	@Test
	void failRecordsNeedsRepairWithoutDeliveryRefresh() {
		var workflowService = workflowService(new VerificationResult(
				VerificationStatus.FAIL,
				List.of("tests failed"),
				List.of("failing tests"),
				"repair"));

		var run = workflowService.checkReadiness(repo);

		assertThat(run.state()).isEqualTo(WorkflowState.NEEDS_REPAIR);
		assertThat(run.receipt().route()).isEqualTo("REPAIR");
		assertThat(run.transitions()).extracting("state").containsExactly(
				WorkflowState.STARTED,
				WorkflowState.EVIDENCE_GATHERED,
				WorkflowState.VERIFIED,
				WorkflowState.NEEDS_REPAIR);
	}

	private WorkflowService workflowService(VerificationResult result) {
		return new WorkflowService(
				new FixedEvidenceService(evidence),
				new FixedVerifierAgent(result),
				new VerificationRouter(),
				Clock.fixed(Instant.parse("2026-09-10T11:30:00Z"), ZoneOffset.UTC));
	}

	private static class FixedEvidenceService extends EvidenceService {
		private final EvidencePacket evidence;

		FixedEvidenceService(EvidencePacket evidence) {
			this.evidence = evidence;
		}

		@Override
		public EvidencePacket gather(Path repo) {
			return evidence;
		}
	}

	private static class FixedVerifierAgent implements VerifierAgent {
		private final VerificationResult result;

		FixedVerifierAgent(VerificationResult result) {
			this.result = result;
		}

		@Override
		public VerificationResult verify(EvidencePacket evidence) {
			return result;
		}
	}
}
