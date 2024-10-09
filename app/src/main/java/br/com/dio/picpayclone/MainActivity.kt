package br.com.dio.picpayclone

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val componentesViewModel: ComponentesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        componentesViewModel.componentes.observe(
            this,
            Observer {
                it?.let { temComponentes ->
                    if (temComponentes.bottomNavigation) {
                        navView.visibility = VISIBLE
                    } else {
                        navView.visibility = GONE
                    }
                }
            },
        )

        navView.setupWithNavController(navController)
    }
}
