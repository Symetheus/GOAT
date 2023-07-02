package com.example.goat.data.remote.dto.character


import com.example.goat.domain.model.Character
import com.squareup.moshi.Json

data class CharactersResponseItem(
    @Json(name = "house")
    val house: HouseDto,
    @Json(name = "name")
    val name: String,
    @Json(name = "quotes")
    val quotes: List<String>?,
    @Json(name = "slug")
    val slug: String
)

fun CharactersResponseItem.toCharacter() = Character(
    name = name,
    slug = slug,
    house = house.name,
    houseSlug = house.slug,
    quotes = quotes ?: emptyList()
)