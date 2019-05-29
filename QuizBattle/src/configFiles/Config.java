package configFiles;

public interface Config {
    //Database
    String DB_DATABASE_NAME = "QuizBattle";
    String DB_URL = "jdbc:postgresql://localhost/";
    String DB_USERNAME = "postgres";
    String DB_PASSWD = "postgres";
    String DB_DRIVER = "org.postgresql.Driver";
    //Server-Client
    String IP_ADDRESS = "192.168.88.85";
} 