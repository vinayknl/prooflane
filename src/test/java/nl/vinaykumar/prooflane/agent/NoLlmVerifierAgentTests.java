package nl.vinaykumar.prooflane.agent;

import static org.assertj.core.api.Assertions.assertThat;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.model.VerificationStatus;

import org.junit.jupiter.api.Test;

class NoLlmVerifierAgentTests {

	private final NoLlmVerifierAgent verifier = new NoLlmVerifierAgent();

	@Test
	void blocksWhenGitStatusFails() {
		var result = verifier.verify(new EvidencePacket(
				"/tmp/not-a-repo",
				128,
				"fatal: not a git repository",
				128,
				"fatal: not a git repository",
				"./mvnw test",
				null,
				"Repository path is not a git work tree."));

		assertThat(result.status()).isEqualTo(VerificationStatus.BLOCKED);
		assertThat(result.risks()).contains("repository git state could not be read");
	}

	@Test
	void blocksWhenTestCommandIsUnavailable() {
		var result = verifier.verify(new EvidencePacket(
				"/tmp/repo",
				0,
				"",
				0,
				"",
				"./mvnw test",
				null,
				"No Maven or Gradle wrapper found."));

		assertThat(result.status()).isEqualTo(VerificationStatus.BLOCKED);
		assertThat(result.risks()).contains("verification command unavailable");
	}
}
