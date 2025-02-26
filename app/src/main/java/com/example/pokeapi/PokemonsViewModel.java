package com.example.pokeapi;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class PokemonsViewModel extends AndroidViewModel {
    private final Application app;
    private final AppDatabase appDataBase;
    private final PokemonDao pokemonDAO;


    public PokemonsViewModel(Application app) {
        super(app);

        this.app = app;
        this.appDataBase = AppDatabase.getDatabase(this.getApplication());
        this.pokemonDAO = appDataBase.getPokemonDao();
    }

    public LiveData<List<Pokemon>> getPokemons() {
        return pokemonDAO.getPokemons();
    }

    public void reload() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }

    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                    app.getApplicationContext()
            );

            PokeAPI api = new PokeAPI();
            ArrayList<Pokemon> result;

            result = api.buscar();

            pokemonDAO.deletePokemons();
            pokemonDAO.addPokemons(result);

            return null;
        }
    }
}
