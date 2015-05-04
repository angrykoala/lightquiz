package com.demiurgosoft.lightquiz;

import java.util.Random;

/**
 * Created by demiurgosoft - 4/28/15
 */
public enum QuestionType {
    TEXT, SOUND, IMAGE;

    public static QuestionType getRandomType() {
        Random rand = new Random();
        int selec = rand.nextInt(QuestionType.values().length);
        return QuestionType.values()[selec];
    }

}

