package nl.vinaykumar.prooflane.guardrails;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "prooflane.guardrails")
public class CostGuardrails {

	private int maxEvidenceChars = 12_000;
	private int maxLlmCalls = 1;
	private int estimatedInputTokenBudget = 4_000;
	private int estimatedOutputTokenBudget = 800;
	private int calls = 0;

	public EvidencePacket truncate(EvidencePacket packet) {
		return new EvidencePacket(
				packet.repo(),
				packet.gitStatusExitCode(),
				truncate(packet.gitStatus()),
				packet.diffSummaryExitCode(),
				truncate(packet.diffSummary()),
				packet.testCommand(),
				packet.testExitCode(),
				truncate(packet.testOutput()));
	}

	public void beforeLlmCall(String prompt) {
		calls++;
		if (calls > maxLlmCalls) {
			throw new CostGuardrailException("LLM call budget exceeded: " + maxLlmCalls);
		}
		var estimatedInputTokens = Math.ceilDiv(prompt.length(), 4);
		if (estimatedInputTokens > estimatedInputTokenBudget) {
			throw new CostGuardrailException(
					"Estimated input token budget exceeded: " + estimatedInputTokens + " > " + estimatedInputTokenBudget);
		}
	}

	public double estimatedMaxOpenAiGpt5MiniCostUsd() {
		return (estimatedInputTokenBudget / 1_000_000.0 * 0.25)
				+ (estimatedOutputTokenBudget / 1_000_000.0 * 2.00);
	}

	private String truncate(String value) {
		if (value == null || value.length() <= maxEvidenceChars) {
			return value;
		}
		return value.substring(0, maxEvidenceChars) + "\n[truncated by Prooflane cost guardrail]";
	}

	public int getMaxEvidenceChars() {
		return maxEvidenceChars;
	}

	public void setMaxEvidenceChars(int maxEvidenceChars) {
		this.maxEvidenceChars = maxEvidenceChars;
	}

	public int getMaxLlmCalls() {
		return maxLlmCalls;
	}

	public void setMaxLlmCalls(int maxLlmCalls) {
		this.maxLlmCalls = maxLlmCalls;
	}

	public int getEstimatedInputTokenBudget() {
		return estimatedInputTokenBudget;
	}

	public void setEstimatedInputTokenBudget(int estimatedInputTokenBudget) {
		this.estimatedInputTokenBudget = estimatedInputTokenBudget;
	}

	public int getEstimatedOutputTokenBudget() {
		return estimatedOutputTokenBudget;
	}

	public void setEstimatedOutputTokenBudget(int estimatedOutputTokenBudget) {
		this.estimatedOutputTokenBudget = estimatedOutputTokenBudget;
	}
}
