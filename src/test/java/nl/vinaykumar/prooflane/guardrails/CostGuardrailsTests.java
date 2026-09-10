package nl.vinaykumar.prooflane.guardrails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;

import org.junit.jupiter.api.Test;

class CostGuardrailsTests {

	@Test
	void truncatesOversizedEvidenceBeforeModelCall() {
		var guardrails = new CostGuardrails();
		guardrails.setMaxEvidenceChars(8);

		var truncated = guardrails.truncate(new EvidencePacket(".", 0, "clean", 0, "0123456789", "./mvnw test", 0, "abcdefghij"));

		assertThat(truncated.diffSummary()).contains("[truncated by Prooflane cost guardrail]");
		assertThat(truncated.testOutput()).contains("[truncated by Prooflane cost guardrail]");
	}

	@Test
	void stopsAfterConfiguredLlmCallBudget() {
		var guardrails = new CostGuardrails();
		guardrails.setMaxLlmCalls(1);

		guardrails.beforeLlmCall("small prompt");

		assertThatThrownBy(() -> guardrails.beforeLlmCall("second prompt"))
				.isInstanceOf(CostGuardrailException.class)
				.hasMessageContaining("LLM call budget exceeded");
	}
}
