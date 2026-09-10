package nl.vinaykumar.prooflane.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class RepoToolsTests {

	private final RepoTools repoTools = new RepoTools();

	@TempDir
	Path tempDir;

	@Test
	void gitStatusReportsNonGitDirectoryAsCommandFailure() {
		var result = repoTools.gitStatus(tempDir);

		assertThat(result.exitCode()).isNotZero();
		assertThat(result.output()).containsIgnoringCase("not a git repository");
	}

	@Test
	void runTestsBlocksNonGitDirectoryBeforeLookingForWrappers() {
		var result = repoTools.runTests(tempDir);

		assertThat(result.exitCode()).isEqualTo(-1);
		assertThat(result.output()).isEqualTo("Repository path is not a git work tree.");
	}

	@Test
	void runTestsBlocksMissingDirectory() {
		var result = repoTools.runTests(tempDir.resolve("missing"));

		assertThat(result.exitCode()).isEqualTo(-1);
		assertThat(result.output()).isEqualTo("Repository path is not a directory.");
	}

	@Test
	void runTestsBlocksNonExecutableGradleWrapper() throws Exception {
		initGitRepo(tempDir);
		Files.writeString(tempDir.resolve("gradlew"), "echo test");

		var result = repoTools.runTests(tempDir);

		assertThat(result.exitCode()).isEqualTo(-1);
		assertThat(result.output()).isEqualTo("Gradle wrapper exists but is not executable.");
	}

	private void initGitRepo(Path repo) throws Exception {
		var result = new ProcessBuilder("git", "init")
				.directory(repo.toFile())
				.redirectErrorStream(true)
				.start()
				.waitFor();
		assertThat(result).isZero();
	}
}
