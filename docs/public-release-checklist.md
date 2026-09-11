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
- [x] Confirm No-LLM mode remains the default.
- [x] Confirm real LLM mode requires explicit opt-in.
- [x] Confirm `./mvnw test` passes.
- [x] Confirm open issues are understandable to an outside engineer.
- [x] Add repository description on GitHub.
- [x] Add CI for pull requests and pushes to `main`.
- [x] Add Dependabot configuration for Maven and GitHub Actions.
- [x] Enable Dependabot vulnerability alerts and security updates.
- [x] Add CodeQL workflow for Java scanning. It skips while the repo is private because GitHub code scanning is not enabled yet.
- [x] Disable blank issues and route security reports to private advisories.
- [x] Strengthen `.gitignore` for local env files and logs.
- [x] Fill POM license, developer, and SCM metadata.
- [x] Enable automatic branch deletion after merge.

Suggested GitHub description:

```text
Java-flavored learning project for evidence-driven agentic workflows with Spring AI, typed handoffs, deterministic routing, and cost guardrails.
```

## Nice To Have

- [ ] Add a short demo GIF or terminal screenshot.
- [ ] Add `docs/architecture.md`.
- [x] Add a license badge after license choice.
- [x] Add a CI workflow.

## GitHub Settings To Enable After Public

These are currently blocked while the repository is private on the current
GitHub plan.

- [ ] Enable branch protection on `main`.
- [ ] Require pull request reviews before merging to `main`.
- [ ] Require status checks to pass before merging, including CI.
- [ ] Require branches to be up to date before merging.
- [ ] Confirm secret scanning is enabled.
- [ ] Confirm CodeQL runs successfully after the first public push or pull request.

## Manual GitHub Steps

1. Review the repository one final time.
2. Add the chosen license.
3. Go to repository settings.
4. Change visibility from private to public.
5. Pin or share the README and article link.
