package ohm.softa.a06;

import com.google.gson.GsonBuilder;
import ohm.softa.a06.model.Joke;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * @author Peter Kurfer
 * Created on 11/10/17.
 */
public class App {

	public static void main(String[] args) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Joke.class, new JokeAdapter());

		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.chucknorris.io")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		var chuckNorrisService = retrofit.create(CNJDBApi.class);

		try {
			System.out.println(chuckNorrisService.getRandomJoke().execute().body());
		} catch (IOException e) {
			System.err.println("Failed to get joke");
		}

		System.exit(0);
	}
}
