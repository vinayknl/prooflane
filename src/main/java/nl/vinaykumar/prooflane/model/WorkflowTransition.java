package nl.vinaykumar.prooflane.model;

import java.time.Instant;

public record WorkflowTransition(
		WorkflowState state,
		Instant at,
		String reason) {
}
