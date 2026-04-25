import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class SilentEmergencyApp {

    // Database details
    static final String URL = "jdbc:mysql://localhost:3306/emergency";
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== SILENT EMERGENCY ALERT APP ===");
        System.out.print("Enter your name: ");
        String user = sc.nextLine();

        System.out.println("Press ENTER to trigger PANIC ALERT...");
        sc.nextLine(); // Panic trigger

        // Step 1: Get location (simulated)
        String location = getLocation();

        // Step 2: Send alert
        sendAlert(user, location);

        sc.close();
    }

    // Simulated GPS location
    public static String getLocation() {
        double lat = 12.9716;
        double lon = 77.5946;
        return lat + "," + lon;
    }

    // Main alert function (backend logic)
    public static void sendAlert(String user, String location) {

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            // Create table if not exists
            Statement stmt = con.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS alerts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user VARCHAR(50)," +
                "location VARCHAR(100)," +
                "time VARCHAR(50))"
            );

            // Insert alert data
            String query = "INSERT INTO alerts(user, location, time) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, user);
            ps.setString(2, location);
            ps.setString(3, LocalDateTime.now().toString());

            ps.executeUpdate();

            // Simulate sending message
            System.out.println("\n🚨 ALERT SENT SUCCESSFULLY!");
            System.out.println("User: " + user);
            System.out.println("Location: https://maps.google.com/?q=" + location);
            System.out.println("Emergency contacts notified!");

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
