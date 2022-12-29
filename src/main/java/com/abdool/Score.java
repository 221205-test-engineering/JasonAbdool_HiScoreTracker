package com.abdool;

public class Score {
    private int id;
    private String initials;
    private int score;

    public Score() {
    }

    public Score(int id, String initials, int score) {
        this.id = id;
        this.initials = initials;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", initials='" + initials + '\'' +
                ", score=" + score +
                '}';
    }
}
