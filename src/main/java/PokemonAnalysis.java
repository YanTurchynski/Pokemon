import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class PokemonAnalysis {

    private static final Map<String, JSONObject> pokeMap = new HashMap<>();

    public static void initMap() {
        String url = "https://pokeapi.co/api/v2/pokemon?limit=100&offset=0";
        JSONObject response = new JSONObject(Pokemon.getPokemonAPI(url));
        JSONArray results = response.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject obj = results.getJSONObject(i);
            String name = obj.getString("name");
            String detailUrl = obj.getString("url");

            JSONObject fullDetails = new JSONObject(Pokemon.getPokemonAPI(detailUrl));
            pokeMap.put(name, fullDetails);
        }
    }

    public static Map<String, JSONObject> getPokemap() {
        return pokeMap;
    }

    public static void printStats() {
        for (Map.Entry<String, JSONObject> entry : pokeMap.entrySet()) {
            JSONObject pokemon = entry.getValue();
            System.out.println("Name: " + pokemon.getString("name"));
            System.out.println("Base Experience: " + pokemon.getInt("base_experience"));
            System.out.println("Height: " + pokemon.getInt("height"));
            System.out.println("Weight: " + pokemon.getInt("weight"));
            System.out.println();
        }
    }

    //  1 Find the Pokémon type with the highest average base experience.
    public static String typeWithHighestAvgBaseExp() {
        Map<String, List<Integer>> typeToBaseExp = new HashMap<>();

        for (JSONObject p : pokeMap.values()) {
            int baseExp = p.getInt("base_experience");
            JSONArray types = p.getJSONArray("types");

            for (int i = 0; i < types.length(); i++) {
                String typeName = types.getJSONObject(i).getJSONObject("type").getString("name");
                typeToBaseExp.computeIfAbsent(typeName, k -> new ArrayList<>()).add(baseExp);
            }
        }

        return typeToBaseExp.entrySet().stream()
                .max(Comparator.comparingDouble(e -> e.getValue().stream().mapToInt(i -> i).average().orElse(0)))
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }

    // 2 Total weight per type

    public static Map<String, Integer> totalWeightPerType() {
        Map<String, Integer> typeToWeight = new HashMap<>();

        for (JSONObject p : pokeMap.values()) {
            int weight = p.getInt("weight");
            JSONArray types = p.getJSONArray("types");

            for (int i = 0; i < types.length(); i++) {
                String typeName = types.getJSONObject(i).getJSONObject("type").getString("name");
                typeToWeight.put(typeName, typeToWeight.getOrDefault(typeName, 0) + weight);
            }
        }

        return typeToWeight;
    }

    // 3 List all Pokémon names where height exceeds a given threshold

    public static List<String> pokemonAboveHeight(int threshold) {
        return pokeMap.values().stream()
                .filter(p -> p.getInt("height") > threshold)
                .map(p -> p.getString("name"))
                .collect(Collectors.toList());
    }

    // 4 Find the top 3 Pokémon with the highest attack stat

    public static List<String> top3Attackers() {
        return pokeMap.values().stream()
                .sorted((a, b) -> Integer.compare(
                        getStatValue(b, "attack"),
                        getStatValue(a, "attack")
                ))
                .limit(3)
                .map(p -> p.getString("name"))
                .collect(Collectors.toList());
    }

    // 5 Calculate the percentage of Pokémon above the average speed stat.

    public static double percentageAboveAverageSpeed() {
        List<JSONObject> allPokemon = new ArrayList<>(pokeMap.values());

        double avgSpeed = allPokemon.stream()
                .mapToInt(p -> getStatValue(p, "speed"))
                .average()
                .orElse(0);

        long countAboveAvg = allPokemon.stream()
                .filter(p -> getStatValue(p, "speed") > avgSpeed)
                .count();

        return 100.0 * countAboveAvg / allPokemon.size();
    }

    private static int getStatValue(JSONObject pokemon, String statName) {
        JSONArray stats = pokemon.getJSONArray("stats");
        for (int i = 0; i < stats.length(); i++) {
            JSONObject stat = stats.getJSONObject(i);
            String name = stat.getJSONObject("stat").getString("name");
            if (name.equals(statName)) {
                return stat.getInt("base_stat");
            }
        }
        return 0;
    }
}