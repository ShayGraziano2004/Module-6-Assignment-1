import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StaffManagement {

    // JDBC database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASS = "your_password";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. View Staff");
                System.out.println("2. Insert Staff");
                System.out.println("3. Update Staff");
                System.out.println("4. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        viewStaff(connection);
                        break;
                    case 2:
                        insertStaff(connection);
                        break;
                    case 3:
                        updateStaff(connection);
                        break;
                    case 4:
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewStaff(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Staff ID to view: ");
        String id = scanner.nextLine();

        String sql = "SELECT * FROM Staff WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                displayStaffRecord(resultSet);
            } else {
                System.out.println("Staff record not found.");
            }
        }
    }

    private static void insertStaff(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Staff information:");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Middle Initial: ");
        String mi = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Telephone: ");
        String telephone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        String sql = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, mi);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, state);
            preparedStatement.setString(8, telephone);
            preparedStatement.setString(9, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Staff record inserted successfully.");
            } else {
                System.out.println("Failed to insert Staff record.");
            }
        }
    }

    private static void updateStaff(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Staff ID to update: ");
        String id = scanner.nextLine();

        // Check if the record exists
        String checkSql = "SELECT * FROM Staff WHERE id = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setString(1, id);

            ResultSet resultSet = checkStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Staff record not found.");
                return;
            }
        }

        System.out.println("Enter new Staff information:");
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Middle Initial: ");
        String mi = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Telephone: ");
        String telephone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        String sql = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, mi);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, state);
            preparedStatement.setString(7, telephone);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Staff record updated successfully.");
            } else {
                System.out.println("Failed to update Staff record.");
            }
        }
    }

    private static void displayStaffRecord(ResultSet resultSet) throws SQLException {
        System.out.println("Staff Information:");
        System.out.println("ID: " + resultSet.getString("id"));
        System.out.println("Last Name: " + resultSet.getString("lastName"));
        System.out.println("First Name: " + resultSet.getString("firstName"));
        System.out.println("Middle Initial: " + resultSet.getString("mi"));
        System.out.println("Address: " + resultSet.getString("address"));
        System.out.println("City: " + resultSet.getString("city"));
        System.out.println("State: " + resultSet.getString("state"));
        System.out.println("Telephone: " + resultSet.getString("telephone"));
        System.out.println("Email: " + resultSet.getString("email"));
    }
}
