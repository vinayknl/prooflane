# Prooflane: Learning Agentic Workflows The Boring Java Way

Agentic workflows are easier to understand when they are grounded in familiar
backend engineering ideas.

Not as a pile of buzzwords. Not as a demo where an agent magically does ten
things while the application hides what actually happened. The useful starting
point is something small enough to reason about, test, and break.

That is the purpose of Prooflane.

Prooflane is a Java and Spring AI learning project for evidence-driven agentic
workflows. The first version is intentionally boring:

```text
Gather evidence.
Ask a verifier for a typed judgment.
Route with Java code.
Return a receipt.
```

That is it.

And that simplicity is the point.

## The Problem Prooflane Explores

A lot of agent examples start with the exciting part:

```text
The model can use tools.
The model can make decisions.
The model can call APIs.
The model can continue a task.
```

Those are useful capabilities, but they can blur an important engineering
question:

```text
Who owns the workflow?
```

If the model owns both the reasoning and the process, it becomes harder to
answer basic software questions:

- What state is the workflow in?
- What evidence caused the next step?
- What happens when verification fails?
- Can a test prove that a failed check does not accidentally ship?
- How much can one run cost?
- What exactly was sent to the model?

Prooflane makes those questions concrete.

## The Core Idea

The design principle is:

```text
LLMs can reason over evidence.
Java should own workflow state, routing, budgets, and safety.
```

That means the model is not treated as a magical process manager. It is a
component inside an ordinary application boundary.

In the first workflow, Prooflane checks a local repository and decides whether
it looks ready.

The flow is:

```text
Engineer
-> Prooflane CLI
-> EvidenceService
-> RepoTools
-> VerifierAgent
-> VerificationResult
-> VerificationRouter
-> WorkflowRun
```

The verifier returns a typed result:

```java
record VerificationResult(
    VerificationStatus status,
    List<String> evidence,
    List<String> risks,
    String nextStep
) {}
```

The status is one of:

```text
PASS
FAIL
BLOCKED
```

Then Java routes it:

```text
PASS    -> DELIVER
FAIL    -> REPAIR
BLOCKED -> BLOCKED
```

The model can help judge evidence, but the application controls the route.

## What The First Version Checks

The current implementation is deliberately small. It gathers:

```text
git status --short
git diff --stat
./mvnw test or ./gradlew test
```

Fake mode is the default. In fake mode, the verifier is just Java logic:

```text
unknown test result -> BLOCKED
failing tests       -> FAIL
dirty working tree  -> FAIL
passing and clean   -> PASS
```

That may sound too simple, but it creates a useful baseline. Before adding a
real model, tool calling, GitHub state, or review comments, the project can test the
workflow itself.

This is the first lesson the repo is designed to teach:

```text
Agentic does not have to mean autonomous.
It can mean typed judgment inside controlled workflow code.
```

## Why Fake Mode Matters

Fake mode is not a toy afterthought. It is a design guardrail.

It lets the project test the orchestration without spending money or depending
on a model provider.

The tests can ask questions like:

- Does PASS route to delivery?
- Does FAIL route to repair?
- Does BLOCKED avoid delivery?
- Does a passing verifier trigger a delivery refresh?
- Does the workflow record its transitions?

Those tests should not require an API key.

That matters because a lot of agentic systems accidentally make their test
suite dependent on a live model. Then the tests become slow, flaky, expensive,
or too vague to trust.

Prooflane keeps that boundary explicit.

## Where Spring AI Fits

Spring AI enters at the verifier boundary.

Prooflane has a `VerifierAgent` interface. There are two implementations:

```text
FakeVerifierAgent
OpenAiVerifierAgent
```

The fake verifier uses deterministic Java rules.

The OpenAI verifier uses Spring AI's `ChatClient` to ask the model to interpret
the compact evidence packet and return a `VerificationResult`.

Even in real model mode, the workflow contract remains the same:

```text
EvidencePacket in.
VerificationResult out.
Java router decides the next state.
```

That is the second lesson:

```text
You can introduce an LLM behind an interface without giving it ownership of the system.
```

## Cost Guardrails From The Start

Cost behavior is visible from day one.

Prooflane starts with a few simple guardrails:

```text
fake mode by default
real LLM mode requires explicit opt-in
maximum LLM calls per run
evidence size cap
estimated input token budget
estimated output token budget
```

The first real-model path is intentionally constrained. It is not allowed to
loop forever. It should not receive full repositories or giant logs by default.
It should fail before calling the provider if the evidence packet is too large.

This is not sophisticated billing infrastructure. It is basic engineering
hygiene.

But for a learning project, that is enough to make the habit visible:

```text
Model calls are not free control flow.
They are resource-consuming operations that need budgets.
```

## What This Shape Makes Clear

The project maps agentic workflow concepts onto familiar backend ideas:

```text
Agent        -> service behind an interface
Handoff      -> DTO
Router       -> switch over an enum
Evidence     -> compact input object
Receipt      -> structured output
Guardrail    -> precondition before expensive or risky work
Eval         -> scenario test
Workflow run -> state object with transitions
```

That makes the learning curve much gentler.

Instead of starting with a multi-agent framework, Prooflane starts with things
most Java engineers already know.

## What It Does Not Do Yet

Prooflane is not production-ready, and it should not pretend otherwise.

It does not yet:

- read GitHub PR review state
- check remote CI
- detect merge conflicts
- inspect security-sensitive file paths
- persist workflow runs
- use Spring AI tool calling
- perform multi-agent fan-out
- mutate pull requests

Those are future learning steps.

The current value is smaller and clearer:

```text
It shows how to put an LLM-shaped judgment inside a deterministic workflow.
```

## The Bigger Lesson

The bigger lesson is that useful agentic systems do not have to start with
drama.

They can start with a boring loop:

```text
Collect evidence.
Make a typed judgment.
Route deterministically.
Record what happened.
Test the behavior.
```

That loop is not glamorous, but it is understandable.

And understandable is a good place to start.

Prooflane is a small attempt to learn agentic workflows through that lens:

```text
Reason with AI.
Progress with proof.
Keep the workflow boring enough to trust.
```

Repo: <add public GitHub URL after release>
