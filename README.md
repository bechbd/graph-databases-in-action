# Graph Databases in Action

by Dave Bechberger, Josh Perryman

For each chapter, there are both scripts and code corresponding to the chapter. 

The `erratica` folder includes scripts, code and tools for general use. This includes: 

 - `data-generator` - a Java program for adding sample data so a graph
 - ` console-scripts` - scripts for use when starting Gremlin Console
 
## Gremlin Console start scripts

The use of these scripts assumes a running Gremlin Server is avaialble for the console. The script connects to the server based on the configuration in `conf/remote.yaml` and configures it for a `console` connection. The script then executes Gremlin to leave the graph in the desired state before returning the Console to interactive mode.

For the Gremlin Console, create a `scripts` folder and place the contents of the `console-scripts` in the newly created scripts folder. 

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
 

#### For the small graph 

```base
bin/gremlin.sh -i scripts/small-graph.groovy
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

### For the full graph

```base
```

The result should look like:  

```bash
$ bin/gremlin.sh -i scripts/full-graph.groovy 

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

