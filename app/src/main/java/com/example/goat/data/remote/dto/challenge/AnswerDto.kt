package com.example.goat.data.remote.dto.challenge

import com.example.goat.domain.model.Answer

data class AnswerDto(
    val name: String = "",
    val slug: String = "",
    val veracity: Boolean = false,
)

fun AnswerDto.toAnswer(): Answer {
    return Answer(
        name = name,
        slug = slug,
        veracity = veracity,
    )
}