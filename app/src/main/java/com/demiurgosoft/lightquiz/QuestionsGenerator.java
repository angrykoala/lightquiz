package com.demiurgosoft.lightquiz;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by demiurgosoft - 3/25/15
 */
public class QuestionsGenerator {
    private ArrayList<Question> questionsList;

    //Default constructor
    public QuestionsGenerator() {
        questionsList = new ArrayList<>();
        loadQuestions();
        shuffle();
    }

    public QuestionsGenerator(int number) {
        questionsList = new ArrayList<>();
        loadQuestions(number);
        shuffle();
    }
    //true if it have at least one valid question
    public boolean empty() {
        return questionsList.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(this.questionsList);
    }

    //return a question (runtime exception if empty), removes question from list
    public Question getQuestion() {
        if (questionsList.isEmpty()) throw new RuntimeException("QuestionGenerator empty");
        else {
            Question res = questionsList.get(0);
            questionsList.remove(0);
            if (!res.isValid())
                throw new RuntimeException("Question not valid"); //read another question?
            res.randomize();
            return res;
        }
    }

    //adds a question if valid
    private boolean addQuestion(Question q) {
        boolean valid = q.isValid();
        if (valid) {
            questionsList.add(q);
        }
        return valid;
    }

    //return generator size
    public int size() {
        return questionsList.size();
    }


    private void loadQuestions(int questionNumber) {

        if (questionNumber > Question.getQuestionSize())
            questionNumber = Question.getQuestionSize();
        if (questionNumber <= 0) throw new RuntimeException("raw questions empty");

        ImageQuestionLoader imageLoader = new ImageQuestionLoader();
        SoundQuestionLoader soundLoader = new SoundQuestionLoader();
        TextQuestionLoader textLoader = new TextQuestionLoader();
        QuestionType type;

        for (int i = 0; i < questionNumber; i++) {
            Question quest = null;
            do {
                type = QuestionType.getRandomType();
                switch (type) {
                    case IMAGE:
                        quest = imageLoader.load();
                        break;
                    case SOUND:
                        quest = soundLoader.load();
                        break;
                    case TEXT:
                        quest = textLoader.load();
                        break;
                }
            } while (quest == null);
            addQuestion(quest);
        }
        }

    private void loadQuestions() {
        this.loadQuestions(Question.getQuestionSize());
    }
    }
