package com.melvin.ongandroid.model

data class APIResponse<D : Any, E : Any?> (
    val success: Boolean,
    val data: D?,
    val error: E?,
    )