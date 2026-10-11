---
name: testing
description: Designs, implements, and executes automated tests for Java applications using the project's Maven configuration and test framework. Use when adding features, fixing defects, changing behavior, diagnosing test failures, or verifying release readiness.
---

# Skill: Testing

Version: 1.0.0

## Purpose

Verify application behavior through meaningful, deterministic automated tests and report evidence of the results.

## When to Use

- When implementing a new feature or fixing a defect.
- When existing behavior changes.
- When investigating a failing test or build.
- When reviewing test coverage for critical behavior.
- Before declaring a change complete or preparing a release.

## Workflow

1. Read AGENTS.md, applicable project rules, and the Maven configuration.
2. Identify the test framework, test naming conventions, existing test utilities, and available Maven profiles.
3. Inspect the implementation and define expected behavior from requirements and existing contracts.
4. Create or update tests before or alongside the implementation.
5. Cover relevant success cases, boundary conditions, invalid inputs, and failure handling.
6. Prefer unit tests for isolated business logic and integration tests when component interaction must be verified.
7. Keep tests deterministic, isolated, readable, and independent of execution order.
8. Run the narrowest relevant test set first.
9. Run the broader Maven verification command required by the project.
10. Investigate failures and distinguish product defects from test defects or environmental problems.
11. Re-run affected tests after corrections.
12. Report the commands executed and their actual results.

## Test Design Rules

- Follow the testing framework already configured in the project.
- For JUnit 5 projects, use established JUnit Jupiter conventions.
- Test observable behavior rather than private implementation details.
- Use descriptive test names that explain the scenario and expected result.
- Use assertions that clearly identify incorrect behavior.
- Avoid tests that pass without exercising the intended behavior.
- Do not depend on network access, system time, randomness, or shared mutable state unless explicitly required.
- Isolate filesystem, database, and external-service interactions appropriately.
- Never use production credentials or real customer data in tests.
- Do not weaken assertions or delete meaningful tests just to obtain a green build.
- Do not add test dependencies without checking the existing Maven configuration.

## Maven Verification

Use commands appropriate to the project:

- Run a targeted test using Maven's -Dtest option when applicable.
- Run mvn test for the normal unit-test lifecycle.
- Run mvn verify for the complete configured verification lifecycle.
- Inspect Surefire and Failsafe reports when results are unclear.

Do not assume every project has integration tests or configured quality plugins. Inspect pom.xml first.

### Execution Evidence and Environment Recovery

- Execute verification commands in the actual project root.
- Prefer the project's Maven Wrapper when it is present and configured.
- Otherwise use the Maven executable available in the development environment.
- If Maven cannot be found, report the exact command and error. Do not report the build as successful.
- Before concluding that Maven is unavailable, check the current working directory, the project's available wrapper files, and the configured execution environment.
- If the environment may have stale PATH settings, retry in a newly initialized terminal or another supported execution environment.
- Do not repeatedly retry an identical failing command without changing the relevant conditions.
- After resolving an environment issue, run the required verification command again.
- Report the exact command, execution outcome, and relevant test counts or build errors.
- Distinguish clearly between tests that were written, tests that were attempted, tests that actually ran, and tests that passed.
- A successful compilation alone does not prove that tests passed.
- A successful test run does not prove that the complete Maven verification lifecycle passed.
- If verification remains impossible, state that acceptance criteria requiring successful execution are still unmet.

## Failure Handling

When a test fails:

1. Capture the failing test name and relevant error message.
2. Identify the failing assertion or execution stage.
3. Determine whether the cause is application behavior, test setup, dependency configuration, or the environment.
4. Make the smallest justified correction.
5. Re-run the failing test and the relevant broader test suite.
6. Report unresolved failures explicitly.

## Completion Criteria

Testing is complete when relevant tests have been executed, their results are understood, and failures or limitations are documented.

If execution is impossible, report that the tests were not run.

## Expected Output

Provide:

1. Tests added or modified.
2. Behaviors and edge cases covered.
3. Exact commands executed.
4. Test counts, failures, errors, and skipped tests when available.
5. Unresolved failures or verification limitations.

Never report a test as passed merely because its source code was created.