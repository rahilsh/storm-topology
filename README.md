# storm-topology

[![Java CI with Maven](https://github.com/rahilsh/storm-topology/actions/workflows/maven.yml/badge.svg)](https://github.com/rahilsh/storm-topology/actions/workflows/maven.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Java 25](https://img.shields.io/badge/Java-25-orange.svg)](https://adoptium.net/)
[![Apache Storm 3.0.0](https://img.shields.io/badge/Apache%20Storm-3.0.0-brightgreen.svg)](https://storm.apache.org/)

Runnable, self-contained [Apache Storm](https://storm.apache.org/) examples that
demonstrate core streaming, Trident, and DRPC patterns — plus the new features
in **Storm 3.0** (windowing, Zstd compression, jitter-aware grouping). Every
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
| Windowed aggregation (tumbling windows) | `storm3-features` |
| Storm 3.0 Zstd compression & dynamic batching | `storm3-features` |
| Storm 3.0 `JitterAwareStreamGrouping` | `storm3-features` |

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

### `storm3-features` — new in Apache Storm 3.0

Examples of capabilities introduced in Storm 3.0.

#### Windowed aggregation

`TumblingSumBolt` (a `BaseWindowedBolt`) sums the numbers in each fixed-size
window. `WindowedSumTopology` wires `SequentialNumberSpout -> windowed-sum` and
also enables Zstd tuple compression via `Storm3Configs`.

```bash
mvn -pl storm3-features exec:java \
  -Dexec.mainClass=com.rsh.st.storm3.windowing.WindowedSumTopology
```

#### JitterAwareStreamGrouping

`JitterAwareGroupingTopology` routes tuples with
`customGrouping(..., new JitterAwareStreamGrouping())` — the Storm 3.0 grouping
that steers work away from high-jitter tasks — instead of `shuffleGrouping`.

```bash
mvn -pl storm3-features exec:java \
  -Dexec.mainClass=com.rsh.st.storm3.grouping.JitterAwareGroupingTopology
```

#### Transport config features

`Storm3Configs` shows how to enable the opt-in Storm 3.0 transport features via
the topology `Config`: **Zstd tuple compression** and **AIMD dynamic producer
batching**. See `Storm3ConfigsTest` for the exact keys.

## Dependency injection in a bolt

Storm constructs bolts itself, so `PrintBolt` can't be created by a Guice
injector directly. This module uses Storm's recommended
[worker-hook](https://storm.apache.org/releases/3.0.0/Hooks.html) mechanism to
share a single injector across every task in a worker:

- `AppModule` declares the Guice bindings.
- `GuiceWorkerHook` builds the `Injector` once per worker in `start(...)` and
  publishes it with `WorkerUserContext.setResource("guice.injector", injector)`.
- `Main` registers the hook via `TopologyBuilder.addWorkerHook(...)`.
- `PrintBolt.prepare()` reads it back with
  `context.getResource("guice.injector")`.

```
AppModule (bindings)
   -> GuiceWorkerHook.start()  --setResource-->  worker
        -> PrintBolt.prepare()  --getResource-->  Injector
```

Per the Storm docs, worker-level `userResources` are "shared across executors,
tasks, worker hooks and task hooks" and "can only be written by worker hooks" —
making this the sanctioned way to publish an application-level resource such as a
DI injector. Unlike a static singleton, the hook is serialized with the
topology, runs at a defined lifecycle point with access to the topology `Config`,
and keeps no global static state.

## Build & Test

Make sure Maven runs on JDK 25, then:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)   # macOS

mvn clean test     # fast unit tests
mvn clean verify   # + integration tests, formatting, SpotBugs, 85% coverage gate
```

`mvn verify` runs the full pipeline: `spotless:check` (formatting + license
headers), SpotBugs static analysis, unit tests, end-to-end integration tests
(`*IT`, which run the example topologies on a `LocalCluster`), and a JaCoCo
line-coverage gate of 85%.

> Note: if `java_home -v 25` resolves to an older JDK, the Homebrew JDK 25 is
> not registered with macOS. Point `JAVA_HOME` at it directly, e.g.
> `JAVA_HOME=/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home`.
