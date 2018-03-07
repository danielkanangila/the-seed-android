package org.acreglise.theseed.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JSONUtil {
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }
}
