# storm-topology

Runnable, self-contained [Apache Storm](https://storm.apache.org/) examples that
demonstrate core streaming, Trident, and DRPC patterns on Storm 3.x. Every
example runs on an in-process `LocalCluster`, so you can explore Storm without
deploying a real cluster.

## Requirements

- **Java 25** (required by Apache Storm 3.0.0)
- Maven 3.9+

The build fails fast with a clear message (via `maven-enforcer-plugin`) if a
lower JDK is used.

## What you'll learn

| Concept | Where |
| --- | --- |
| Spouts, bolts & stream groupings | `topology` |
| Dependency injection inside a bolt (Guice) | `topology` |
| Trident high-level API (aggregation, persistent state) | `trident-drpc` |
| Distributed RPC (DRPC) queries | `trident-drpc` |
| Writing custom Trident functions | `trident-drpc` |

## Modules

### `topology` — spouts, bolts & Guice DI

A plain Storm topology that generates numbers, squares them, and collects the
results.

```
GenerateNumberSpout  ->  SquareBolt  ->  PrintBolt
   (emits 2,4,6…)        (n -> n*n)      (logs results)
```

- **`GenerateNumberSpout`** emits an increasing stream of even numbers.
- **`SquareBolt`** reads the `numbers` field and emits `(numbers, numbersquare)`.
- **`PrintBolt`** collects the pairs into a sorted map and logs them. It is wired
  with [Guice](https://github.com/google/inject) to demonstrate dependency
  injection inside a bolt.

Run it (spins up a local cluster for ~10s, then shuts down):

```bash
mvn -pl topology exec:java -Dexec.mainClass=com.rsh.st.topology.Main
```

Results are logged via SLF4J, e.g.:

```
INFO  c.r.s.t.bolt.PrintBolt - Result
INFO  c.r.s.t.bolt.PrintBolt - 2 : 4
INFO  c.r.s.t.bolt.PrintBolt - 4 : 16
INFO  c.r.s.t.bolt.PrintBolt - 6 : 36
...
```

### `trident-drpc` — Trident & DRPC

Higher-level [Trident](https://storm.apache.org/releases/current/Trident-tutorial.html)
topologies queried over DRPC.

#### `WordCountTridentDRPC`

Counts words from a DRPC argument string using Trident's built-in `Split`,
`Count`, and `Sum`.

```bash
mvn -pl trident-drpc exec:java \
  -Dexec.mainClass=com.rsh.st.tridentdrpc.WordCountTridentDRPC
```

Expected output:

```
countNoOfOccurrence: [["man",1],["cat",1],["dog",1]]
totalWords: [[3]]
```

#### `LogAnalyserTrident`

Aggregates call records into persistent Trident state (`MemoryMapState`) and
exposes two DRPC queries (`call_count`, `multiple_call_count`). The custom
functions `FormatCall` and `CSVSplit` show how to write your own Trident
operations.

```bash
mvn -pl trident-drpc exec:java \
  -Dexec.mainClass=com.rsh.st.tridentdrpc.LogAnalyserTrident
```

## Build & Test

Make sure Maven runs on JDK 25, then:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)   # macOS
mvn clean test
```

> Note: if `java_home -v 25` resolves to an older JDK, the Homebrew JDK 25 is
> not registered with macOS. Point `JAVA_HOME` at it directly, e.g.
> `JAVA_HOME=/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home`.
