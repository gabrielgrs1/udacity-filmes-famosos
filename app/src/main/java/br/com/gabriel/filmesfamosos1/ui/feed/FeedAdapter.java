package br.com.gabriel.filmesfamosos1.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.api.feed.FeedDto;
import br.com.gabriel.filmesfamosos1.ui.detail.DetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private final List<FeedDto.Result> mMovieList;
    private final Context mContext;

    public FeedAdapter(Context context, List<FeedDto.Result> mMovieList) {
        this.mContext = context;
        this.mMovieList = mMovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View createdView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, viewGroup, false);
        ButterKnife.bind((FeedActivity) mContext);

        return new ViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mMovieList.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_movie_banner_imageview)
        ImageView mBannerImageView;

        @BindView(R.id.item_movie_date_textview)
        TextView mReleaseDateTextView;

        @BindView(R.id.item_movie_title_textview)
        TextView mTitleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(FeedDto.Result movie) {
            mReleaseDateTextView.setText(configureDate(movie.getReleaseDate()));
            mTitleTextView.setText(movie.getTitle());
            setPosterImage(BuildConfig.IMAGE_URL + "/" + movie.getPosterPath().replace(".png", ".svg"));
        }

        private String configureDate(String date) {
            //TODO Extrair para um helper de string
            String[] dateArray = date.split("-");
            date = dateArray[0].trim();
            date = "Lançamento: " + date;

            return date;
        }

        void setPosterImage(String posterUrl) {
            Glide.with(mContext)
                    .load(posterUrl)
                    .thumbnail(0.01f)
                    .into(mBannerImageView);
        }

        @OnClick
        void onClick() {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("moviedId", mMovieList.get(getAdapterPosition()).getId());
            mContext.startActivity(intent);

            //TODO implementar exibição de detalhes
        }
    }
}
