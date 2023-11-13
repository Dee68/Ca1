public class Essay extends Exam implements Scorable{
    private String essayAnswer;//essay answer written by student
    private  int grammar;//mark attained by student for grammar
    private int content;//mark attained by student for essay content
    private  int wordLimit;//upper word limit for the essay
    // ANSI escape code constants for text colors
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";

    public Essay() {
        super();
    }

    public Essay(int examId, String subject, int duration, String essayAnswer, int grammar, int content, int wordLimit) throws ExamException {
        super(examId, subject, duration);
        if (duration < 30) {
            throw new ExamException(RED+" Exam duration can only be between 30 minutes to 180 minutes"+RESET);
        } else if (duration > 180) {
            throw new ExamException(RED+" Exam duration can only be between 30 minutes to 180 minutes"+RESET);
        }
        if (wordLimit < 500){
            throw new ExamException(RED+" WordLimit can not be less than 500 words."+RESET);
        }else if (wordLimit > 10000){
            throw new ExamException(RED+" WordLimit should not exceed 10000 words."+RESET);
        }
        if (grammar < 0){
            throw new ExamException(RED+" Grammar score can not be less than zero!!"+RESET);
        } else if (grammar > 100) {
            throw new ExamException(RED+" Grammar score can not exceed 100%!!!"+RESET);
        }
        if (content < 0){
            throw new ExamException(RED+" Content score can not be less than zero!!"+RESET);
        } else if (content > 100) {
            throw new ExamException(RED+" Content score can not exceed 100%!!!"+RESET);
        }
        this.essayAnswer = essayAnswer;
        this.grammar = grammar;
        this.content = content;
        this.wordLimit = wordLimit;
    }
    /*
       gets the count of words from student
       essayAnswer
    */
    public int getWordCount() throws ExamException{
        if (essayAnswer == null || essayAnswer.isEmpty()){
            return 0;
        }
        essayAnswer = essayAnswer.trim();
        // Splitting the string into words array of strings
        String[] words = essayAnswer.split("\\s+");
        //get the number of wordCount
        if (words.length > 0) return words.length;
        throw new ExamException("EssayAnswer can not be empty ");

    }
    public int getGrammar() {
        return grammar;
    }
    public int getContent() {
        return content;
    }


    /*
        This method grades the essay as such:
        40% allocated for content, 40% for grammar
        and 20% for adhering to the wordLimit count:
        if wordCount of essay is greater or less by 5 % of the wordLimit
        no deduction, if by (6-19) % 50% of wordLimit will be deducted,
        if by 20 % or above no score will be awarded
     */
    public double gradeEssay() throws ExamException {
        int wordCount;
        //double gradeForWordCount;
        wordCount = this.getWordCount();
        double i = (double) (wordCount)/wordLimit;
        i = 100 - Math.abs(i)*100;
        if (i <= 5.0){
            return  20.0;
        }else if(i <= 19.0) {
            return 10.0;
        }else{
            return 0.0;
        }

    }
    @Override
    public double calculateScore() {
        try {
            return (((double) this.grammar * 0.4) + ((double) this.content * 0.4) + this.gradeEssay());
        } catch (ExamException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void displayExamDetails() throws ExamException {
        //displays exam and results on screen
        System.out.println("==============================================================================================");
        System.out.printf("%10s %15s %12s %10s %10s %10s %10s","Exam Id","Subject","Duration","Exam Type","Grammar","Content","WordCount");
        System.out.println("\n=================================================================================================");
        String str = String.format("%8d %20s %7d %10s %8d %10d %12d",
                getExamId(),
                getSubject(),
                getDuration(),
                "Essay",
                this.grammar,
                this.content,
                (int)((this.gradeEssay()/20)*100)
        );
        System.out.println(str);


    }

    @Override
    public String toString() {
        System.out.println("==============================================================================================");
        System.out.printf("%5s %12s %12s %12s %12s %12s","Exam Id","Subject","Duration","Exam Type","Grammar","Content");
        System.out.println("\n=================================================================================================");
        return String.format("%5d  %12s  %12d  %10s %10d  %10d",
                getExamId(),
                getSubject(),
                getDuration(),
                "Essay",
                this.grammar,
                this.content);
    }

    @Override
    public String getExamType() {
        return "Essay";
    }

}
