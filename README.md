# simple-storm-topology

Collection of useful [Apache Storm](http://storm.apache.org/) topologies

## How to run topology using rest API

1. LinearDRPCTopologyBuilder - but it supports linear topologies only
2. evaluate - Need to check if trident supports rest API
3. Need to explore coordinated bolts - How to use CoordinatedBolt directly - http://useof.org/java-open-source/org.apache.storm.coordination.CoordinatedBolt
4. KeyedFairBolt for weaving the processing of multiple requests at the same time
5. Need to check if trident supports rest API, Trident + DRPC - https://www.tutorialspoint.com/apache_storm/apache_storm_trident.htm
