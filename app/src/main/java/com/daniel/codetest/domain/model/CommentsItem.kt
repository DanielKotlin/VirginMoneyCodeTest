package com.daniel.codetest.domain.model

data class CommentsItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val post_id: Int
)