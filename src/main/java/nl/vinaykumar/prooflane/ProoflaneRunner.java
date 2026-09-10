package nl.vinaykumar.prooflane;

import java.nio.file.Path;

import nl.vinaykumar.prooflane.guardrails.CostGuardrails;
import nl.vinaykumar.prooflane.workflow.WorkflowService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class ProoflaneRunner implements CommandLineRunner {

	private final WorkflowService workflowService;
	private final CostGuardrails guardrails;

	ProoflaneRunner(WorkflowService workflowService, CostGuardrails guardrails) {
		this.workflowService = workflowService;
		this.guardrails = guardrails;
	}

	@Override
	public void run(String... args) {
		var repo = Path.of(System.getProperty("prooflane.repo", ".")).toAbsolutePath().normalize();
		var receipt = workflowService.checkReadiness(repo);
		System.out.println("Prooflane route: " + receipt.route());
		System.out.println("Status: " + receipt.result().status());
		System.out.println("Evidence: " + receipt.result().evidence());
		System.out.println("Risks: " + receipt.result().risks());
		System.out.println("Next step: " + receipt.result().nextStep());
		System.out.printf("Configured max GPT-5 Mini call cost: $%.4f%n", guardrails.estimatedMaxOpenAiGpt5MiniCostUsd());
	}
}
