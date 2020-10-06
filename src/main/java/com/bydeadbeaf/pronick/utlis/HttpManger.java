package com.bydeadbeaf.pronick.utlis;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManger {
    public String get(String url , String arg){
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format(url, arg)).openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String output;
                while ((output = bufferedReader.readLine()) != null){
                    stringBuilder.append(output);
                }
                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "error";
    }
}
