package ohm.softa.a06;

import ohm.softa.a06.model.Joke;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Peter Kurfer
 * Created on 11/10/17.
 */
public interface CNJDBApi {

	@GET("/jokes/random")
	Call<Joke> getRandomJoke();

	@GET("/jokes/random")
	Call<Joke> getRandomJoke(@Query("categories") String[] categories);

	@GET("/jokes/search")
	Call<Joke[]> getJokesBySearch(@Query("query") String query);

	@GET("/jokes/{id}")
	Call<Joke> getJoke(@Path("id") String identifier);
}
