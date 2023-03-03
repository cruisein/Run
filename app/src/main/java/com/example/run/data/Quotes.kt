package com.example.run.data

import kotlin.random.Random

private val quotes = listOf(
    "“Be yourself; everyone else is already taken.” - Oscar Wilde",
    "“A room without books is like a body without a soul.” - Marcus Tullius Cicero",
    "“A friend is someone who knows all about you and still loves you.” - Elbert Hubbard",
)

fun pickQuote(): String {
    val randomIndex = Random.nextInt(quotes.size)
    return quotes[randomIndex]
}