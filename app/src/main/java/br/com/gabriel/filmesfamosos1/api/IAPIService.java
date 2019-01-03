package br.com.gabriel.filmesfamosos1.api;

import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDetailDto;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAPIService {

    @GET("popular?api_key=" + BuildConfig.API_KEY + "&language=pt-BR")
    Call<MovieDto> getMovieOrderByPopularity(@Query("page") int page);

    @GET("top_rated?api_key=" + BuildConfig.API_KEY + "&language=pt-BR")
    Call<MovieDto> getMovieOrderByRating(@Query("page") int page);

    @GET("{id}?api_key=" + BuildConfig.API_KEY + "&language=pt-BR")
    Call<MovieDetailDto> getMovieDetail(@Path("id") int id);
}
