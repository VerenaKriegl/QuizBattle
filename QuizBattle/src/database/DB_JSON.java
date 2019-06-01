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
        URL urlForGetRequest = new URL("https://opentdb.com/api.php?amount=50&category=21&type=multiple");
        String readLine = "";
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
            System.out.println("JSON String: " + response.toString());
            String jsonString = response.toString();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 15; i <= jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String category = StringEscapeUtils.unescapeHtml4(object.getString("category"));
                String question = StringEscapeUtils.unescapeHtml4(object.getString("question"));
                question = question.replace("'", "");
                String correctAnswer = StringEscapeUtils.unescapeHtml4(object.getString("correct_answer"));
                JSONArray array = object.getJSONArray("incorrect_answers");
                System.out.println(category + ", " + question + ", " + correctAnswer);
                String falseAnswerOne = StringEscapeUtils.unescapeHtml4(array.get(0).toString());
                String falseAnswerTwo = StringEscapeUtils.unescapeHtml4(array.get(1).toString());
                String falseAnswerThree = StringEscapeUtils.unescapeHtml4(array.get(2).toString());
                DBAccess db = new DBAccess();
                ArrayList<String> wrongAnswers = new ArrayList();
                wrongAnswers.add(falseAnswerOne);
                wrongAnswers.add(falseAnswerTwo);
                wrongAnswers.add(falseAnswerThree);
                db.addQuestions(new Question(question, correctAnswer, wrongAnswers, i), "Sports");
            }
        } else {
            System.out.println("Error - no connection available");
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            DB_JSON json = new DB_JSON();
            json.getRequest();
        } catch (SQLException ex) {
            Logger.getLogger(DB_JSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DB_JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
