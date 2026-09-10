package nl.vinaykumar.prooflane.model;

import java.time.Instant;
import java.util.List;

public record WorkflowRun(
		String runId,
		String repo,
		Instant startedAt,
		Instant completedAt,
		WorkflowState state,
		List<WorkflowTransition> transitions,
		WorkflowReceipt receipt) {
}
