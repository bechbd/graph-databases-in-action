package com.diningbyfriends;

import org.apache.commons.lang3.StringUtils;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.WithOptions;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.process.traversal.*;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.Contains.within;
import static org.apache.tinkerpop.gremlin.process.traversal.Scope.*;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static org.apache.tinkerpop.gremlin.structure.Column.*;

public class App {

    public static void main(String[] args) {
        Cluster cluster = connectToDatabase();
        System.out.println("Using cluster connection: " + cluster.toString());
        GraphTraversalSource g = getGraphTraversalSource(cluster);
        System.out.println("Using traversal source: " + g.toString());

        displayMenu(g, cluster);

        cluster.close();
        System.exit(0);
    }

    public static void displayMenu(GraphTraversalSource g, Cluster cluster) {
        int option = -1;
        while (option != 0) {
            option = showMenu();
            switch (option) {
                case 0:
                    break;
                case 1:
                    //Get Person
                    System.out.println("Person Vertex: " + getPerson(g));
                    break;
                case 2:
                    //Add Person
                    System.out.println("New person Vertex: " + addPerson(g));
                    break;
                case 3:
                    //Update Person
                    System.out.println("Update person Vertex: " + updatePerson(g));
                    break;
                case 4:
                    //Delete Person
                    System.out.println(deletePerson(g));
                    break;
                case 5:
                    //Add Edge
                    System.out.println(addFriendsEdge(g));
                    break;
                case 6:
                    //Find Friends
                    System.out.println(getFriends(g));
                    break;
                case 7:
                    //Find Friends of Friends
                    System.out.println(getFriendsOfFriends(g));
                    break;
                case 8:
                    //findPathBetweenUsers
                    System.out.println(findPathBetweenPeople(g));
                    break;
                case 9:
                    //newest Restaurant Reviews
                    System.out.println(newestRestaurantReviews(g));
                    break;
                case 10:
                    //newest Restaurant Reviews
                    System.out.println(highestRatedRestaurants(g));
                    break;
                case 11:
                    //newest Restaurant Reviews
                    System.out.println(highestRatedByCuisine(g));
                    break;
                case 12:
                    //TODO 9.2
                    //friends top 3 resturants for local city
                    //findTop3FriendsRestaurantsForCity(cluster).forEach( r -> System.out.println(r.getObject().toString()));
                    break;
                default:
                    System.out.println("Sorry, please enter valid Option");
            }
        }

        System.out.println("Exiting DiningByFriends, Bye!");
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
        System.out.println("9) Find the newest reviews for a restaurant");
        System.out.println("10) What are the ten highest rated restaurants near me");
        System.out.println("11) What restaurant near me, with a specific cuisine, is the highest rated");
        //TODO 9.2
        //System.out.println("12) What are my friends top three restaurants for a city");
        System.out.println("0) Quit");
        System.out.println("--------------");
        System.out.println("Enter your choice:");
        option = keyboard.nextInt();

        return option;
    }

    public static Cluster connectToDatabase() {
        Cluster.Builder builder = Cluster.build();
        builder.addContactPoint("localhost");
        builder.port(8182);

        return builder.create();
    }

    public static GraphTraversalSource getGraphTraversalSource(Cluster cluster) {
        return traversal().withRemote(DriverRemoteConnection.using(cluster));
    }

    public static String getPerson(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the first name for the person to find:");
        String name = keyboard.nextLine();

        //This returns a List of the properties
        List properties = g.V().
                has("person", "first_name", name).
                valueMap().toList();

        return properties.toString();
    }

    public static String addPerson(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to add:");
        String name = keyboard.nextLine();

        //This returns a Vertex type
        Vertex newVertex = g.addV("person").property("first_name", name).next();

        return newVertex.toString();
    }

    public static String updatePerson(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to update:");
        String name = keyboard.nextLine();
        System.out.println("Enter the new name for the person:");
        String newName = keyboard.nextLine();

        //This returns a Vertex type
        Vertex vertex = g.V().has("person", "first_name", name).property("first_name", newName).next();
        return vertex.toString();
    }

    public static String deletePerson(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to delete:");
        String name = keyboard.nextLine();

        //This returns a count of the vertices dropped
        Long vertexCount = g.V().has("person", "first_name", name).
                sideEffect(__.drop().iterate()).
                count().
                next();

        g.V().has("person","first_name", name).drop().next();

        return vertexCount.toString();
    }

    public static String addFriendsEdge(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to start the edge at:");
        String fromName = keyboard.nextLine();
        System.out.println("Enter the name for the person to end the edge at:");
        String toName = keyboard.nextLine();

        //This returns an Edge type
        Edge newEdge = g.V().has("person", "first_name", fromName)
                .addE("friends").to(__.V().has("person", "first_name", toName))
                .next();

        return newEdge.toString();
    }

    public static String getFriends(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to find the friends of:");
        String name = keyboard.nextLine();

        //Returns a list of Objects representing the friend person vertex properties
        List<Object> friends = g.V().has("person", "first_name", name).
                both("friends").dedup().
                values().
                toList();

        return StringUtils.join(friends, "\r\n");
    }

