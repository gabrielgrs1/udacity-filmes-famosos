package br.com.gabriel.filmesfamosos1.api.feed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailDto {

    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("belongs_to_collection")
    private Object belongsToCollection;

    @SerializedName("budget")
    private Integer budget;

    @SerializedName("genres")
    private List<Genre> genres = null;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private Integer id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<ProductionCompany> productionCompanies = null;

    @SerializedName("production_countries")
    private List<ProductionCountry> productionCountries = null;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    private Integer revenue;

    @SerializedName("runtime")
    private Integer runtime;

    @SerializedName("spoken_languages")
    private List<SpokenLanguage> spokenLanguages = null;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("vote_count")
    private Integer voteCount;

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    private class Genre {

        @SerializedName("id")
        private Integer id;

        @SerializedName("name")
        private String name;
    }

    private class ProductionCompany {

        @SerializedName("id")
        private Integer id;

        @SerializedName("logo_path")
        private String logoPath;

        @SerializedName("name")
        private String name;

        @SerializedName("origin_country")
        private String originCountry;
    }

    private class ProductionCountry {

        @SerializedName("iso_3166_1")
        private String iso31661;

        @SerializedName("name")
        private String name;
    }

    private class SpokenLanguage {

        @SerializedName("iso_639_1")
        private String iso6391;

        @SerializedName("name")
        private String name;
    }
}