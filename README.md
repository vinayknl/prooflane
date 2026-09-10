# Prooflane

Prooflane is a small Spring AI learning project for evidence-driven agentic
workflows.

The first workflow is intentionally compact:

```text
request -> evidence -> verifier agent -> deterministic router -> receipt
```

The agent makes a typed judgment. Java owns the workflow route.

## Cost Guardrails

- Fake verifier mode is the default.
- Real LLM mode requires `PROOFLANE_AGENT_MODE=openai`.
- Real LLM mode also requires `OPENAI_API_KEY`.
- Each run has a maximum LLM call count.
- Evidence packets are capped before they reach the model.
- Tests use fake agents and do not call paid APIs.

## Run

```bash
./mvnw test
./mvnw spring-boot:run -Dspring-boot.run.arguments=/path/to/repo
```

Real OpenAI mode:

```bash
export OPENAI_API_KEY=...
export PROOFLANE_AGENT_MODE=openai
export PROOFLANE_SPRING_AI_MODEL=openai
./mvnw spring-boot:run -Dspring-boot.run.arguments=/path/to/repo
```
