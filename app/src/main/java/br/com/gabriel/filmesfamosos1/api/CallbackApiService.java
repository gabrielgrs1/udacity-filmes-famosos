package br.com.gabriel.filmesfamosos1.api;

import retrofit2.Response;

/**
 * Created by gabrielgrs
 * Date: 03/01/19
 * Time: 9:16 AM
 * Project: udacity-filmes-famosos
 */
public interface CallbackApiService<T> {
    void onResponse(Response<T> response);

    void onError(String message);

    void startLoading();

    void hideLoading();
}
