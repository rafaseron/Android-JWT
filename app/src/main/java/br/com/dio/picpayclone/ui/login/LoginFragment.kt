package br.com.dio.picpayclone.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.dio.picpayclone.ComponentesViewModel
import br.com.dio.picpayclone.R
import br.com.dio.picpayclone.model.LoginRequest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private val componentesViewModel: ComponentesViewModel by activityViewModels()

    // lateinit var do findViewById
    private lateinit var buttonLogin: Button
    private lateinit var edtLogin: EditText
    private lateinit var edtSenha: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Desabilita a navegação para trás
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            // Não faça nada quando o usuário pressionar o botão de voltar
//        }

        componentesViewModel.hideBottomNavigation()

        // findViewById
        buttonLogin = view.findViewById<Button>(R.id.button_login)
        edtLogin = view.findViewById<EditText>(R.id.edt_login)
        edtSenha = view.findViewById<EditText>(R.id.edt_senha)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val login = LoginRequest(email = edtLogin.text.toString(), password = edtSenha.text.toString())

        buttonLogin.setOnClickListener {
            viewModel.newPassword(edtSenha.text.toString())
            viewModel.updateEmail(edtLogin.text.toString())

            viewModel.login()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.uiState.collect { state ->
                    if (state.loginSucess) {
                        findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
                    }
                }
            }

            // Trocar para Navegar para Login aqui depois, se der certo o Login ou fazer num Interceptor sei la
            // findNavController().navigate(R.id.action_navigation_transferencia_to_navigation_pagar)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.onLoading) {
                    progressBar.visibility = VISIBLE
                } else {
                    progressBar.visibility = INVISIBLE
                }

                if (state.onError != null) {
                    Toast.makeText(requireContext(), "Ocorreu um erro", Toast.LENGTH_LONG).show()
                }

                if (state.loginError != null){
                    Toast.makeText(requireContext(), "${state.loginError}", Toast.LENGTH_LONG)

                }
            }
        }
    }
}
