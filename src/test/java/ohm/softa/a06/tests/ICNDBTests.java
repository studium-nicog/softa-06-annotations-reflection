package ohm.softa.a06.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ohm.softa.a06.ICNDBApi;
import ohm.softa.a06.adapters.JokeAdapter;
import ohm.softa.a06.adapters.JokeArrayAdapter;
import ohm.softa.a06.model.Joke;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Peter Kurfer
 * Created on 11/10/17.
 */
class ICNDBTests {

	private static final Logger logger = LogManager.getLogger(ICNDBTests.class);
	private static final int REQUEST_COUNT = 1000;

	private ICNDBApi icndbApi;

	@BeforeEach
	void setup() {
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(Joke.class, new JokeAdapter())
			.registerTypeAdapter(Joke[].class, new JokeArrayAdapter())
			.create();

		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create(gson))
			.baseUrl("https://api.chucknorris.io")
			.build();

		icndbApi = retrofit.create(ICNDBApi.class);
	}

	@Test
	void testCollision() throws IOException {
		Set<String> jokeNumbers = new HashSet<>();
		int requests = 0;
		boolean collision = false;

		while (requests++ < REQUEST_COUNT) {
			Call<Joke> jokeCall = icndbApi.getRandomJoke();
			Response<Joke> jokeResponse = jokeCall.execute();
			if (!jokeResponse.isSuccessful()) continue;
			Joke j = jokeResponse.body();

			if (jokeNumbers.contains(j.getIdentifier())) {
				logger.info(String.format("Collision at joke %s", j.getIdentifier()));
				collision = true;
				break;
			}

			jokeNumbers.add(j.getIdentifier());
			logger.info(j.toString());
		}

		assertTrue(collision, String.format("Completed %d requests without collision; consider increasing REQUEST_COUNT", requests));
	}

	@Test
	void testGetJokeById() throws IOException {

		List<String> randomIds = new ArrayList<>(10);

		for (int i = 0; i < 10; i++) {
			randomIds.add(icndbApi.getRandomJoke().execute().body().getIdentifier());
		}

		for (String id : randomIds) {
			Joke j = icndbApi.getJoke(id).execute().body();
			assertNotNull(j);
			assertTrue(randomIds.contains(j.getIdentifier()));
			logger.info(j.toString());
		}
	}

	@Test
	void testGetJokeByQuery() throws IOException {

		Joke[] jokes = icndbApi.getJokesBySearch("horse").execute().body();

		assert jokes != null;
		for (Joke j : jokes) {
			assertNotNull(j);
			logger.info(j.getContent());
			assertTrue(j.getContent().toLowerCase().contains("horse"));
		}

	}
}
