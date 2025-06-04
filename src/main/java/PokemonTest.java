
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PokemonTest {
    public static void main(String[] args) {
        JSONObject pokeObj = new JSONObject(Pokemon.getPokemonAPI("https://pokeapi.co/api/v2/pokemon?limit=100offset=0"));
        JSONArray pokemonArray = pokeObj .getJSONArray("results");
        JSONObject pokemon = new JSONObject(Pokemon.getPokemonAPI("https://pokeapi.co/api/v2/pokemon/terapages"));

        System.out.println("Name: " + pokemon.get("name"));
        System.out.println("Weight: " + pokemon.get("weight"));
        System.out.println("Base Experience: " + pokemon.get("base_experience"));
        JSONArray pokeStats = pokemon.getJSONArray("stats");
        for(int i = 0; i < pokeStats.length(); i++)
            System.out.printf("\t%s: %d\n",
                    pokeStats.getJSONObject(i).getJSONObject("stat").getString("name"),
                    pokeStats.getJSONObject(i).getInt("base_stat"));
        PokemonAnalysis.initMap();
        System.out.println(PokemonAnalysis.getPokemap().size);
        PokemonAnalysis.printStats();




    }

}
