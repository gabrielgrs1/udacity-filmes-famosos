package br.com.gabriel.filmesfamosos1.utils.formatter;

import br.com.gabriel.filmesfamosos1.MoviesApplication;
import br.com.gabriel.filmesfamosos1.R;

public class StringFormatter {

    public static String configureDateToYear(String date) {
        String[] dateArray = date.split("-");
        date = dateArray[0].trim();
        date = MoviesApplication.getInstance().getResources().getString(R.string.helper_release_date) + " " + date;

        return date;
    }

    public static String configureDate(String date) {
        String[] dateArray = date.split("-");
        date = dateArray[2].trim() + "/" + dateArray[1].trim() + "/" + dateArray[0].trim();
        return date;
    }

    public static String verifyHaveOverview(String overview) {
        if (overview.equals("")) {
            return MoviesApplication.getInstance().getResources().getString(R.string.helper_overview_not_found);
        }

        return overview;
    }


}
