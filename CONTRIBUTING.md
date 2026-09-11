# Contributing To Prooflane

Prooflane is an evidence-driven agentic workflow learning project. The goal is
to keep agent behavior useful while Java owns workflow control, routing,
budgets, and safety gates.

## Contribution Principles

- Keep the workflow deterministic where possible. LLMs may judge evidence, but
  Java code should own routing and terminal state.
- Prefer compact evidence packets over dumping whole repositories, logs, or
  files into the model.
- No-LLM tests must remain the default. Unit tests and behavioral evals must
  not require paid API calls.
- Make cost behavior visible. Any feature that can call an LLM should have a
  bounded call count and context budget.
- Treat delivery as a claim backed by evidence, not as model confidence.

## Local Setup

Requirements:

- Java 21 or newer
- Git
- No API key is required for No-LLM mode

Run:

```bash
./mvnw test
./mvnw spring-boot:run -Dspring-boot.run.arguments=/path/to/repo
```

Real OpenAI mode is opt-in:

```bash
export OPENAI_API_KEY=...
export PROOFLANE_AGENT_MODE=openai
export PROOFLANE_SPRING_AI_MODEL=openai
./mvnw spring-boot:run -Dspring-boot.run.arguments=/path/to/repo
```

## Definition Of Done

Every contribution should include:

- A small explanation of the behavior changed.
- Tests or behavioral evals for the route, guardrail, or tool behavior.
- A note on whether the change can trigger LLM calls.
- Proof that `./mvnw test` passes.

## Pull Request Expectations

- Keep PRs focused.
- Do not commit API keys, logs with secrets, or local environment files.
- Mention any new cost, token, network, or tool-execution behavior.
- Use No-LLM verifiers or deterministic fixtures for tests unless a real-provider
  integration test is explicitly isolated and opt-in.

