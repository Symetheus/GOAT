package com.example.goat.data.remote.dto.house


import com.example.goat.domain.model.HouseMember
import com.squareup.moshi.Json

data class MemberDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String
)

fun MemberDto.toMember() = HouseMember(
    name = name,
    slug = slug
)