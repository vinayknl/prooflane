# Security Policy

Prooflane is early-stage and private-first. Please report security issues
privately until the repository is public and a formal disclosure channel exists.

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

For now, open a private issue or contact the repository owner directly with:

- affected version or commit
- reproduction steps
- expected and actual behavior
- security impact
- any secret exposure risk

