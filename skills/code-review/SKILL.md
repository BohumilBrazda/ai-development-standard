---
name: code-review
description: Reviews Java code changes for correctness, security vulnerabilities, maintainability, regressions, and compliance with project rules. Use after implementation, before merging changes, or when reviewing a pull request or code diff.
---

# Skill: Code Review

Version: 1.0.0

## Purpose

Identify actionable defects and risks in a proposed change before it is accepted or merged.

## When to Use

- After implementing a feature or fixing a defect.
- Before merging a branch or pull request.
- When reviewing a diff produced by an AI coding agent.
- When changing authentication, authorization, data handling, dependencies, or configuration.
- When checking compliance with project quality gates.

## Workflow

1. Read AGENTS.md and applicable project rules, especially rules/SECURITY.md and rules/CHANGE_POLICY.md when present.
2. Inspect the complete diff and identify the intent of the change.
3. Read the surrounding implementation and relevant callers, tests, interfaces, and configuration.
4. Check functional correctness, edge cases, error handling, and backward compatibility.
5. Inspect input validation, authorization, data exposure, secrets handling, logging, and dependency risks.
6. Check concurrency, resource lifecycle, and state management where relevant.
7. Evaluate readability, cohesion, duplication, and unnecessary complexity.
8. Inspect test quality and determine whether important behaviors are untested.
9. Run relevant checks when execution is available and appropriate.
10. Report actionable findings with severity, file location, reasoning, and a suggested correction.
11. Separate confirmed defects from possible risks and questions.
12. If no findings are identified, state the scope of the review and any checks that were not performed.

## Review Rules

- Review the actual diff, not only the author's summary.
- Prioritize correctness, security, data integrity, and regressions over stylistic preferences.
- Do not report speculative problems as confirmed defects.
- Do not flag a style preference as a defect unless it violates an explicit project rule.
- Verify findings against surrounding code before reporting them.
- Never assume passing tests prove the absence of defects.
- Never assume a security-sensitive operation is safe without inspecting its relevant context.
- Do not make unrelated modifications during a review.
- Do not expose secrets in review findings or logs.
- Do not approve a change solely because an AI implementation or testing skill reports success.

### Contract and Verification Review

- Compare behavioral changes against the existing implementation, domain validation, callers, and tests.
- Specifically inspect changes to case sensitivity, normalization, null handling, validation, exception behavior, and public method contracts.
- Flag an unexplained behavioral change even when the new behavior appears reasonable in isolation.
- Verify that new tests assert the intended contract rather than merely reproducing the implementation's assumptions.
- Check whether the reported test and build results are supported by actual execution evidence.
- Treat an unexecuted test suite as unverified, not as passed.
- Do not approve a change solely because the implementation summary claims that acceptance criteria are satisfied.

## Severity Classification

- Critical: A severe security or data-integrity issue with substantial immediate impact.
- High: A major defect, exploitable weakness, or serious regression.
- Medium: A meaningful functional defect, reliability issue, or important missing validation.
- Low: A limited-impact defect or maintainability problem with a concrete consequence.
- Informational: A non-blocking observation or improvement suggestion.

Assign severity based on evidence and plausible impact, not on the number of findings.

## Finding Format

For each finding, provide:

- Severity.
- File and relevant line or code location.
- Description of the problem.
- Concrete scenario in which it matters.
- Recommended correction.

Avoid findings without a clear explanation of their impact.

## Completion Criteria

The review is complete when the diff has been inspected, material risks have been assessed, and findings are documented.

If the repository, execution environment, or relevant context is unavailable, explicitly state the limitation.

## Expected Output

Provide:

1. Findings ordered by severity.
2. Relevant file locations and evidence.
3. Recommended corrections.
4. Checks performed and their results.
5. Remaining risks or limitations.

If no findings are identified, explicitly state that no actionable findings were found within the reviewed scope.

Do not claim that the change is guaranteed defect-free.