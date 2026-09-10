# Phase 1 Roadmap

Phase 1 turns Prooflane from a tiny local demo into a credible private
open-source-style learning project.

## Goal

Build a minimal PR-readiness workflow that demonstrates Skippy-like principles:

```text
evidence -> verifier judgment -> typed result -> deterministic router -> receipt
```

## Scope

- Keep fake mode as the default.
- Add opt-in real OpenAI mode with clear cost guardrails.
- Add behavioral evals for route correctness.
- Add compact evidence packets and token-budget estimates.
- Add hardened contribution guidelines before public release.

## Out Of Scope

- Autonomous issue selection.
- GitHub PR mutation.
- Multi-agent fan-out.
- Persistent workflow state.
- Public release automation.

## Phase 1 Issues

1. Harden repository contribution guidelines and templates.
2. Add first-class workflow state objects.
3. Improve evidence collection and command configurability.
4. Strengthen cost guardrails and per-run budget reporting.
5. Add behavioral eval fixtures for PASS, FAIL, and BLOCKED routes.
6. Make real OpenAI verifier mode production-safe enough for local experiments.
7. Add a GitHub readiness evidence provider.
8. Document the end-to-end architecture for engineers.

