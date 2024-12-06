package kz.kbtu.sf.orderservice;

import com.github.javafaker.Faker;
import io.gatling.javaapi.core.OpenInjectionStep.RampRate.RampRateOpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

@Slf4j
public class AccountSimulation extends Simulation {

    // private static final HttpProtocolBuilder HTTP_PROTOCOL_BUILDER = setupProtocolForSimulation();

    // private static final Iterator<Map<String, Object>> REGISTRATION_FEED = setupRegistrationFeedData();

    // public AccountSimulation() {
    //     setUp(buildAuthScenario().injectOpen(getEndpointInjectionProfile())
    //             .protocols(HTTP_PROTOCOL_BUILDER))
    //             .assertions(global().responseTime().max().lte(1000),
    //                     global().successfulRequests().percent().gt(90d));
    // }

    // private RampRateOpenInjectionStep getEndpointInjectionProfile() {
    //     int totalDesiredUserCount = 100;
    //     double userRampUpPerInterval = 50;
    //     double rampUpIntervalSeconds = 2;

    //     int totalRampUptimeSeconds = 10;
    //     int steadyStateDurationSeconds = 10;
    //     return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalSeconds / 60)).to(totalDesiredUserCount)
    //             .during(Duration.ofSeconds(totalRampUptimeSeconds + steadyStateDurationSeconds));
    // }

    // private static HttpProtocolBuilder setupProtocolForSimulation() {
    //     return http.baseUrl("http://localhost:9191/api/auth")
    //             .acceptHeader("application/json")
    //             .contentTypeHeader("application/json")
    //             .maxConnectionsPerHost(10)
    //             .userAgentHeader("Gatling/Performance Test");
    // }

    // private static Iterator<Map<String, Object>> setupRegistrationFeedData() {
    //     Faker faker = new Faker();
    //     return Stream.generate(() -> {
    //         Map<String, Object> data = new HashMap<>();
    //         data.put("email", faker.internet().emailAddress());
    //         data.put("password", "123");
    //         return data;
    //     }).iterator();
    // }

    // private static ScenarioBuilder buildAuthScenario() {
    //     return scenario("Authentication Test")
    //             .feed(REGISTRATION_FEED)
    //             .exec(http("Register User")
    //                     .post("/register")
    //                     .body(StringBody(session -> {
    //                         Map<String, Object> data = session.get("email", Map.class);
    //                         return String.format("{\"email\":\"%s\",\"password\":\"123\"}", data.get("email"));
    //                     }))
    //                     .check(status().is(201)))
    //             .pause(1)
    //             .exec(http("Login User")
    //                     .post("/login")
    //                     .body(StringBody(session -> {
    //                         Map<String, Object> data = session.get("email", Map.class);
    //                         return String.format("{\"email\":\"%s\",\"password\":\"123\"}", data.get("email"));
    //                     }))
    //                     .check(status().is(200)));
    // }
}
