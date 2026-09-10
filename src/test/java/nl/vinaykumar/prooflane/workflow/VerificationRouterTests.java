package nl.vinaykumar.prooflane.workflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.model.VerificationResult;
import nl.vinaykumar.prooflane.model.VerificationStatus;

import org.junit.jupiter.api.Test;

class VerificationRouterTests {

	private final VerificationRouter router = new VerificationRouter();
	private final EvidencePacket cleanEvidence = new EvidencePacket(".", 0, "", 0, "", "./mvnw test", 0, "OK");

	@Test
	void passRoutesToDeliveryWithRefreshEvidence() {
		var receipt = router.route(new VerificationResult(VerificationStatus.PASS, List.of("tests passed"), List.of(), "deliver"), cleanEvidence);

		assertThat(receipt.route()).isEqualTo("DELIVER");
		assertThat(receipt.deliveryEvidence()).contains("delivery refresh gitClean=true");
	}

	@Test
	void failRoutesToRepair() {
		var receipt = router.route(new VerificationResult(VerificationStatus.FAIL, List.of("tests failed"), List.of("failure"), "repair"), cleanEvidence);

		assertThat(receipt.route()).isEqualTo("REPAIR");
		assertThat(receipt.deliveryEvidence()).isEmpty();
	}

	@Test
	void blockedRoutesToBlockedReceipt() {
		var receipt = router.route(new VerificationResult(VerificationStatus.BLOCKED, List.of("missing command"), List.of("unknown"), "install wrapper"), cleanEvidence);

		assertThat(receipt.route()).isEqualTo("BLOCKED");
		assertThat(receipt.deliveryEvidence()).isEmpty();
	}
}
