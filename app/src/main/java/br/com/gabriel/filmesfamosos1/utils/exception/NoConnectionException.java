package br.com.gabriel.filmesfamosos1.utils.exception;

import br.com.gabriel.filmesfamosos1.R;
import br.com.gabriel.filmesfamosos1.application.MoviesApplication;

import java.io.IOException;

public class NoConnectionException extends IOException {

    @Override
    public String getMessage() {
        return MoviesApplication.getInstance().getString(R.string.generic_no_connection_message);
    }
}
