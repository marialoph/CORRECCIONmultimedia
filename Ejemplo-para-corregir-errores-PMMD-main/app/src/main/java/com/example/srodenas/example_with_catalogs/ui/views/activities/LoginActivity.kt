package com.example.srodenas.example_with_catalogs.ui.views.activities

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.srodenas.example_with_catalogs.databinding.LoginactivityBinding

import com.example.srodenas.example_with_catalogs.domain.users.models.User
import com.example.srodenas.example_with_catalogs.ui.viewmodel.users.UserViewModel
import com.example.srodenas.example_with_catalogs.ui.views.activities.fragments.dialogs.DialogRegisterUser


class LoginActivity : AppCompatActivity() {
    lateinit var binding: LoginactivityBinding
    val userViewModel: UserViewModel by viewModels()  //viewModel del Usuario.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerLiveData()
        initEvent()

    }

    private fun initEvent() {
        binding.btnLogin.setOnClickListener {
            userViewModel.isLogin(
                binding.txtEmail.text.toString(),
                binding.txtPassword.text.toString()
            )

        }
        binding.btnRegistro.setOnClickListener {
            val dialog = DialogRegisterUser() { user ->
                okOnRegisterUser(user)   //Ya tengo el nuevo usuario capturado.
            }
            dialog.show(this.supportFragmentManager, "Registro de nuevo usuario")

        }
    }

    //método que registra el usuario a patir del viewmodel.
    private fun okOnRegisterUser(user: User) {
        userViewModel.register(user)
    }

    private fun registerLiveData() {
        userViewModel.login.observe(this, { login ->
            try {
                if (login != null) {
                    //aquí tengo que lanzar el activity ppal.
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error en el logueo", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Manejar la excepción de manera adecuada
                e.printStackTrace()
            }
        })

        userViewModel.register.observe(this, { register ->
            try {
                if (register) {
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Usuario No registrado", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Manejar la excepción de manera adecuada
                e.printStackTrace()
            }
        })
    }
}