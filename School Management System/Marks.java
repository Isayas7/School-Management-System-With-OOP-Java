import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Marks {

    Scanner input = new Scanner(System.in);
    String studentId;
    String subjectId;
    int quiz;
    int assignment;
    int midexam;
    int finalexam;
    int total;

    Marks(String studentId, String subjectId, int quiz, int assignment, int midexam, int finalexam, int total) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.quiz = quiz;
        this.assignment = assignment;
        this.midexam = midexam;
        this.finalexam = finalexam;
        this.total = total;
    }

    public Marks(Object object, Object object2, Object object3, Object object4, Object object5, Object object6,
            Object object7, Object object8) {
    }

    void setMarks(String studentId, String subjectId, int quiz, int assignment, int midexam, int finalexam, int total) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.quiz = quiz;
        this.assignment = assignment;
        this.midexam = midexam;
        this.finalexam = finalexam;
        this.total = total;
    }

    public int getAssignment() {
        return assignment;
    }

    public int getFinalexam() {
        return finalexam;
    }

    public int getMidexam() {
        return midexam;
    }

    public int getQuiz() {
        return quiz;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public int getTotal() {
        return total;
    }

    String validityChecker(String Checkable) {
        while (true) {
            if (!Checkable.matches("[0-9]+")) {
                System.out.println("Invalid Grade");
                System.out.println("Please Enter another");
                Checkable = input.nextLine();
            } else {
                break;
            }
        }
        return Checkable;
    }

    // Getting the marks from the database through DataBaseAccess class
    void setMarksCon(String sql) throws ClassNotFoundException, SQLException {
        Marks[] marks = new Marks[100];
        for (int i = 0; i < marks.length; i++) {
            marks[i] = new Marks(null, null, null, null, null, null, null, null);
        }
        DataBaseAccess db = new DataBaseAccess();
        Connection con = db.Connection();
        Statement statements = con.createStatement();
        ResultSet resultStore = statements.executeQuery(sql);

        int i = 0;
        while (resultStore.next()) {
            marks[i].setMarks(resultStore.getString(1), resultStore.getString(2),
                    resultStore.getInt(3), resultStore.getInt(4),
                    resultStore.getInt(5),
                    resultStore.getInt(6), resultStore.getInt(7));
            i++;
        }
        if (i == 0) {
            System.out.println("There is no specified student yet!");
        } else {
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------");
            System.out.printf("%10s %15s %15s %15s %15s %15s %15s",
                    "StudentId", "SubjectCoode", "Quiz", "Assignment", "Mid_Exam", "Final-Exam", "Total");
            System.out.println();
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------");
            for (int j = 0; j < i; j++) {
                System.out.format("%10s %15s %15s %15s %15s %15s %15s",
                        marks[j].getStudentId(), marks[j].getSubjectId(), marks[j].getQuiz(),
                        marks[j].getAssignment(), marks[j].getMidexam(),
                        marks[j].getFinalexam(),
                        marks[j].getTotal());
                System.out.println();
            }
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------");

        }
    }

    // Inserthing Marks of students
    void markInserting() throws SQLException, ClassNotFoundException {
        System.out.println("Enter student ID ");
        studentId = input.nextLine();
        System.out.println("Enter subject ID ");
        subjectId = input.nextLine();
        System.out.println("Enter quiz ");
        String quizs = input.nextLine();
        quizs = validityChecker(quizs);
        quiz = Integer.parseInt(quizs);
        System.out.println("Enter assignment ");
        String assignments = input.nextLine();
        assignments = validityChecker(assignments);
        assignment = Integer.parseInt(assignments);
        System.out.println("Enter mid-exam ");
        String mid_exam = input.nextLine();
        mid_exam = validityChecker(mid_exam);
        midexam = Integer.parseInt(mid_exam);
        System.out.println("Enter final ");
        String final_exam = input.nextLine();
        final_exam = validityChecker(final_exam);
        finalexam = Integer.parseInt(final_exam);
        total = quiz + assignment + midexam + finalexam;
        String queryInsert = "insert into marks values(?,?,?,?,?,?,?)";
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
            if (studentId.equals(existanceOfId[j])) {
                break;
            } else {
                System.out.println("There is no student with this ID ");
                System.out.println("Please Enter another");
                studentId = input.nextLine();
                studentId = validityChecker(studentId);

            }
        }
        resultStore = statements.executeQuery("select * from subject");
        i = 0;
        while (resultStore.next()) {
            existanceOfId[i] = resultStore.getString(2);
            i++;
        }
        for (int j = 0; j < i; j++) {
            if (subjectId.equals(existanceOfId[j])) {
                break;
            } else {
                System.out.println("There is no subject with this ID ");
                System.out.println("Please Enter another");
                subjectId = input.nextLine();
            }
        }

        PreparedStatement insert = con.prepareStatement(queryInsert);
        insert.setString(1, studentId);
        insert.setString(2, subjectId);
        insert.setInt(3, quiz);
        insert.setInt(4, assignment);
        insert.setInt(5, midexam);
        insert.setInt(6, finalexam);
        insert.setInt(7, total);
        insert.execute();
        System.out.println("Successfully inserted");
    }

    // Updating marks of student
    String marksUpdate(String studentID) throws SQLException, ClassNotFoundException {
        try {
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            System.out.println("1.Quiz  2.Assignment  3.Mid-Exam  5.Final Exam  ");
            String info = "";
            String updatedInfo = "";
            int uptoDate;
            String opt = input.nextLine();
            int options = Integer.parseInt(opt);
            if (options == 1) {
                info = "quiz";
                System.out.println("Enter  quiz");
                updatedInfo = input.nextLine();
                uptoDate = Integer.parseInt(updatedInfo);
            } else if (options == 2) {
                info = "assignment";
                System.out.println("Enter assignment");
                updatedInfo = input.nextLine();
                uptoDate = Integer.parseInt(updatedInfo);
            } else if (options == 3) {
                info = "midExam";
                System.out.println("Enter Mid-Exam");
                updatedInfo = input.nextLine();
                uptoDate = Integer.parseInt(updatedInfo);
            } else if (options == 4) {
                info = "finalExam";
                System.out.println("Enter Final-Exam");
                updatedInfo = input.nextLine();
                uptoDate = Integer.parseInt(updatedInfo);
            } else {
                System.out.println("Incorrect input");
                return "This is Hidden!";
            }

            String queryUpdate = "update marks set " + info + " = ? where studentID = ?";
            PreparedStatement update = con.prepareStatement(queryUpdate);
            update.setInt(1, uptoDate);
            update.setString(2, studentID);
            update.executeUpdate();
            ResultSet resultStore = statements.executeQuery("select * from marks where studentID=" + studentID);
            while (resultStore.next())
                total = resultStore.getInt(3) + resultStore.getInt(4) + resultStore.getInt(5) + resultStore.getInt(6);
            queryUpdate = "update marks set total = ? where studentID = ?";
            update = con.prepareStatement(queryUpdate);
            update.setInt(1, total);
            update.setString(2, studentID);
            update.executeUpdate();
            return "Successfully Updated!";

        } catch (

        Exception e) {
            System.out.println("This is Hidden please Try Again");
            return " not Successfully Updated!";

        }
    }

}
