---
name: Bug report
about: Report a problem with an example or the build
title: ''
labels: bug
assignees: ''

---

**Describe the bug**
A clear and concise description of what the bug is.

**Which example / module**
e.g. `topology` (`Main`), `trident-drpc` (`WordCountTridentDRPC` / `LogAnalyserTrident`).

**To Reproduce**
The exact command you ran, e.g.:

```bash
mvn -pl trident-drpc exec:java -Dexec.mainClass=com.rsh.st.tridentdrpc.WordCountTridentDRPC
```

**Expected behavior**
A clear and concise description of what you expected to happen.

**Logs / stack trace**
Paste the relevant output (please include the full stack trace).

**Environment**
 - OS: [e.g. macOS 15]
 - JDK vendor & version (`java -version`): [e.g. Temurin 25]
 - Maven version (`mvn -version`): [e.g. 3.9.12]
 - Apache Storm version: [e.g. 3.0.0]

**Additional context**
Add any other context about the problem here.
