package com.vinsguru.movie;

import com.vinsguru.movie.dto.MovieDetails;
import com.vinsguru.netflux.events.MovieAddedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.json.JsonMapper;


@EnableTestBinder
@AutoConfigureRestTestClient
@SpringBootTest(properties = "app.import-movies=false")
public class MovieEventBinderTest {

    @Autowired
    private RestTestClient testClient;

    @Autowired
    private OutputDestination outputDestination;

    @Test
    public void movieAddedEvent() {
        var json = """
                    {"title":"8 Mile","voteAverage":7.124,"voteCount":6692,"releaseDate":"2002-11-08","revenue":242875078,"runtime":111,"backdropPath":"/bfccQmQWNFQYRv4PHgCnjDu7PXn.jpg","budget":41000000,"homepage":"https://www.uphe.com/movies/8-mile","overview":"For Jimmy Smith, Jr., life is a daily fight just to keep hope alive. Feeding his dreams in Detroit's vibrant music scene, Jimmy wages an extraordinary personal struggle to find his own voice - and earn a place in a world where rhymes rule, legends are born and every moment… is another chance.","popularity":36.179,"posterPath":"/7BmQj8qE1FLuLTf7Xjf9sdIHzoa.jpg","genres":["Drama","Music"]}
                """;
        var request = JsonMapper.shared().readValue(json, MovieDetails.class);
        var response = this.testClient.post()
                .uri("/api/movies")
                .body(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(MovieDetails.class)
                .getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.id());
        Assertions.assertEquals("8 Mile", response.title());

        var message = this.outputDestination.receive(1000, "movie-events");
        var event = JsonMapper.shared().readValue(message.getPayload(), MovieAddedEvent.class);
        Assertions.assertEquals(response.id(), message.getHeaders().get(KafkaHeaders.KEY, Integer.class));
        Assertions.assertEquals(response.id(), event.movieId());
        Assertions.assertEquals("8 Mile", event.title());

    }

}
