package com.itis.pnderrd0n4to


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.itis.pnderrd0n4to.activities.ChatActivity
import com.itis.pnderrd0n4to.activities.SignUp
import com.itis.pnderrd0n4to.activities.join_chat

class MainActivity : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLogin = findViewById(R.id.loginBtn)
        btnRegister = findViewById(R.id.registerBtn)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)

        btnRegister.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        btnLogin.setOnClickListener{
            val emailUser = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if(emailUser.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(emailUser,password).addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        startActivity(Intent(this,join_chat::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Credenziali errate", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"Inserisci le passoword",Toast.LENGTH_LONG).show()
            }
        }
    }
}