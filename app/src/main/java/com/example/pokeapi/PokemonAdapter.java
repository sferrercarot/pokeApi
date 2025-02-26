package com.example.pokeapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {
    public PokemonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Pokemon> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pokemon pokemon = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());


        if (convertView == null){

            /*  inflater = LayoutInflater.from(getContext());*/

            convertView = inflater.inflate(R.layout.pokemon_list_item, parent, false);
        }

        TextView textoPokemonName = convertView.findViewById(R.id.textoPokemonName);
        TextView textoPokemonSpecie = convertView.findViewById(R.id.textoPokemonSpecie);
        ImageView imgPokemonSprite = convertView.findViewById(R.id.imgPokemonSprite);

        textoPokemonName.setText(pokemon.getName());
        textoPokemonSpecie.setText(pokemon.getSpecies());

        Glide.with(getContext()).load(pokemon.getSprite()).into(imgPokemonSprite);



        return convertView;
    }
}
