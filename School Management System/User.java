import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

abstract class User {
    // Marks marks = new Marks(null, null, 0, 0, 0, 0, 0);
    Scanner input = new Scanner(System.in);
    String firstName;
    String middleName;
    String lastName;
    String userId;
    String gender;

    User(String firstName, String middleName, String lastName, String userId, String gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userId = userId;
        this.gender = gender;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getGender() {
        return gender;
    }

    abstract void setUserCon(String query) throws ClassNotFoundException, SQLException;

    abstract String userUpdate(String userId) throws ClassNotFoundException, SQLException;

    abstract void userInserting() throws SQLException, ClassNotFoundException;

    String validityChecker(String Checkable) {
        while (true) {
            if (!Checkable.matches("[0-9]+")) {
                System.out.println("Invalid");
                System.out.println("Please Enter another");
                Checkable = input.nextLine();
            } else {
                break;
            }
        }
        return Checkable;
    }

    String nameChecker(String Name) {
        while (true) {
            if (!Name.matches("[A-z]+")) {
                System.out.println("Invalid Name");
                System.out.println("Please Enter another");
                Name = input.nextLine();
            } else {
                break;
            }
        }
        return Name;
    }

    void deleteUser(String sql, String userID) throws ClassNotFoundException, SQLException {
        try {
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            PreparedStatement delete = con.prepareStatement(sql);
            delete.setString(1, userID);
            delete.executeUpdate();
            System.out.println("Successfully Deleted!");
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }
}
