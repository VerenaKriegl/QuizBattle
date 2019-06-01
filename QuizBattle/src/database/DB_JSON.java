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
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DB_JSON {

    /* Zum Einlesen der Question von der API in die Datenbank */
    private void getRequest() throws IOException, JSONException, SQLException {
        URL urlForGetRequest = new URL("https://opentdb.com/api.php?amount=50&category=21&type=multiple");
        String readLine = "";
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        int responseCode = conection.getResponseCode();
        //Versucht eine Verbindung zu der URL aufzubauen
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conection.getInputStream(), Charset.forName("UTF-8")));
            StringBuffer response = new StringBuffer();
            while ((readLine = br.readLine()) != null) {
                response.append(readLine);
            }
            br.close();
            //Bei Erfolgreicher API-Abfrage erhält man den vollständigen JSON-String
            String jsonString = response.toString();
            System.out.println("JSON String: " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            loadIntoDatabase(jsonArray);
        } else {
            System.out.println("Error - no connection available");
        }
    }

    private void loadIntoDatabase(JSONArray jsonArray) throws JSONException, SQLException {
        for (int i = 0; i <= jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            //StringEscapeUtils.unescapeHtml4 => wird verwendet, damit Sonderzeichen richtig angezeigt werden können
            String category = StringEscapeUtils.unescapeHtml4(object.getString("category"));
            String question = StringEscapeUtils.unescapeHtml4(object.getString("question"));
            question = question.replace("'", "");
            String correctAnswer = StringEscapeUtils.unescapeHtml4(object.getString("correct_answer"));
            //Alle incorrect_answers wurden nochmals in einem Array festgelegt
            JSONArray array = object.getJSONArray("incorrect_answers");
            String falseAnswerOne = StringEscapeUtils.unescapeHtml4(array.get(0).toString());
            String falseAnswerTwo = StringEscapeUtils.unescapeHtml4(array.get(1).toString());
            String falseAnswerThree = StringEscapeUtils.unescapeHtml4(array.get(2).toString());
            //In die Datenbank einfügen
            DBAccess db = new DBAccess();
            ArrayList<String> wrongAnswers = new ArrayList();
            wrongAnswers.add(falseAnswerOne);
            wrongAnswers.add(falseAnswerTwo);
            wrongAnswers.add(falseAnswerThree);
            db.addQuestions(new Question(question, correctAnswer, wrongAnswers, i), "Sports");
        }
    }
}
