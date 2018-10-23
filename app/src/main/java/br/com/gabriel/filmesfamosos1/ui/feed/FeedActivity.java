package br.com.gabriel.filmesfamosos1.ui.feed;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.feed.DetailDto;
import br.com.gabriel.filmesfamosos1.api.feed.FeedDto;
import br.com.gabriel.filmesfamosos1.api.feed.FeedRepository;
import br.com.gabriel.filmesfamosos1.ui.GenericActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends GenericActivity implements FeedRepository.FeedServiceListener, FeedRepository.DetailResponse {


    @BindView(R.id.feed_movie_recyclerview)
    RecyclerView mMovieRecyclerView;

    private ProgressDialog progressDialog;
    private List<FeedDto.Result> mMovieResultList;
    private FeedAdapter mFeedAdapter;
    private int mPage = 0;
    private int mTotalPages = 1;
    private String mOrdenation = "popularity";

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
    }

    @Override
    public void loadingMethods() {
        configureRecyclerView();
        getMoviesOrderByPopularity(1);

        mMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mPage < mTotalPages) {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (mOrdenation.equals("popularity")) {
                            getMoviesOrderByPopularity(mPage + 1);
                        } else if (mOrdenation.equals("rated")) {
                            getMoviesOrderByRating(mPage + 1);
                        }
                    }
                }
            }
        });

    }

    private void configureRecyclerView() {
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void configureAdapter() {
        mFeedAdapter = new FeedAdapter(this, mMovieResultList);
        mMovieRecyclerView.setAdapter(mFeedAdapter);
    }

    @Override
    public void response(FeedDto feedDto) {
        if (mPage == 0) {
            mMovieResultList = feedDto.getResults();
            configureAdapter();
        } else {
            mMovieResultList.addAll(feedDto.getResults());
            mFeedAdapter.notifyDataSetChanged();
        }

        mTotalPages = feedDto.getTotalPages();
        mPage = feedDto.getPage();
    }

    @Override
    public void startLoading() {
        if (mPage == 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Aguarde");
            progressDialog.setMessage("Carregando lista de filmes");
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
                mOrdenation = "popularity";
                mPage = 0;
                mTotalPages = 1;
                getMoviesOrderByPopularity(1);
                break;
            case R.id.menu_ordenation_top_rated_item:
                mOrdenation = "rated";
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
}
