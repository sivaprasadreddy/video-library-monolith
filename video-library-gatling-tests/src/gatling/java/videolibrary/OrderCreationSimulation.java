package videolibrary;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class OrderCreationSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    FeederBuilder<String> productFeeder = csv("data/feeders/products.csv").random();
    FeederBuilder<String> credentialsFeeder = csv("data/feeders/credentials.csv").random();
    Iterator<Map<String, Object>> randomString =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                        String randString = RandomStringUtils.randomAlphanumeric(20);
                        return Collections.singletonMap("randString", randString);
                    }
            ).iterator();
    Iterator<Map<String, Object>> randomQuantity =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                        int randInt = RandomGenerator.getDefault().nextInt(4);
                        return Collections.singletonMap("randomQuantity", randInt);
                    }
            ).iterator();

    ChainBuilder login = feed(credentialsFeeder)
            .exec(http("Login")
                    .post("/login")
                    .formParam("username", "${username}")
                    .formParam("password", "${password}")
            ).pause(1);

    ChainBuilder createOrder = feed(randomQuantity)
            .feed(productFeeder)
            .feed(randomString)
            .exec(http("Add Item To Cart").post("/cart/items")
                .body(ElFileBody("data/feeders/item.json")).asJson()
            )
            .exec(
                http("Create Order")
                .post("/cart/checkout")
                    .formParam("customerName", "${randString}")
                    .formParam("customerEmail", "${randString}@gmail.com")
                    .formParam("deliveryAddress", "${randString}")
                    .formParam("creditCardNumber", "1111222233334444")
                    .formParam("cvv", "123")
            ).pause(1);

    ChainBuilder createOrderFlow =
            exec(login)
            .pause(2)
            .exec(createOrder);

    /*ScenarioBuilder scnCreateOrder = scenario("Create Order")
            .during(Duration.ofMinutes(1), "Counter")
            .on(createOrderFlow);*/

    ScenarioBuilder scnCreateOrder = scenario("Create Order").exec(createOrderFlow);

    {
        setUp(
            scnCreateOrder.injectOpen(rampUsers(10).during(10))
        )
        .protocols(httpProtocol)
            .assertions(
                global().responseTime().max().lt(2000),
                global().successfulRequests().percent().gt(95.0)
            );
    }
}
