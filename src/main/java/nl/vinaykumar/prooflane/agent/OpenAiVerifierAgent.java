package nl.vinaykumar.prooflane.agent;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.guardrails.CostGuardrails;
import nl.vinaykumar.prooflane.model.VerificationResult;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "prooflane.agent.mode", havingValue = "openai")
class OpenAiVerifierAgent implements VerifierAgent {

	private final ChatClient chatClient;
	private final CostGuardrails guardrails;

	OpenAiVerifierAgent(ChatClient.Builder builder, CostGuardrails guardrails) {
		this.chatClient = builder.build();
		this.guardrails = guardrails;
	}

	@Override
	public VerificationResult verify(EvidencePacket evidence) {
		guardrails.beforeLlmCall(evidence.compactText());
		return chatClient.prompt()
				.system("""
						You are Prooflane's verifier agent. Decide whether the evidence proves
						the requested software-delivery state. Return a compact structured
						VerificationResult only. Use PASS only when tests passed and delivery
						state is clean. Use FAIL for repairable local issues. Use BLOCKED for
						missing authority, missing commands, or unavailable evidence.
						""")
				.user(evidence.compactText())
				.call()
				.entity(VerificationResult.class);
	}
}
