# Public Release Checklist

Use this before switching the GitHub repository from private to public.

## Repository Intent

Prooflane should be presented as:

```text
A Java-flavored learning project for boring, explicit agentic workflows.
```

It should not yet be presented as:

```text
A production PR-review tool.
A general-purpose autonomous software delivery agent.
A general-purpose autonomous agent framework.
```

## Required Before Public

- [x] Decide and add a license: MIT.
- [x] Keep README positioning modest and clear.
- [x] Confirm no local machine paths remain in public docs.
- [x] Confirm no API keys, tokens, `.env` files, or private logs are committed.
- [x] Keep `.env.example` placeholder-only.
- [x] Confirm fake mode remains the default.
- [x] Confirm real LLM mode requires explicit opt-in.
- [x] Confirm `./mvnw test` passes.
- [x] Confirm open issues are understandable to an outside engineer.
- [x] Add repository description on GitHub.

Suggested GitHub description:

```text
Java-flavored learning project for evidence-driven agentic workflows with Spring AI, typed handoffs, deterministic routing, and cost guardrails.
```

## Nice To Have

- [ ] Add a short demo GIF or terminal screenshot.
- [ ] Add `docs/architecture.md`.
- [x] Add a license badge after license choice.
- [ ] Add a CI workflow after the project is public.

## Manual GitHub Steps

1. Review the repository one final time.
2. Add the chosen license.
3. Go to repository settings.
4. Change visibility from private to public.
5. Pin or share the README and article link.
