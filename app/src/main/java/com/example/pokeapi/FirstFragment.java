package com.example.pokeapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pokeapi.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Pokemon> pokemonLista;
    private ArrayAdapter<Pokemon> adapter;
    private PokemonsViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater);
        View view = binding.getRoot();

        pokemonLista = new ArrayList<>();
        adapter = new PokemonAdapter(
                getContext(),
                R.layout.pokemon_list_item,
                pokemonLista
        );

        binding.listaPokemons.setAdapter(adapter);
        binding.listaPokemons.setOnItemClickListener((adapterView, view1, position, id) -> {
            Pokemon pokemon = adapter.getItem(position);
            Bundle navigation = new Bundle();
            navigation.putSerializable("Pokemon", pokemon);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_pokemonDetailsFragment, navigation);
        });

        model = new ViewModelProvider(this).get(PokemonsViewModel.class);
        model.getPokemons().observe(getViewLifecycleOwner(), pokemons -> {
            adapter.clear();
            adapter.addAll(pokemons);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void refresh() {
        model.reload();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<Pokemon> fetchedPokemons = PokeAPI.buscar();
            getActivity().runOnUiThread(() -> {
                pokemonLista.clear();
                pokemonLista.addAll(fetchedPokemons);
                adapter.notifyDataSetChanged();
            });
        });

        binding.listaPokemons.setOnItemClickListener((parent, view1, position, id) -> {
            Pokemon selectedPokemon = adapter.getItem(position);
            Bundle args = new Bundle();
            args.putSerializable("Pokemon", selectedPokemon);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_pokemonDetailsFragment, args);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Toast.makeText(getContext(), "Click hecho", Toast.LENGTH_SHORT).show();
            Log.d("XXXMenu", "CLick");
            refresh();
        } else if (id == R.id.action_settings) {
            Log.d("XXX", "Settings Clicado");
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
