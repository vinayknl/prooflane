# LinkedIn Post Draft

Prooflane is a small learning repo for agentic workflows.

It is a Java/Spring AI experiment, but deliberately not a flashy autonomous
agent demo.

The idea is much more boring, and that is the point:

```text
LLMs can reason over evidence.
Java should own workflow state, routing, budgets, and safety.
```

The first workflow checks whether a local repo is ready:

```text
gather evidence
-> verifier returns PASS / FAIL / BLOCKED
-> Java router decides DELIVER / REPAIR / BLOCKED
```

Fake mode is the default, so tests cost nothing. Real OpenAI mode is opt-in and
guarded by context and call budgets.

It is designed as a hands-on way to understand agentic workflows through
familiar backend engineering concepts: DTOs, services, routers, tests, receipts,
and explicit state.

The main lesson so far:

An agentic workflow does not need to start with a swarm of agents. It can start
with one typed handoff and one deterministic router.

Repo: <add public GitHub URL after release>

Article: <add Substack URL after publishing>
