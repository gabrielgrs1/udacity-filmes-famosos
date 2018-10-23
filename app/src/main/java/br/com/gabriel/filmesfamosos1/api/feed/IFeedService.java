package br.com.gabriel.filmesfamosos1.api.feed;

public interface IFeedService {

    void getMovieOrderByPopularity(int page);
    void getMovieOrderByRating(int page);
    void getMovieDetail(int movieId);
}
