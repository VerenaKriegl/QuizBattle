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
        String sqlQuery = "CREATE TABLE account ("
                + "userid SERIAL PRIMARY KEY,"
                + "username VARCHAR(40),"
                + "password VARCHAR(10),"
                + "dateOfBirth Date,"
                + "mailAddress VARCHAR(30));";
        statement.execute(sqlQuery);
    }
    
    public List<Account> getAccountByUsername(String username) throws SQLException{
        PreparedStatement pStat = stmtPool.getPreparedStatement(DB_StatementType.GET_ACCOUNT_BY_USERNAME);
        pStat.setString(1, username); //Werte für Fragezeichen einsetzen
        ResultSet rs = pStat.executeQuery();
        List<Account> filmNames = new LinkedList<>();
        while(rs.next()){
            String password = rs.getString("password");
            java.sql.Date dateOfBirth = rs.getDate("dateOfBirth");
            int userid = rs.getInt("userid");
            String email = rs.getString("mailAddress");
            Account account = new Account(username, password, email, userid, dateOfBirth);
            filmNames.add(account);
        }
        stmtPool.releaseStatement(pStat);
        return filmNames;
    }
}
