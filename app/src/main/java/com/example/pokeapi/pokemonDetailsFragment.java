package com.example.pokeapi;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pokeapi.databinding.FragmentPokemonDetailsBinding;

public class pokemonDetailsFragment extends Fragment {

    private PokemonDetailsViewModel mViewModel;
    private FragmentPokemonDetailsBinding binding;

    public static pokemonDetailsFragment newInstance() {
        return new pokemonDetailsFragment();
    }

    public pokemonDetailsFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PokemonDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            Pokemon pokemon = (Pokemon) args.getSerializable("Pokemon");
            if (pokemon != null) {
                Log.d("XXXDetail", pokemon.toString());
                showPokemon(pokemon);

            }
        }
    }
    private void showPokemon(Pokemon pokemon) {
        Log.d("POKEMON",pokemon.toString());
        binding.textoPokemonNameDetail.setText(pokemon.getName());
        binding.textoPokemonSptiteDetail.setText(pokemon.getSprite());
        binding.textoPokemonSpecieDetail.setText(pokemon.getSpecies());
        Glide.with(getContext()).load(pokemon.getSprite()).into(binding.imgPokemonSpriteDetail);
    }
}