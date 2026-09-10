package nl.vinaykumar.prooflane.agent;

import nl.vinaykumar.prooflane.evidence.EvidencePacket;
import nl.vinaykumar.prooflane.model.VerificationResult;

public interface VerifierAgent {
	VerificationResult verify(EvidencePacket evidence);
}
