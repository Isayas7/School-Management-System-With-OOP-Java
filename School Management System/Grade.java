import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Grade {
    Student student = new Student(null, null, null, null, null, null, null, null);
    Scanner input = new Scanner(System.in);
    String gradeNumber;
    String teacherId;
    int numberOfStudent;

    Grade(String gradeNumber, String teacherId, int numberOfStudent) {
        this.gradeNumber = gradeNumber;
        this.teacherId = teacherId;
        this.numberOfStudent = numberOfStudent;

    }

    public void setGrade(String gradeNumber, String teacherId, int numberOfStudent) {

        this.gradeNumber = gradeNumber;
        this.teacherId = teacherId;
        this.numberOfStudent = numberOfStudent;
    }

    public String getGradeNumber() {
        return gradeNumber;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setGradeCon(String sql) throws ClassNotFoundException, SQLException {
        try {
            Grade[] grade = new Grade[100];
            for (int i = 0; i < grade.length; i++) {
                grade[i] = new Grade(null, null, 0);
            }
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            ResultSet resultStore = statements.executeQuery(sql);
            int i = 0;
            while (resultStore.next()) {
                grade[i].setGrade(resultStore.getString(1), resultStore.getString(2), resultStore.getInt(3));
                i++;
            }
            if (i == 0) {
                System.out.println("There is no specified Grade yet!");
            } else {
                System.out.println(
                        "------------------------------------------------------");
                System.out.printf("%10s %15s %20s", "Grade Number", "Teacher-ID", "Number of Student");
                System.out.println();
                System.out.println(
                        "------------------------------------------------------");
                for (int j = 0; j < i; j++) {
                    System.out.format("%10s %15s %20s",
                            grade[j].getGradeNumber(),
                            grade[j].getTeacherId(),
                            grade[j].getNumberOfStudent());
                    System.out.println();
                }
                System.out.println(
                        "------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    void gradeInserting() throws ClassNotFoundException, SQLException {
        try {
            System.out.println("Grade Number");
            gradeNumber = input.nextLine();
            gradeNumber = student.validityChecker(gradeNumber);
            System.out.println("Enter Number of student");
            numberOfStudent = input.nextInt();
            String queryInsert = "insert into grade values(?,?,?)";
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            PreparedStatement insert = con.prepareStatement(queryInsert);
            insert.setString(1, gradeNumber);
            insert.setString(2, null);
            insert.setInt(3, numberOfStudent);
            insert.execute();
            System.out.println("Successfully Added");
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    String gradeUpdate(String gradeNumber) throws ClassNotFoundException, SQLException {
        try {
            int count = 0;
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            ResultSet resultStore = statements.executeQuery("select* from grade");
            String[] existanceOfGrade = new String[12];
            int i = 0;
            while (resultStore.next()) {
                existanceOfGrade[i] = resultStore.getString(1);
                i++;
            }
            for (int j = 0; j <= i; j++) {
                if (gradeNumber.equals(existanceOfGrade[j])) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return "This grade is not specified";
            } else {
                String info = "teacherID";
                System.out.println("Enter Teacher ID");
                String updatedInfo = input.nextLine();
                updatedInfo = student.validityChecker(updatedInfo);
                String queryUpdate = "update grade set " + info + " = ? where gradeNumber = ?";
                PreparedStatement update = con.prepareStatement(queryUpdate);
                update.setString(1, updatedInfo);
                update.setString(2, gradeNumber);
                update.executeUpdate();
                return "Successfully Updated!";
            }
        } catch (

        Exception e) {
            System.out.println("This is Hidden please Try Again");
            return "not Successfully Updated!";
        }
    }
}