package br.com.gabriel.filmesfamosos1.api.feed;

import br.com.gabriel.filmesfamosos1.BuildConfig;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FeedService {

    @GET("popular?api_key=" + BuildConfig.API_KEY + "&language=pt-BR")
    Call<FeedDto> getMovieOrderByPopularity(@Query("page") int page);

    @GET("top_rated?api_key=" + BuildConfig.API_KEY + "&language=pt-BR")
    Call<FeedDto> getMovieOrderByRating(@Query("page") int page);

    @GET("{id}?api_key=" + BuildConfig.API_KEY + "&language=pt-BR")
    Call<DetailDto> getMovieDetail(@Path("id") int id);
}
