package nl.vinaykumar.prooflane.tools;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class RepoTools {

	public CommandResult gitStatus(Path repo) {
		return run(repo, "git", "status", "--short");
	}

	public CommandResult diffSummary(Path repo) {
		return run(repo, "git", "diff", "--stat");
	}

	public CommandResult runTests(Path repo) {
		if (!Files.isDirectory(repo)) {
			return new CommandResult(-1, "Repository path is not a directory.");
		}
		if (!isGitWorkTree(repo)) {
			return new CommandResult(-1, "Repository path is not a git work tree.");
		}
		if (Files.isExecutable(repo.resolve("mvnw"))) {
			return run(repo, "./mvnw", "-q", "test");
		}
		if (Files.exists(repo.resolve("mvnw"))) {
			return new CommandResult(-1, "Maven wrapper exists but is not executable.");
		}
		if (Files.isExecutable(repo.resolve("gradlew"))) {
			return run(repo, "./gradlew", "test");
		}
		if (Files.exists(repo.resolve("gradlew"))) {
			return new CommandResult(-1, "Gradle wrapper exists but is not executable.");
		}
		return new CommandResult(-1, "No Maven or Gradle wrapper found.");
	}

	private boolean isGitWorkTree(Path repo) {
		var result = run(repo, "git", "rev-parse", "--is-inside-work-tree");
		return result.exitCode() == 0 && result.output().trim().equals("true");
	}

	private CommandResult run(Path repo, String... command) {
		if (!Files.isDirectory(repo)) {
			return new CommandResult(-1, "Repository path is not a directory.");
		}
		try {
			var process = new ProcessBuilder(command)
					.directory(repo.toFile())
					.redirectErrorStream(true)
					.start();
			var output = CompletableFuture.supplyAsync(() -> readOutput(process));
			var completed = process.waitFor(Duration.ofSeconds(90).toMillis(), TimeUnit.MILLISECONDS);
			if (!completed) {
				process.destroyForcibly();
				process.waitFor(5, TimeUnit.SECONDS);
				return new CommandResult(-1, appendOutput("Command timed out.", output.getNow("")));
			}
			return new CommandResult(process.exitValue(), output.join());
		}
		catch (Exception ex) {
			return new CommandResult(-1, ex.getMessage());
		}
	}

	private String readOutput(Process process) {
		try {
			return new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		}
		catch (Exception ex) {
			return ex.getMessage();
		}
	}

	private String appendOutput(String message, String output) {
		if (output == null || output.isBlank()) {
			return message;
		}
		return message + "\n" + output;
	}
}
