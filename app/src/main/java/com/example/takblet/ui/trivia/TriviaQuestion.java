package com.example.takblet.ui.trivia;

public class TriviaQuestion {

  private final String question;
  private final boolean answer;
  private final String difficulty;
  private final String category;
  private boolean answered;

  public String getQuestion() {
    return question;
  }

  public TriviaQuestion(String question, boolean answer, String difficulty, String category) {
    this.question = question;
    this.answer = answer;
    this.difficulty = difficulty;
    this.category = category;
    this.answered = false;
  }

  public boolean getAnswer() {
    return answer;
  }

  public void setAnswered() {
    this.answered = true;
  }

  public boolean getAnswered() {
    return answered;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public String getCategory() {
    return category;
  }
}
