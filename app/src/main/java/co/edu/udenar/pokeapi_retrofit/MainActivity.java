package co.edu.udenar.pokeapi_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import co.edu.udenar.pokeapi_retrofit.models.Pokemon;
import co.edu.udenar.pokeapi_retrofit.models.PokemonRespuesta;
import co.edu.udenar.pokeapi_retrofit.pokeapi.PokeApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.os.Bundle;
import android.support.v4.*;
import android.support.*;
import android.util.Log;


import java.util.Arrays;




public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private final static String TAG="POKEDESK =>"; // esta constante aparece en cada uno de los mensajes de error de abajo

    private RecyclerView recyclerView;
    private ArrayList<Pokemon> data;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        obtenerDatos();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.mi_recicler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void obtenerDatos(){
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if(response.isSuccessful()){
                    PokemonRespuesta pokemonRespuesta = response.body();

                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    for(int i=0 ; i<listaPokemon.size() ; i++)
                    {
                        Pokemon p = listaPokemon.get(i);
                        Log.i(TAG," Pokemon: "+p.getName()+" url: "+p.getUrl());
                    }

                }else
                {
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                Log.e(TAG," onFailure: "+t.getMessage());
            }
        });
    }
}
