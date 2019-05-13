package database;

import beans.Question;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DB_JSON {

    private void getRequest() throws IOException, JSONException, SQLException {
        URL urlForGetRequest = new URL("https://opentdb.com/api.php?amount=20&category=12&type=multiple");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conection.getInputStream(), Charset.forName("UTF-8")));
            StringBuffer response = new StringBuffer();
            while ((readLine = br.readLine()) != null) {
                response.append(readLine);
            }
            br.close();
            System.out.println("JSON String Result " + response.toString());
            String jsonString = response.toString();
            JSONObject json = new JSONObject(jsonString);
            System.out.println(json.get("results"));
            JSONArray ja_data = json.getJSONArray("results");
            for (int i = 11; i <= ja_data.length(); i++) {
                JSONObject rec = ja_data.getJSONObject(i);
                String category = StringEscapeUtils.unescapeHtml4(rec.getString("category"));
                String question = StringEscapeUtils.unescapeHtml4(rec.getString("question"));
                question = question.replace("'", "");
                String correctAnswer = StringEscapeUtils.unescapeHtml4(rec.getString("correct_answer"));
                JSONArray array = rec.getJSONArray("incorrect_answers");
                System.out.println(category + ", " + question + ", " + correctAnswer);
                String falscheAntwort1 = StringEscapeUtils.unescapeHtml4(array.get(0).toString());
                String falschwAntwort2 = StringEscapeUtils.unescapeHtml4(array.get(0).toString());
                String falscheAntwort3 = StringEscapeUtils.unescapeHtml4(array.get(0).toString());
                DBAccess db = new DBAccess();
                ArrayList<String> wrongAnswers = new ArrayList();
                wrongAnswers.add(falscheAntwort1);
                wrongAnswers.add(falschwAntwort2);
                wrongAnswers.add(falscheAntwort3);
                db.addQuestions(new Question(question, correctAnswer, wrongAnswers, i), "Music");
            }
        } else {
            System.out.println("GET NOT WORKED");
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            DB_JSON json = new DB_JSON();
            try {
                json.getRequest();
            } catch (JSONException ex) {
                Logger.getLogger(DB_JSON.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB_JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
