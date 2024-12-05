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

    private static final HttpProtocolBuilder HTTP_PROTOCOL_BUILDER = setupProtocolForSimulation();

    private static final Iterator<Map<String, Object>> FEED_DATA = setupTestFeedData();

    public AccountSimulation() {

        setUp(buildGetScenario().injectOpen(getEndpointInjectionProfile())
                .protocols(HTTP_PROTOCOL_BUILDER)).assertions(global().responseTime()
                .max()
                .lte(100), global().successfulRequests()
                .percent()
                .gt(90d));
    }

    private RampRateOpenInjectionStep getEndpointInjectionProfile() {
        int totalDesiredUserCount = 100;
        double userRampUpPerInterval = 50;
        double rampUpIntervalSeconds = 2;

        int totalRampUptimeSeconds = 10;
        int steadyStateDurationSeconds = 10;
        return rampUsersPerSec(userRampUpPerInterval / (rampUpIntervalSeconds / 60)).to(totalDesiredUserCount)
                .during(Duration.ofSeconds(totalRampUptimeSeconds + steadyStateDurationSeconds));
    }

    private static HttpProtocolBuilder setupProtocolForSimulation() {
        return http.baseUrl("http://localhost:9191/register")
                .acceptHeader("application/json")
                .maxConnectionsPerHost(10)
                .userAgentHeader("Gatling/Performance Test");
    }

    private static Iterator<Map<String, Object>> setupTestFeedData() {
        Faker faker = new Faker();
        Iterator<Map<String, Object>> iterator;
        iterator = Stream.generate(() -> {
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    stringObjectMap.put("id", faker.name().firstName());
                    return stringObjectMap;
                })
                .iterator();
        return iterator;
    }

    private static ScenarioBuilder buildGetScenario() {
        return scenario("Load Test Retrieving Accounts")
                .feed(FEED_DATA)
                .exec(http("get-accounts-request").get("http://localhost:9191/accounts")
                        .check(status().is(200)));
    }
}
