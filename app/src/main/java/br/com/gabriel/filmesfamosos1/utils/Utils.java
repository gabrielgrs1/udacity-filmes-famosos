package br.com.gabriel.filmesfamosos1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import br.com.gabriel.filmesfamosos1.application.MoviesApplication;


public class Utils {

    public static boolean isNotOnline() {
        Context context = MoviesApplication.getInstance();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;

        if (connectivityManager != null) {
            netInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (netInfo == null || !netInfo.isConnected());
    }
}
