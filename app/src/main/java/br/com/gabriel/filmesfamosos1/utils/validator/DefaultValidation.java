package br.com.gabriel.filmesfamosos1.utils.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;

public class DefaultValidation implements IValidator {

    private final TextInputLayout textInputLayout;
    private final EditText field;

    DefaultValidation(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
        this.field = this.textInputLayout.getEditText();
    }

    private boolean validateRequiredField() {
        String text = field.getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(MoviesApplication.getInstance().getString(R.string.generic_required_field));
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid() {
        if (!validateRequiredField()) return false;
        removeError();
        return true;
    }

    private void removeError() {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }

}
