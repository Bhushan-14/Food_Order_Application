package com.example.myapplication.beasfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.beasfood.databinding.ActivityLoginActicityBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginActicityBinding by lazy {
        ActivityLoginActicityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        if (auth.currentUser != null) {
            updateUI(auth.currentUser!!)
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.loginbutton.setOnClickListener {
            email = binding.loginEmail.text.toString().trim()
            password = binding.loginPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        binding.loginGoogleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }

        binding.dontHaveAccountButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                updateUI(auth.currentUser!!)
            } else {
                Toast.makeText(this, "Sign-in Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.w("GoogleSignIn", "Google sign in failed", e)
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                updateUI(auth.currentUser!!)
            } else {
                Log.w("GoogleSignIn", "signInWithCredential:failure", task.exception)
                Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
