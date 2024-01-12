import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Admin extends User {
    Student student = new Student(null, null, null, null, null, null, null, null);
    Teacher teacher = new Teacher(null, null, null, null, null, null);
    Grade grade = new Grade(null, null, 0);
    Subject subject = new Subject(null, null, null, null);
    String userName;
    String password;
    int validity;

    Admin(String firstName, String middleName, String lastName, String userId, String gender, String password,
            String userName) {
        super(firstName, middleName, lastName, userId, gender);
        this.password = password;
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getValidity() {
        return validity;
    }

    private void checkValidity() throws ClassNotFoundException, SQLException {
        try {
            System.out.println("Enter Your User Name");
            String adminUserName = input.nextLine();
            System.out.println("Enter your Password");
            String adminPassword = input.nextLine();
            System.out.println("Loading...");
            DataBaseAccess db = new DataBaseAccess();
            Connection con = db.Connection();
            Statement statements = con.createStatement();
            String sql = "select userName , Password from admin";
            ResultSet resultStore = statements.executeQuery(sql);
            while (resultStore.next()) {
                setUserName(resultStore.getString(1));
                setPassword(resultStore.getString(2));
            }
            if (adminUserName.equals(getUserName()) && adminPassword.equals(getPassword())) {
                this.validity = 1;
            } else {
                this.validity = 0;
            }
        } catch (Exception e) {
            System.out.println("This is Hidden Please Try Again");
        }
    }

    void adminPort() throws InterruptedException, IOException, ClassNotFoundException, SQLException {
        try {
            /// initialazation of query as local variable in this method
            int count = 0;
            String query = "";
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            /// admin option is initialyy by zero since it is used by many method
            int adminOption = 0;
            while (adminOption != -1) {
                //// this count can count number of try of the user

                if (count == 3)
                    break;
                checkValidity();
                if (getValidity() == 1) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    System.out.println("1. To View Student Information");
                    System.out.println("2. To View Staffs information");
                    System.out.println("3. To Assign Teacher for the Grade");
                    System.out.println("press -1 to exit ");
                    String adOption = input.nextLine();
                    adminOption = Integer.parseInt(adOption);
                    while (adminOption != -1) {
                        if (adminOption == 1) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            System.out.println("1. To Search student by Their identification number");
                            System.out.println("2. To Search student by their Grade");
                            System.out.println("3. To View your all student");
                            System.out.println("4. To Update Students information");
                            System.out.println("5. To Add Students information");
                            System.out.println("6. To Delete student");
                            System.out.println("press -1 to exit ");
                            String admin2Option = input.nextLine();
                            int adminSecondOption = Integer.parseInt(admin2Option);
                            while (adminSecondOption != -1) {
                                if (adminSecondOption == 1) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter student ID number");
                                    String studentId = input.nextLine();
                                    studentId = validityChecker(studentId);
                                    query = "select * from students where studentID = " + studentId;
                                    student.setUserCon(query);

                                } else if (adminSecondOption == 2) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter Your student Grade");
                                    String gradeNumber = input.nextLine();
                                    gradeNumber = validityChecker(gradeNumber);
                                    query = "select * from students where gradeNumber = " + gradeNumber;
                                    student.setUserCon(query);

                                } else if (adminSecondOption == 3) {
                                    query = "select * from students";
                                    student.setUserCon(query);
                                } else if (adminSecondOption == 4) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter student ID number you want to update");
                                    String studentId = input.nextLine();
                                    if (!studentId.matches("[0-9]+")) {
                                        System.out.println("Invalid ID");
                                    } else {
                                        query = "select * from students where studentID = " + studentId;
                                        student.setUserCon(query);
                                        System.out.println(student.userUpdate(studentId));
                                    }

                                } else if (adminSecondOption == 5) {
                                    student.userInserting();
                                } else if (adminSecondOption == 6) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter student ID number");
                                    String studentId = input.nextLine();
                                    if (!studentId.matches("[0-9]+")) {
                                        System.out.println("Invalid ID");
                                    } else {
                                        query = "delete from students where studentID =? ";
                                        student.deleteUser(query, studentId);
                                    }
                                } else {
                                    System.out.println("Please Enter correct option");
                                }
                                System.out.println("1. To search student by Their identification number");
                                System.out.println("2. To search student by their Grade");
                                System.out.println("3. To View your all student");
                                System.out.println("4. To Update Students information");
                                System.out.println("5. To Add Students information");
                                System.out.println("6. To Delete student");
                                System.out.println("press -1 to exit ");
                                admin2Option = input.nextLine();
                                adminSecondOption = Integer.parseInt(admin2Option);
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            }

                        } else if (adminOption == 2) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            System.out.println("1. To Search Teacher by Their identification number");
                            System.out.println("2. To Search student by their Subject");
                            System.out.println("3. To View all Teacher ");
                            System.out.println("4. To update Teachers Information ");
                            System.out.println("5. To Add Teacher information");
                            System.out.println("6. To Delete Teacher information");
                            System.out.println("press -1 to exit ");
                            String admin2Option = input.nextLine();
                            int adminSecondOption = Integer.parseInt(admin2Option);
                            while (adminSecondOption != -1) {
                                if (adminSecondOption == 1) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter Teacher ID number");
                                    String teacherID = input.nextLine();
                                    teacherID = validityChecker(teacherID);
                                    query = "select * from teacher where teacherID = " + teacherID;
                                    teacher.setUserCon(query);

                                } else if (adminSecondOption == 2) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter Your Teacher Subject");
                                    String teacherSubject = input.nextLine();
                                    if (!teacherSubject.matches("[A-z]+")) {
                                        System.out.println("Invalid Subject");
                                    } else {
                                        query = "select * from teacher where subject = " + teacherSubject;
                                        teacher.setUserCon(query);
                                    }
                                } else if (adminSecondOption == 3) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    query = "select * from teacher";
                                    teacher.setUserCon(query);

                                } else if (adminSecondOption == 4) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter Teacher ID number you want to update");
                                    String teacherID = input.nextLine();
                                    teacherID = validityChecker(teacherID);
                                    query = "select * from teacher where teacherID = " + teacherID;
                                    teacher.setUserCon(query);
                                    System.out.println(teacher.userUpdate(teacherID));
                                } else if (adminSecondOption == 5) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    teacher.userInserting();
                                } else if (adminSecondOption == 6) {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println("Enter teacher ID number");
                                    String teacherID = input.nextLine();
                                    teacherID = validityChecker(teacherID);
                                    query = "delete from teacher where teacherID =? ";
                                    teacher.deleteUser(query, teacherID);

                                } else {
                                    System.out.println("Please Enter correct option");
                                }
                                System.out.println("1. To Search Teacher by Their identification number");
                                System.out.println("2. To Search your student by their Subject");
                                System.out.println("3. To Add your all Teacher ");
                                System.out.println("4. To Update  Teachers Information ");
                                System.out.println("5. To Add Teacher information");
                                System.out.println("6. To Delete Teacher information");
                                System.out.println("press -1 to exit ");
                                admin2Option = input.nextLine();
                                adminSecondOption = Integer.parseInt(admin2Option);
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            }
                        } else if (adminOption == 3) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                            System.out.println("1.To view all Grade");
                            System.out.println("2.To Assign Teacher for Grade");
                            System.out.println("3.To Add Grade");
                            System.out.println("4.To View Subjects");
                            System.out.println("5.To Assign Teacher for Subject");
                            System.out.println("6.To Add Subject");
                            String admin2Option = input.nextLine();
                            int adminSecondOption = Integer.parseInt(admin2Option);
                            if (adminSecondOption == 1) {
                                query = "select * from grade";
                                grade.setGradeCon(query);
                            } else if (adminSecondOption == 2) {
                                query = "select * from grade";
                                grade.setGradeCon(query);
                                System.out.println("Enter Grade number");
                                String gradeNumber = input.nextLine();
                                gradeNumber = validityChecker(gradeNumber);
                                System.out.println(grade.gradeUpdate(gradeNumber));
                            } else if (adminSecondOption == 3) {
                                grade.gradeInserting();
                            } else if (adminSecondOption == 4) {
                                query = "select * from subject";
                                subject.setSubjectCon(query);
                            } else if (adminSecondOption == 5) {
                                query = "select * from subject";
                                subject.setSubjectCon(query);
                                System.out.println("Enter Subject Code");
                                String subjectCode = input.nextLine();
                                System.out.println(subject.subjectUpdate(subjectCode));
                            } else if (adminSecondOption == 6) {
                                subject.subjectInserting();
                            } else {
                                System.out.println("Enter the correct choice");
                            }
                        } else {
                            System.out.println("Please Enter the correct option\n");
                        }
                        System.out.println("1. To View student Information");
                        System.out.println("2. To View Staffs information");
                        System.out.println("3. To Assign Teacher");
                        System.out.println("press -1 to exit ");
                        adOption = input.nextLine();
                        adminOption = Integer.parseInt(adOption);
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    }
                } else {
                    count++;
                    System.out.println("Please Enter Correct User Name and Password");

                }
            }
        } catch (Exception e) {
            System.out.println("This is Hidden please Try Again");
        }
    }

    @Override
    void setUserCon(String query) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    String userUpdate(String userId) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void userInserting() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

    }
}
