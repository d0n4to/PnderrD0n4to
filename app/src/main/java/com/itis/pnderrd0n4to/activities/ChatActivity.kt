package com.itis.pnderrd0n4to.activities

import ChatAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.itis.pnderrd0n4to.activities.Message
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.itis.pnderrd0n4to.R



// meccadonato79@gmail.com
// 12345678
// 52794
class ChatActivity : AppCompatActivity() {
    lateinit var btnBack: ImageView
    lateinit var title: TextView
    lateinit var sendMessage: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var message: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = findViewById(R.id.textViewChatTitle)
        btnBack = findViewById(R.id.buttonBack)
        sendMessage = findViewById(R.id.buttonSend)
        recyclerView = findViewById(R.id.recyclerViewChat)
        message = findViewById(R.id.editTextMessage)
        recyclerView.layoutManager = LinearLayoutManager(this) // Imposta il LayoutManager

        val idChat = intent.getIntExtra("IdChat", 0)
        title.text = "Chat $idChat"

        getMessages(idChat.toString()) // Avvia il listener per i messaggi
        startChat(idChat.toString(), "User1", "User2")

        btnBack.setOnClickListener {
            startActivity(Intent(this, join_chat::class.java))
        }

        sendMessage.setOnClickListener {
            if (message.text.toString().isNotEmpty()) {
                sendMessage(idChat.toString(), "user1", message.text.toString())
            }
        }
    }


    fun startChat(idChat: String, idUser1: String, idUser2: String){
            val chat = mapOf(
                "user1" to idUser1,
                "user2" to idUser2
            )
        val chatReference = FirebaseDatabase.getInstance().getReference("chats").child(idChat)
        chatReference.setValue(chat)
    }

    fun sendMessage(idChat: String, senderId: String, messageText: String){
        val messageReference = FirebaseDatabase.getInstance().getReference("chats").child(idChat).child("messages")
        val message = mapOf(
            "sender" to senderId,
            "content" to messageText,
            "timestamp" to System.currentTimeMillis()
        )
        messageReference.push().setValue(message) // Salva il messaggio nel database
        findViewById<EditText>(R.id.editTextMessage).text.clear() // Pulisci il campo di input
    }


    fun getMessages(idChat: String) {
        val messagesRef = FirebaseDatabase.getInstance().getReference("chats").child(idChat).child("messages")
        val messageList = mutableListOf<Message>()
        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if(message != null){
                    messageList.add(message)
                    updateRecycler(messageList)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun updateRecycler(message:List<Message>){
        val adapter = ChatAdapter(message)
        recyclerView.adapter = adapter
    }

}