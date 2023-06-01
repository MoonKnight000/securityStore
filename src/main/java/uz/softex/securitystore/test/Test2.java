package uz.softex.securitystore.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class Test2 {
    private static String backupDirectoryPath = "backUp";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "1973";

    private static ResultSet getFilteredDataForCompany(String companyName) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM lap WHERE user_id = '" + companyName + "'";
        return statement.executeQuery(query);
    }

    public static void backupDataForCompany(String companyName) {
        String backupFileName = companyName + "_backup.csv";
        String backupFilePath = backupDirectoryPath + "/" + backupFileName;

        try {
            ResultSet resultSet = getFilteredDataForCompany(companyName);

            // Faylga yozish operatsiyalari
            FileWriter fileWriter = new FileWriter(backupFilePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // CSV fayl uchun ustun nomlarini yozish
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                printWriter.print(metaData.getColumnName(i));
                if (i < columnCount) {
                    printWriter.print(",");
                }
            }
            printWriter.println();

            // Qatorlarni yozish
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    printWriter.print(value != null ? value.toString() : "");
                    if (i < columnCount) {
                        printWriter.print(",");
                    }
                }
                printWriter.println();
            }

            printWriter.close();
            fileWriter.close();

            resultSet.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        backupDataForCompany("1");
    }
}
