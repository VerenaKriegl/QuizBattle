package database;

public enum DB_StatementType {
    GET_ACCOUNT_BY_USERNAME("SELECT * FROM account WHERE username = ?;");

    private DB_StatementType(String sqlString) {
        this.sqlString = sqlString;
    }
    
    private String sqlString;

    public String getSqlString() {
        return sqlString;
    }
}
 