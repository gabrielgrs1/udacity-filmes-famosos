package br.com.gabriel.filmesfamosos1.application;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import br.com.gabriel.filmesfamosos1.BuildConfig;
import br.com.gabriel.filmesfamosos1.api.APIClient;

public class MoviesApplication extends Application {

    private static MoviesApplication moviesApplication;
    private APIClient apiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        moviesApplication = this;
        setApiClient();
    }

    private void setApiClient() {
        apiClient = new APIClient(BuildConfig.SERVER_URL);
    }

    public synchronized static MoviesApplication getInstance() {
        return moviesApplication;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public APIClient getApiClient() {
        return apiClient;
    }

}
