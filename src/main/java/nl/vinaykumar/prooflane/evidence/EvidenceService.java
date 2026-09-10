package nl.vinaykumar.prooflane.evidence;

import java.nio.file.Path;

import nl.vinaykumar.prooflane.guardrails.CostGuardrails;
import nl.vinaykumar.prooflane.tools.RepoTools;

import org.springframework.stereotype.Service;

@Service
public class EvidenceService {

	private final RepoTools repoTools;
	private final CostGuardrails guardrails;

	public EvidenceService(RepoTools repoTools, CostGuardrails guardrails) {
		this.repoTools = repoTools;
		this.guardrails = guardrails;
	}

	public EvidencePacket gather(Path repo) {
		var testResult = repoTools.runTests(repo);
		var raw = new EvidencePacket(
				repo.toString(),
				repoTools.gitStatus(repo),
				repoTools.diffSummary(repo),
				"./mvnw test",
				testResult.exitCode(),
				testResult.output());
		return guardrails.truncate(raw);
	}
}
