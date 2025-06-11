import org.json.JSONArray;
import org.json.JSONObject;

public class PokemonTest {
    public static void main(String[] args) {
        // Basic single Pokémon test
        JSONObject pokeObj = new JSONObject(Pokemon.getPokemonAPI("https://pokeapi.co/api/v2/pokemon?limit=100&offset=0"));
        JSONArray pokemonArray = pokeObj.getJSONArray("results");

        JSONObject pokemon = new JSONObject(Pokemon.getPokemonAPI("https://pokeapi.co/api/v2/pokemon/pikachu"));
        System.out.println("Name: " + pokemon.get("name"));
        System.out.println("Weight: " + pokemon.get("weight"));
        System.out.println("Base Experience: " + pokemon.get("base_experience"));

        JSONArray pokeStats = pokemon.getJSONArray("stats");
        for (int i = 0; i < pokeStats.length(); i++) {
            System.out.printf("\t%s: %d\n",
                    pokeStats.getJSONObject(i).getJSONObject("stat").getString("name"),
                    pokeStats.getJSONObject(i).getInt("base_stat"));
        }

        // Init Pokémon data
        System.out.println("\nInitializing map...");
        PokemonAnalysis.initMap();
        System.out.println("Total Pokémon loaded: " + PokemonAnalysis.getPokemap().size());

        System.out.println("\n--- Pokémon Stats ---");
        PokemonAnalysis.printStats();

        // Task 1
        System.out.println("\n1. Pokémon type with highest average base experience");
        System.out.println(PokemonAnalysis.typeWithHighestAvgBaseExp());

        // Task 2
        System.out.println("\n2. Total weight per Pokémon type");
        PokemonAnalysis.totalWeightPerType().forEach((type, weight) ->
                System.out.println(type + ": " + weight));

        // Task 3
        System.out.println("\n3. Pokémon taller than 10");
        PokemonAnalysis.pokemonAboveHeight(10).forEach(System.out::println);

        // Task 4
        System.out.println("\n4. Top 3 Pokémon by attack");
        PokemonAnalysis.top3Attackers().forEach(System.out::println);

        // Task 5
        System.out.println("\n5. Percentage of Pokémon above average speed");
        System.out.printf("%.2f%%\n", PokemonAnalysis.percentageAboveAverageSpeed());

        // Task 6
        System.out.println("\n6. Map from Pokémon type to unique abilities (JSON):");
        System.out.println(PokemonAnalysis.typeToAbilitiesJson().toString(2));

        // Task 7
        System.out.println("\n7. Average attack of Pokémon weighing more than 2000");
        System.out.println("Average Attack: " + PokemonAnalysis.averageAttackOverWeight(2000));

        // Task 8
        System.out.println("\n8. Sorted list of Pokémon names (uppercase):");
        PokemonAnalysis.sortedUppercasePokemonNames().forEach(System.out::println);

        // Task 9
        System.out.println("\n9. Pokémon with largest attack-defense difference:");
        System.out.println("Name: " + PokemonAnalysis.pokemonWithLargestAttackDefenseDiff());

        // Task 10
        System.out.println("\n10. Partitioned Pokémon by legendary status (JSON):");
        System.out.println(PokemonAnalysis.partitionLegendaryStatusJson().toString(2));
    }
}
