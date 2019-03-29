import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.reference.ReferenceVertex;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.apache.commons.lang3.StringUtils;


import java.util.List;
import java.util.Scanner;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.both;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.has;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.out;

public class App {

    public static void main( String[] args )
    {
        Cluster cluster = connectToDatabase();
        GraphTraversalSource g = getGraphTraversalSource(cluster);

        displayMenu(g);

        cluster.close();
    }

    public static void displayMenu(GraphTraversalSource g){
        int option = -1;
        while (option != 0)
        {
            option = showMenu();
            switch (option) {
                case 1:
                    //Get Vertex Count
                    System.out.println(getVertexCount(g));
                    break;
                case 2:
                    //Get Edge Count
                    System.out.println(getEdgeCount(g));
                    break;
                case 3:
                    //Add Person
                    System.out.println(addPerson(g));
                    break;
                case 4:
                    //Add Edge
                    System.out.println(addIsFriendsWithEdge(g));
                    break;
                case 5:
                    //Find Friends
                    System.out.println(getFriends(g));
                    break;
                case 6:
                    //Find Friends of Friends
                    System.out.println(getFriendsOfFriends(g));
                    break;
                case 7:
                    //findPathBetweenUsers
                    System.out.println(findPathBetweenUsers(g));
                default:
                    System.out.println("Sorry, please enter valid Option");
            }
        }
    }

    public static int showMenu() {

        int option = -1;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Main Menu:");
        System.out.println("--------------");
        System.out.println("1) Get Count of the Vertices");
        System.out.println("2) Get Count of the Edges");
        System.out.println("3) Add person Vertex");
        System.out.println("4) Add is_friends_with Edge");
        System.out.println("5) Find your Friends");
        System.out.println("6) Find the Friends of your Friends");
        System.out.println("7) Find the path between two people");
        System.out.println("0) Quit");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        option = keyboard.nextInt();

        return option;
    }

    public static Cluster connectToDatabase(){
        Cluster.Builder builder = Cluster.build();
        builder.addContactPoint("localhost");
        builder.port(8182);

        return builder.create();
    }

    public static GraphTraversalSource getGraphTraversalSource(Cluster cluster) {
        return traversal().withRemote(DriverRemoteConnection.using(cluster));
    }

    public static Long getVertexCount(GraphTraversalSource g){
        GraphTraversal t = g.V().count();

        return (Long)t.next();

        //return g.V().count().next();
    }

    public static Long getEdgeCount(GraphTraversalSource g){
        return g.E().count().next();
    }

    public static String addPerson(GraphTraversalSource g){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to add:");
        String name = keyboard.nextLine();

        //This returns a Vertex type
        Vertex newVertex = g.addV("person").property("name", name).next();

        return newVertex.toString();
    }

    public static String addIsFriendsWithEdge(GraphTraversalSource g){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to start the edge at:");
        String fromName = keyboard.nextLine();
        System.out.println("Enter the name for the person to end the edge at:");
        String toName = keyboard.nextLine();

        //This returns an Edge type
        Edge newEdge = g.V().has("person", "name", fromName)
                .addE("is_friends_with").to(__.V().has("person", "name", toName))
                .next();

        return newEdge.toString();
    }

    public static String getFriends(GraphTraversalSource g){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to find the friends of:");
        String name = keyboard.nextLine();
        List<Object> friends = g.V().has("person", "name", name).
                out("is_friends_with").dedup().
                values().toList();

        return StringUtils.join(friends, "\r\n");
    }

    public static String getFriendsOfFriends(GraphTraversalSource g){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to find the friends of:");
        String name = keyboard.nextLine();
        List<Object> foff = g.V().has("person", "name", name).
                repeat(
                        out("is_friends_with")
                ).times(2).dedup().values().toList();

        return StringUtils.join(foff, "\r\n");

    }

    public static String findPathBetweenUsers(GraphTraversalSource g){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to start the edge at:");
        String fromName = keyboard.nextLine();
        System.out.println("Enter the name for the person to end the edge at:");
        String toName = keyboard.nextLine();

        List<Path> friends = g.V().has("person", "name", fromName).
                until(has("person", "name", toName)).
                repeat(
                        both("is_friends_with").simplePath()
                ).path().toList();

        return StringUtils.join(friends, "\r\n");
    }
}
