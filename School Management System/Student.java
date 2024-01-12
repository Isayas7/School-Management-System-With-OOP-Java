import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Student extends User {
    Scanner input = new Scanner(System.in);
    String age;
    String year;
    String gradeNumber;

    Student(String firstName, String middileName, String lastName, String userId, String gender, String gradeNamber,
            String age, String year) {
        super(firstName, middileName, lastName, userId, gender);
        this.age = age;
        this.gradeNumber = gradeNamber;
        this.year = year;
        // TODO Auto-generated constructor stub
    }

    Marks marks = new Marks(null, null, 0, 0, 0, 0, 0);

    void setStudent(String firstName, String middileName, String lastName, String userId, String gender,
            String gradeNamber,
            String age, String year) {
        super.firstName = firstName;
        super.middleName = middileName;
        super.lastName = lastName;
        super.userId = userId;
        super.gender = gender;
        this.age = age;
        this.gradeNumber = gradeNamber;
        this.year = year;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGradeNumber(String gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAge() {
        return age;
    }

    public String getGradeNumber() {
        return gradeNumber;
    }

    public String getYear() {
        return year;
    }

    @Override
    void setUserCon(String sql) throws ClassNotFoundException, SQLException {
        try {
            Student[] student = new Student[100];
            for (int i = 0; i < student.length; i++) {
                student[i] = new Student(null, null, null, null, null, null, null, null);
            }
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            ResultSet resultStore = statements.executeQuery(sql);
            int i = 0;
            while (resultStore.next()) {
                student[i].setStudent(resultStore.getString(1), resultStore.getString(2),
                        resultStore.getString(3), resultStore.getString(4),
                        resultStore.getString(5),
                        resultStore.getString(6), resultStore.getString(7),
                        resultStore.getString(8));
                i++;
            }
            if (i == 0) {
                System.out.println("There is no specified Student yet!");
            } else {

                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%10s %15s %15s %15s %15s %15s %15s %15s", "FirstName", "MiddleName", "LastName",
                        "ID_No",
                        "Gender", "Grade", "Age", "Reg-Date");
                System.out.println();
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------");
                for (int j = 0; j < i; j++) {
                    System.out.format("%10s %15s %15s %15s %15s %15s %15s %15s",
                            student[j].getFirstName(),
                            student[j].getMiddleName(), student[j].getLastName(),
                            student[j].getUserId(),
                            student[j].getGender(), student[j].getGradeNumber(),
                            student[j].getAge(),
                            student[j].getYear());
                    System.out.println();
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------");

            }
        } catch (Exception e) {
            System.out.println("This is hidden please try again");
        }
    }

    @Override
    String userUpdate(String studentID) throws ClassNotFoundException, SQLException {
        try {
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            System.out.println("1.First Name  2.Middle Name  3.Last Name  5.Grade 4.Age  ");
            String info = "";
            String updatedInfo = "";
            String opt = input.nextLine();
            int options = Integer.parseInt(opt);
            if (options == 1) {
                info = "studentFirstName";
                System.out.println("Enter Firt Name of the Student");
                updatedInfo = input.nextLine();
                updatedInfo = nameChecker(updatedInfo);
            } else if (options == 2) {
                info = "studentMiddleName";
                System.out.println("Enter Middle Name of the Student");
                updatedInfo = input.nextLine();
                updatedInfo = nameChecker(updatedInfo);
            } else if (options == 3) {
                info = "studentLastName";
                System.out.println("Enter Last Name of the Student");
                updatedInfo = input.nextLine();
                updatedInfo = nameChecker(updatedInfo);
            } else if (options == 4) {
                info = "gradeNumber";
                System.out.println("Enter Grade Student");
                updatedInfo = input.nextLine();
            } else if (options == 5) {
                info = "age";
                System.out.println("Enter Age  Student");
                updatedInfo = input.nextLine();
            } else {
                System.out.println("Incorrect input");
                return "This is Hidden!";
            }

            String queryUpdate = "update students set " + info + " = ? where studentID = ?";
            PreparedStatement update = con.prepareStatement(queryUpdate);
            update.setString(1, updatedInfo);
            update.setString(2, studentID);
            update.executeUpdate();
            return "Successfully Updated!";
        } catch (Exception e) {
            System.out.println("This is an hidden please try again");
            return " not Successfully Updated!";
        }
    }

    void userInserting() throws SQLException, ClassNotFoundException {
        try {
            System.out.println("Enter student First Name");
            firstName = input.nextLine();
            firstName = nameChecker(firstName);
            System.out.println("Enter student Middle Name");
            middleName = input.nextLine();
            middleName = nameChecker(middleName);
            System.out.println("Enter student Last Name");
            lastName = input.nextLine();
            lastName = nameChecker(lastName);
            System.out.println("Enter student ID ");
            userId = input.nextLine();
            userId = validityChecker(userId);
            System.out.println("Enter student Gender ");
            gender = input.nextLine();
            System.out.println("Enter student Grade ");
            gradeNumber = input.nextLine();
            gradeNumber = validityChecker(gradeNumber);
            System.out.println("Enter student Age ");
            age = input.nextLine();
            age = validityChecker(age);
            int Age = Integer.parseInt(age);
            System.out.println("Enter student registration year");
            year = input.nextLine();
            year = validityChecker(year);
            String queryInsert = "insert into students values(?,?,?,?,?,?,?,?)";
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            String[] existanceOfId = new String[100];
            ResultSet resultStore = statements.executeQuery("select * from students");
            int i = 0;
            while (resultStore.next()) {
                existanceOfId[i] = resultStore.getString(4);
                i++;
            }
            for (int j = 0; j < i; j++) {
                if (userId.equals(existanceOfId[j])) {
                    System.out.println("There is student with this Id ");
                    System.out.println("Please Enter another");
                    userId = input.nextLine();
                    userId = validityChecker(userId);
                }
            }
            resultStore = statements.executeQuery("select * from grade");
            i = 0;
            while (resultStore.next()) {
                existanceOfId[i] = resultStore.getString(1);
                i++;
            }
            for (int j = 0; j < i; j++) {
                if (gradeNumber.equals(existanceOfId[j])) {
                    break;
                } else {
                    System.out.println("There is no grade with this number ");
                    System.out.println("Please Enter another");
                    gradeNumber = input.nextLine();
                    gradeNumber = validityChecker(gradeNumber);
                }
            }
            PreparedStatement insert = con.prepareStatement(queryInsert);
            insert.setString(1, firstName);
            insert.setString(2, middleName);
            insert.setString(3, lastName);
            insert.setString(4, userId);
            insert.setString(5, gender);
            insert.setString(6, gradeNumber);
            insert.setInt(7, Age);
            insert.setString(8, year);
            insert.execute();
            System.out.println("Successfully inserted");
        } catch (Exception e) {
            System.out.println("This is hidden please try again");
        }
    }

    void studentInfo()
            throws ClassNotFoundException, SQLException, InterruptedException, IOException, NumberFormatException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        try {
            String query = "";
            System.out.println("1. To View your Information");
            System.out.println("2. To View your Marks");
            System.out.println("press -1 to exit ");
            String studOption = input.nextLine();
            int studentOption = Integer.parseInt(studOption);
            while (studentOption != -1) {
                if (studentOption == 1) {
                    System.out.println("Enter Your ID number");
                    String studentId = input.nextLine();
                    studentId = validityChecker(studentId);
                    query = "select * from students where studentID = " + studentId;
                    setUserCon(query);

                } else if (studentOption == 2) {
                    System.out.println("Enter Your ID number");
                    String studentId = input.nextLine();
                    studentId = validityChecker(studentId);
                    query = "select * from marks where studentID = " + studentId;
                    marks.setMarksCon(query);

                } else {
                    System.out.println("Please Enter the correct option\n");
                }
                System.out.println("1. To View your Information");
                System.out.println("2. To View your Marks");
                System.out.println("press -1 to exit ");
                studOption = input.nextLine();
                studentOption = Integer.parseInt(studOption);
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        } catch (Exception e) {
            System.out.println("This is hidden please try again");
        }
    }

}