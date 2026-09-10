## Summary

What changed and why?

## Workflow Impact

- [ ] No LLM calls added or changed
- [ ] LLM calls added or changed, with guardrails described below
- [ ] Routing or terminal-state behavior changed
- [ ] Evidence collection changed

## Cost And Safety

Describe any token, cost, network, command-execution, or secret-handling impact.

## Public Contribution Checklist

- [ ] I did not commit API keys, tokens, `.env` files, private logs, or private repository content
- [ ] Tests and evals use fake agents or deterministic fixtures by default
- [ ] Any real LLM path is explicit, bounded, and documented
- [ ] Any new command execution is application-controlled and described

## Verification

Paste the commands run and the result.

```text
./mvnw test
```

## Notes

Known limits, follow-up work, or skipped checks.
