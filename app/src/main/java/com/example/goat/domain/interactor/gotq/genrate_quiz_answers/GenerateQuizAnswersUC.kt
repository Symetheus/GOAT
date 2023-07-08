package com.example.goat.domain.interactor.gotq.genrate_quiz_answers

import com.example.goat.domain.model.Answer
import com.example.goat.domain.model.Character
import com.example.goat.domain.model.toAnswer
import javax.inject.Inject

class GenerateQuizAnswersUC @Inject constructor() {
    operator fun invoke(
        characters: List<Character>,
        trueAnswer: Answer
    ): List<Answer> {
        val answers = mutableListOf<Answer>()

        val answersFromCharacters = mutableListOf<Answer>()
        characters.forEach {
            answersFromCharacters.add(it.toAnswer())
        }

        answersFromCharacters.remove(trueAnswer)

        for (i in 0..2) {
            val randomIndex = (0 until answersFromCharacters.size).random()
            answers.add(answersFromCharacters[randomIndex])
            answersFromCharacters.removeAt(randomIndex)
        }
        answers.add(trueAnswer)

        answers.shuffle()

        return answers
    }
}
