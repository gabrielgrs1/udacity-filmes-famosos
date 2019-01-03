package br.com.gabriel.filmesfamosos1.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.utils.Utils;

public abstract class GenericActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMethods();
    }

    private void startMethods() {
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
        if (Utils.isNotOnline()) {
            Toast.makeText(this, getString(R.string.generic_erro_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected abstract void setLayout();

    protected abstract void loadingMethods();
}
