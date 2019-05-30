package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Verena Kriegl
 */

public class DB_ConnectionPool implements configFiles.Config {

    private static DB_ConnectionPool theInstace;

    public static DB_ConnectionPool getInstance() {
        if (theInstace == null) {
            synchronized (DB_ConnectionPool.class) {
                theInstace = new DB_ConnectionPool();
            }
        }
        return theInstace;
    }

    private DB_ConnectionPool() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Queue<Connection> connPool = new LinkedList();
    private final int MAX_CONN = 500; //Maximale Anzahl von Connections
    private int numConn = 0; //Aktuelle Anzahl von Connections

    public Connection getConnection() throws SQLException {
        synchronized (connPool) {
            if (connPool.isEmpty()) {
                if (numConn < MAX_CONN) //Bedienung damit neue Connection erzeugt werden kann
                {
                    Connection connection = DriverManager.getConnection(DB_URL + DB_DATABASE_NAME, DB_USERNAME, DB_PASSWD);
                    numConn++;
                    return connection;
                } else {
                    throw new RuntimeException("Sorry - no available Connection! Try again later!"); //Alle Connections vergeben
                }
            }
            return connPool.poll(); //holt und löscht das erste Elemente aus der Queue
        }
    }

    public void releaseConnection(Connection connection) //Connections wieder freigeben
    {
        synchronized (connPool) {
            connPool.offer(connection);
        }
    }
}
