package com.example.goat.data.remote.dto.challenge

import com.example.goat.domain.model.Player
import java.util.Timer

data class PlayerDto(
    val id: String = "",
    val score: Int = 0,
    val timer: Timer? = Timer(),
    val status: String? = null,
)

fun PlayerDto.toPlayer(): Player {
    return Player(
        id = id,
        score = score,
        timer = timer,
        status = status,
    )
}