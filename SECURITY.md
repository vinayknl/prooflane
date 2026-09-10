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

## LLM Safety Boundaries

Features that call an LLM must:

- Require explicit provider configuration.
- Respect a maximum LLM call count.
- Respect a context budget before sending evidence to the model.
- Avoid sending full repositories by default.
- Keep tool execution inside application-controlled Java code.

## Reporting

After the repository is public, prefer a private GitHub security advisory when
available. Until then, contact the repository owner directly with:

- affected version or commit
- reproduction steps
- expected and actual behavior
- security impact
- any secret exposure risk
