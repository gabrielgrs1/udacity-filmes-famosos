package br.com.gabriel.filmesfamosos1.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.utils.Utils;

public abstract class GenericActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMethods();
    }

    protected void startMethods() {
        setLayout();
        loadingMethods();
    }

    protected void setHomeButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected boolean checkInternet() {
        if (!Utils.isOnline()) {
            Toast.makeText(this, MoviesApplication.getInstance().getString(R.string.generic_erro_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public abstract void setLayout();

    public abstract void loadingMethods();
}
