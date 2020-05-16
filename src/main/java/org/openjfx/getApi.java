package org.openjfx;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

public class getApi {
    private static String readUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    private static JSONArray apiCalls(String apiChoice) throws IOException {
        JSONArray finnhub = new JSONArray(readUrl("https://finnhub.io/api/v1/stock/symbol?exchange=US&token=bqsjk1frh5rc3vfds0ug"));
        return finnhub;
    }

    public static String finnhubMetrics(String metricKey) throws IOException {
        String metricVal = "";
        JSONArray metricType = apiCalls("finnhub");
        try {
            for (int i = 0; i < finnhub.length(); i++) {
                JSONObject item = finnhub.getJSONObject(i);
                metricVal = item.optString(metricKey);
                while (metricVal.length() != -1) {
                    return metricVal;
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return "No more items";
    }
}
