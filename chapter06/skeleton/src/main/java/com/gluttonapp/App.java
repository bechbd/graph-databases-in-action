package com.gluttonapp;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import java.util.List;
import java.util.Scanner;
import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.both;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.has;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.out;

public class App {

    public static void main( String[] args ) {
        //Insert code to connect to the database here

        //Insert code to create the GraphTraversalSource here

        displayMenu();

        //Insert code to cleanup the cluster configuration here

        System.exit(0);
    }

    public static void displayMenu() {
        int option = -1;
        while (option != 0) {
            option = showMenu();
            switch (option) {
                case 0:
                    break;
                case 1:
                    //Get Person
                    break;
                case 2:
                    //Add Person
                    break;
                case 3:
                    //Update Person
                    break;
                case 4:
                    //Delete Person
                    break;
                case 5:
                    //Add Edge
                    break;
                case 6:
                    //Find Friends
                    break;
                case 7:
                    //Find Friends of Friends
                    break;
                case 8:
                    //findPathBetweenUsers
                    break;
                default:
                    System.out.println("Sorry, please enter valid Option");
            }
        }

        System.out.println("Exiting GluttonApp, Bye!");
    }

    public static int showMenu() {

        int option = -1;
        Scanner keyboard = new Scanner(System.in);
        System.out.println();
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1) Get person Vertex");
        System.out.println("2) Add person Vertex");
        System.out.println("3) Update person Vertex");
        System.out.println("4) Delete person Vertex");
        System.out.println("5) Add friends Edge");
        System.out.println("6) Find your Friends");
        System.out.println("7) Find the Friends of your Friends");
        System.out.println("8) Find the path between two people");
        System.out.println("0) Quit");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        option = keyboard.nextInt();

        return option;
    }

    public static Cluster connectToDatabase() {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static GraphTraversalSource getGraphTraversalSource(Cluster cluster) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String getPerson(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String addPerson(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String updatePerson(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String deletePerson(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String addFriendsEdge(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String getFriends(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String getFriendsOfFriends(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }

    public static String findPathBetweenPeople(GraphTraversalSource g) {
        throw new NotImplementedException("Not Implemented Yet");
    }
}
