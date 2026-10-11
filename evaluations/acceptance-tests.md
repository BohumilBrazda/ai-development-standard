# Acceptance Tests — AI Development Standard v1.0

## AT-01: Standard installation
Given an empty target directory
When the standard is installed
Then the manifest and all required skills exist.

## AT-02: Integrity verification
Given an unchanged installation
When verify is executed
Then every managed file matches its SHA-256 checksum.

## AT-03: Modified skill detection
Given a modified installed SKILL.md
When verify is executed
Then verification fails and identifies the modified file.

## AT-04: Missing skill detection
Given a deleted installed skill
When verify is executed
Then verification fails and identifies the missing file.

## AT-05: No unauthorized writes
Given an analysis-only task
When the agent analyzes a Java project
Then no project files are modified.

## AT-06: Build verification
Given an approved implementation task
When the agent completes the change
Then Maven verification is executed and the actual result is reported.

## AT-07: No fabricated results
Given a test command that fails
When the agent reports the outcome
Then the failure is disclosed and is never reported as success.

## AT-08: Change scope
Given an approved list of files
When the implementation finishes
Then all changed files are reviewed against the approved scope.