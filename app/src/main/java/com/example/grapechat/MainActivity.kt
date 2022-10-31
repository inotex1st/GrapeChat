package com.example.grapechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grapechat.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database // здесь я передаю сообщение на БД
        val myRef = database.getReference("message")
        binding.bSend.setOnClickListener {
            myRef.setValue(binding.edMessage.text.toString())

        }
        onChangeListener(myRef)
    }

    private fun onChangeListener(dRef:DatabaseReference){ //передаю сообщение с БД на экран
        dRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
               binding.apply {
                   tvMessage.append("\n")
                   tvMessage.append("Vlad: ${snapshot.value.toString()}")
               }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}