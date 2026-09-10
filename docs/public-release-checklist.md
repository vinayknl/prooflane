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
A full Skippy replacement.
A general-purpose autonomous agent framework.
```

## Required Before Public

- [ ] Decide and add a license.
- [ ] Keep README positioning modest and clear.
- [ ] Confirm no local machine paths remain in public docs.
- [ ] Confirm no API keys, tokens, `.env` files, or private logs are committed.
- [ ] Keep `.env.example` placeholder-only.
- [ ] Confirm fake mode remains the default.
- [ ] Confirm real LLM mode requires explicit opt-in.
- [ ] Confirm `./mvnw test` passes.
- [ ] Confirm open issues are understandable to an outside engineer.
- [ ] Add repository description on GitHub.

Suggested GitHub description:

```text
Java-flavored learning project for evidence-driven agentic workflows with Spring AI, typed handoffs, deterministic routing, and cost guardrails.
```

## Nice To Have

- [ ] Add a short demo GIF or terminal screenshot.
- [ ] Add `docs/architecture.md`.
- [ ] Add a license badge after license choice.
- [ ] Add a CI workflow after the project is public.

## Manual GitHub Steps

1. Review the repository one final time.
2. Add the chosen license.
3. Go to repository settings.
4. Change visibility from private to public.
5. Pin or share the README and article link.
