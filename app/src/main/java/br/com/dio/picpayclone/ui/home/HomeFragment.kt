package br.com.dio.picpayclone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.picpayclone.Componentes
import br.com.dio.picpayclone.ComponentesViewModel
import br.com.dio.picpayclone.R
import br.com.dio.picpayclone.data.Transacao
import br.com.dio.picpayclone.data.UsuarioLogado.isAuthenticated
import br.com.dio.picpayclone.extension.formatarMoeda
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val componentesViewModel: ComponentesViewModel by activityViewModel()
    private val homeViewModel: HomeViewModel by viewModel()
    private val controlador by lazy { findNavController() }

    // variaveis lateinit para usar no findViewById
    private lateinit var textViewLabelSaldo: TextView
    private lateinit var textViewSaldo: TextView
    private lateinit var progressBarSaldo: ProgressBar
    private lateinit var progressBarTransferencia: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        authenticationVerify()

        componentesViewModel.temComponentes = Componentes(bottomNavigation = true)

        // Inicializar as variaveis usando findViewById
        textViewLabelSaldo = view.findViewById(R.id.textViewLabelSaldo)
        textViewSaldo = view.findViewById(R.id.textViewSaldo)
        progressBarSaldo = view.findViewById(R.id.progressBarSaldo)
        progressBarTransferencia = view.findViewById(R.id.progressBarTransferencia)
        recyclerView = view.findViewById(R.id.recyclerView)

        observarSaldo()
        observarTransferencias()
        observarErroSaldo()
        observarErroTransferencia()
        observarLoadingSaldo()
        observarLoadingTransferencia()
    }

    private fun observarLoadingSaldo() {
        homeViewModel.onLoadingSaldo.observe(
            viewLifecycleOwner,
            Observer { onLoading ->
                if (onLoading) {
                    progressBarSaldo.visibility = VISIBLE
                    textViewSaldo.visibility = INVISIBLE
                    textViewLabelSaldo.visibility = INVISIBLE
                } else {
                    progressBarSaldo.visibility = GONE
                    textViewSaldo.visibility = VISIBLE
                    textViewLabelSaldo.visibility = VISIBLE
                }
            },
        )
    }

    private fun observarLoadingTransferencia() {
        homeViewModel.onLoadingTransferencia.observe(
            viewLifecycleOwner,
            Observer { onLoading ->
                if (onLoading) {
                    progressBarTransferencia.visibility = VISIBLE
                    recyclerView.visibility = GONE
                } else {
                    progressBarTransferencia.visibility = GONE
                    recyclerView.visibility = VISIBLE
                }
            },
        )
    }

    private fun observarErroTransferencia() {
        homeViewModel.onErrorTransferencia.observe(
            viewLifecycleOwner,
            Observer {
                it?.let { mensagem ->
                    configuraRecyclerView(mutableListOf())
                    Toast.makeText(this.context, mensagem, Toast.LENGTH_SHORT).show()
                }
            },
        )
    }

    private fun observarErroSaldo() {
        homeViewModel.onErrorSaldo.observe(
            viewLifecycleOwner,
            Observer {
                it?.let { mensagem ->
                    Toast.makeText(this.context, mensagem, Toast.LENGTH_SHORT).show()
                }
            },
        )
    }

    private fun observarTransferencias() {
        homeViewModel.transferencias.observe(
            viewLifecycleOwner,
            Observer {
                it?.let { transferencias ->
                    configuraRecyclerView(transferencias)
                }
            },
        )
    }

    private fun configuraRecyclerView(transferencais: List<Transacao>) {
        recyclerView.adapter = HomeAdapter(transferencais)
    }

    private fun observarSaldo() {
        homeViewModel.saldo.observe(
            viewLifecycleOwner,
            Observer {
                textViewSaldo.text = it.formatarMoeda()
            },
        )
    }

    private fun authenticationVerify()  {
        if (!isAuthenticated())
            {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_login)
            }
    }
}
