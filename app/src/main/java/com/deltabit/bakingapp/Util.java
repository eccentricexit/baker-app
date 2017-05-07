package com.deltabit.bakingapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by rigel on 07/05/17.
 */

public class Util {

    public static final String PLACEHOLDER_URL = "https://s3.amazonaws.com/sitesusa/wp-content/uplo" +
            "ads/sites/212/2016/05/600-x-360-Background-with-t" +
            "he-ingredients-for-a-cake-MaksimVasic-iStock-Thin" +
            "kstock-464617462.jpg";

    public static final String NO_PREVIEW_AVAILABLE = "http://www.finescale.com/sitefiles/images/no-" +
            "preview-available.png";

    public static String getStringFromJsonFile(Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.baking);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
    }
}
