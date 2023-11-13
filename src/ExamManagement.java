import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ExamManagement {
    // ANSI escape code constants for text colors
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001b[34m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";

    public static void main(String[] args){
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<ExamResult> results = new ArrayList<>();
        while (true){
            System.out.println(BLUE+"""
                =======================================
                    Welcome to ExamManagement System
                =======================================
                """+RESET);
            System.out.println(BLUE+"  1. - Add Student"+RESET);
            System.out.println(BLUE+"  2. - Add Exam Result"+RESET);
            System.out.println(BLUE+"  3. - Display exam details on screen"+RESET);
            System.out.println(BLUE+"  4. - Print student result to file"+RESET);
            System.out.println(BLUE+"  5. - Print ExamResult to file"+RESET);
            System.out.println(BLUE+"  6. - Display ExamResult sorted by score"+RESET);
            System.out.println(BLUE+"  7. - Display ExamResult sorted by type"+RESET);
            System.out.println(BLUE+"  8. - Display ExamResult sorted by subject"+RESET);
            System.out.println(BLUE+"  9. - Exit"+RESET);
            int choice = enterChoice();
            switch (choice){
                case 1:
                    //add student
                    System.out.println(GREEN+" Creating student object..."+RESET);
                    //Student student = new Student();
                    students.add(new Student());
                    break;
                case 2:
                    //add exam result
                    System.out.println(GREEN+" Get student by Id and add exam taken by student..."+RESET);
                    if (students.isEmpty()){
                        System.out.println(RED+" No students added yet. Please add a student first."+RESET);
                        break;
                    }
                    //gets student with given studentId or returns null object
                    Student selectedStudent = findStudentById(students, enterStudId());
                    if (selectedStudent == null){
                        System.out.println(RED+" Student not found. Please enter a valid Student ID."+RESET);
                        break;
                    }
                    // gets the exam type or returns null
                    Exam exam = getExamType();
                    if (exam == null){
                        System.out.println(RED+" Please add exam before proceeding"+RESET);
                        break;
                    }
                    //add exam to student array list of exams taken
                    selectedStudent.getExamsTaken().add(exam);
                    int score = (int)exam.calculateScore();//gets the score for the exam
                    ExamResult result = new ExamResult(selectedStudent,exam,score);//creates exam result
                    results.add(result);//add examResult to array list of ExamResults
                    break;
                case 3:
                    //display exam details on screen
                    System.out.println(GREEN+" Displaying exam details on screen unsorted..."+RESET);
                    if (results.isEmpty()){
                        System.out.println(RED+ """ 
                                No ExamResult of student added yet.
                                Please add exam result of student first"""+RESET);
                        break;
                    }
                    results.forEach(ExamResult::displaySort);

                    break;
                case 4:
                    //print student result to file
                    System.out.println(GREEN+" Printing student(s) result to file(exams.txt & examDetails.txt)..."+RESET);
                    if (students.isEmpty()){
                        System.out.println(RED+" No students added yet. Please add a student first."+RESET);
                        break;
                    }
                    for (Student value : students) {
                        value.printSummaryResult();
                        value.printDetailedResults();
                    }
                    System.out.println(GREEN+" Student(s) results printed to file."+RESET);
                    break;
                case 5:
                    //print exam result to file
                    System.out.println(GREEN+" Printing Exam results to file(examResults.txt)..."+RESET);
                    if (results.isEmpty()){
                        System.out.println(RED+ """ 
                                No ExamResult of student added yet.
                                Please add exam result of student first"""+RESET);
                        break;
                    }
                    Collections.sort(results);
                    for (ExamResult examResult:results) {
                        examResult.printToFile();
                    }
                    System.out.println(GREEN+" Exam result(s) printed to file."+RESET
                    );
                    break;
                case 6:
                    //display exam result sorted by score
                    System.out.println(GREEN+" Sorting exam result by score..."+RESET);
                    if (results.isEmpty()){
                        System.out.println(RED+ """ 
                                No ExamResult of student added yet.
                                Please add exam result of student first"""+RESET);
                        break;
                    }
                    Collections.sort(results);//sorts Exam results in ascending order by score
                    for (ExamResult examResult:results) {
                        examResult.displaySort();
                    }
                    break;
                case 7:
                    //display exam result by exam type
                    System.out.println(GREEN+" Sorting exam result by exam type..."+RESET);
                    if (results.isEmpty()){
                        System.out.println(RED+ """ 
                                No ExamResult of student added yet.
                                Please add exam result of student first"""+RESET);
                        break;
                    }
                    results.sort(ExamResult.ExamTypeComparator);
                    for (ExamResult examResult:results) {
                        examResult.displaySort();
                    }
                    break;
                case 8:
                    //display exam result by exam subject
                    System.out.println(GREEN+" Sorting exam result by exam subject..."+RESET);
                    if (results.isEmpty()){
                        System.out.println(RED+ """ 
                                No ExamResult of student added yet.
                                Please add exam result of student first"""+RESET);
                        break;
                    }
                    results.sort(ExamResult.ExamSubjectComparator);
                    for (ExamResult examResult:results) {
                        examResult.displaySort();
                    }
                    break;
                case 9:
                    //exit loop
                    System.out.println(GREEN+" \033[1mThank you for using the application."+RESET);
                    System.exit(0);
                default:
                    System.out.println(RED+" Invalid input: Please pay attention to instructions!!!"+RESET);
                    break;

            }
        }

    }
    // choice validation
    public static int enterChoice() {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int choice = 0;
        while (value == 0) {
            System.out.print(BLUE+"  Enter Choice: "+RED+"(only +numbers allowed): "+RESET);
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED + " You did not enter a value!!" + RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    if (value < 0){
                        value = 0;
                        System.out.println(RED+" Please only positive numbers allowed!!"+RESET);
                    }
                    choice = value;

                } catch (NumberFormatException ex) {
                    System.out.println(RED + " Please enter a valid input!!!" + RESET);
                }

            }

        }
        return choice;
    }

    //get exam type & return exam
    public static Exam getExamType(){
        int select = 0;
        while (select == 0){
            System.out.println(" Select exam type you will like to create:");
            System.out.println(" Type 1 - MultiChoice Exam");
            System.out.println(" Type 2 - Essay Exam");
            System.out.println(" Type 3 - Exit");
            select = enterChoice();
            switch (select){
                case 1:
                    return multiChoiceExam();
                case 2:
                    return essayExam();
                case 3:
                    System.out.println("Choose an exam type for student!!!");
                    break;
                default:
                    System.out.println(RED+" Invalid Choice, please follow instructions!!!" +RESET);
                    break;
            }
        }
        return null;
    }


    //find student by id
    private static Student findStudentById(ArrayList<Student> students, int studentId){
        for (Student student:students) {
            if (student.getStudentId() == studentId){
                return student;
            }
        }
        return null;//student not found
    }
    //enter studentId to find
    public static int enterStudId() {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int studId = 0;
        while (value == 0) {
            System.out.print(" Enter Student Id"+RED+"(only +numbers allowed): "+RESET);
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED + " You did not enter a value!!" + RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    try{
                        if (value < 0){
                            value = 0;
                        }
                        studId = value;
                    }catch (RuntimeException e){
                        System.out.println(RED+" Please only positive numbers allowed!!"+RESET);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println(RED + " Please enter a valid input!!!" + RESET);
                }

            }

        }
        return studId;
    }
    //get examId value
    public static int fetchExamId()  {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int exId = 0;
        while (value == 0) {
            System.out.print(" Enter examId(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    if (value < 0){
                        value = 0;
                        System.out.println(RED+" Please only positive numbers allowed!!!"+RESET);
                    }
                    exId = value;

                } catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return exId;
    }
    //get duration value
    public  static int fetchDuration() {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int dur = 0;
        while (value == 0) {
            System.out.print(" Enter exam Duration(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(" You did not enter a value!!");
            } else {
                try {
                    value = Integer.parseInt(s);

                    if (value < 30 || value > 180){
                        value = 0;
                        System.out.println(" ExamException: "+RED+"Exam duration can only be between 30 minutes to 180 minutes"+RESET);
                    }
                    dur = value;

                } catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return dur;
    }
    public static String fetchSubject() {
        Scanner input = new Scanner(System.in);
        String subject;
        boolean flag = false;
        String pattern = "[a-zA-Z ]+";
        do {
            System.out.print(" Enter exam subject(only alphabets): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else if (!s.matches(pattern)) {

                System.out.println(RED+" Please enter a valid input!!!"+RESET);
            } else {
                flag = s.matches(pattern);
            }
            subject = s;
        }while (!flag);

        return subject;
    }
    //get no questions value
    public  static int fetchNoQuestions()  {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int nQuest = 0;
        while (value == 0) {
            System.out.print(" Enter exam number of questions(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else {
                try {
                    value = Integer.parseInt(s);

                    if (value < 10 || value > 50){
                        value = 0;
                        System.out.println("ExamException: "+RED+"Questions can only be between 10 and 50!!"+RESET);
                    }
                    nQuest = value;

                }
                catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return nQuest;
    }
    //get correct Answers value
    public  static int fetchCorrectAns()  {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int correctAns = 0;
        while (value == 0) {
            System.out.print(" Enter number of correct answers(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    if (value < 0){
                        value = 0;
                        System.out.println(RED+"Please only positive numbers allowed!!!"+RESET);
                    }
                    correctAns = value;
                } catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return correctAns;
    }
    //get grammar score value
    public static int fetchGrammar() {
        Scanner input = new Scanner(System.in);
        int value = 0;
        int grammar = 0;
        while (value == 0) {
            System.out.print(" Enter score for grammar(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    if (value < 0 || value > 100){
                        value = 0;
                        System.out.println(RED+" Grammar score can only be between 0% and 100%!!!"+RESET);
                    }
                    grammar = value;

                } catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return grammar;
    }
    //get content score value
    public  static int fetchContent(){
        Scanner input = new Scanner(System.in);
        int value = 0;
        int content = 0;
        while (value == 0) {
            System.out.print("Enter score for content(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                    if (value < 0 || value > 100){
                        value = 0;
                        System.out.println(RED+" Content score can only be between 0 and 100%"+RESET);
                    }
                    content = value;
                } catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return content;
    }
    /* get essayAnswer */
    public static String fetchEssayAns(){
        Scanner scanner = new Scanner(System.in);
        String essayAnswer;
        scanner.useDelimiter("\\n");
        System.out.println(YELLOW+" Enter text and press enter"+RESET);
        while (true) {
            System.out.println(" Enter essay answer: ");
            essayAnswer = scanner.next();
            break;
        }

        return essayAnswer;
    }
    //get word limit needed for essay exam
    public static int fetchWordLimit(){
        Scanner input = new Scanner(System.in);
        int value = 0;
        int wLimit = 0;
        while (value == 0) {
            System.out.print(" Enter wordLimit for exam(only +numbers allowed): ");
            String s = input.nextLine();
            if (s.isEmpty()) {
                System.out.println(RED+" You did not enter a value!!"+RESET);
            } else {
                try {
                    value = Integer.parseInt(s);
                        if (value < 500 || value > 10000){
                            value = 0;
                            System.out.println(" ExamException: "+RED+"WordLimit can only be between 500 and 10000!!!"+RESET);
                        }
                        wLimit = value;

                } catch (Exception ex) {
                    System.out.println(RED+" Please enter a valid input!!!"+RESET);
                }
            }
        }
        return wLimit;
    }
    //create multiChoice type of exam
    public static Exam multiChoiceExam() {
        Exam exam = new MultipleChoice();
        int exId,dur,nQuest,correctAns;
        String subject;
        try{
            exId = fetchExamId();
            subject = fetchSubject();
            String info = YELLOW+"  Duration time in minutes"+
                    " can only be between 30 and 180!!"+RESET;
            System.out.println(info);
            dur = fetchDuration();

            System.out.println(YELLOW+" Number of questions can only be between 10 and 50,"+
                    " also answers can not be more than questions!!."+RESET);
            nQuest = fetchNoQuestions();
            System.out.println(YELLOW+"""
                      Please make sure the number of correct answers value
                      does not surpass the number of questions!!!
                    """+RESET);
            correctAns = fetchCorrectAns();
            while (nQuest < correctAns){
                System.out.println(RED+" Correct answers can not be greater than number of questions!!!"+RESET);
                correctAns = fetchCorrectAns();
            }

            exam = new MultipleChoice(exId,subject,dur,nQuest,correctAns);
            System.out.println(GREEN+" Successfully created a multiChoice exam."+RESET);
            return exam;
        }catch (ExamException e){
            System.out.println("Invalid input!!!");
        }
        System.out.println(RED+" Unable to create exam, invalid inputs"+RESET);
        return exam;
    }
    //create essay type of exam
    public  static Exam essayExam(){
        Exam es = new Essay();
        int exId,dur,grammar,content,wordLimit;
        String subject,essayAnswer;
        try{
            exId = fetchExamId();
            subject = fetchSubject();
            String info = " Exam duration time in minutes"+
                    " can only be between 30 and 180!!";
            System.out.println(YELLOW+info+RESET);
            dur = fetchDuration();
            System.out.println(YELLOW + " !!Remember grammar score can not exceed 100%"+RESET);
            grammar = fetchGrammar();
            while (grammar > 100){
                grammar = fetchGrammar();
            }
            System.out.println(YELLOW + " !!Remember content score can not exceed 100%"+RESET);
            content = fetchContent();
            while (content > 100){
                System.out.println(YELLOW + " !!Content score can not exceed 100%"+RESET);
                content = fetchContent();
            }
            wordLimit = fetchWordLimit();
            essayAnswer = fetchEssayAns();
            es = new Essay(exId,subject,dur,essayAnswer,grammar,content,wordLimit);
            System.out.println(GREEN+" \nSuccessfully created an essay type exam."+RESET);
            return es;
        }catch (ExamException e){
            System.out.println(RED+" Invalid input!!"+RESET);
        }
        System.out.println(RED+" Unable to create exam, invalid inputs"+RESET);
        return es;
    }
}
