import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Student student = new Student(null, null, null, null, null, null, null, null);
        Teacher teacher = new Teacher(null, null, null, null, null, null);
        Admin admin = new Admin(null, null, null, null, null, null, null);
        try (Scanner input = new Scanner(System.in)) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("---------------------THIS IS SCHOOL MANAGEMENT SYSTEM---------------------");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("---------------------1. For Student portal--------------------------------");
            System.out.println("---------------------2. For Teacher portal--------------------------------");
            System.out.println("---------------------3. For Admin portal----------------------------------");

            String op = input.nextLine();
            int option = Integer.parseInt(op);
            while (option != -1) {
                try {
                    switch (option) {
                        //// This is the student porta

                        case 1:
                            student.studentInfo();
                            break;
                        ////////////// Teacher portal
                        case 2:
                            teacher.teacherInfo();
                            ////// Admin Portal
                        case 3:
                            admin.adminPort();
                            break;
                        default:
                            System.out.println("Please Enter Correct input");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input Cannot be Character");
                }
                System.out.println("---------------------1. For Student portal--------------------------------");
                System.out.println("---------------------2. For Teacher portal--------------------------------");
                System.out.println("---------------------3. For Admin portal----------------------------------");
                op = input.nextLine();
                option = Integer.parseInt(op);

            }

        }
    }
}
