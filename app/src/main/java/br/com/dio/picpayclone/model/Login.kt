package br.com.dio.picpayclone.model

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val token: String,
)
