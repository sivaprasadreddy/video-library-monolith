package videolibrary;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class CatalogBrowsingSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

    FeederBuilder<String> searchFeeder = csv("data/feeders/search.csv").random();
    FeederBuilder<String> genreFeeder = csv("data/feeders/genres.csv").random();

    ChainBuilder gotoPage = repeat(5, "n").on(
        exec(session -> session.set("pageNo", (int)session.get("n") + 1))
        .exec(http("Products Page ${pageNo}").get("/?page=${pageNo}"))
        .pause(1)
    );

    ChainBuilder byGenre = feed(genreFeeder)
            .exec(http("Products By Genre").get("/category/${genre}"))
            .pause(3);

    ChainBuilder search = feed(searchFeeder)
            .exec(http("Search").get("/?query=${key}"))
            .pause(3);

    ChainBuilder browseProducts = exec(byGenre)
            .exec(gotoPage)
            .exec(search);

/*
    ScenarioBuilder scnBrowseProducts = scenario("Browse Products")
            .during(Duration.ofMinutes(1), "Counter")
            .on(browseProducts);
*/

    ScenarioBuilder scnBrowseProducts = scenario("Browse Products").exec(browseProducts);

    {
        setUp(
            scnBrowseProducts.injectOpen(rampUsers(10).during(10))
        )
        .protocols(httpProtocol)
            .assertions(
                global().responseTime().max().lt(2000),
                global().successfulRequests().percent().gt(95.0)
            );
    }
}
