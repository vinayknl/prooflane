package nl.vinaykumar.prooflane.evidence;

import java.nio.file.Path;

import nl.vinaykumar.prooflane.guardrails.CostGuardrails;
import nl.vinaykumar.prooflane.tools.RepoTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvidenceService {

	private final RepoTools repoTools;
	private final CostGuardrails guardrails;

	@Autowired
	public EvidenceService(RepoTools repoTools, CostGuardrails guardrails) {
		this.repoTools = repoTools;
		this.guardrails = guardrails;
	}

	protected EvidenceService() {
		this.repoTools = null;
		this.guardrails = null;
	}

	public EvidencePacket gather(Path repo) {
		if (repoTools == null || guardrails == null) {
			throw new IllegalStateException("EvidenceService test subclass must override gather");
		}
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
