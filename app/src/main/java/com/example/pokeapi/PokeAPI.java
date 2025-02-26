package com.example.pokeapi;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.OptIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PokeAPI {
    String BASE_URL = "https://pokeapi.co/api/v2/pokemon";

    String getNames(String name){
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                /* .appendPath("pokemon")
                   .appendQueryParameter("limit", "30")*/
                .build();
        String url = builtUri.toString();

        return doCall(url);
    }

    public static ArrayList<Pokemon> buscar() {
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        try {
            String response = HttpUtils.get("https://pokeapi.co/api/v2/pokemon?limit=20");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject pokemonObj = results.getJSONObject(i);
                String name = pokemonObj.getString("name");
                String url = pokemonObj.getString("url");

                String pokemonsDetailsResponse = HttpUtils.get(url);
                JSONObject pokemonDetails = new JSONObject(pokemonsDetailsResponse);

                Log.d("DEBUG", "JSON de " + name + ": " + pokemonDetails);

                Double id = pokemonDetails.getDouble("id");
                String species = pokemonDetails.getJSONObject("species").getString("name");
                Integer weight = pokemonDetails.getInt("weight");
                JSONObject spriteObject = pokemonDetails.getJSONObject("sprites");
                String sprite = spriteObject.getString("front_default");

                Pokemon pokemon = new Pokemon(id,name,species,weight,sprite);
                pokemons.add(pokemon);
            }
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }
    private String doCall(String url){
        try {
            String JsonResponse = HttpUtils.get(url);
            return JsonResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
