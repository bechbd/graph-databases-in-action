# GluttonApp

Companion application for ['Graph Databases in Action'][1] by Dave Bechberger & Josh Perryman

Application is a Java console application. 

## Compile, Run

Compile and run the application with the following commands 

### Compile 

```bash
mvn clean compile
```

### Compile & run 

```bash
mvn -q clean compile exec:java -Dexec.mainClass="com.gluttonapp.App"
```

## Tags and viewing different versions of the code

The development of this code 


### Show tags

Use `git tag` or `git tag -l` to display the available tags. All tags will start with `s6.x` indicating that they correspond to content of section 6.x within chapter 6 of the book ['Graph Databases in Action'][1]. 

The tags included are: 

 - `s6.1`: Setting up the project
 - `s6.2`: Add a database driver to the project
 - `s6.3`: Connect to the database 
 - `s6.4`: Retrieving data
 - `s6.5`: Adding/Modifying/Deleting Data
 - `s6.6`: Building out our GluttonApp Social Network

### Checkout a tag



[1]: https://www.manning.com/books/graph-databases-in-action