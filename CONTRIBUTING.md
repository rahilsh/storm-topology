# Contributing

Thanks for your interest in improving this project!

## Workflow

1. Create a feature branch from `main`.
2. Make your changes on the feature branch.
3. Open a pull request targeting `main`.

## Before you open a PR

This project targets **Java 25** (required by Apache Storm 3.x). Make sure Maven
runs on JDK 25, then:

```bash
# Auto-format sources (google-java-format + license headers)
mvn spotless:apply

# Build, run static analysis (SpotBugs) and tests
mvn verify
```

CI runs `mvn verify`, which fails on formatting violations, SpotBugs findings,
or failing tests, so running it locally first saves a round-trip.
