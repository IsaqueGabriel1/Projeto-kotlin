package com.example.projetokotlin.view.formcadastro

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projetokotlin.R
import com.example.projetokotlin.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {
    private lateinit var binding: ActivityFormCadastroBinding
    private var auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btbCadastrar.setOnClickListener{view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view,"preencha todos os campos",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener{cadastro ->
                    if(cadastro.isSuccessful){
                        binding.msgExe.setText("Usuario cadastrado com sucesso!")
                        binding.msgExe.setTextColor(Color.GREEN)
                        binding.editEmail.setText("")
                        binding.editSenha.setText("")
                    }

                }.addOnFailureListener{exe ->
                    val mensagemErro = when(exe){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com 8 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email valido"
                        is FirebaseAuthUserCollisionException -> "Essa conta já foi cadastrada"
                        is FirebaseNetworkException -> "Sem conexao com a internet!"
                        else -> "Erro ao cadastrar usuario";
                    }
                    binding.msgExe.setText(mensagemErro)

                }
            }
        }
    }
}