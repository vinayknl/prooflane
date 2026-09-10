# Security Policy

Prooflane is an early-stage learning project. Please avoid posting secrets,
private repository content, or exploit details in public issues.

## Sensitive Data

Never commit:

- API keys
- access tokens
- `.env` files
- raw private test logs
- repository content that the owner did not intend to send to an LLM

## Local Repository Execution

Prooflane is intended for repositories you trust. The local readiness workflow
may execute repository-local build wrappers such as `./mvnw test` or
`./gradlew test`. Treat unknown repositories as untrusted code and review them
before running Prooflane against them.

## LLM Safety Boundaries

Features that call an LLM must:

- Require explicit provider configuration.
- Respect a maximum LLM call count.
- Respect a context budget before sending evidence to the model.
- Avoid sending full repositories by default.
- Keep tool execution inside application-controlled Java code.

## Reporting

Please prefer a private GitHub security advisory for sensitive reports:

https://github.com/vinayknl/prooflane/security/advisories/new

Include:

- affected version or commit
- reproduction steps
- expected and actual behavior
- security impact
- any secret exposure risk
