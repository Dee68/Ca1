import java.io.*;
import java.util.Comparator;

public class ExamResult implements Comparable<ExamResult>{
    Student student;
    Exam exam;
    private int score;

    public ExamResult(Student student, Exam exam, int score) {
        this.student = student;
        this.exam = exam;
        this.score = score;
    }
    public int getScore() {
        if (this.exam instanceof Essay){
            score = (int)this.exam.calculateScore();
        } else if (this.exam instanceof MultipleChoice) {
            score = (int)this.exam.calculateScore();
        }
        return score;
    }

    //display sorted result on screen
    public void displaySort(){
        System.out.println("==============================================================================================");
        System.out.printf("%5s %15s %12s %12s %12s","StudentId","StudentName","Exam Type", "Subject", "Exam Score");
        System.out.println("\n=================================================================================================");
        String str = String.format("%5d %20s %10s %13s %6d", this.student.getStudentId(),this.student.getStudentName(),this.exam.getExamType(),this.exam.getSubject(),this.getScore());
        System.out.println(str);
    }

    //print exam result to file
    public void printToFile(){
        String pr = "-------------------------------------------------------------------------------------------------------";
        String str = String.format("Student Id: %d  Name: %s", this.student.getStudentId(), this.student.getStudentName());
        try {
            File myFile = new File("examResults.txt");
            FileWriter fr = new FileWriter(myFile, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr1 = new PrintWriter(br);
            pr1.println(pr);
            pr1.println(str);
            pr1.println(pr);
            if (exam instanceof MultipleChoice) {
                pr1.printf("%5s %10s %15s %12s %17s %12s%n","Exam Id","Subject","Exam Type","No Correct","No Question","Score");
                pr1.println(pr);
                pr1.printf("%5d  %11s  %17s %7d %12d %17d%n",
                        exam.getExamId(),
                        exam.getSubject(),
                        "Multi Choice",
                        ((MultipleChoice) exam).getCorrectAnswers(),
                        ((MultipleChoice) exam).getNoQuestions(),
                        (int)exam.calculateScore()
                );
                pr1.println(pr);
            } else if (exam instanceof Essay) {
                pr1.printf("%5s %10s  %12s %17s %12s %10s %12s%n","Exam Id","Subject","Exam Type","Grammar","Content","WordCount","Score");
                pr1.println(pr);
                pr1.printf("%5d  %11s  %11s %14s %14d %10d %12d%n",
                        exam.getExamId(),
                        exam.getSubject(),
                        "Essay",
                        ((Essay) exam).getGrammar(),
                        ((Essay) exam).getContent(),
                        (int)((((Essay) exam).gradeEssay()/20.0)*100),
                        (int)exam.calculateScore()
                );
                pr1.println(pr);
            }

            pr1.close();
            br.close();
            fr.close();

        } catch (ExamException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(ExamResult o) {
        return Integer.compare(this.score, o.score);
    }

    //comparator usage:e.g. from GeeksForGeeks, sort by exam type ascending order
    public static Comparator<ExamResult> ExamTypeComparator = new Comparator<>() {
        @Override
        public int compare(ExamResult o1, ExamResult o2) {
            String type1 = (o1.exam instanceof Essay)? "Essay" : "MultiChoice";
            String type2 = (o2.exam instanceof  Essay)? "Essay" : "MultiChoice";
            return type1.compareTo(type2);
        }
    };
    //sort by exam subject in ascending order
    public static Comparator<ExamResult> ExamSubjectComparator = new Comparator<>() {
        @Override
        public int compare(ExamResult o1, ExamResult o2) {
            return o1.exam.getSubject().toUpperCase().compareTo(o2.exam.getSubject().toUpperCase());
        }
    };
    @Override
    public String toString() {
        return "ExamResult{" +
                "student=" + student.getStudentName() +
                ", exam=" + exam.getSubject() +
                ", score=" + score +
                '}';
    }

}
