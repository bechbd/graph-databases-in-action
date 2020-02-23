# Graph Databases in Action  <img src='https://images.manning.com/360/480/resize/book/b/7825565-46a5-4846-b899-a0dfb64e54bb/Bechberger-GD-MEAP-HI.png' width=125 height=170 />
by Dave Bechberger, Josh Perryman

For each chapter, there are both scripts and code corresponding to the chapter. 

The `extra` folder includes extra content not used in the book but was used throughout the creation of the book which we thought might prove helpful to the reader. This includes: 

 - `data-generator` - a Java program for adding sample data to a graph
 
## Gremlin Console start scripts

The use of these scripts assumes a running Gremlin Server is available for the console. The script connects to the server based on the configuration in `conf/remote.yaml` and configures it for a `console` connection. The script then executes Gremlin to leave the graph in the desired state before returning the Console to interactive mode.

For chapter 8 and 9, due to the amount of data needed we have changed from using scripts to create our graph to using data preloaded from json files.  We refer to these as the `io` scripts. These `io` scripts, (e.g. `8.1-restaurant-review-network-io.groovy` and `9.1-restaurant-review-network-io.groovy`) require the following: 

 1. Access to the `groovy` files in the `<chapter number>/scripts` directory. 
 2. Change the configured `full_path_and_filename` variable with the full path and filename of the JSON files which are located in the same directory.
 3. Save the updated script
