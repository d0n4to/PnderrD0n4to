package com.itis.pnderrd0n4to.activities
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
import com.itis.pnderrd0n4to.MainActivity
import com.itis.pnderrd0n4to.R


class SignUp : AppCompatActivity() {
    lateinit var btnReturn: Button
    lateinit var btnRegister: Button
    lateinit var emailInput: EditText
    lateinit var passwordInput: EditText
    lateinit var confirmPassword: EditText
    var auth :FirebaseAuth = FirebaseAuth.getInstance()
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
        confirmPassword = findViewById(R.id.confirmPasswordInput)
        btnReturn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnRegister.setOnClickListener{
            RegisterFun(emailInput.text.toString(),passwordInput.text.toString(),confirmPassword.text.toString())
        }

 }

    private fun RegisterFun(email: String, password: String,confirmPassword:String) {
        if(password == confirmPassword){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    Toast.makeText(this,"Successful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"ERRORE ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this,"Le password devono combaciare",Toast.LENGTH_LONG).show()
        }
    }
}