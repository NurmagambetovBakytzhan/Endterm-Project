package kz.kbtu.sf.orderservice;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class OrderServiceSimulation extends Simulation {

    // HTTP Protocol Configuration
    private static final String BASE_URL = "http://localhost:9191"; // Replace with your service URL
    private static final HttpProtocolBuilder HTTP_PROTOCOL = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Scenario: Load Test for Creating Orders
    private static final ScenarioBuilder CREATE_ORDERS_SCENARIO = scenario("Create Orders Load Test")
            .exec(http("Create Order")
                    .post("/orders")
                    .body(StringBody("{ \"productId\": 1, \"quantity\": 2, \"customerId\": 101 }"))
                    .check(status().is(201))) // Check for HTTP 201 Created
            .pause(1); // Pause 1 second between requests

    // Scenario: Load Test for Retrieving Orders
    private static final ScenarioBuilder RETRIEVE_ORDERS_SCENARIO = scenario("Retrieve Orders Load Test")
            .exec(http("Get All Orders")
                    .get("/orders")
                    .check(status().is(200))) // Check for HTTP 200 OK
            .pause(1);

    // Scenario: Load Test for Retrieving a Specific Order
    private static final ScenarioBuilder RETRIEVE_ORDER_BY_ID_SCENARIO = scenario("Retrieve Order by ID Test")
            .exec(http("Get Order by ID")
                    .get("/orders/1") // Replace with a valid order ID
                    .check(status().is(200))) // Check for HTTP 200 OK
            .pause(1);

    public OrderServiceSimulation() {
        // Define Load Profile: Ramp up users and steady-state load
        setUp(
                CREATE_ORDERS_SCENARIO.injectOpen(rampUsers(10).during(Duration.ofSeconds(10))),
                RETRIEVE_ORDERS_SCENARIO.injectOpen(constantUsersPerSec(5).during(Duration.ofSeconds(20))),
                RETRIEVE_ORDER_BY_ID_SCENARIO.injectOpen(rampUsers(10).during(Duration.ofSeconds(15)))
        ).protocols(HTTP_PROTOCOL).assertions(
                global().responseTime().max().lte(2000), // Max response time <= 2 seconds
                global().successfulRequests().percent().gt(95.0) // At least 95% successful requests
        );
    }
}
