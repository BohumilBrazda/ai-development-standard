---
name: java-implementation
description: Implements Java application features, fixes defects, and refactors code using existing architecture, project conventions, security requirements, and maintainable design.
---

# Skill: Java Implementation

Version: 1.1.0

## Purpose

Implement correct, maintainable, secure, and testable Java code while preserving existing behavior and following the project's development standard.

## When to Use

- Implementing a new Java feature.
- Fixing a defect in application code.
- Refactoring existing Java classes.
- Changing service logic, domain models, interfaces, or configuration.
- Modifying Maven dependencies or build configuration as part of a requested change.

## Workflow

1. Read AGENTS.md and applicable project rules.
2. Use project analysis when the relevant architecture or implementation constraints are not yet understood.
3. Identify acceptance criteria and the smallest set of files required for the change.
4. Inspect existing implementations, callers, public APIs, and tests before creating new abstractions or modifying existing behavior.
5. Identify existing public methods, constructors, interfaces, and observable behavior that must remain compatible.
6. Design the change to fit the existing architecture and public contracts.
7. Implement the change using the project's configured Java version and conventions.
8. Validate external inputs at appropriate application boundaries.
9. Handle failures explicitly and avoid swallowing exceptions.
10. Add or update automated tests for changed behavior while preserving relevant regression tests.
11. Review the complete diff for correctness, compatibility, unnecessary complexity, security issues, and accidental unrelated changes.
12. Run the required tests and build checks using commands available in the current environment.
13. If a check fails, inspect the actual error, determine whether it is caused by the change or the environment, and resolve the issue where possible.
14. After making corrections, rerun the relevant checks and verify the final state.
15. Summarize the implementation and report verification results honestly.

## Implementation Rules

- Follow the Java version declared by the project.
- For the java-21-maven profile, use Java 21-compatible code.
- Prefer readable, cohesive classes and methods with clear responsibilities.
- Use meaningful names and explicit contracts.
- Preserve existing public APIs unless the requested change explicitly requires otherwise.
- Avoid unnecessary abstractions, speculative generalization, and premature optimization.
- Prefer constructor injection when dependency injection is used.
- Use immutable values and defensive copying where appropriate.
- Use try-with-resources for resources that require closing.
- Handle null values, invalid inputs, and exceptional conditions deliberately.
- Avoid broad exception handling that hides the original failure.
- Never hardcode credentials, tokens, private keys, or environment-specific secrets.
- Avoid logging sensitive information.
- Do not add dependencies without explaining why existing project capabilities are insufficient.
- Do not weaken compiler settings, tests, validation, or quality gates to make a change pass.
- Do not modify unrelated files.
- Do not claim code compiles or tests pass without actually running the corresponding checks.

## Existing Contracts and Behavioral Compatibility

- Inspect existing implementations, callers, tests, and domain validation before deciding how new behavior should work.
- Preserve existing public contracts and observable behavior unless the requirements explicitly authorize a change.
- Before modifying an existing class, identify its public methods and determine which must remain available after the change.
- When extending a class or service, add the new behavior without removing or renaming existing public methods unless explicitly required.
- Do not replace existing functionality with the new functionality when both are expected to coexist.
- Treat existing tests as regression safeguards. Keep relevant tests and extend them for the new behavior instead of replacing them wholesale.
- If a test must be removed or changed because the requirements intentionally change existing behavior, explain why and ensure the new contract is covered by tests.
- Treat compilation errors involving missing methods, constructors, types, or interfaces as potential contract regressions. Inspect the affected production code and tests before changing either side.
- Do not fix a compilation error by deleting a test or weakening an assertion when the test represents a valid existing requirement.
- Do not introduce new normalization, case-sensitivity, validation, null-handling, or error-handling rules without evidence that they are required.
- Treat existing tests and implementation conventions as evidence of the current contract, but verify that they are consistent with the requested requirements.
- When multiple reasonable behaviors are possible, identify the ambiguity before implementation if it materially affects compatibility or correctness.
- Do not justify a behavioral change solely by calling it a common practice or industry standard.
- For email addresses, inspect the existing validation and comparison rules. Do not introduce case-insensitive comparison or normalization unless supported by the existing contract or explicitly approved requirements.
- If a behavioral change is necessary, document its impact and add tests that explicitly verify the new behavior.

## Testing Requirements

- Add tests for new business behavior.
- Update tests when existing behavior changes.
- Preserve existing tests that protect behavior outside the scope of the requested change.
- Before completing an implementation, verify that tests still cover important pre-existing public behavior affected by or related to the change.
- Cover relevant success paths, boundary conditions, and failure paths.
- For new service methods, test both the expected result and relevant interactions with existing methods.
- For collection-returning methods, verify the required mutability, snapshot, ordering, and isolation semantics when applicable.
- Prefer deterministic unit tests without external services.
- Follow the project's existing test framework and naming conventions.
- Never remove a failing test merely to obtain a successful build.
- Ensure the production code and test code compile together before reporting the task as complete.
- When compilation or tests fail, inspect the actual failure output, correct defects within scope, and rerun the relevant checks.
- Do not report a test as passed based on source inspection or manual reasoning alone.

## Build and Verification Requirements

- Inspect the project instructions and build configuration to identify the required verification command.
- Prefer the command specified by the project or task, such as `mvn verify` for a Maven project.
- Determine which build commands are actually available in the current execution environment.
- Do not assume that Maven Wrapper is available merely because the project uses Maven.
- If `./mvnw`, `mvnw.cmd`, or another preferred command cannot run, record the actual error and check whether an appropriate alternative, such as `mvn verify` when Maven is installed, is available.
- Do not describe a command as an environment limitation without evidence from its actual output.
- If no suitable verification command is available, report that verification could not be completed and explain why.
- A successful compilation alone does not establish that all required tests and quality gates passed.
- After fixing a compilation or test failure, rerun the failed check and any other required checks affected by the change.
- Report the exact commands executed and their actual outcomes.
- Distinguish successful checks, failed checks, and checks that were not executed.

## Completion Criteria

The implementation is complete only when:

- The stated acceptance criteria are satisfied.
- Applicable project instructions and existing public contracts are preserved.
- Relevant existing functionality has not been accidentally removed or changed.
- Appropriate automated tests have been added or updated.
- The final production and test sources compile successfully.
- Required verification checks have been executed successfully, or any unresolved failure is explicitly reported.
- The final diff contains no unjustified or unrelated changes.
- The final report accurately describes the implementation, verification results, and remaining limitations.

If a required check fails, do not claim full completion. Explain the failure, its known or suspected cause, and what remains unresolved.

## Expected Output

Report:

1. What was implemented and why.
2. Files created or modified.
3. Important design decisions and compatibility considerations.
4. Tests and commands executed.
5. Actual compilation, test, and build results.
6. Any regressions discovered and how they were addressed.
7. Known limitations or remaining risks.