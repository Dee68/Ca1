public class MultipleChoice extends Exam implements Scorable{
    private int correctAnswers;//number of correct answers
    private int noQuestions;//number of questions
    // ANSI escape code constants for text colors
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";



    public MultipleChoice() {
        super();
    }

    public MultipleChoice(int examId, String subject, int duration, int noQuestions, int correctAnswers) throws ExamException {
        super(examId, subject, duration);
        if (noQuestions < 10){
            throw new ExamException(RED+" Number of questions can not be less than 10."+RESET);
        } else if (noQuestions > 50) {
            throw  new ExamException(RED+" Number of question can not exceed 50."+RESET);
        }
        if (correctAnswers < 0){
            throw new ExamException(RED+" Number of correct answers can not be less than zero."+RESET);
        } else if (correctAnswers > noQuestions) {
            throw new ExamException(RED+" Answers can not be more than questions"+RESET);
        }
        this.noQuestions = noQuestions;
        this.correctAnswers = correctAnswers;
    }


    //get correct answers
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    // get no of questions
    public int getNoQuestions() {
        return noQuestions;
    }
    @Override
    public String getExamType() {
        return "MultiChoice";
    }

    @Override
    public void displayExamDetails(){
        //displays exam and results on screen
        System.out.println("==============================================================================================");
        System.out.printf("%10s %15s %12s %15s %15s %15s","Exam Id","Subject","Duration","Exam Type","No Questions","CorrectAnswers");
        System.out.println("\n=================================================================================================");
        String str = String.format("%8d %15s %12d %20s %8d %12d", getExamId(),getSubject(),getDuration(),"Multi Choice",this.correctAnswers,this.noQuestions);
        System.out.println(str);
    }

    @Override
    public String toString() {
        System.out.println("========================================================================================================================");
        System.out.printf(GREEN+"%5s\t\t\t%10s\t\t\t%10s\t\t\t%10s\t\t\t%10s\t\t\t%10s\t\t\t%n","Exam Id","Subject","Duration","Exam Type","No Questions","CorrectAnswers"+RESET);
        System.out.println("========================================================================================================================");
        return String.format("%5d\t\t\t %8s\t\t\t%8d\t\t\t %10s\t\t\t%5d\t\t\t%12d",
                getExamId(),
                getSubject(),
                getDuration(),
                "Multi Choice",
                this.noQuestions,
                this.correctAnswers);
    }
    @Override
    public double calculateScore() {
        return ((double) (this.correctAnswers) / (double) this.noQuestions) * 100;
    }
}
