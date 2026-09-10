package nl.vinaykumar.prooflane.evidence;

public record EvidencePacket(
		String repo,
		Integer gitStatusExitCode,
		String gitStatus,
		Integer diffSummaryExitCode,
		String diffSummary,
		String testCommand,
		Integer testExitCode,
		String testOutput) {

	public boolean gitClean() {
		return gitStatusSucceeded() && gitStatus != null && gitStatus.isBlank();
	}

	public boolean gitStatusExitCodeKnown() {
		return gitStatusExitCode != null;
	}

	public boolean gitStatusSucceeded() {
		return gitStatusExitCodeKnown() && gitStatusExitCode == 0;
	}

	public boolean testExitCodeKnown() {
		return testExitCode != null;
	}

	public String compactText() {
		return """
				Repository: %s
				Git status exit code: %s
				Git status:
				%s

				Diff summary exit code: %s
				Diff summary:
				%s

				Test command: %s
				Test exit code: %s
				Test output:
				%s
				""".formatted(repo, gitStatusExitCode, blankAsNone(gitStatus), diffSummaryExitCode, blankAsNone(diffSummary), testCommand, testExitCode, blankAsNone(testOutput));
	}

	private static String blankAsNone(String value) {
		return value == null || value.isBlank() ? "(none)" : value;
	}
}
