package com.example.goat.data.remote.dto.house


import com.example.goat.domain.model.House
import com.squareup.moshi.Json

data class HousesResponseItem(
    @Json(name = "members")
    val members: List<MemberDto>,
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String
)

fun HousesResponseItem.toHouse() = House(
    name = name,
    slug = slug,
    members = members.map { it.toMember() }
)