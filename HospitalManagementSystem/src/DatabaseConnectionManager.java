import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private String url;
    private String username;
    private String password;

    public DatabaseConnectionManager(){
        loadDataBaseProperties();
    }

    private void loadDataBaseProperties(){
        Properties properties = new Properties();
        try(FileInputStream input= new FileInputStream("src/db.properties")){
            properties.load(input);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        }catch (IOException e){
            System.out.println("Failed to load database properties: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
