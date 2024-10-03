package br.com.dio.picpayclone.ui.ajuste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.dio.picpayclone.Componentes
import br.com.dio.picpayclone.ComponentesViewModel
import br.com.dio.picpayclone.R
import br.com.dio.picpayclone.data.UsuarioLogado
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AjusteFragment : Fragment() {

    private val componentesViewModel: ComponentesViewModel by sharedViewModel()
    private val ajusteViewModel: AjusteViewModel by viewModel()
    private val controlador by lazy { findNavController() }

    // variaveis lateinit para usar no findViewById
    private lateinit var textViewLoginPrincipal: TextView
    private lateinit var textViewNomeCompleto: TextView
    private lateinit var textViewLogin: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewNumero: TextView
    private lateinit var buttonSair: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ajuste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentesViewModel.temComponentes = Componentes(bottomNavigation = true)

        // findViewById
        textViewLoginPrincipal = view.findViewById(R.id.textViewLoginPrincipal)
        textViewNomeCompleto = view.findViewById(R.id.textViewNomeCompleto)
        textViewLogin = view.findViewById(R.id.textViewLogin)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewNumero = view.findViewById(R.id.textViewNumero)
        buttonSair = view.findViewById(R.id.buttonSair)

        configuraBotaoSair()
        configuraDadosUsuario()
    }

    private fun configuraDadosUsuario() {
        UsuarioLogado.usuario.let { usuario ->
            textViewLoginPrincipal.text = usuario.login
            textViewNomeCompleto.text = usuario.nomeCompleto
            textViewLogin.text = usuario.login
            textViewEmail.text = usuario.email
            textViewNumero.text = usuario.numeroTelefone
        }
    }

    private fun configuraBotaoSair() {
        buttonSair.setOnClickListener {

        }
    }
}