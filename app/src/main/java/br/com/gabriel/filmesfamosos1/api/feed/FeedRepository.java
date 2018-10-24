package br.com.gabriel.filmesfamosos1.api.feed;

import android.support.annotation.NonNull;
import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository implements IFeedService {

    private final FeedServiceListener FEED_LISTENER;
    private final DetailResponseListener DETAIL_RESPONSE_LISTENER;
    private final APIClient API_CLIENT;

    public FeedRepository(FeedServiceListener feedListener, DetailResponseListener DETAIL_RESPONSE_LISTENER) {
        this.API_CLIENT = MoviesApplication.getInstance().getApiClient();
        this.FEED_LISTENER = feedListener;
        this.DETAIL_RESPONSE_LISTENER = DETAIL_RESPONSE_LISTENER;
    }

    @Override
    public void getMovieOrderByPopularity(int page) {
        FEED_LISTENER.startLoading();

        FeedService feedService = this.API_CLIENT.getRetrofit().create(FeedService.class);
        Call<FeedDto> feedResponse = feedService.getMovieOrderByPopularity(page);

        feedResponse.enqueue(new Callback<FeedDto>() {
            @Override
            public void onResponse(@NonNull Call<FeedDto> call, @NonNull Response<FeedDto> response) {
                FEED_LISTENER.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    FEED_LISTENER.response(response.body());
                } else if (response.code() == 500) {
                    FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != 200) {
                    FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedDto> call, @NonNull Throwable t) {
                FEED_LISTENER.hideLoading();
                FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    @Override
    public void getMovieOrderByRating(int page) {
        FEED_LISTENER.startLoading();

        FeedService feedService = this.API_CLIENT.getRetrofit().create(FeedService.class);
        Call<FeedDto> feedResponse = feedService.getMovieOrderByRating(page);

        feedResponse.enqueue(new Callback<FeedDto>() {
            @Override
            public void onResponse(@NonNull Call<FeedDto> call, @NonNull Response<FeedDto> response) {
                FEED_LISTENER.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    FEED_LISTENER.response(response.body());
                } else if (response.code() == 500) {
                    FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != 200) {
                    FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedDto> call, @NonNull Throwable t) {
                FEED_LISTENER.hideLoading();
                FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    @Override
    public void getMovieDetail(int id) {
        FEED_LISTENER.startLoading();

        FeedService feedService = this.API_CLIENT.getRetrofit().create(FeedService.class);
        Call<DetailDto> feedResponse = feedService.getMovieDetail(id);

        feedResponse.enqueue(new Callback<DetailDto>() {
            @Override
            public void onResponse(@NonNull Call<DetailDto> call, @NonNull Response<DetailDto> response) {
                FEED_LISTENER.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    DETAIL_RESPONSE_LISTENER.response(response.body());
                } else if (response.code() == 500) {
                    FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
                } else if (response.code() != 200) {
                    FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_unkown_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailDto> call, @NonNull Throwable t) {
                FEED_LISTENER.hideLoading();
                FEED_LISTENER.serverError(MoviesApplication.getInstance().getString(R.string.generic_server_error));
            }
        });
    }

    public interface FeedServiceListener {
        void response(FeedDto feedDto);

        void startLoading();

        void hideLoading();

        void serverError(String message);
    }

    public interface DetailResponseListener {
        void response(DetailDto detailDto);
    }

}
