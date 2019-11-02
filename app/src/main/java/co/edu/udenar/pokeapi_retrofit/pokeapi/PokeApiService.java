package co.edu.udenar.pokeapi_retrofit.pokeapi;

import co.edu.udenar.pokeapi_retrofit.models.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApiService {

    @GET("pokemon") //ultima parte de la url del repositorio
    Call<PokemonRespuesta> obtenerListaPokemon();

}
