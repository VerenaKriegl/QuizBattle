package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DB_JSON {

    private void getRequest() throws IOException {
        URL urlForGetRequest = new URL("https://opentdb.com/api.php?amount=10&category=23&type=multiple");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            } 
            in.close();
            System.out.println("JSON String Result " + response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    }

    public static void main(String[] args) throws IOException {
        //   DB_JSON json = new DB_JSON();
        //  json.getRequest();
        DB_JSON db = new DB_JSON();
        db.getRequest();
    }
}
