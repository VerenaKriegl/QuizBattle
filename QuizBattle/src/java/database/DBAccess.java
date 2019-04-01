package database;

import beans.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBAccess {

    private DB_StatementPool stmtPool = DB_StatementPool.getInstance();
    private ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
        try {
            dba.createTableAccount();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void createTableAccount() throws SQLException {
        Statement statement = stmtPool.getStatement();
        String sqlString = "CREATE TABLE account ("
                + "userid SERIAL PRIMARY KEY,"
                + "username VARCHAR(40),"
                + "password VARCHAR(10),"
                + "dateOfBirth Date,"
                + "mailAddress VARCHAR(30));";
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
