package br.com.dio.picpayclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

data class ComponentesUiState(
    val bottomNavigation: Boolean = false,
)

class ComponentesViewModel :
    ViewModel(),
    KoinComponent {
    private val _componentes =
        MutableLiveData<Componentes>().also {
            it.value = temComponentes
        }
    val componentes: LiveData<Componentes> = _componentes

    var temComponentes = Componentes()
        set(value) {
            field = value
            _componentes.value = value
        }

    fun hideBottomNavigation()  {
        _componentes.value = _componentes.value?.copy(bottomNavigation = false)
    }

    fun showBottomNavigation()  {
        _componentes.value = _componentes.value?.copy(bottomNavigation = true)
    }
}

data class Componentes(
    val bottomNavigation: Boolean = false,
)
