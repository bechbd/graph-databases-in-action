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

This code is tagged so that it can be checked out to a known working state at the end of each section of chapter 6. 

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

To checkout a specific tag, at a commmand line enter the git command: `git checkout s6.#` where `#` is replaced with the section number. The code state for that tag will correpond to the working state of the code at the end of that section number. 

Example output looks like the following: 

```bash
$ git checkout c6.1
Note: checking out 'c6.1'.

You are in 'detached HEAD' state. You can look around, make experimental
changes and commit them, and you can discard any commits you make in this
state without impacting any branches by performing another checkout.

If you want to create a new branch to retain commits you create, you may
do so (now or later) by using -b with the checkout command again. Example:

  git checkout -b <new-branch-name>

HEAD is now at 8543000 Setting up the project
```

### Return to most recent state of the repository

To return to the most current state of the repository, do a checkout of the `master` branch with: 

```bash
$ git checkout master
Previous HEAD position was 8543000 Setting up the project
Switched to branch 'master'
Your branch is up to date with 'origin/master'.
```



[1]: https://www.manning.com/books/graph-databases-in-action