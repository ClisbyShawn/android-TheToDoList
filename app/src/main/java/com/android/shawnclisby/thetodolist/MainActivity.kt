package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.shawnclisby.androidauth.network.TokenEntry
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val navController = navController()

        authViewModel.token.observe(this, { response ->
            response.data?.let { authToken ->
                TokenEntry.onToken(authToken)
                navController.navigate(R.id.mainFragment)
                authViewModel.me()
            }

            response.message?.let { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}