package com.gluttonapp;

import java.util.Scanner;
// TODO: s6.3
//import org.apache.tinkerpop.gremlin.driver.Cluster;
//import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
//import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
//import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
// TODO: s6.4
//import java.util.List;
// TODO: s6.5
//import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
//import org.apache.tinkerpop.gremlin.structure.Edge;
//import org.apache.tinkerpop.gremlin.structure.Vertex;
// TODO: s6.6
//import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.both;
//import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.has;
//import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.out;
//import org.apache.commons.lang3.StringUtils;

public class App {

    public static void main( String[] args ) {
// TODO: s6.3
//        Cluster cluster = connectToDatabase();
//        System.out.println("Using cluster connection: " + cluster.toString());
//        GraphTraversalSource g = getGraphTraversalSource(cluster);
//        System.out.println("Using traversal source: " + g.toString());

// TODO: s6.4
//        displayMenu(g);
        displayMenu();

// TODO: s6.3
//        cluster.close();
        System.exit(0);
    }

    // TODO: s6.3
//    public static void displayMenu(GraphTraversalSource g) {
    public static void displayMenu() {
        int option = -1;
        while (option != 0)
        {
            option = showMenu();
            switch (option) {
                case 0:
                    break;
// TODO: s6.4
//                case 1:
//                    //Get Person
//                    System.out.println(getPerson(g));
//                    break;
// TODO: s6.5
//                case 2:
//                    //Add Person
//                    System.out.println(addPerson(g));
//                    break;
// TODO: s6.5
//                case 3:
//                    //Update Person
//                    System.out.println(updatePerson(g));
//                    break;
// TODO: s6.5
//                case 4:
//                    //Delete Person
//                    System.out.println(deletePerson(g));
//                    break;
// TODO: s6.5
//                case 5:
//                    //Add Edge
//                    System.out.println(addIsFriendsWithEdge(g));
//                    break;
// TODO: s6.6
//                case 6:
//                    //Find Friends
//                    System.out.println(getFriends(g));
//                    break;
// TODO: s6.6
//                case 7:
//                    //Find Friends of Friends
//                    System.out.println(getFriendsOfFriends(g));
//                    break;
// TODO: s6.6
//                case 8:
//                    //findPathBetweenUsers
//                    System.out.println(findPathBetweenUsers(g));
                default:
                    System.out.println("Sorry, please enter valid Option");
            }
        }

        System.out.println("Exiting GluttonApp, Bye!");
    }

    public static int showMenu() {

        int option = -1;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
// TODO: s6.4
//        System.out.println("1) Get person Vertex");
// TODO: s6.5
//        System.out.println("2) Add person Vertex");
//        System.out.println("3) Update person Vertex");
//        System.out.println("4) Delete person Vertex");
//        System.out.println("5) Add friends Edge");
// TODO: s6.6
//        System.out.println("6) Find your Friends");
//        System.out.println("7) Find the Friends of your Friends");
//        System.out.println("8) Find the path between two people");
        System.out.println("0) Quit");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        option = keyboard.nextInt();

        return option;
    }

// TODO: s6.3
//    public static Cluster connectToDatabase() {
//        Cluster.Builder builder = Cluster.build();
//        builder.addContactPoint("localhost");
//        builder.port(8182);
//
//        return builder.create();
//    }

// TODO: s6.3
//    public static GraphTraversalSource getGraphTraversalSource(Cluster cluster) {
//        return traversal().withRemote(DriverRemoteConnection.using(cluster));
//    }

// TODO: s6.4
//    public static Long getVertexCount(GraphTraversalSource g) {
//        return g.V().count().next();
//    }

// TODO: s6.4
//    public static Long getEdgeCount(GraphTraversalSource g) {
//        return g.E().count().next();
//    }

// TODO: s6.4
//    public static String getPerson(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to find:");
//        String name = keyboard.nextLine();
//
//        //This returns a List of the properties
//        List properties = g.V().
//                has("person", "first_name", name).
//                valueMap().toList();
//
//        return properties.toString();
//    }

// TODO: s6.5
//    public static String addPerson(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to add:");
//        String name = keyboard.nextLine();
//
//        //This returns a Vertex type
//        Vertex newVertex = g.addV("person").property("first_name", name).next();
//
//        return newVertex.toString();
//    }

// TODO: s6.5
//    public static String updatePerson(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to update:");
//        String name = keyboard.nextLine();
//        System.out.println("Enter the new name for the person:");
//        String newName = keyboard.nextLine();
//
//        //This returns a Vertex type
//        Vertex vertex = g.V().has("person", "first_name", name).property("first_name", newName).next();
//
//        return vertex.toString();
//    }

// TODO: s6.5
//    public static String deletePerson(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to delete:");
//        String name = keyboard.nextLine();
//
//        //This returns a count of the vertices dropped
//       Long vertexCount = g.V().has("person","first_name", name).sideEffect(__.drop().iterate()).count().next();
//
//
//       return vertexCount.toString();
//    }

// TODO: s6.5
//    public static String addIsFriendsWithEdge(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to start the edge at:");
//        String fromName = keyboard.nextLine();
//        System.out.println("Enter the name for the person to end the edge at:");
//        String toName = keyboard.nextLine();
//
//        //This returns an Edge type
//        Edge newEdge = g.V().has("person", "first_name", fromName)
//                .addE("friends").to(__.V().has("person", "first_name", toName))
//                .next();
//
//        return newEdge.toString();
//    }

// TODO: s6.6
//    public static String getFriends(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to find the friends of:");
//        String name = keyboard.nextLine();
//
//        //Returns a list of Objects representing the friend person vertex properties
//        List<Object> friends = g.V().has("person", "first_name", name).
//                both("friends").dedup().
//                values().toList();
//
//        return StringUtils.join(friends, "\r\n");
//    }

// TODO: s6.6
//    public static String getFriendsOfFriends(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to find the friends of:");
//        String name = keyboard.nextLine();
//
//        //Returns a List of Objects representing the friend of a friend person vertex properties
//        List<Object> foff = g.V().has("person", "first_name", name).
//                repeat(
//                        out("friends")
//                ).times(2).dedup().values().toList();
//
//        return StringUtils.join(foff, "\r\n");
//    }

// TODO: s6.6
//    public static String findPathBetweenUsers(GraphTraversalSource g) {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name for the person to start the edge at:");
//        String fromName = keyboard.nextLine();
//        System.out.println("Enter the name for the person to end the edge at:");
//        String toName = keyboard.nextLine();
//
//        //Returns a List of Path objects representing the path between the two person vertices
//        List<Path> friends = g.V().has("person", "first_name", fromName).
//                until(has("person", "first_name", toName)).
//                repeat(
//                        both("friends").simplePath()
//                ).path().toList();
//
//        return StringUtils.join(friends, "\r\n");
//    }
}