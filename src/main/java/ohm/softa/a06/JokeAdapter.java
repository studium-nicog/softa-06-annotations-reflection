package ohm.softa.a06;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ohm.softa.a06.model.Joke;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.util.List;

public class JokeAdapter extends TypeAdapter<Joke> {

	@Override
	public void write(JsonWriter out, Joke value) {
		throw new NotImplementedException();		// TODO
	}

	@Override
	public Joke read(JsonReader in) throws IOException {
		System.out.println("Gson: JSON -> Object");
		Joke joke = new Joke();

		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
				case "id":
					joke.setIdentifier(in.nextString());
					break;
				case "value":
					joke.setContent(in.nextString());
					break;
				case "categories":
					joke.setRubrics(List.of("TODO"));
					break;
				default:
					throw new IllegalStateException("Unknown field");
			}
		}

		return joke;
	}
}
