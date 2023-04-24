package ohm.softa.a06.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ohm.softa.a06.model.Joke;

import java.io.IOException;

/**
 * @author Peter Kurfer
 * Created on 11/13/17.
 */
public class JokeAdapter extends TypeAdapter<Joke> {

	private final Gson gson;

	public JokeAdapter() {
		gson = new Gson();
	}

	@Override
	public void write(JsonWriter out, Joke value) throws IOException {
		/* won't implement because we don't want to send requests to the API */
	}

	@Override
	public Joke read(JsonReader in) throws IOException {
		Joke result = null;

		result = gson.fromJson(in, Joke.class);

		return result;
	}
}
