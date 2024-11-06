package com.itis.pnderrd0n4to.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.itis.pnderrd0n4to.MainActivity
import com.itis.pnderrd0n4to.R

class Register : AppCompatActivity() {
    lateinit var btnReturn: Button
    lateinit var btnRegister: Button
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnReturn = findViewById(R.id.btnBack)
        btnRegister = findViewById(R.id.registerBtn)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)

        btnRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}