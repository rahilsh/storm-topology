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

## Dependency injection in a bolt

Storm constructs bolts itself, so `PrintBolt` can't be created by a Guice
injector directly. Instead the injector is exposed as a process-wide singleton
and the bolt pulls its collaborators when it starts:

- `AppModule` declares the Guice bindings.
- `GuiceUtil` holds a single `Injector` in a `static` field.
- `PrintBolt.prepare()` resolves its dependencies from that injector.

```
AppModule (bindings)  ->  GuiceUtil (static Injector)  ->  PrintBolt.prepare()
```

> **Caveat — local vs. distributed mode.** Bolts are serialized and shipped to
> workers; the static `Injector` is **not** serialized — it is lazily recreated
> in each worker JVM the first time `GuiceUtil` is loaded. That is fine for the
> stateless bindings used here, but any binding that depends on runtime
> configuration passed through the topology `Config` will not see it. For
> production wiring, resolve dependencies in `prepare()` from the Storm
> `conf`/`TopologyContext`, or use a Storm `WorkerHook`.

## Build & Test

Make sure Maven runs on JDK 25, then:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)   # macOS
mvn clean test
```

> Note: if `java_home -v 25` resolves to an older JDK, the Homebrew JDK 25 is
> not registered with macOS. Point `JAVA_HOME` at it directly, e.g.
> `JAVA_HOME=/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home`.
