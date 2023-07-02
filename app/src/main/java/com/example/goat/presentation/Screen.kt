package com.example.goat.presentation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main")

}