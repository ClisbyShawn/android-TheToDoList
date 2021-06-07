package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel.AuthEvent.LoginError
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel.AuthEvent.LoginSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val navController = navController()

        lifecycleScope.launchWhenStarted {
            authViewModel.authFlow.collect { status ->
                when (status) {
                    is LoginSuccess -> navController.navigate(R.id.action_loginFragment_to_homeFragment)
                    //TODO: Remove Toast Message
                    is LoginError -> Toast.makeText(
                        this@MainActivity,
                        status.errorMessage,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

    }

    private fun navController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}