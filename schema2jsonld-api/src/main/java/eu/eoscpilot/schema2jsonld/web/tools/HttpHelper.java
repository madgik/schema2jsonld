package eu.eoscpilot.schema2jsonld.web.tools;

import eu.eoscpilot.schema2jsonld.web.exception.ApplicationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public static String get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int code = con.getResponseCode();

        if(code != 200) throw new ApplicationException(String.format("response code is #d with message: %s", code, con.getResponseMessage()));

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } finally {
            try {
                in.close();
            } catch (Exception ex) {
            }
        }

        return response.toString();
    }
}
