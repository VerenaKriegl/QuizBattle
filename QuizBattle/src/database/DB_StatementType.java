package database;

public enum DB_StatementType {
    GET_ACCOUNT_BY_USERNAME("SELECT * FROM account WHERE username = ?;"),
    GET_HIGHEST_USERID("SELECT MAX(userid) FROM account;"),
    GET_QUESTION_BY_CATEGORY("SELECT * FROM question WHERE categoryname = ?;");

    private DB_StatementType(String sqlString) {
        this.sqlString = sqlString;
    }
    
    private String sqlString;

    public String getSqlString() {
        return sqlString;
    }
}
 