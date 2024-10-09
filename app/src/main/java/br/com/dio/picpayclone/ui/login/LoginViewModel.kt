package br.com.dio.picpayclone.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpayclone.data.UsuarioLogado.token
import br.com.dio.picpayclone.model.LoginRequest
import br.com.dio.picpayclone.services.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class LoginUiState(
    val texto: String = "",
    val token: String? = null,
    val onError: Exception? = null,
    val onLoading: Boolean = false,
    val loginSucess: Boolean = false,
    val loginError: String? = null,
    val email: String = "",
    val senha: String = ""
)

class LoginViewModel :
    ViewModel(),
    KoinComponent {
    private val service: ApiService by inject()

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()


    private fun loginRequest(login: LoginRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(onLoading = true)
            try {
                val response = service.login(login)

                if (!response.isSuccessful)
                    {
                        logout()
                        _uiState.value = _uiState.value.copy(onLoading = false)
                        _uiState.value = _uiState.value.copy(loginError = response.body().toString())
                        return@launch
                    }

                _uiState.value = _uiState.value.copy(token = response.body()?.token)
                response.body()?.let {
                    token = it.token
                    _uiState.value = _uiState.value.copy(loginSucess = true)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(onError = e)
            }
            _uiState.value = _uiState.value.copy(onLoading = false)
        }
    }

    fun logout()  {
        // Implementar a lógica de Logout aqui
    }

    fun updateEmail(newEmail: String){
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun newPassword(newPassword: String){
        _uiState.value = _uiState.value.copy(senha = newPassword)
    }

    fun login(){
        val email = uiState.value.email
        val senha = uiState.value.senha

        val loginRequest = LoginRequest(email = email, password = senha)
        loginRequest(loginRequest)
    }

}
