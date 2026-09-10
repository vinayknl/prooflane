package nl.vinaykumar.prooflane.agent;

import java.util.List;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.model.VerificationResult;
import nl.vinaykumar.prooflane.model.VerificationStatus;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "prooflane.agent.mode", havingValue = "fake", matchIfMissing = true)
class FakeVerifierAgent implements VerifierAgent {

	@Override
	public VerificationResult verify(EvidencePacket evidence) {
		if (!evidence.testExitCodeKnown()) {
			return new VerificationResult(
					VerificationStatus.BLOCKED,
					List.of("test command did not produce a known exit code"),
					List.of("verification command unavailable"),
					"Fix the local test command before asking for delivery.");
		}
		if (evidence.testExitCode() != 0) {
			return new VerificationResult(
					VerificationStatus.FAIL,
					List.of("test command exited with " + evidence.testExitCode()),
					List.of("tests are failing"),
					"Repair the failing test path and re-run verification.");
		}
		if (!evidence.gitClean()) {
			return new VerificationResult(
					VerificationStatus.FAIL,
					List.of("working tree has uncommitted changes"),
					List.of("delivery state is not clean"),
					"Review or commit the pending changes before delivery.");
		}
		return new VerificationResult(
				VerificationStatus.PASS,
				List.of("tests passed", "working tree is clean"),
				List.of(),
				"Ready for delivery refresh.");
	}
}
