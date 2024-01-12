import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Subject {
    Student student = new Student(null, null, null, null, null, null, null, null);
    Scanner input = new Scanner(System.in);

    String subjectName;
    String subjectCode;
    String teacherId;
    String gradeNumber;

    Subject(String subjectName, String subjectCode, String teacherId, String gradeNumber) {

        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.gradeNumber = gradeNumber;
    }

    void setSubject(String subjectName, String subjectCode, String teacherId, String gradeNumber) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.gradeNumber = gradeNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getGradeNumber() {
        return gradeNumber;
    }

    public void setSubjectCon(String sql) throws ClassNotFoundException, SQLException {
        try {
            Subject[] subject = new Subject[100];
            for (int i = 0; i < subject.length; i++) {
                subject[i] = new Subject(sql, sql, sql, sql);
            }
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            ResultSet resultStore = statements.executeQuery(sql);
            int i = 0;
            while (resultStore.next()) {
                subject[i].setSubject(resultStore.getString(1), resultStore.getString(2),
                        resultStore.getString(3), resultStore.getString(4));
                i++;
            }
            if (i == 0) {
                System.out.println("There is no specified Student yet!");
            } else {
                System.out.println(
                        "------------------------------------------------------------------------------------------");
                System.out.printf("%10s %15s %15s %15s", "Subject Name", "Subject Code", "Grade Number", "Teacher ID");
                System.out.println();
                System.out.println(
                        "------------------------------------------------------------------------------------------");
                for (int j = 0; j < i; j++) {
                    System.out.format("%10s %15s %15s %15s",
                            subject[j].getSubjectName(),
                            subject[j].getSubjectCode(),
                            subject[j].getTeacherId(),
                            subject[j].getGradeNumber());
                    System.out.println();
                }
                System.out.println(
                        "------------------------------------------------------------------------------------------");
            }
        } catch (

        Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    void subjectInserting() throws ClassNotFoundException, SQLException {
        try {
            System.out.println("Subject Name");
            subjectName = input.nextLine();
            System.out.println("Subject Code");
            subjectCode = input.nextLine();
            System.out.println("Grade Number");
            gradeNumber = input.nextLine();
            String queryInsert = "insert into subject values(?,?,?,?)";
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            PreparedStatement insert = con.prepareStatement(queryInsert);
            insert.setString(1, subjectName);
            insert.setString(2, subjectCode);
            insert.setString(3, gradeNumber);
            insert.setString(4, null);
            insert.execute();
            System.out.println("Successfully Added");
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    String subjectUpdate(String subjectCode) throws ClassNotFoundException, SQLException {
        try {
            Subject[] subject = new Subject[100];
            for (int i = 0; i < subject.length; i++) {
                subject[i] = new Subject(null, null, null, null);
            }
            int count = 0;
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            ResultSet resultStore = statements.executeQuery("select* from subject");
            String[] existanceOfSubject = new String[12];
            int i = 0;
            while (resultStore.next()) {
                existanceOfSubject[i] = resultStore.getString(2);
                i++;
            }
            for (int j = 0; j < i; j++) {
                if (subjectCode.equals(existanceOfSubject[j])) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return "This subject is not specified";
            } else {
                String info = "teacherID";
                System.out.println("Enter Teacher ID");
                String updatedInfo = input.nextLine();
                updatedInfo = student.validityChecker(updatedInfo);
                String queryUpdate = "update subject set " + info + " = ? where subjectCode = ?";
                PreparedStatement update = con.prepareStatement(queryUpdate);
                update.setString(1, updatedInfo);
                update.setString(2, subjectCode);
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
