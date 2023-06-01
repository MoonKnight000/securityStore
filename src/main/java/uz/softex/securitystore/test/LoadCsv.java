package uz.softex.securitystore.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadCsv {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "1973";

    public static void loadCSVIntoTable(String filePath, String tableName) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
//
//            // Truncate the table to remove existing data (optional)
//            statement.executeUpdate("TRUNCATE TABLE " + tableName);

            // Load the CSV file
            String copyQuery = "COPY " + tableName + " FROM '" + filePath + "' CSV HEADER";
            statement.executeUpdate(copyQuery);

            statement.close();
            connection.close();

            System.out.println("Data inserted successfully from CSV file.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\murod\\IdeaProjects\\SecurityStore\\backUp\\1_backup.csv";
        String tableName = "lap";
        loadCSVIntoTable(filePath, tableName);
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
