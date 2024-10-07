package br.com.dio.picpayclone.model

data class LoginRequest(
    val login: String,
    val senha: String,
)

data class LoginResponse(
    val token: String,
)
