package br.com.gabriel.filmesfamosos1.api;

import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;

import java.io.IOException;

class NoConnectionException extends IOException {

    @Override
    public String getMessage() {
        return MoviesApplication.getInstance().getString(R.string.generic_no_connection_message);
    }
}
