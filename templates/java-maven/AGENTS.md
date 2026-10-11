# AI Development Contract

## 1. Project baseline

- Language: Java 21.
- Build system: Maven.
- Use Maven Wrapper (`mvnw` / `mvnw.cmd`) when available.
- Tests: JUnit 5 unless the project specifies otherwise.
- Encoding: UTF-8.
- Follow the installed AI Development Standard version recorded in
  `.ai-standard/manifest.json`.

## 2. Required instructions

Before starting work, read:

1. This file.
2. `project-rules.md`.
3. The installed global rules and applicable security policies.
4. The relevant installed skills under `.devoxxgenie/skills/`.
5. Relevant architecture documentation and existing code conventions.

If a required file is missing or cannot be read, report this explicitly.
Do not claim that a skill or rule was loaded merely because it is listed
in the manifest.

## 3. Mandatory workflow

For every non-trivial task:

1. Analyze the user request.
2. Inspect relevant source files, tests and build configuration.
3. Identify assumptions, dependencies and risks.
4. Prepare a plan with acceptance criteria and affected files.
5. Obtain approval when required by the project's change policy.
6. Implement only the authorized scope.
7. Run relevant tests and quality checks.
8. Review the resulting diff.
9. Report the actual results.

For trivial changes, keep the workflow proportionate, but never bypass
applicable security or approval requirements.

## 4. Implementation rules

- Preserve the existing architecture and coding conventions.
- Prefer small, focused and maintainable changes.
- Avoid speculative abstractions and unnecessary refactoring.
- Keep business logic independent of infrastructure where appropriate.
- Prefer explicit dependencies and constructor injection when applicable.
- Handle errors explicitly; never silently swallow exceptions.
- Avoid unnecessary mutable shared state.
- Do not introduce dependencies without the required approval.
- Do not change the Java version, framework versions or build configuration
  without the required approval.
- Do not change public APIs without evaluating compatibility and obtaining
  the required approval.
- Do not modify unrelated files.

## 5. Testing and quality gates

Before declaring a task complete:

- Add or update tests for changed behavior.
- Include relevant negative and boundary cases.
- Run the applicable Maven checks.
- Review all changed files.
- Verify that acceptance criteria are satisfied.

Preferred verification command:

    ./mvnw -B verify

On Windows, use:

    .\mvnw.cmd -B verify

If the Maven Wrapper is unavailable, report that fact and use the project's
approved Maven alternative.

Never claim that a build, test or quality check passed unless its result
was actually observed. Distinguish passed, failed, skipped and not executed.

## 6. Security and restricted actions

- Never expose credentials, tokens, private keys or other secrets.
- Do not transmit source code or sensitive project data to external services
  without authorization.
- Do not execute destructive commands without explicit approval.
- Do not commit, push, publish, release or deploy unless explicitly authorized.
- Do not disable tests or quality checks merely to obtain a successful build.
- Follow `rules/CHANGE_POLICY.md` and `rules/SECURITY.md` when installed.

## 7. Change scope and approvals

Classify changes according to the project's change policy.

If the scope expands, a significant assumption changes, or a new high-risk
action becomes necessary, stop and request the required approval.

An approved plan does not automatically authorize destructive actions,
deployment or publication.

## 8. Final report

Every completed task must include:

1. Summary of the implemented change.
2. Files created, modified or removed.
3. Important design decisions.
4. Commands executed and their actual results.
5. Acceptance criteria status.
6. Remaining risks, limitations and unexecuted checks.