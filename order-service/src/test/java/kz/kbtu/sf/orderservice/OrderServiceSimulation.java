package kz.kbtu.sf.orderservice;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class OrderServiceSimulation extends Simulation {

    private static final String BASE_URL = "http://localhost:9191/api/orders";
    private static final String REGISTER_URL = "http://localhost:9191/api/auth/register";
    private static final String AUTH_URL = "http://localhost:9191/api/auth/login";
    private static final HttpProtocolBuilder HTTP_PROTOCOL = http
            .baseUrl(BASE_URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private static final ScenarioBuilder USER_FLOW_SCENARIO = scenario("User Registration, Authorization, and Create Order Flow")
            .exec(session -> session
                    .set("email", "user" + UUID.randomUUID() + "@test.com")
                    .set("password", "password" + UUID.randomUUID().toString().substring(0, 8))
            )
            .exec(session -> {
                return session;
            })
            .exec(http("User Registration")
                    .post(REGISTER_URL)
                    .header("Content-Type", "application/json")
                    .body(StringBody("""
                        {
                            "email": "#{email}",
                            "password": "#{password}"
                        }
                    """))
                    .check(status().is(200))
            )
            .pause(1)
            .exec(http("Login Request")
                    .post(AUTH_URL)
                    .header("Content-Type", "application/json")
                    .body(StringBody("""
                        {
                            "email": "#{email}",
                            "password": "#{password}"
                        }
                    """))
                    .check(jsonPath("$.token").saveAs("authToken"))
            )
            .pause(1)
            .exec(http("Create Order")
                    .post(BASE_URL)
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
                    .check(status().is(201))
            )
            .pause(1);

    public OrderServiceSimulation() {
        setUp(
                USER_FLOW_SCENARIO.injectOpen(rampUsers(2000).during(Duration.ofSeconds(50)))
        ).protocols(HTTP_PROTOCOL).assertions(
                global().responseTime().max().lte(3000), // Max response time <= 3 seconds
                global().successfulRequests().percent().gt(95.0) // At least 95% successful requests
        );
    }
}
