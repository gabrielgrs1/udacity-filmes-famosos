package br.com.gabriel.filmesfamosos1.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.feed.DetailDto;
import br.com.gabriel.filmesfamosos1.api.feed.FeedDto;
import br.com.gabriel.filmesfamosos1.api.feed.FeedRepository;
import br.com.gabriel.filmesfamosos1.ui.GenericActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;

public class DetailActivity extends GenericActivity implements FeedRepository.DetailResponse, FeedRepository.FeedServiceListener {

    @BindView(R.id.detail_movie_banner_background_imageview)
    ImageView mBannerBackgroundImageView;

    @BindView(R.id.detail_movie_banner_imageview)
    ImageView mBannerImageView;

    @BindView(R.id.detail_movie_sinopse_textview)
    TextView mSinopseTextView;

    @BindView(R.id.detail_movie_release_date_textview)
    TextView mReleaseDateTextView;

    @BindView(R.id.detail_movie_user_rate_text_textview)
    TextView mUserRateTextView;

    @BindView(R.id.detail_movie_user_rate_ratingbar)
    RatingBar mUserRateRatingBar;

    @BindView(R.id.detail_movie_title_textview)
    TextView mTitleTextView;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void loadingMethods() {
        if (checkInternet()) {
            Bundle bundle = getIntent().getExtras();
            int movieId = bundle.getInt("movieId");

            new FeedRepository(this, this).getMovieDetail(movieId);
        }
    }

    @Override
    public void response(DetailDto detailDto) {
        mTitleTextView.setText(detailDto.getTitle());
        mUserRateTextView.setText(Double.toString(detailDto.getVoteAverage()));
        mReleaseDateTextView.setText(detailDto.getReleaseDate());
        mSinopseTextView.setText(detailDto.getOverview());

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + "/" + detailDto.getBackdropPath().replace(".png", ".svg"))
                .thumbnail(0.001f)
                .into(mBannerBackgroundImageView);

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL + "/" + detailDto.getPosterPath().replace(".png", ".svg"))
                .thumbnail(0.01f)
                .into(mBannerImageView);
    }

    @Override
    public void response(FeedDto feedDto) {

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void serverError(String message) {

    }
}
