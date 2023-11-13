# Software Development CA1
    1. An interface called Scorable containing the following method:

        a. calculateScore(): This method should be implemented by classes that represent exams and calculates the score of the exam


    2. An abstract class called Exam containing the following attributes:

        a. examId (integer) to uniquely identify each exam
        b. subject (string) to represent the subject of the exam
        c. duration (integer) to store the duration of the exam in minutes


    3. Two classes, MultipleChoice and Essay, that inherit the Exam class and implement the Scorable interface

        a. For MultipleChoice, include the following additional attributes:
            i. correctAnswers (integer) to store the number of correct answers
            ii. noQuestions (integer) to store the number of questions on the exam
        b. For Essay, include the following attributes:
            i. essayAnswer (string) to store the essay answer written by the student
            ii. grammar (integer) to store the mark attained by the student for grammar
            iii. content (integer) to store the mark attained by the student for essay content
            iv. wordLimit (integer) to store the upper word limit for the essay
        c. Essay should also contain a method gradeEssay() that calculates the essay score (there is no set method, you decide, but it should be based on grammar, content and word count, eg, you could allocate x marks for each, weighting them as you see fit, and penalise if the word count goes a
         certain percentage above or below the number of words allowed.  Use a string method to work out the number of words in the essay answer supplied for this!)
        d. Implement a method displayExamDetails() in both MultipleChoice and Essay classes to display the exam details and results on screen
        e. When implementing the calculateScore() method from the Scorable interface for both classes, assume the mark being returned is out of 100, eg, if you have 25 questions and 15 correct answers, the score is 60%.
    4. Create an ExamException class and use it to add the following validation to the Exam, Essay and MultipleChoice classes:

        a. duration should be between 30 and 180 minutes (Exam)
        b. wordCount should be greater than or equal to 0 (Essay)
        c. wordLimit should be between 500 and 10000 (Essay)
        d. noQuestions should be between 10 and 50 (MultipleChoice)
        e. correctAnswers should be greater than or equal to zero (MultipleChoice)


    5. Create a class Student with the following attributes:

        a. studentId (integer) to uniquely identify each student
        b. studentName (string) to store the name of the student
        c. examsTaken (a list of Exam objects) to store the exams taken by the student


    6. Create a StudentException class and use it in the Student class to ensure that studentName is between 2 and 30 characters in length


    7. Create a Printable interface with methods printSummaryResult() and printDetailedResults().

    8. Implement the Printable interface in the Student class to allow printing of student results to a text file (Loads of info available of the internet but you could start here https://www.homeandlearn.co.uk/java/write_to_textfile.html).  Output should be formatted appropriately.  It does not have to look exactly like the example below but it should be clearly laid out in tabular format. 


	
	Both print methods should be fully implemented.
    9. Create a class ExamResult that stores the result of an exam for a specific student. It should have the following attributes:

        d. student (a Student object) representing the student who took the exam
        e. exam (an Exam object) representing the exam taken by the student
        f. score (integer) representing the score achieved by the student in the exam
        g. Implement the Comparable interface in the ExamResult class to allow for comparison of results based on scores


    10. Create a main tester class ExamManagement that demonstrates the use of these classes. It should use ArrayLists and create instances of Student, Exam, and ExamResult.  It should also contain a menu driven system to allow exam results for multiple students to be entered from the keyboard and stored.  These results should then be displayed (offer several options here, eg, by exam type, sorted by score, etc) and a print to file option offered if required.

