package edu.uga.cs.csci4060_statecapitalsquiz;

import java.util.Arrays;

public class Quiz {
    long id;
    String date;
    String[] question;
    String[] correct;
    String[] answer;
    int score;

    public Quiz(String d, String q[], String c[], String a[], int s){
        id=-1;
        date = d;
        question = q;
        correct = c;
        answer = a;
        score = s;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String[] getQuestion() { return question; }
    public void setQuestion(String[] question) { this.question = question; }
    public String[] getCorrect() { return correct; }
    public void setCorrect(String[] correct) { this.correct = correct; }
    public String[] getAnswer() { return answer; }
    public void setAnswer(String[] answer) { this.answer = answer; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", question=" + Arrays.toString(question) +
                ", correct=" + Arrays.toString(correct) +
                ", answer=" + Arrays.toString(answer) +
                ", score=" + score +
                '}';
    }
}
