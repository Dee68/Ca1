import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Student implements Printable{
    private int studentId;
    private String studentName;
    private ArrayList<Exam> examsTaken;

    // ANSI escape code constants for text colors
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";

    public Student()  {
        this.studentId = fetchStudId();
        this.studentName = fetchName();
        this.examsTaken = new ArrayList<>();
        System.out.println(GREEN+" Student with Id: "+ this.studentId +" successfully created"+RESET);
    }

    public Student(int studentId, String studentName) throws StudentException {
        if (studentName.length() < 2 || studentName.length() > 30){
            throw new StudentException(RED+" Student Name can only be between 2 and 30 characters long."+RESET);
        }
        this.studentName = studentName;
        this.studentId = studentId;
        this.examsTaken = new ArrayList<>();
        System.out.println(GREEN+" Student with Id: "+ this.studentId +" successfully created"+RESET);
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setExamsTaken(ArrayList<Exam> examsTaken) {
        this.examsTaken = examsTaken;
    }
    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public ArrayList<Exam> getExamsTaken() {
        return examsTaken;
    }

    //validate student Id value
    public  int fetchStudId() {
        ArrayList<Student> students = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        int value = 0;
        int studId = 0;
        while (value == 0) {
            System.out.print(" Enter Student Id"+RED+"(only +numbers allowed): "+RESET);
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!" + RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    if (value < 0){
                        value = 0;
                        System.out.println(RED+" Please only positive numbers allowed!!!"+RESET);
                    }

                    studId = value;
                    studId += (int)Math.floor(Math.random()*1000 + 1);


                } catch (NumberFormatException ex) {
                    System.out.println(" NumberFormatException: For input: "+RED + s + RESET);
                }
            }
        }
        return studId;
    }
    //validate student name

    public String fetchName() {
        Scanner input = new Scanner(System.in);
        String name;
        boolean flag = false;
        String pattern = "[a-zA-Z ]+";
        do {
            System.out.print(" Enter Student Name(only alphabets): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(" You did not enter a value!!");
            } else if(s.length() < 2 || s.length() > 30) {
                System.out.println(RED+" Student Name can only be between 2 and 30 characters long."+RESET);
            } else if (!s.matches(pattern)) {

                System.out.println(RED+" Please enter a valid input!!!"+RESET);
            }else{
                flag = s.matches(pattern);
            }
            name = s;
        }while (!flag);

        return name;
    }

    @Override
    public void printSummaryResult() {
        String pr = "-------------------------------------------------------------------------------------";
        String str = String.format("Student Id: %d  Name: %s", this.studentId, this.studentName);
        try {
            File myFile = new File("exams.txt");
            FileWriter fr = new FileWriter(myFile, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr1 = new PrintWriter(br);
            pr1.println(pr);
            pr1.println(str);
            pr1.println(pr);
            pr1.printf("%5s %10s %15s %12s %17s %12s%n","Exam Id","Subject","Duration","Exam Type","No Correct","Score");
            pr1.println(pr);
            if (!this.examsTaken.isEmpty()){
                for (Exam exam:this.examsTaken) {
                    if (exam instanceof MultipleChoice) {
                        pr1.printf("%5d  %12s  %7d %21s %10d %14d%n",
                                exam.getExamId(),
                                exam.getSubject(),
                                exam.getDuration(),
                                "Multi Choice",
                                ((MultipleChoice) exam).getCorrectAnswers(),
                                (int)exam.calculateScore()
                        );
                        pr1.println(pr);
                    } else if (exam instanceof Essay) {
                        pr1.printf("%5d  %12s  %7d %16s %10s %18d%n",
                                exam.getExamId(),
                                exam.getSubject(),
                                exam.getDuration(),
                                "Essay",
                                " ",
                                (int)exam.calculateScore()
                        );
                        pr1.println(pr);
                    }
                }
                pr1.close();
                br.close();
                fr.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void printDetailedResults(){
        String pr = "-------------------------------------------------------------------------------------------------------";
        String str = String.format("Student Id: %d  Name: %s", this.studentId, this.studentName);
        try {
            File myFile = new File("examDetails.txt");
            FileWriter fr = new FileWriter(myFile, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr1 = new PrintWriter(br);
            pr1.println(pr);
            pr1.println(str);
            pr1.println(pr);
            if (!this.examsTaken.isEmpty()){
                for (Exam exam:this.examsTaken) {
                    if (exam instanceof MultipleChoice) {
                        pr1.printf("%5s %10s %15s %12s %17s %12s%n","Exam Id","Subject","Duration","Exam Type","No Correct","No Question");
                        pr1.println(pr);
                        pr1.printf("%5d  %12s  %7d %21s %10d %14d%n",
                                exam.getExamId(),
                                exam.getSubject(),
                                exam.getDuration(),
                                "Multi Choice",
                                ((MultipleChoice) exam).getCorrectAnswers(),
                                ((MultipleChoice) exam).getNoQuestions()
                        );
                        pr1.println(pr);
                    } else if (exam instanceof Essay) {
                        pr1.printf("%5s %10s %15s %12s %17s %12s %12s%n","Exam Id","Subject","Duration","Exam Type","Grammar","Content","WordCount");
                        pr1.println(pr);
                        pr1.printf("%5d  %12s  %7d %16s %10s %18d %10d%n",
                                exam.getExamId(),
                                exam.getSubject(),
                                exam.getDuration(),
                                "Essay",
                                ((Essay) exam).getGrammar(),
                                ((Essay) exam).getContent(),
                                (int)((((Essay) exam).gradeEssay()/20.0)*100)
                        );
                        pr1.println(pr);
                    }
                }
                pr1.close();
                br.close();
                fr.close();

            }

        } catch (ExamException | IOException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", examsTaken=" + examsTaken +
                '}';
    }

}
