import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Teacher extends User {
    Scanner input = new Scanner(System.in);
    Student student = new Student(null, null, null, null, null, null, null, null);
    Marks marks = new Marks(null, null, 0, 0, 0, 0, 0);
    String subject;

    Teacher(String firstName, String middleName, String lastName, String userId, String gender, String subject) {
        super(firstName, middleName, lastName, userId, gender);
        this.subject = subject;
    }

    void setTeacher(String firstName, String middleName, String lastName, String userId, String gender,
            String subject) {
        super.firstName = firstName;
        super.middleName = middleName;
        super.lastName = lastName;
        super.userId = userId;
        super.gender = gender;
        this.subject = subject;

    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setUserCon(String sql) throws ClassNotFoundException, SQLException {
        try {
            Teacher[] teacher = new Teacher[100];
            for (int i = 0; i < teacher.length; i++) {
                teacher[i] = new Teacher(sql, sql, sql, sql, sql, sql);
            }
            DataBaseAccess teachdb = new DataBaseAccess();
            Connection con = teachdb.Connection();
            Statement statements = con.createStatement();
            ResultSet resultStore = statements.executeQuery(sql);
            int i = 0;

            while (resultStore.next()) {
                teacher[i].setTeacher(resultStore.getString(1), resultStore.getString(2),
                        resultStore.getString(3), resultStore.getString(4),
                        resultStore.getString(5), resultStore.getString(6));
                i++;
            }
            if (i == 0) {
                System.out.println("There is no specified Teacher yet!");
            } else {

                System.out.println(
                        "------------------------------------------------------------------------------------------");
                System.out.printf("%10s %15s %15s %15s %15s %15s", "FirstName", "MiddleName", "LastName", "ID_No",
                        "Gender",
                        "Subject");
                System.out.println();
                System.out.println(
                        "------------------------------------------------------------------------------------------");
                for (int j = 0; j < i; j++) {
                    System.out.format("%10s %15s %15s %15s %15s %15s",
                            teacher[j].getFirstName(),
                            teacher[j].getMiddleName(), teacher[j].getLastName(),
                            teacher[j].getUserId(),
                            teacher[j].getGender(), teacher[j].getSubject());
                    System.out.println();
                }
                System.out.println(
                        "------------------------------------------------------------------------------------------");

            }
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    void userInserting() throws ClassNotFoundException, SQLException {
        try {
            System.out.println("Enter Teacher First Name");
            firstName = input.nextLine();
            System.out.println("Enter Teacher Middle Name");
            middleName = input.nextLine();
            System.out.println("Enter Teacher Last Name");
            lastName = input.nextLine();
            System.out.println("Enter Teacher ID ");
            userId = input.nextLine();
            userId = validityChecker(userId);
            System.out.println("Enter Teacher Gender ");
            gender = input.nextLine();
            System.out.println("Enter Subject of Teacher");
            subject = input.nextLine();
            String queryInsert = "insert into teacher values(?,?,?,?,?,?)";
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            PreparedStatement insert = con.prepareStatement(queryInsert);
            insert.setString(1, firstName);
            insert.setString(2, middleName);
            insert.setString(3, lastName);
            insert.setString(4, userId);
            insert.setString(5, gender);
            insert.setString(6, subject);
            insert.execute();
            System.out.println("Successfully Added");
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    void teacherInfo() throws ClassNotFoundException, SQLException, InterruptedException, IOException {
        try {
            String query = "";
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("1. To View your Information");
            System.out.println("2. To View students information");
            System.out.println("3. To Add Mark of your student");
            System.out.println("4. To Update mark of your student");
            System.out.println("press -1 to exit ");
            String teachOption = input.nextLine();
            int teacherOption = Integer.parseInt(teachOption);
            while (teacherOption != -1) {
                if (teacherOption == 1) {
                    System.out.println("Enter Your ID number");
                    String teacherID = input.nextLine();
                    teacherID = validityChecker(teacherID);
                    query = "select * from teacher where teacherId = " + teacherID;
                    setUserCon(query);
                } else if (teacherOption == 2) {
                    query = "select * from students";
                    student.setUserCon(query);
                } else if (teacherOption == 3) {
                    marks.markInserting();
                } else if (teacherOption == 4) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    System.out.println("Enter Student ID number you want to update");
                    String studentId = input.nextLine();
                    studentId = validityChecker(studentId);
                    query = "select * from marks where studentID = " + studentId;
                    marks.setMarksCon(query);
                    System.out.println(marks.marksUpdate(studentId));

                } else {
                    System.out.println("Please Enter the correct option\n");
                }
                System.out.println("1. To see your Information");
                System.out.println("2. To see your student information");
                System.out.println("3. To Add Mark of your student");
                System.out.println("4. To Update mark of your student");
                System.out.println("press -1 to exit ");
                teachOption = input.nextLine();
                teacherOption = Integer.parseInt(teachOption);
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    @Override
    String userUpdate(String teacherID) throws ClassNotFoundException, SQLException {
        try {
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            System.out.println("1.First Name  2.Middle Name  3.Last Name  4.Subject ");
            String info = "";
            String updatedInfo = "";
            String opt = input.nextLine();
            int options = Integer.parseInt(opt);
            if (options == 1) {
                info = "teacherFirstName";
                System.out.println("Enter Firt Name of the Teacher");
                updatedInfo = input.nextLine();
            } else if (options == 2) {
                info = "teacherMiddleName";
                System.out.println("Enter Middle Name of the Teacher");
                updatedInfo = input.nextLine();
            } else if (options == 3) {
                info = "teacherLastName";
                System.out.println("Enter Last Name of the Teacher");
                updatedInfo = input.nextLine();
            } else if (options == 4) {
                info = "subject";
                System.out.println("Enter Subject of Teacher");
                updatedInfo = input.nextLine();
            } else {
                System.out.println("Incorrect input");
                return "This is Hidden!";
            }

            String queryUpdate = "update teacher set " + info + " = ? where teacherID = ?";
            PreparedStatement update = con.prepareStatement(queryUpdate);
            update.setString(1, updatedInfo);
            update.setString(2, teacherID);
            update.executeUpdate();
            return "Successfully Updated!";
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
            return "not Successfully Updated!";
        }

    }
}