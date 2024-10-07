package br.com.dio.picpayclone.data

object UsuarioLogado {
    lateinit var token: String

    val usuario =
        Usuario(
            "joaovf",
            "123456",
            "joaofreitas@gmail.com",
            "João Vitor Freitas",
            "62992920466",
            "1980-06-20",
            "62992920466",
            0.0,
        )

    fun setSaldo(saldo: Double) {
        usuario.saldo = saldo
    }

    fun isAuthenticated(): Boolean = this::token.isInitialized

    fun isNotAuthenticated(): Boolean = !isAuthenticated()
}
