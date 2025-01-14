package br.com.dio.picpayclone.di

import br.com.dio.picpayclone.ComponentesViewModel
import br.com.dio.picpayclone.ui.ajuste.AjusteViewModel
import br.com.dio.picpayclone.ui.home.HomeViewModel
import br.com.dio.picpayclone.ui.login.LoginViewModel
import br.com.dio.picpayclone.ui.pagar.PagarViewModel
import br.com.dio.picpayclone.ui.transacao.TransacaoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { ComponentesViewModel() }
        viewModel { HomeViewModel(get()) }
        viewModel { PagarViewModel(get()) }
        viewModel { AjusteViewModel() }
        viewModel { TransacaoViewModel(get()) }
        viewModel { LoginViewModel() }
    }
