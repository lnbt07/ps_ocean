package br.com.takakadev.ps_ocean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.util.Log
import br.com.takakadev.ps_ocean.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email: String = binding.layoutEmail.editText?.text.toString().trim()
        val password: String = binding.layoutPassword.editText?.text.toString().trim()


        binding.btLogin.setOnClickListener(){
            signIn(email, password)
        }

        binding.btCriar.setOnClickListener(){
            abrirTelaCadastrar();
        }
    }

    private fun signIn(email: String, password: String) {
        val email = binding.layoutEmail.editText?.text.toString().trim()
        val password = binding.layoutPassword.editText?.text.toString().trim()
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Autenticação falhou, favor validar o email e a senha.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }


    private fun updateUI(user: FirebaseUser?) {
        val currentUser = user
        if(currentUser != null){
            reload();
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    private fun reload() {
        abrirTelaMain()
    }


    fun abrirTelaMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun abrirTelaCadastrar(){
        val intent = Intent(this,CadastrarActivity::class.java)
        startActivity(intent)
        finish()
    }
}