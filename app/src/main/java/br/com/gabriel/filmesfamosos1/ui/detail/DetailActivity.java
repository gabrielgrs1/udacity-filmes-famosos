package br.com.gabriel.filmesfamosos1.ui.detail;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.CallbackApiService;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDetailDto;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDto;
import br.com.gabriel.filmesfamosos1.api.repository.FeedRepository;
import br.com.gabriel.filmesfamosos1.application.MoviesApplication;
import br.com.gabriel.filmesfamosos1.ui.GenericActivity;
import br.com.gabriel.filmesfamosos1.utils.formatter.StringFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import retrofit2.Response;

import java.util.Objects;

public class DetailActivity extends GenericActivity implements CallbackApiService {

    private static final String MOVIE_KEY_BUNDLE = "movie";
    @BindView(R.id.detail_movie_banner_background_imageview)
    ImageView mBannerBackgroundImageView;

    @BindView(R.id.detail_movie_banner_imageview)
    ImageView mBannerImageView;

    @BindView(R.id.detail_movie_overview_textview)
    TextView mOverviewTextView;

    @BindView(R.id.detail_movie_release_date_textview)
    TextView mReleaseDateTextView;

    @BindView(R.id.detail_movie_user_rate_textview)
    TextView mUserRateTextView;

    private ProgressDialog progressDialog;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void loadingMethods() {
        getMovieBundle();
        setHomeButton();
    }

    @Override
    public void onResponse(Response response) {
        setMovieDetail((MovieDetailDto) response.body());
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(MoviesApplication.getInstance().getResources().getString(R.string.dialog_title_wait));
        progressDialog.setMessage(MoviesApplication.getInstance().getResources().getString(R.string.dialog_body_loading_movie_infos));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    private void getMovieBundle() {
        Bundle bundle = getIntent().getExtras();
        if (checkInternet() && bundle != null) {
            MovieDto.Result movie = (MovieDto.Result) bundle.getSerializable(MOVIE_KEY_BUNDLE);

            if (movie != null) {
                new FeedRepository(this, this).getMovieDetail(movie.getId());
            }
        }
    }

    private void setMovieDetail(MovieDetailDto movie) {
        setMovieTextDetail(movie);
        setMovieImageDetail(movie);
    }

    private void setMovieImageDetail(MovieDetailDto movie) {
        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + "/" + movie.getBackdropPath().replace(".png", ".svg"))
                .into(mBannerBackgroundImageView);

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + "/" + movie.getPosterPath().replace(".png", ".svg"))
                .thumbnail(0.01f)
                .into(mBannerImageView);
    }

    @SuppressLint("SetTextI18n")
    private void setMovieTextDetail(MovieDetailDto movie) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getTitle());
        mUserRateTextView.setText(Double.toString(movie.getVoteAverage()));
        mReleaseDateTextView.setText(StringFormatter.configureDate(movie.getReleaseDate()));
        mOverviewTextView.setText(StringFormatter.verifyHaveOverview(movie.getOverview()));


    }
}
