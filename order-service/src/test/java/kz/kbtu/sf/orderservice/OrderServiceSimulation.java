package kz.kbtu.sf.orderservice;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.core.Session;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class OrderServiceSimulation extends Simulation {

    private static final String BASE_URL = "http://localhost:9191/api/orders"; 
    private static final String AUTH_URL = "http://localhost:9191/api/auth/login";
    private static final HttpProtocolBuilder HTTP_PROTOCOL = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private static final ScenarioBuilder CREATE_ORDERS_SCENARIO = scenario("Create Orders Load Test")
            .exec(http("Login Request")
                    .post(AUTH_URL)
                    .header("Content-Type", "application/json")
                    .body(StringBody("""
                        {
                            "email": "nurmagambetovbakytzan@gmail.com",
                            "password": "123"
                        }
                    """))
                    .check(jsonPath("$.token").saveAs("authToken"))
            )
            .exec(http("Create Order")
                    .post("")
                    .header("Authorization", "Bearer ${authToken}")
                    .body(StringBody("""
                        {
                            "name": "SAMSUNG",
                            "order_id": "6da48f13-f903-49b1-a5d9-aac6264f7622",
                            "product_id": "6da48f13-f903-49b1-a5d9-aac6264f7622",
                            "product_type": "Electronics",
                            "quantity": 1,
                            "price": 1000
                        }
                    """))
                    .check(status().is(201)))
            .pause(1);

    public OrderServiceSimulation() {
        setUp(
                CREATE_ORDERS_SCENARIO.injectOpen(rampUsers(50000).during(Duration.ofSeconds(10)))
        ).protocols(HTTP_PROTOCOL).assertions(
                global().responseTime().max().lte(3000), // Max response time <= 3 seconds
                global().successfulRequests().percent().gt(90.0) // At least 90% successful requests
        );
    }
}
