package com.example.projetokotlin.view.formlogin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetokotlin.R
import com.example.projetokotlin.databinding.ActivityFormCadastroBinding
import com.example.projetokotlin.databinding.ActivityFormLoginBinding
import com.example.projetokotlin.view.formcadastro.FormCadastro
import com.example.projetokotlin.view.telaprincipal.telaPrincipal
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btbEntrar.setOnClickListener{
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                val msg = binding.msgExe.setText("Preencha todos os campos!")
                binding.msgExe.setTextColor(Color.RED)
            }else{
                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener{autentic ->
                    if(autentic.isSuccessful){
                        navegarTelainicial()
                    }else{
                        binding.msgExe.setText("Email ou senha invalidos!")
                    }
                }
            }
        }

        binding.telaCadastrar.setOnClickListener{view ->
            val intent = Intent(this,FormCadastro::class.java)
            startActivity(intent )
        }
    }

    private fun navegarTelainicial(){
        val intent = Intent(this,telaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart(){
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if(usuarioAtual != null){
            navegarTelainicial()
        }
    }
}