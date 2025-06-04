import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Pokemon {
    public static String getPokemonAPI(String urlToRead) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URI(urlToRead).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            con.disconnect();
        } catch (IOException |
                 URISyntaxException err) {
            throw new RuntimeException(err);
        }
        return result.toString();
    }
}

