package com.vinsguru.movie;

import com.vinsguru.movie.dto.MovieDetails;
import com.vinsguru.netflux.events.MovieAddedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@AutoConfigureRestTestClient
@Import({
        TestcontainersConfiguration.class,
        MovieEventKafkaTest.TestConsumerConfiguration.class
})
@SpringBootTest(
        properties = {
                "app.import-movies=false",
                "spring.cloud.function.definition=testConsumer",
                "spring.cloud.stream.bindings.testConsumer-in-0.destination=movie-events",
                "spring.cloud.stream.kafka.binder.consumer-properties.key.deserializer=org.apache.kafka.common.serialization.IntegerDeserializer",
                "spring.cloud.stream.kafka.binder.consumer-properties.auto.offset.reset=earliest"
        }
)
public class MovieEventKafkaTest {

    @Autowired
    private RestTestClient testClient;

    @Autowired
    private BlockingQueue<Message<MovieAddedEvent>> queue;

    @Test
    public void movieAddedEvent() throws InterruptedException {
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

        var message = this.queue.poll(5, TimeUnit.SECONDS);
        Assertions.assertNotNull(message);
        var event = message.getPayload();
        Assertions.assertEquals(response.id(), message.getHeaders().get(KafkaHeaders.RECEIVED_KEY, Integer.class));
        Assertions.assertEquals(response.id(), event.movieId());
        Assertions.assertEquals("8 Mile", event.title());
    }

    @TestConfiguration
    static class TestConsumerConfiguration{

        @Bean
        public BlockingQueue<Message<MovieAddedEvent>> queue(){
            return new LinkedBlockingQueue<>();
        }

        @Bean
        Consumer<Message<MovieAddedEvent>> testConsumer(BlockingQueue<Message<MovieAddedEvent>> queue){
            return queue::add;
        }

    }

}
