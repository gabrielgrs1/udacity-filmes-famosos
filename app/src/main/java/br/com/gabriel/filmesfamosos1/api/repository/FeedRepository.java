package br.com.gabriel.filmesfamosos1.api.repository;

import androidx.annotation.NonNull;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.CallbackApiService;
import br.com.gabriel.filmesfamosos1.api.IAPIService;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDetailDto;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDto;
import br.com.gabriel.filmesfamosos1.application.MoviesApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.HttpURLConnection;

public class FeedRepository {

    private CallbackApiService feedListener;
    private CallbackApiService detailResponseListener;
    private IAPIService iApiService;

    public FeedRepository(CallbackApiService feedListener, CallbackApiService detailResponseListener) {
        this.feedListener = feedListener;
        this.detailResponseListener = detailResponseListener;
        iApiService = MoviesApplication.getInstance().getApiClient().getRetrofit().create(IAPIService.class);
    }

    public void getMovieOrderByPopularity(int page) {
        feedListener.startLoading();

        Call<MovieDto> feedResponse = iApiService.getMovieOrderByPopularity(page);
        feedResponse.enqueue(new Callback<MovieDto>() {
            @Override
            public void onResponse(@NonNull Call<MovieDto> call, @NonNull Response<MovieDto> response) {
                feedListener.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    feedListener.onResponse(response);
                } else if (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != HttpURLConnection.HTTP_OK) {
                    feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDto> call, @NonNull Throwable t) {
                feedListener.hideLoading();
                feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    public void getMovieOrderByRating(int page) {
        feedListener.startLoading();

        Call<MovieDto> feedResponse = iApiService.getMovieOrderByRating(page);
        feedResponse.enqueue(new Callback<MovieDto>() {
            @Override
            public void onResponse(@NonNull Call<MovieDto> call, @NonNull Response<MovieDto> response) {
                feedListener.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    feedListener.onResponse(response);
                } else if (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != HttpURLConnection.HTTP_OK) {
                    feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDto> call, @NonNull Throwable t) {
                feedListener.hideLoading();
                feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    public void getMovieDetail(int id) {
        feedListener.startLoading();

        Call<MovieDetailDto> feedResponse = iApiService.getMovieDetail(id);
        feedResponse.enqueue(new Callback<MovieDetailDto>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailDto> call, @NonNull Response<MovieDetailDto> response) {
                feedListener.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    detailResponseListener.onResponse(response);
                } else if (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != HttpURLConnection.HTTP_OK) {
                    feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailDto> call, @NonNull Throwable t) {
                feedListener.hideLoading();
                feedListener.onError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

}
