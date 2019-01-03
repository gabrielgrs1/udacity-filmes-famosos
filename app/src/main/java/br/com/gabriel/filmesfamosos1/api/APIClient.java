package br.com.gabriel.filmesfamosos1.api;

import androidx.annotation.NonNull;
import br.com.gabriel.filmesfamosos1.utils.Utils;
import br.com.gabriel.filmesfamosos1.utils.exception.NoConnectionException;
import com.google.gson.Gson;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * API Client for request on server
 */
public class APIClient {

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public APIClient(@NonNull String baseUrl) {

        buildOkHttpClient();
        buildRetrofit(baseUrl);
    }

    private void buildOkHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(logging)
                .addInterceptor(checkConnectionInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

    }

    private void buildRetrofit(@NonNull String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private final Interceptor checkConnectionInterceptor = chain -> {
        if (Utils.isNotOnline()) {
            throw new NoConnectionException();
        }
        return chain.proceed(chain.request());
    };

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
