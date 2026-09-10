package nl.vinaykumar.prooflane.workflow;

import java.util.List;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.model.VerificationResult;
import nl.vinaykumar.prooflane.model.WorkflowReceipt;

import org.springframework.stereotype.Component;

@Component
public class VerificationRouter {

	public WorkflowReceipt route(VerificationResult result, EvidencePacket refreshedEvidence) {
		return switch (result.status()) {
			case PASS -> new WorkflowReceipt("DELIVER", result, List.of(
					"delivery refresh gitClean=" + refreshedEvidence.gitClean(),
					"delivery refresh testExitCode=" + refreshedEvidence.testExitCode()));
			case FAIL -> new WorkflowReceipt("REPAIR", result, List.of());
			case BLOCKED -> new WorkflowReceipt("BLOCKED", result, List.of());
		};
	}
}
