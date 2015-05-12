package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 5/12/15
 */
public enum QuestionGenres {
    GEOGRAFIA, CIENCIAS, ARTE, DEPORTES, CINE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
