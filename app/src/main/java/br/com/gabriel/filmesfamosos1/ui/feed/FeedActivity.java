package br.com.gabriel.filmesfamosos1.ui.feed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.CallbackApiService;
import br.com.gabriel.filmesfamosos1.api.domain.MovieDto;
import br.com.gabriel.filmesfamosos1.api.repository.FeedRepository;
import br.com.gabriel.filmesfamosos1.application.MoviesApplication;
import br.com.gabriel.filmesfamosos1.ui.GenericActivity;
import br.com.gabriel.filmesfamosos1.ui.detail.DetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

public class FeedActivity extends GenericActivity implements CallbackApiService, SwipeRefreshLayout.OnRefreshListener {


    private static final String POPULARITY = "popularity";
    private static final String RATED = "rated";
    private static final String MOVIE_KEY_BUNDLE = "movie";

    @BindView(R.id.feed_movie_recyclerview)
    RecyclerView mMovieRecyclerView;

    @BindView(R.id.feed_movie_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressDialog progressDialog;
    private List<MovieDto.Result> mMovieResultList;
    private FeedAdapter mFeedAdapter;
    private int mPage = 0;
    private int mTotalPages = 1;
    private String mOrdenation = POPULARITY;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
    }

    @Override
    public void loadingMethods() {
        configureRecyclerView();
        getMoviesOrderByPopularity(1);
        configureScrollListener();
        configureRefreshLayout();
        configureToolbar();
    }

    @Override
    public void onResponse(Response response) {
        if (response.body() instanceof MovieDto) {
            mSwipeRefreshLayout.setRefreshing(false);
            setRecyclerDataBy((MovieDto) response.body());
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        if (mPage == 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(MoviesApplication.getInstance().getResources().getString(R.string.dialog_title_wait));
            progressDialog.setMessage(MoviesApplication.getInstance().getResources().getString(R.string.dialog_body_loading_movies));
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ordenation_popularity_item:
                Objects.requireNonNull(getSupportActionBar()).setTitle(MoviesApplication.getInstance().getResources().getString(R.string.toolbar_most_popular));
                mOrdenation = POPULARITY;
                mPage = 0;
                mTotalPages = 1;
                getMoviesOrderByPopularity(1);
                break;
            case R.id.menu_ordenation_top_rated_item:
                Objects.requireNonNull(getSupportActionBar()).setTitle(MoviesApplication.getInstance().getResources().getString(R.string.toolbar_best_rated));
                mOrdenation = RATED;
                mTotalPages = 1;
                mPage = 0;
                getMoviesOrderByRating(1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ordenation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        getMoviesByOrdenation();
    }

    private void configureScrollListener() {
        mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                getNextMoviePages(recyclerView);
            }
        });
    }

    private void getNextMoviePages(@NonNull RecyclerView recyclerView) {
        if (mPage < mTotalPages) {
            if (!recyclerView.canScrollVertically(1)) {
                if (mOrdenation.equals(POPULARITY)) {
                    getMoviesOrderByPopularity(mPage + 1);
                } else if (mOrdenation.equals(RATED)) {
                    getMoviesOrderByRating(mPage + 1);
                }
            }
        }
    }

    private void configureRecyclerView() {
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void configureAdapter() {
        mFeedAdapter = new FeedAdapter(this, mMovieResultList);
        mMovieRecyclerView.setAdapter(mFeedAdapter);
    }

    private void getMoviesOrderByPopularity(int page) {
        if (checkInternet()) {
            new FeedRepository(this, this).getMovieOrderByPopularity(page);
        }
    }

    private void getMoviesOrderByRating(int page) {
        if (checkInternet()) {
            new FeedRepository(this, this).getMovieOrderByRating(page);
        }
    }

    public void goToDetailBy(MovieDto.Result movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY_BUNDLE, movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getMoviesByOrdenation() {
        if (mOrdenation.equals(POPULARITY)) {
            getMoviesOrderByPopularity(1);
        } else {
            getMoviesOrderByRating(1);
        }
    }

    private void configureRefreshLayout() {
        if (mMovieResultList != null) {
            mMovieResultList.clear();
        }

        mSwipeRefreshLayout.setOnRefreshListener(this::getMoviesByOrdenation);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light);
    }

    private void configureToolbar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(MoviesApplication.getInstance().getResources().getString(R.string.toolbar_most_popular));
    }

    private void setRecyclerDataBy(MovieDto movie) {
        if (mPage == 0) {
            mMovieResultList = movie.getResults();
            configureAdapter();
        } else {
            mMovieResultList.addAll(movie.getResults());
            mFeedAdapter.notifyDataSetChanged();
        }

        configurePagesBy(movie);
    }

    private void configurePagesBy(MovieDto movie) {
        mTotalPages = movie.getTotalPages();
        mPage = movie.getPage();
    }
}
