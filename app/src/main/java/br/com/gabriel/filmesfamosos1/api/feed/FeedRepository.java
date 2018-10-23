package br.com.gabriel.filmesfamosos1.api.feed;

import android.support.annotation.NonNull;
import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository implements IFeedService {

    private FeedServiceListener feedListener;
    private DetailResponse detailResponse;
    private APIClient apiClient;

    public FeedRepository(FeedServiceListener feedListener, DetailResponse detailResponse) {
        this.apiClient = MoviesApplication.getInstance().getApiClient();
        this.feedListener = feedListener;
        this.detailResponse = detailResponse;
    }

    @Override
    public void getMovieOrderByPopularity(int page) {
        feedListener.startLoading();

        FeedService feedService = this.apiClient.getRetrofit().create(FeedService.class);
        Call<FeedDto> feedResponse = feedService.getMovieOrderByPopularity(page);

        feedResponse.enqueue(new Callback<FeedDto>() {
            @Override
            public void onResponse(@NonNull Call<FeedDto> call, @NonNull Response<FeedDto> response) {
                feedListener.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    feedListener.response(response.body());
                } else if (response.code() == 500) {
                    feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != 200) {
                    feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedDto> call, @NonNull Throwable t) {
                feedListener.hideLoading();
                feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    @Override
    public void getMovieOrderByRating(int page) {
        feedListener.startLoading();

        FeedService feedService = this.apiClient.getRetrofit().create(FeedService.class);
        Call<FeedDto> feedResponse = feedService.getMovieOrderByRating(page);

        feedResponse.enqueue(new Callback<FeedDto>() {
            @Override
            public void onResponse(@NonNull Call<FeedDto> call, @NonNull Response<FeedDto> response) {
                feedListener.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    feedListener.response(response.body());
                } else if (response.code() == 500) {
                    feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != 200) {
                    feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedDto> call, @NonNull Throwable t) {
                feedListener.hideLoading();
                feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    @Override
    public void getMovieDetail(int id) {
        feedListener.startLoading();

        FeedService feedService = this.apiClient.getRetrofit().create(FeedService.class);
        Call<DetailDto> feedResponse = feedService.getMovieDetail(id);

        feedResponse.enqueue(new Callback<DetailDto>() {
            @Override
            public void onResponse(@NonNull Call<DetailDto> call, @NonNull Response<DetailDto> response) {
                feedListener.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    detailResponse.response(response.body());
                } else if (response.code() == 500) {
                    feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != 200) {
                    feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailDto> call, @NonNull Throwable t) {
                feedListener.hideLoading();
                feedListener.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    public interface FeedServiceListener {
        void response(FeedDto feedDto);

        void startLoading();

        void hideLoading();

        void serverError(String message);
    }

    public interface DetailResponse {
        void response(DetailDto detailDto);
    }

}