    public static String getFriendsOfFriends(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to find the friends of:");
        String name = keyboard.nextLine();

        // Returns a List of Objects representing the vertex properties
        // of the friend of a friend person vertex
        List<Object> foff = g.V().has("person", "first_name", name).
                repeat(
                        out("friends")
                ).times(2).dedup().values().toList();

        return StringUtils.join(foff, "\r\n");
    }

    public static String findPathBetweenPeople(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name for the person to start the edge at:");
        String fromName = keyboard.nextLine();
        System.out.println("Enter the name for the person to end the edge at:");
        String toName = keyboard.nextLine();

        // Returns a List of Path objects which represent
        // the path between the two person vertices
        List<Path> friends = g.V().has("person", "first_name", fromName).
                until(has("person", "first_name", toName)).
                repeat(
                        both("friends").simplePath()
                ).path().toList();

        return StringUtils.join(friends, "\r\n");
    }

    private static String newestRestaurantReviews(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the id for the restaurant:");
        Integer restaurantId = Integer.valueOf(keyboard.nextLine());

        // Returns a Map of Objects containing the restaurant id and the review
        List<Map<Object, Object>> reviews = g.V().has("restaurant", "restaurant_id", restaurantId).
                in("about").
                order().
                    by("created_date").
                limit(3).
                valueMap("rating", "created_date", "body").
                    with(WithOptions.tokens).toList();


        return StringUtils.join(reviews, "\r\n");
    }

    private static String highestRatedRestaurants(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the id for the user:");
        Integer personId = Integer.valueOf(keyboard.nextLine());

        // Returns a Map of Objects containing the restaurant id and the review
        List<Map<String, Object>> restaurants = g.V().has("person", "person_id", personId).
                out("lives").
                in("within").
                group().
                    by(__.identity()).
                    by(__.in("about").values("rating").mean()).
                order(local).
                    by(values, Order.desc).
                limit(local, 10).
                unfold().
                project("restaurant_id", "restaurant_name", "address", "rating_avg").
                    by(select(keys).values("restaurant_id")).
                    by(select(keys).values("name")).
                    by(select(keys).values("address")).
                    by(select(values)).
                toList();

        return StringUtils.join(restaurants, "\r\n");
    }

    private static String highestRatedByCuisine(GraphTraversalSource g) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the id for the person:");
        Integer personId = Integer.valueOf(keyboard.nextLine());
        System.out.println("Enter a comma separated list of cuisines:");
        List<String> cuisineList = Arrays.asList(keyboard.nextLine().split(","));
        cuisineList.replaceAll(String::trim);

        // Returns a Map of Objects containing the restaurant id and the review
        List<Map<String, Object>> restaurants = g.V().has("person", "person_id", personId).
                    out("lives").
                    in("within").
                    where(out("serves").has("name", P.within(cuisineList))).
                    group().
                        by(__.identity()).
                        by(__.in("about").values("rating").mean()).
                    order(local).
                        by(values, Order.desc).
                        unfold().
                    limit(1).
                    project("restaurant_id", "restaurant_name", "address", "rating_average", "cuisine").
                        by(select(keys).values("restaurant_id")).
                        by(select(keys).values("name")).
                        by(select(keys).values("address")).
                        by(select(values)).
                        by(select(keys).out("serves").values("name")).toList();

        return StringUtils.join(restaurants, "\r\n");
    }

    //TODO 9.2
    /*private static List<Result> findTop3FriendsRestaurantsForCity(Cluster cluster) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the first name for the person: ");
        String name = keyboard.nextLine();
        System.out.println("Enter the city to search in: ");
        String city = keyboard.nextLine();

        Client client = cluster.connect("sgSession");
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        params.put("city",city);

        String defineSubgraph = "subgraph = g.V()." +
                "has('user','first_name',name)." +
                "bothE().subgraph('sg').otherV()." +
                "outE('wrote').subgraph('sg').inV()." +
                "optional(outE('about').subgraph('sg').inV())." +
                "outE('about').subgraph('sg').inV()." +
                "outE('located').subgraph('sg')." +
                "cap('sg').next(); null";

        client.submit(defineSubgraph, params);

        String defineSgTraversal = "sg = subgraph.traversal(); null";

        client.submit(defineSgTraversal);

        String findTopLocalRestaurants = "sg.V()." +
                "has('city','name',city)." +
                "in('located')." +
                "group()." +
                  "by(identity())." +
                  "by(__.in('about').values('rating').mean()).\n" +
                "order(local)." +
                  "by(values, desc)." +
                "limit(local,3)." +
                "unfold()." +
                "project('restaurant_id','restaurant_name','address','rating_average')." +
                  "by(select(keys).values('restaurant_id'))." +
                  "by(select(keys).values('restaurant_name'))." +
                  "by(select(keys).values('address'))." +
                  "by(select(values))";

        List<Result> results = new ArrayList<>();

        try {
            results = client.submit(findTopLocalRestaurants, params).all().get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }

        return results;
    }*/
}
