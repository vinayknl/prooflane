package nl.vinaykumar.prooflane.tools;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class RepoTools {

	public String gitStatus(Path repo) {
		return run(repo, "git", "status", "--short").output();
	}

	public String diffSummary(Path repo) {
		return run(repo, "git", "diff", "--stat").output();
	}

	public CommandResult runTests(Path repo) {
		if (Files.isExecutable(repo.resolve("mvnw"))) {
			return run(repo, "./mvnw", "-q", "test");
		}
		if (Files.exists(repo.resolve("gradlew"))) {
			return run(repo, "./gradlew", "test");
		}
		return new CommandResult(-1, "No Maven or Gradle wrapper found.");
	}

	private CommandResult run(Path repo, String... command) {
		try {
			var process = new ProcessBuilder(command)
					.directory(repo.toFile())
					.redirectErrorStream(true)
					.start();
			var completed = process.waitFor(Duration.ofSeconds(90).toMillis(), TimeUnit.MILLISECONDS);
			if (!completed) {
				process.destroyForcibly();
				return new CommandResult(-1, "Command timed out.");
			}
			var output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
			return new CommandResult(process.exitValue(), output);
		}
		catch (Exception ex) {
			return new CommandResult(-1, ex.getMessage());
		}
	}
}
