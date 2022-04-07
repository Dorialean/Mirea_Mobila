package ru.mirea.lukutin.firebaseauth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() , View.OnClickListener {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mStatusTextView: TextView
    private lateinit var mDetailTextView: TextView
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detial)
        emailField = findViewById(R.id.emailEdit)
        passwordField = findViewById(R.id.passEdit)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.btnNewAcc) {
            createAccount(emailField.text.toString(), passwordField.text.toString())
        } else if (i == R.id.btnSignIn) {
            signIn(emailField.text.toString(), passwordField.text.toString())
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            mStatusTextView.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            mDetailTextView.text = getString(R.string.firebase_status_fmt, user.uid)
        } else {
            mStatusTextView.setText(R.string.signed_out)
            mDetailTextView.text = null

        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = emailField.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailField.error = "Required."
            valid = false
        } else {
            emailField.error = null
        }
        val password = passwordField.text.toString()
        if (TextUtils.isEmpty(password)) {
            passwordField.error = "Required."
            valid = false
        } else {
            passwordField.error = null
        }
        return valid
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                }
                else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
                if (!task.isSuccessful) {
                    mStatusTextView.setText(R.string.auth_failed)
                }
            }
    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }
}