package library.table;

import library.sqltable.DVDRating;

public class DVD extends LibraryItems {

    int duration;
    String director;
    private DVDRating score;

    public DVD(int id, String title, int yearPublished, String typeOfItem, int duration, String director, DVDRating score) {
        super(id, title, yearPublished, typeOfItem);
        this.duration = duration;
        this.director = director;
        this.score = score;
    }

    public DVD(int duration, String director, DVDRating score) {
        this.duration = duration;
        this.director = director;
        this.score = score;
    }

    public DVD(String title, int yearPublished, String typeOfItem, int duration, String director, DVDRating score) {
        super(title, yearPublished, typeOfItem);
        this.duration = duration;
        this.director = director;
        this.score = score;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public DVDRating getScore() {
        return score;
    }

    public void setScore(DVDRating score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return super.toString() +
                "duration: " + duration + "\n" +
                "director: " + director + "\n" +
                "score: " + score;
    }

}