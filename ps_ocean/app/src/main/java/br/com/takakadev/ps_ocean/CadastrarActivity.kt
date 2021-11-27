package br.com.takakadev.ps_ocean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.takakadev.ps_ocean.databinding.ActivityCadastrarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastrarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCadastrarBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email: String = binding.layoutEmail.editText?.text.toString().trim()
        val password: String = binding.layoutPassword.editText?.text.toString().trim()

        binding.btCadastrar.setOnClickListener(){
            createAccount(email, password);
        }
    }

    private fun createAccount(email: String, password: String) {

        val email = binding.layoutEmail.editText?.text.toString().trim()
        val password = binding.layoutPassword.editText?.text.toString().trim()

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        val currentUser = user
        if(currentUser != null){
            reload();
        }
    }

    private fun reload() {
        abrirTelaLogin()
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

    fun abrirTelaLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}