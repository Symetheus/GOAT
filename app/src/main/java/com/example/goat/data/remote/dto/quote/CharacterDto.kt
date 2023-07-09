package com.example.goat.data.remote.dto.quote


import com.squareup.moshi.Json

data class CharacterDto(
    @Json(name = "house")
    val house: HouseDto?,
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String
)