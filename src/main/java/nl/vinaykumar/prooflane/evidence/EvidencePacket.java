package nl.vinaykumar.prooflane.evidence;

public record EvidencePacket(
		String repo,
		String gitStatus,
		String diffSummary,
		String testCommand,
		Integer testExitCode,
		String testOutput) {

	public boolean gitClean() {
		return gitStatus != null && gitStatus.isBlank();
	}

	public boolean testExitCodeKnown() {
		return testExitCode != null;
	}

	public String compactText() {
		return """
				Repository: %s
				Git status:
				%s

				Diff summary:
				%s

				Test command: %s
				Test exit code: %s
				Test output:
				%s
				""".formatted(repo, blankAsNone(gitStatus), blankAsNone(diffSummary), testCommand, testExitCode, blankAsNone(testOutput));
	}

	private static String blankAsNone(String value) {
		return value == null || value.isBlank() ? "(none)" : value;
	}
}
