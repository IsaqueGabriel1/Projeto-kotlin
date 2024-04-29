package com.example.projetokotlin.view.telaprincipal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetokotlin.R
import com.example.projetokotlin.databinding.ActivityTelaPrincipalBinding
import com.example.projetokotlin.view.formlogin.FormLogin
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class telaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnDeslogar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this, FormLogin:: class.java)
            startActivity(voltarTelaLogin)
            finish()
        }
    }
}