package database;

/**
 *
 * @author Verena Kriegl
 */

public enum DB_StatementType {
    GET_ACCOUNT_BY_USERNAME("SELECT * FROM account WHERE username = ?;"),
    GET_HIGHEST_USERID("SELECT MAX(userid) FROM account;"),
    GET_QUESTION_BY_CATEGORYNAME_AND_QUESTIONID("SELECT * FROM question WHERE categoryname = ? AND questionid = ?;"),
    GET_QUESTION_COUNT_BY_CATEGORY("SELECT COUNT(*) FROM question WHERE categoryname = ?;");

    private DB_StatementType(String sqlString) {
        this.sqlString = sqlString;
    }

    private String sqlString;

    public String getSqlString() {
        return sqlString;
    }
}
