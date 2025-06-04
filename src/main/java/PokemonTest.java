
import org.json.JSONArray;
import org.json.JSONObject;


public class PokemonTest {
    public static void main(String[] args) {
        JSONObject pokeObj = new JSONObject(Pokemon.getPokemonAPI("https://pokeapi.co/api/v2/pokemon?limit=100offset=0"));
        JSONArray pokemonArray = pokeObj .getJSONArray("results");
        JSONObject pokemon = new JSONObject(Pokemon.getPokemonAPI("https://pokeapi.co/api/v2/pokemon/pikachu"));

        System.out.println("Name: " + pokemon.get("name"));
        System.out.println("Weight: " + pokemon.get("weight"));
        System.out.println("Base Experience: " + pokemon.get("base_experience"));
        JSONArray pokeStats = pokemon.getJSONArray("stats");
        for(int i = 0; i < pokeStats.length(); i++)
            System.out.printf("\t%s: %d\n",
                    pokeStats.getJSONObject(i).getJSONObject("stat").getString("name"),
                    pokeStats.getJSONObject(i).getInt("base_stat"));
        PokemonAnalysis.initMap();
        System.out.println(PokemonAnalysis.getPokemap().size());

        PokemonAnalysis.printStats();

        // print task 1
        System.out.println(" Pokémon type with highest average base experience");
        System.out.println(PokemonAnalysis.typeWithHighestAvgBaseExp());

        // print task 2
        System.out.println(" Total weight per Pokemon type");
        PokemonAnalysis.totalWeightPerType().forEach((type, totalWeight) ->
                System.out.println(type + ": " + totalWeight));

        // print task 3
        System.out.println(" Pokémon taller than 10");
        PokemonAnalysis.pokemonAboveHeight(10).forEach(System.out::println);

        // print task 4
        System.out.println("Top 3 Pokémon by attack");
        PokemonAnalysis.top3Attackers().forEach(System.out::println);

        // print task 5
        System.out.println("Percentage of Pokémon Above Average Speed");
        System.out.printf("%.2f%%\n", PokemonAnalysis.percentageAboveAverageSpeed());






    }

}
