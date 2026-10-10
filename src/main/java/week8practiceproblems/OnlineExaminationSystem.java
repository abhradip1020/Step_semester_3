// Implement: Online Examination System

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OnlineExaminationSystem {

    public static void main(String[] args) {
        Student student1 = new Student("S1", "Student 1");

        Examination examA = new Examination("EX-101", "Exam A");
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Question 1", "Which keyword is used for inheritance in Java?", "C", 5);
        TrueFalseQuestion q2 = new TrueFalseQuestion("Question 2", "Java is a purely functional programming language.", false, 5);

        examA.addQuestion(q1);
        examA.addQuestion(q2);

        // Student 1 starts Exam A
        Attempt attempt = examA.startAttempt(student1);

        // Student 1 answers Question 1 (MCQ) with option C
        attempt.recordAnswer("Question 1", "C");

        // Student 1 answers Question 2 (TF) with True
        attempt.recordAnswer("Question 2", "True");

        // Student 1 submits Exam A
        attempt.submit();

        // Student 1 attempts to change answer for Question 1
        attempt.recordAnswer("Question 1", "A");
    }
}

class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

abstract class Question {
    private String id;
    private String text;
    private int points;

    public Question(String id, String text, int points) {
        this.id = id;
        this.text = text;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean evaluate(Answer answer);
}

class MultipleChoiceQuestion extends Question {
    private String correctOption;

    public MultipleChoiceQuestion(String id, String text, String correctOption, int points) {
        super(id, text, points);
        this.correctOption = correctOption;
    }

    @Override
    public boolean evaluate(Answer answer) {
        if (answer == null || answer.getValue() == null) {
            return false;
        }
        return correctOption.equalsIgnoreCase(answer.getValue().trim());
    }
}

class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(String id, String text, boolean correctAnswer, int points) {
        super(id, text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(Answer answer) {
        if (answer == null || answer.getValue() == null) {
            return false;
        }
        return String.valueOf(correctAnswer).equalsIgnoreCase(answer.getValue().trim());
    }
}

class ShortAnswerQuestion extends Question {
    private String expectedKeyword;

    public ShortAnswerQuestion(String id, String text, String expectedKeyword, int points) {
        super(id, text, points);
        this.expectedKeyword = expectedKeyword;
    }

    @Override
    public boolean evaluate(Answer answer) {
        if (answer == null || answer.getValue() == null) {
            return false;
        }
        return answer.getValue().trim().equalsIgnoreCase(expectedKeyword);
    }
}

class Answer {
    private String questionId;
    private String value;

    public Answer(String questionId, String value) {
        this.questionId = questionId;
        this.value = value;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getValue() {
        return value;
    }
}

enum AttemptStatus {
    IN_PROGRESS,
    SUBMITTED
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<String, Answer> recordedAnswers;
    private AttemptStatus status;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.recordedAnswers = new LinkedHashMap<>();
        this.status = AttemptStatus.IN_PROGRESS;
    }

    public Student getStudent() {
        return student;
    }

    public Examination getExamination() {
        return examination;
    }

    public AttemptStatus getStatus() {
        return status;
    }

    public void recordAnswer(String questionId, String answerValue) {
        if (status == AttemptStatus.SUBMITTED) {
            System.out.println("Cannot change answers for a submitted examination.");
            return;
        }
        recordedAnswers.put(questionId, new Answer(questionId, answerValue));
        System.out.printf("Answer recorded for %s.%n", questionId);
    }

    public void submit() {
        if (status == AttemptStatus.SUBMITTED) {
            System.out.println("Examination has already been submitted.");
            return;
        }
        status = AttemptStatus.SUBMITTED;
        System.out.printf("%s submitted by %s.%n", examination.getTitle(), student.getName());

        evaluateResults();
    }

    private void evaluateResults() {
        int earnedScore = 0;
        int totalPossibleScore = 0;
        StringBuilder resultBuilder = new StringBuilder("Result: ");

        List<Question> questions = examination.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            totalPossibleScore += q.getPoints();
            Answer ans = recordedAnswers.get(q.getId());
            boolean isCorrect = (ans != null && q.evaluate(ans));

            int awardedPoints = isCorrect ? q.getPoints() : 0;
            earnedScore += awardedPoints;

            resultBuilder.append(q.getId())
                         .append(": ")
                         .append(isCorrect ? "Correct" : "Incorrect")
                         .append(" (")
                         .append(awardedPoints)
                         .append(" points)");

            if (i < questions.size() - 1) {
                resultBuilder.append(", ");
            }
        }

        resultBuilder.append(". Total score: ")
                     .append(earnedScore)
                     .append("/")
                     .append(totalPossibleScore)
                     .append(".");

        System.out.println(resultBuilder.toString());
    }
}

class Examination {
    private String id;
    private String title;
    private List<Question> questions;
    private Map<String, Attempt> submittedAttempts;

    public Examination(String id, String title) {
        this.id = id;
        this.title = title;
        this.questions = new ArrayList<>();
        this.submittedAttempts = new LinkedHashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Attempt startAttempt(Student student) {
        if (submittedAttempts.containsKey(student.getId())) {
            System.out.printf("Student %s already has a submitted attempt for %s.%n",
                    student.getName(), title);
            return null;
        }
        System.out.printf("%s started by %s.%n", title, student.getName());
        Attempt attempt = new Attempt(student, this);
        submittedAttempts.put(student.getId(), attempt);
        return attempt;
    }
}
