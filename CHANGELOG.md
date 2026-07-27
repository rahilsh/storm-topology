# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Unit tests for `SquareBolt`, `ExternalService`, `CSVSplit`, and `FormatCall`.
- `maven-enforcer-plugin` to require Java 25 and Maven 3.9+ with an actionable
  error message.
- `exec-maven-plugin` so each example can be run with `mvn exec:java`.
- Learning-guide README with per-example flow diagrams, run commands, and
  expected output, plus a section on the Guice dependency-injection pattern.
- This changelog.

### Changed
- Upgraded **Apache Storm 2.8.x → 3.0.0**, which requires **Java 25** (was
  Java 21).
- CI now runs `mvn verify` (which executes the tests) instead of `mvn package`.
- Renamed package `com.rsh.st.trident_drpc` → `com.rsh.st.tridentdrpc` to match
  the module `groupId` and Java naming conventions.
- Consolidated `java.version` into the parent POM as the single source of truth.

### Fixed
- Resource leaks in the Trident examples: `LocalDRPC` and `LocalCluster` are now
  shut down via try-with-resources even when execution throws.
