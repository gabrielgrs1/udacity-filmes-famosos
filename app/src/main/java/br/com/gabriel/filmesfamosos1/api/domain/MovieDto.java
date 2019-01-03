package br.com.gabriel.filmesfamosos1.api.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDto implements Parcelable {

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("results")
    private List<Result> results;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result implements Parcelable {

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
        private List<Integer> genreIds;

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

        Result(Parcel in) {
            voteCount = in.readByte() == 0x00 ? null : in.readInt();
            id = in.readByte() == 0x00 ? null : in.readInt();
            byte videoVal = in.readByte();
            video = videoVal == 0x02 ? null : videoVal != 0x00;
            voteAverage = in.readByte() == 0x00 ? null : in.readDouble();
            title = in.readString();
            popularity = in.readByte() == 0x00 ? null : in.readDouble();
            posterPath = in.readString();
            originalLanguage = in.readString();
            originalTitle = in.readString();
            if (in.readByte() == 0x01) {
                genreIds = new ArrayList<>();
                in.readList(genreIds, Integer.class.getClassLoader());
            } else {
                genreIds = null;
            }
            backdropPath = in.readString();
            byte adultVal = in.readByte();
            adult = adultVal == 0x02 ? null : adultVal != 0x00;
            overview = in.readString();
            releaseDate = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (voteCount == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeInt(voteCount);
            }
            if (id == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeInt(id);
            }
            if (video == null) {
                dest.writeByte((byte) (0x02));
            } else {
                dest.writeByte((byte) (video ? 0x01 : 0x00));
            }
            if (voteAverage == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeDouble(voteAverage);
            }
            dest.writeString(title);
            if (popularity == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeDouble(popularity);
            }
            dest.writeString(posterPath);
            dest.writeString(originalLanguage);
            dest.writeString(originalTitle);
            if (genreIds == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(genreIds);
            }
            dest.writeString(backdropPath);
            if (adult == null) {
                dest.writeByte((byte) (0x02));
            } else {
                dest.writeByte((byte) (adult ? 0x01 : 0x00));
            }
            dest.writeString(overview);
            dest.writeString(releaseDate);
        }

        @SuppressWarnings("unused")
        public final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
    }

    private MovieDto(Parcel in) {
        page = in.readByte() == 0x00 ? null : in.readInt();
        totalResults = in.readByte() == 0x00 ? null : in.readInt();
        totalPages = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            results = new ArrayList<>();
            in.readList(results, Result.class.getClassLoader());
        } else {
            results = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (page == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(page);
        }
        if (totalResults == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalResults);
        }
        if (totalPages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalPages);
        }
        if (results == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(results);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieDto> CREATOR = new Parcelable.Creator<MovieDto>() {
        @Override
        public MovieDto createFromParcel(Parcel in) {
            return new MovieDto(in);
        }

        @Override
        public MovieDto[] newArray(int size) {
            return new MovieDto[size];
        }
    };
}