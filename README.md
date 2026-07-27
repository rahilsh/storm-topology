# storm-topology

A collection of example [Apache Storm](http://storm.apache.org/) topologies demonstrating core streaming, Trident, and DRPC patterns.

## Requirements

- **Java 25** (required by Apache Storm 3.0.0)
- Maven 3.9+

## Modules

### `topology`
A basic Storm topology wired with [Guice](https://github.com/google/inject) for dependency injection.

- `GenerateNumberSpout` — emits an increasing stream of even numbers
- `SquareBolt` — squares each number
- `PrintBolt` — logs the result (Guice-injected)

Flow: `number_spout -> square_bolt -> print_bolt`, run on a `LocalCluster`.

### `trident-drpc`
Higher-level [Trident](https://storm.apache.org/releases/current/Trident-tutorial.html) topologies with DRPC queries.

- `WordCountTridentDRPC` — word counting exposed over DRPC streams
- `LogAnalyserTrident` — aggregates call records into persistent state, queried via DRPC

## Build & Test

```bash
mvn clean test
```

## Run

Each example exposes a `main` method that submits the topology to a `LocalCluster`.
Run the class directly from your IDE, or package and run it:

- `com.rsh.st.topology.Main`
- `com.rsh.st.trident_drpc.WordCountTridentDRPC`
- `com.rsh.st.trident_drpc.LogAnalyserTrident`
