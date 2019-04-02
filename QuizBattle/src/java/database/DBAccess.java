package database;

import beans.Account;
import beans.Category;
import beans.Question;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBAccess {

    private DB_StatementPool stmtPool = DB_StatementPool.getInstance();
    private ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
        try {
            int counter = 2;
            String frage = "Wer wurde zum ersten Mal Weltfussballer des Jahres?";
            String firstAnswer = "Pele";
            String secondAnswer = "Zinedine Zindane";
            String thirdAnswer = "Stanley Matthews";
            String fourthAnswer = "Diego Maradonna";
            String category = "Sport";
            Question question = new Question(frage, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, counter);
            dba.addQuestions(question, category);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void dropEntry(String sqlQuery) throws SQLException {
        Statement statement = stmtPool.getStatement();
        statement.execute(sqlQuery);
        statement.close();
    }

    public void createTableAccount() throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "CREATE TABLE account ("
                + "userid SERIAL PRIMARY KEY,"
                + "username VARCHAR(40),"
                + "password VARCHAR(10),"
                + "dateOfBirth Date,"
                + "mailAddress VARCHAR(30)"
                + "category VARCHAR(40))";
        statement.execute(sqlString);
        statement.close();
    }

    public void createTableQuestions() throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "CREATE TABLE questions ("
                + "questionid INT,"
                + "question VARCHAR(300),"
                + "firstAnswer VARCHAR(50),"
                + "secondAnswer VARCHAR(50),"
                + "thirdAnswer VARCHAR(50),"
                + "fourthAnswer VARCHAR(50),"
                + "categoryname VARCHAR(50),"
                + "PRIMARY KEY (questionid, categoryname));";
        String sqlForeignKey = "ALTER TABLE questions ADD CONSTRAINT question_category FOREIGN KEY(categoryname) REFERENCES category(categoryname);";
        statement.execute(sqlString);
        statement.execute(sqlForeignKey);
        statement.close();
    }

    public void createTableCategory() throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "CREATE TABLE category ("
                + "categoryid INT,"
                + "categoryname VARCHAR(50) PRIMARY KEY);";
        statement.execute(sqlString);
        statement.close();
    }

    public void addAccount(Account account) throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "INSERT INTO account"
                + "(userid, username, password, dateOfBirth, mailAddress) "
                + "VALUES ('" + account.getUserid()
                + "','" + account.getUsername()
                + "', '" + account.getPassword()
                + "', '" + account.getDateOfBirth()
                + "', '" + account.getMailAddress()
                + "');";
        statement.execute(sqlString);
        statement.close();
    }

    public void addCategory(Category category) throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "INSERT INTO category"
                + "(categoryid, categoryname) "
                + "VALUES ('" + category.getCategoryid()
                + "','" + category.getCategoryname()
                + "');";
        statement.execute(sqlString);
        statement.close();
    }

    public void addQuestions(Question question, String category) throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "INSERT INTO questions"
                + "(questionid, question, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, categoryname)"
                + "VALUES ('" + question.getQuestionid()
                + "','" + question.getQuestion()
                + "','" + question.getFirstAnswer()
                + "','" + question.getSecondAnswer()
                + "','" + question.getThirdAnswer()
                + "','" + question.getFourthAnswer()
                + "','" + category
                + "');";
        statement.execute(sqlString);
        statement.close();
    }

    public Account getAccountByUsername(String username) throws SQLException {
        PreparedStatement pStat = stmtPool.getPreparedStatement(DB_StatementType.GET_ACCOUNT_BY_USERNAME);
        Account account = null;
        pStat.setString(1, username); //Werte für Fragezeichen einsetzen
        ResultSet rs = pStat.executeQuery();
        while (rs.next()) {
            String password = rs.getString("password");
            java.sql.Date dateOfBirth = rs.getDate("dateOfBirth");
            int userid = rs.getInt("userid");
            String email = rs.getString("mailAddress");
            account = new Account(username, password, email, userid, dateOfBirth);
        }
        stmtPool.releaseStatement(pStat);
        return account;
    }
}
