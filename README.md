# Graph Databases in Action

by Dave Bechberger, Josh Perryman

For each chapter, there are both scripts and code corresponding to the chapter. 

The `erratica` folder includes scripts, code and tools for general use. This includes: 

 - `data-generator` - a Java program for adding sample data to a graph
 - `console-scripts` - scripts for use when starting Gremlin Console
 
## Gremlin Console start scripts

The use of these scripts assumes a running Gremlin Server is avaialble for the console. The script connects to the server based on the configuration in `conf/remote.yaml` and configures it for a `console` connection. The script then executes Gremlin to leave the graph in the desired state before returning the Console to interactive mode.

For the Gremlin Console, it is recommended to create a `scripts` folder and place the contents of the `console-scripts` in the newly created scripts folder.  

```bash
$ ls -w1 scripts/
complex-social-network-gremlin.groovy
complex-social-network-io.groovy
no-data.groovy
simple-social-network-gremlin.groovy
simple-social-network-io.groovy
```

The `io` scripts, (e.g. `simple-social-network-io.groovy` and `complex-social-network-io.groovy`) require the following: 

 1. Access to the JSON files in the `erratics/data` directory. Save these to a location on your 
 2. Propertly configured `full_path_and_filename` variable with the full path and filename of the JSON files. 


Then start up the console with one of the following commands: 

#### For an empty graph

```base
bin/gremlin.sh -i scripts/no-data.groovy 
```

The result should look like:  

```bash
$ bin/gremlin.sh -i scripts/no-data.groovy 

         \,,,/
         (o o)
-----oOOo-(3)-oOOo-----
plugin activated: tinkerpop.server
plugin activated: tinkerpop.utilities
plugin activated: tinkerpop.tinkergraph
gremlin> g
==>graphtraversalsource[tinkergraph[vertices:0 edges:0], standard]
gremlin> 

```
 
Note that by using the JSON files to load the graphs then the graph will have the vertex ID values specifically set, instead of being generated. Using this approach to create the graph will help to ensure that the traversals against the graph exactly match the results displayed in the book. 
 
#### For the simple graph from the JSON file

This requires that the `full_path_and_filename` be set to the full path to the file: `simple_social_network.json`.

```base
bin/gremlin.sh -i scripts/simple-social-network-io.groovy
```

The result should look like:  

```base
$ bin/gremlin.sh -i scripts/small-graph.groovy 

         \,,,/
         (o o)
-----oOOo-(3)-oOOo-----
plugin activated: tinkerpop.server
plugin activated: tinkerpop.utilities
plugin activated: tinkerpop.tinkergraph
gremlin> g
==>graphtraversalsource[tinkergraph[vertices:4 edges:5], standard]
gremlin> 

```

### For the complex graph from the JSON file

This requires that the `full_path_and_filename` be set to the full path to the file: `complex_social_network.json`.

```base
bin/gremlin.sh -i scripts/complex-social-network-io.groovy
```

The result should look like:  

```bash
$ bin/gremlin.sh -i scripts/complex-social-network-io.groovy

         \,,,/
         (o o)
-----oOOo-(3)-oOOo-----
plugin activated: tinkerpop.server
plugin activated: tinkerpop.utilities
plugin activated: tinkerpop.tinkergraph
gremlin> g
==>graphtraversalsource[tinkergraph[vertices:8 edges:12], standard]
gremlin> 
```

