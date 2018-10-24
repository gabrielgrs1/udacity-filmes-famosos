package br.com.gabriel.filmesfamosos1.api.feed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FeedDto implements Serializable {

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("results")
    private List<Result> results = null;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result implements Serializable {

        @SerializedName("vote_count")
        private Integer voteCount;

        @SerializedName("id")
        private Integer id;

        @SerializedName("video")
        private Boolean video;

        @SerializedName("vote_average")
        private Double voteAverage;

        @SerializedName("title")
        private String title;

        @SerializedName("popularity")
        private Double popularity;

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("original_language")
        private String originalLanguage;

        @SerializedName("original_title")
        private String originalTitle;

        @SerializedName("genre_ids")
        private List<Integer> genreIds = null;

        @SerializedName("backdrop_path")
        private String backdropPath;

        @SerializedName("adult")
        private Boolean adult;

        @SerializedName("overview")
        private String overview;

        @SerializedName("release_date")
        private String releaseDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public String getReleaseDate() {
            return releaseDate;
        }
    }

}