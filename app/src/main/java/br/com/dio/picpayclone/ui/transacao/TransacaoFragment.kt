package br.com.dio.picpayclone.ui.transacao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.picpayclone.Componentes
import br.com.dio.picpayclone.ComponentesViewModel
import br.com.dio.picpayclone.R
import br.com.dio.picpayclone.data.*
import br.com.dio.picpayclone.extension.formatar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TransacaoFragment : Fragment() {

    private val componentesViewModel: ComponentesViewModel by sharedViewModel()
    private val transacaoViewModel: TransacaoViewModel by viewModel()
    private val argumentos by navArgs<TransacaoFragmentArgs>()
    private val usuario by lazy { argumentos.usuario }
    private val controlador by lazy { findNavController() }

    // variaveis lateinit para usar no findViewById
    private lateinit var textViewNome: TextView
    private lateinit var textViewNomeCompleto: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonCartaoCredito: RadioButton
    private lateinit var constraintLayoutCartaoCredito: View
    private lateinit var editTextNumeroCartao: EditText
    private lateinit var editTextTitular: EditText
    private lateinit var editTextVencimento: EditText
    private lateinit var editTextCVV: EditText
    private lateinit var editTextValor: EditText
    private lateinit var buttonTransferir: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transferencia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentesViewModel.temComponentes = Componentes(bottomNavigation = false)

        // findViewById
        textViewNome = view.findViewById(R.id.textViewNome)
        textViewNomeCompleto = view.findViewById(R.id.textViewNomeCompleto)
        radioGroup = view.findViewById(R.id.radioGroup)
        radioButtonCartaoCredito = view.findViewById(R.id.radioButtonCartaoCredito)
        constraintLayoutCartaoCredito = view.findViewById(R.id.constraintLayoutCartaoCredito)
        editTextNumeroCartao = view.findViewById(R.id.editTextNumeroCartao)
        editTextTitular = view.findViewById(R.id.editTextTitular)
        editTextVencimento = view.findViewById(R.id.editTextVencimento)
        editTextCVV = view.findViewById(R.id.editTextCVV)
        editTextValor = view.findViewById(R.id.editTextValor)
        buttonTransferir = view.findViewById(R.id.buttonTransferir)

        configuraDadosUsuario()
        configuraRadioGroup()
        configuraBotaoTransferir()
        observarTransferencia()
        observarErro()
    }

    private fun observarErro() {
        transacaoViewModel.onError.observe(viewLifecycleOwner, Observer {
            it?.let { mensagem ->
                Toast.makeText(this.context, mensagem, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observarTransferencia() {
        transacaoViewModel.transacao.observe(viewLifecycleOwner, Observer {
            val direcao =
                TransacaoFragmentDirections.actionNavigationTransferenciaToNavigationPagar()
            controlador.navigate(direcao)
        })
    }

    private fun configuraBotaoTransferir() {
        buttonTransferir.setOnClickListener {
            val transferencia = criarTransferencia()
            transacaoViewModel.realizaTransferencia(transferencia)
        }
    }

    private fun criarTransferencia(): Transacao {
        val usuarioOrigem = UsuarioLogado.usuario
        val isCartaoCredito = radioButtonCartaoCredito.isChecked
        val dataHora = Calendar.getInstance().formatar()
        val valor = getValor()
        return if (isCartaoCredito) {
            criarTransacaoComCartaoCredito(usuarioOrigem, dataHora, isCartaoCredito, valor)
        } else {
            criarTransacaoSemCartaoCredito(usuarioOrigem, dataHora, isCartaoCredito, valor)
        }
    }

    private fun criarTransacaoSemCartaoCredito(
        usuarioOrigem: Usuario,
        dataHora: String,
        isCartaoCredito: Boolean,
        valor: Double
    ): Transacao {
        return Transacao(
            Transacao.gerarHash(),
            usuarioOrigem,
            usuario,
            dataHora,
            isCartaoCredito,
            valor
        )
    }

    private fun criarTransacaoComCartaoCredito(
        usuarioOrigem: Usuario,
        dataHora: String,
        isCartaoCredito: Boolean,
        valor: Double
    ): Transacao {
        return Transacao(
            Transacao.gerarHash(),
            usuarioOrigem,
            usuario,
            dataHora,
            isCartaoCredito,
            valor,
            criarCartaoCredito(usuarioOrigem)
        )
    }

    private fun getValor(): Double {
        val valor = editTextValor.text.toString()
        return if (valor.isEmpty()) {
            0.0
        } else {
            valor.toDouble()
        }
    }

    private fun criarCartaoCredito(usuarioOrigem: Usuario): CartaoCredito {
        val numeroCartao = editTextNumeroCartao.text.toString()
        val titular = editTextTitular.text.toString()
        val dataExpiracao = editTextVencimento.text.toString()
        val cvv = editTextCVV.text.toString()
        return CartaoCredito(
            BandeiraCartao.VISA,
            numeroCartao,
            titular,
            dataExpiracao,
            cvv,
            "",
            usuarioOrigem
        )
    }

    private fun configuraDadosUsuario() {
        textViewNome.text = usuario.login
        textViewNomeCompleto.text = usuario.nomeCompleto
    }

    private fun configuraRadioGroup() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonCartaoCredito -> {
                    constraintLayoutCartaoCredito.visibility = VISIBLE
                }
                else -> {
                    constraintLayoutCartaoCredito.visibility = GONE
                }
            }
        }
    }
}