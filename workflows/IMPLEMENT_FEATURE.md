# AI Development Standard — Feature Implementation Workflow

## Task

Implement the following feature, fix, or change:

[DESCRIBE THE REQUIRED CHANGE HERE]

## 1. Read Project Instructions and Skills

Before modifying any files:

1. Identify the actual project root.
2. Read `AGENTS.md` and all applicable project instructions.
3. Read applicable rules, including `rules/GLOBAL_RULES.md`, `rules/SECURITY.md`, and `rules/CHANGE_POLICY.md` when present.
4. Read applicable profile instructions, including Java and Maven conventions.
5. Read the relevant skills:
    - `project-analysis`
    - `java-implementation`
    - `testing`
    - `code-review`
6. Inspect `pom.xml`, relevant source files, existing tests, and related configuration.
7. Identify the Java version, Maven configuration, test framework, and available verification commands.

If an applicable instruction or skill cannot be read, report this limitation explicitly.

Do not assume that a file is empty, missing, or irrelevant without checking it.

## 2. Analyze the Existing Implementation

Before making changes:

1. Identify the classes, methods, interfaces, tests, and configuration affected by the request.
2. Trace relevant dependencies and callers.
3. Determine existing behavioral contracts and validation rules.
4. Identify backward-compatibility, security, and data-integrity risks.
5. Separate verified facts from assumptions.
6. Identify ambiguities that materially affect the implementation.

Preserve existing public contracts and observable behavior unless the requirements explicitly authorize a change.

Do not introduce new normalization, case-sensitivity, null-handling, validation, or exception-handling rules merely because they appear to be common practice.

If a material ambiguity cannot be resolved from the code, project instructions, or requirements, explain it before making an incompatible design decision.

For small, unambiguous changes, proceed without unnecessary questions.

## 3. Implement the Change

Follow the `java-implementation` skill.

1. Implement the smallest change that satisfies the requirements.
2. Follow the existing architecture, naming conventions, and Java version.
3. Preserve unrelated functionality and public contracts.
4. Handle invalid inputs and failures consistently with the existing design.
5. Avoid unnecessary abstractions and dependencies.
6. Never hardcode credentials or expose sensitive information.
7. Do not weaken security controls, tests, compiler settings, or quality gates.
8. Do not modify unrelated files.
9. Add or update automated tests as part of the implementation.

Do not claim that code compiles or works without appropriate verification.

## 4. Add and Execute Tests

Follow the `testing` skill.

1. Inspect the existing test framework and test conventions.
2. Add or update tests for the requested behavior.
3. Cover successful scenarios, missing data, boundary conditions, invalid inputs, and relevant failure cases.
4. Ensure that tests verify the intended contract rather than merely reproducing implementation assumptions.
5. Run the narrowest relevant test set first when appropriate.
6. Run `mvn verify` from the actual project root.

Prefer the project's Maven Wrapper when it exists and is configured. Otherwise, use the available Maven executable.

### Maven Execution Failure

If Maven is not recognized:

1. Report the exact command and error.
2. Check the working directory and available Maven Wrapper files.
3. Check whether the execution environment has the expected `PATH` configuration.
4. Retry only after correcting or changing the relevant execution conditions.
5. If Maven remains unavailable, report verification as incomplete.

Do not claim success merely because the code looks correct or the tests were created.

Clearly distinguish between tests written, tests attempted, tests executed, tests passed, and checks not performed.

A successful compilation alone does not prove that tests passed. A successful test run does not prove that the complete Maven verification lifecycle passed.

## 5. Perform Code Review

Follow the `code-review` skill after implementation.

Inspect the actual Git diff and review:

- Functional correctness and edge cases.
- Compatibility with existing contracts.
- Security and data-integrity risks.
- Input validation and error handling.
- Unintended changes to public APIs or observable behavior.
- Test quality and missing scenarios.
- Unrelated modifications.
- Actual build and test evidence.

Report actionable findings with severity, location, impact, and recommended correction.

Correct identified defects and rerun relevant tests and verification commands.

If Git access or command execution is unavailable, state which checks could not be performed.

## 6. Verify Acceptance Criteria

Before declaring the task complete, confirm that:

- The requested behavior is implemented.
- Applicable project instructions have been followed.
- Existing contracts have been preserved or explicitly changed as required.
- Relevant automated tests have been added or updated.
- Required verification commands have actually run, or their failure is documented.
- The final diff contains no unintended changes.
- Identified defects have been corrected or explicitly reported.
- Remaining risks and limitations are documented.

Do not mark an acceptance criterion as satisfied without supporting evidence.

## 7. Final Report

Return a concise report with the following sections.

### Summary

Describe the implemented change and its purpose.

### Files Changed

List each created, modified, or deleted file and explain why it changed.

### Design Decisions

Explain important behavioral decisions and how they follow existing contracts and project rules.

### Tests and Verification

Include exact commands executed, actual outcomes, test counts when available, and checks that could not be performed.

### Code Review

List actionable findings, corrections, and unresolved issues.

If no actionable findings remain, state the scope of the review and any checks that were not performed.

### Acceptance Criteria

Summarize which requirements are verified, which are incomplete, and the evidence supporting each status.

### Remaining Risks

List unresolved assumptions, limitations, and follow-up work.

## Completion Rule

Be factual and concise. Never invent file contents, command output, test results, or successful verification.

The task is complete only when implementation, testing, and review have been performed to the extent possible and all limitations are explicitly reported.