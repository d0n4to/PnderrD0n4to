package com.itis.pnderrd0n4to.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.itis.pnderrd0n4to.R
import java.lang.ref.Reference
import kotlin.random.Random

class join_chat : AppCompatActivity() {
    lateinit var btnJoin: Button
    lateinit var btnCreate: Button
    lateinit var codeChat: EditText
    lateinit var resultCode: TextView
    val db = FirebaseDatabase.getInstance()
    val reference = db.getReference("chat_lobby")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_join_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnJoin = findViewById(R.id.buttonJoin)
        btnCreate = findViewById(R.id.buttonCreateChat)
        codeChat = findViewById(R.id.editTextChatId)
        resultCode = findViewById(R.id.ResultCode)


        btnCreate.setOnClickListener{
            var id: Int = (0..98560).random()
            createLobby(id)
        }

        btnJoin.setOnClickListener {
            val chatID = codeChat.text.toString()
            if(chatID.isNotEmpty()){
                joinChat(chatID)
            }else{
                Toast.makeText(this,"Inserisci un codice", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun joinChat(id: String){
            reference.child(id).get().addOnSuccessListener {
                dataSnap ->
                if(dataSnap.exists()){
                    Toast.makeText(this,"Sei entrato nella chat $id",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("IdChat", id.toIntOrNull())
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Codice Non valido",Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,"Errore connessione ",Toast.LENGTH_LONG).show()
            }
    }

    private fun checkUnique(id : String, callback: (Boolean) -> Unit){
        reference.child(id).get().addOnSuccessListener {
            snapShot -> val isUnique = !snapShot.exists()
            callback(isUnique)
        }
    }
    private fun createLobby(id: Int) {
        checkUnique(id.toString()){
            isUnique ->
            if(isUnique){
                val lobby = mapOf(
                    "id" to id,
                    "created at" to System.currentTimeMillis()
                )
                reference.child(id.toString()).setValue(lobby).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        resultCode.text = "Code: $id"
                        Toast.makeText(this, "id Aggiunto con successo", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Errore: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                createLobby((0.. 98560).random())
            }
        }
    }
}