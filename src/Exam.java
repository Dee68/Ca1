public abstract class Exam implements Scorable {
    private int examId;//uniquely identifies exam
    private String subject;//subject of exam
    private int duration;//duration of exam in minutes

    public abstract void displayExamDetails() throws ExamException;

    public Exam() {
    }

    public Exam(int examId, String subject, int duration) throws ExamException,NumberFormatException {
        if (duration < 30) {
            throw new ExamException("Exam duration can only be between 30 minutes to 180 minutes");
        } else if (duration > 180) {
            throw new ExamException("Exam duration can only be between 30 minutes to 180 minutes");
        }
        if (!subject.matches("[a-zA-z0-9 ]+")){
            throw new ExamException("Exam subject can only be alphanumeric ");
        } else if (subject.isBlank() || subject.isEmpty()) {
            throw new ExamException("Exam subject must be specified");
        }
        this.examId = examId;
        this.subject = subject;
        this.duration = duration;
    }

    //get examId value
    public int getExamId() {
        return examId;
    }


    public String getSubject() {
        return subject;
        //return fetchSubject();
    }

    public int getDuration() {
        return duration;
        //return fetchDuration();
    }

    @Override
    public String toString() {
        return String.format("Exam Id   Subject  Duration \n\t%d  %s  %d",
                this.examId,
                this.subject,
                this.duration);
    }

    public abstract String getExamType();
}
