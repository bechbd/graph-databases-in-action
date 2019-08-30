package com.gluttonapp;

import com.github.javafaker.Faker;
import com.opencsv.CSVReader;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;
import static org.apache.tinkerpop.gremlin.process.traversal.Order.shuffle;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.*;
import static org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality.single;

public class App {
    private static final String RESTAURANTS_CSV = "restaurants.csv";

    public static void main(String[] agrs) throws IOException {
        Cluster cluster = connectToDatabase();
        GraphTraversalSource g = getGraphTraversalSource(cluster);

        System.out.println("Using cluster connection: " + cluster.toString());
        System.out.println("Using traversal source: " + g.toString());

        System.out.println("Clearing graph");
        clearGraph(g);

        // Add states
        System.out.println("adding states: Texas & Ohio");
        Vertex texas = addState(g, "Texas");
        Vertex ohio  = addState(g, "Ohio");

        // Add cities
        System.out.println("adding cities: Houston & Cincinnati");
        Vertex houston    = addCity(g, texas, "Houston");
        Vertex cincinnati = addCity(g, ohio, "Cincinnati");

        // Add users
        System.out.println("adding users: Dave, Josh, Hank, Ted");
        Vertex dave   = addUser(g, 1, "Dave","Bech", houston);
        Vertex josh   = addUser(g, 2, "Josh","Perry", houston);
        Vertex hank   = addUser(g, 3, "Hank","Erin", cincinnati);
        Vertex ted    = addUser(g, 4, "Ted","Wilson", cincinnati);
        Vertex kelly  = addUser(g, 5, "Kelly", "Gorman", houston);
        Vertex jim    = addUser(g, 6, "Jim", "Miller", houston);
        Vertex paras  = addUser(g, 7, "Paras", "Hilbert", cincinnati);
        Vertex denise = addUser(g, 8, "Denise", "Mande", cincinnati);

        makeFriends(g, dave, josh);
        makeFriends(g, dave, hank);
        makeFriends(g, josh, hank);
        makeFriends(g, ted, josh);
        makeFriends(g, dave, jim);
        makeFriends(g, dave, kelly);
        makeFriends(g, kelly, denise);
        makeFriends(g, jim, denise);
        makeFriends(g, jim, paras);
        makeFriends(g, paras, denise);

        // Add restaurants and cuisines
        System.out.println("add restaurants from: " + RESTAURANTS_CSV);
        try(Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(RESTAURANTS_CSV).toURI()));
            CSVReader csvreader = new CSVReader(reader, ',', '\'', 1);) {
            String[] record;
            while((record = csvreader.readNext()) != null) {
                addRestaurant(g, record[0], record[1], record[2]);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Add reviews
        System.out.println("adding reviews for each of the users");
        addReviews(g, dave);
        addReviews(g, josh);
        addReviews(g, hank);
        addReviews(g, ted);
        addReviews(g, kelly);
        addReviews(g, jim);
        addReviews(g, paras);
        addReviews(g, denise);

        System.out.println("graph label count summary: " + getGroupCounts(g));

        cluster.close();
        System.exit(0);
    }

    private static void addReviews(GraphTraversalSource g, Vertex user) {
        Faker faker = new Faker();
        Random random = new Random();

        Vertex city = g.V(user).out("lives_in").next();

        List<Vertex> restaurants = g.V().hasLabel("restaurant").
                where(out("located_in").is(city)).
                order().by(shuffle).
                toList();

        int reviewCount = (restaurants.size() / 2) + 3;  // create reviews for slightly more than half

        List<Vertex> reviewRestaurants = restaurants.stream().limit(reviewCount).collect(Collectors.toList());

        // make sure everyone reviews Dave's Big Deluxe (restaurant_id = 31), regardless of city
        Vertex specialRestaurant = g.V().has("restaurant","restaurant_id", 31).next();
        if (!reviewRestaurants.contains(specialRestaurant)) {
            reviewRestaurants.add(specialRestaurant);
        }

        for (int i = 0; i < reviewRestaurants.size(); i++) {
            Vertex restaurant = reviewRestaurants.get(i);
            int rating = random.nextInt(5) + 1;

            g.addV("review").
              property(single, "rating", rating).
              property(single, "body", faker.lorem().paragraph()).
              property(single, "created_date", faker.date().past(1500, TimeUnit.DAYS)).
                    as("r").
            addE("about_a").to(V(restaurant)).
            V(user).addE("wrote_a").to("r").
            iterate();
        }
    }

    private static void addRestaurant(GraphTraversalSource g, String restaurant_id, String restaurant_name, String cuisine_name) {
        Faker faker = new Faker();
        Random random = new Random();
        int result = random.nextInt(2);
        String city = (result == 0) ? "Houston" : "Cincinnati";

        Vertex r = (Vertex) g.V().
          has("restaurant","restaurant_id", Integer.parseInt(restaurant_id)).fold().
          coalesce(
              unfold(),
              addV("restaurant").
                  property(single, "restaurant_id", Integer.parseInt(restaurant_id)).
                  property(single, "restaurant_name", restaurant_name).
                  property(single, "address", faker.address().streetAddress())
            ).as("r").
                addE("located_in").to(V().has("city","name",city)).
            select("r").
            next();

        Vertex c = g.V().
          has("cuisine", "cuisine_name", cuisine_name).fold().
          coalesce(
              unfold(),
              addV("cuisine").property(single, "cuisine_name", cuisine_name)
            ).
          next();

        g.V(r).
            coalesce(
                outE("serves").where(outV().is(V(c))),
                addE("serves").to(V(c))
            ).iterate();
    }

    private static Vertex addUser(GraphTraversalSource g, int userId, String firstName, String lastName, Vertex city) {
        return (Vertex) g.V().has("user_id", userId).fold().
                coalesce(
                    unfold(),
                    addV("user").
                        property(single, "user_id",    userId).
                        property(single, "first_name", firstName).
                        property(single, "last_name",  lastName)
                    ).as("v").
                addE("lives_in").to(city).
                select("v").
                next();
    }

    private static void makeFriends(GraphTraversalSource g, Vertex v1, Vertex v2) {
        g.V(v1).addE("is_friends_with").to(v2).iterate();
    }

    public static void clearGraph(GraphTraversalSource g) {
        g.V().drop().iterate();
    }

    public static String getGroupCounts(GraphTraversalSource g) {
        // This approach should only be used on small or toy graphs
        return g.V().fold().
                project("vertexCount","edgeCount").
                  by(unfold().groupCount().by(T.label)).
                  by(unfold().outE().groupCount().by(T.label)).
                next().
                toString();
    }

    public static Vertex addState(GraphTraversalSource g, String name) {
        Vertex returnVertex =  g.V().has("state", "name", name).fold().
            coalesce(
                unfold(),
                addV("state").property(single, "name", name)).
            next();
        return returnVertex;
    };

    public static Vertex addCity(GraphTraversalSource g, Vertex state, String name) {
        Vertex returnVertex = g.V().has("city", "name", name).fold().
                coalesce(
                    unfold(),
                    addV("city").property(single, "name", name)).
                next();

        g.V(returnVertex).addE("located_in").to(V(state)).iterate();

        return returnVertex;
    }

    private static Cluster connectToDatabase() {
        Cluster.Builder builder = Cluster.build();
        builder.addContactPoint("localhost");
        builder.port(8182);

        return builder.create();
    }

    private static GraphTraversalSource getGraphTraversalSource(Cluster cluster) {
        return traversal().withRemote(DriverRemoteConnection.using(cluster));
    }
}