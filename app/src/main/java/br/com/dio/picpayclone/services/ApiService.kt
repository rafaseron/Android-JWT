package br.com.dio.picpayclone.services

import br.com.dio.picpayclone.data.PageTransacao
import br.com.dio.picpayclone.data.Transacao
import br.com.dio.picpayclone.data.Usuario
import br.com.dio.picpayclone.model.LoginRequest
import br.com.dio.picpayclone.model.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("account/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<LoginResponse>

    @GET("/usuarios/contatos")
    suspend fun getTodosUsuarios(
        @Query("login") login: String,
    ): List<Usuario>

    @GET("/usuarios/{login}")
    suspend fun getUsuario(
        @Path("login") login: String,
    ): Usuario

    @GET("/usuarios/{login}/saldo")
    suspend fun getSaldo(
        @Path("login") login: String,
    ): Usuario

    @POST("/transacoes")
    suspend fun realizarTransacao(
        @Body transacao: Transacao,
    ): Transacao

    @GET("/transacoes")
    suspend fun getTransacoes(
        @Query("login") login: String,
    ): PageTransacao
}
