package br.com.gabriel.filmesfamosos1.ui.feed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.feed.DetailDto;
import br.com.gabriel.filmesfamosos1.api.feed.FeedDto;
import br.com.gabriel.filmesfamosos1.api.feed.FeedRepository;
import br.com.gabriel.filmesfamosos1.ui.GenericActivity;
import br.com.gabriel.filmesfamosos1.ui.detail.DetailActivityListener;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;
import java.util.Objects;

public class FeedActivity extends GenericActivity implements FeedRepository.FeedServiceListener, FeedRepository.DetailResponseListener, SwipeRefreshLayout.OnRefreshListener {


    private static final String POPULARITY = "popularity";
    private static final String RATED = "rated";
    private static final String MOVIE_KEY_BUNDLE = "movie";

    @BindView(R.id.feed_movie_recyclerview)
    RecyclerView mMovieRecyclerView;

    @BindView(R.id.feed_movie_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressDialog progressDialog;
    private List<FeedDto.Result> mMovieResultList;
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
    public void response(FeedDto movie) {
        mSwipeRefreshLayout.setRefreshing(false);

        setRecyclerDataBy(movie);
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
    public void serverError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void response(DetailDto detailDto) {

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

    public void goToDetailBy(FeedDto.Result movie) {
        Intent intent = new Intent(this, DetailActivityListener.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MOVIE_KEY_BUNDLE, movie);
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
        mSwipeRefreshLayout.setOnRefreshListener(this::getMoviesByOrdenation);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light);
    }

    private void configureToolbar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(MoviesApplication.getInstance().getResources().getString(R.string.toolbar_most_popular));
    }

    private void setRecyclerDataBy(FeedDto movie) {
        if (mPage == 0) {
            mMovieResultList = movie.getResults();
            configureAdapter();
        } else {
            mMovieResultList.addAll(movie.getResults());
            mFeedAdapter.notifyDataSetChanged();
        }

        configurePagesBy(movie);
    }

    private void configurePagesBy(FeedDto movie) {
        mTotalPages = movie.getTotalPages();
        mPage = movie.getPage();
    }
}
