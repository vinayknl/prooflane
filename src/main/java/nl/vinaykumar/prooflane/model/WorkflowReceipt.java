package nl.vinaykumar.prooflane.model;

import java.util.List;

public record WorkflowReceipt(
		String route,
		VerificationResult result,
		List<String> deliveryEvidence) {
}
