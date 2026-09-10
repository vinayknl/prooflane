package nl.vinaykumar.prooflane.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record VerificationResult(
		@NotNull VerificationStatus status,
		List<String> evidence,
		List<String> risks,
		String nextStep) {
}
