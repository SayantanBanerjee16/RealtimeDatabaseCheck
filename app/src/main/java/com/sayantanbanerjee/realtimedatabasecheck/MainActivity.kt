package com.sayantanbanerjee.realtimedatabasecheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var postReference: DatabaseReference
    private lateinit var childEventListener: ChildEventListener

    private lateinit var uploadButton1: Button
    private lateinit var textView: TextView

    private var downloadText : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        uploadButton1 = findViewById(R.id.uploadButton)
        textView = findViewById(R.id.text)

        uploadButton1.setOnClickListener {
            for (cur_num in 0..1000) {
                uploadToDatabase(cur_num)
            }
        }

        postReference = database.child("NUMBER").child("test1")

        childEventListener = object : ChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val number = snapshot.getValue<Number>()
                downloadText += number?.num.toString() + "\n"
                textView.text = downloadText
            }

            override fun onCancelled(error: DatabaseError) {
                // not needed
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // not needed
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // not needed
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // not needed
            }

        }

        postReference.addChildEventListener(childEventListener)

    }

    private fun uploadToDatabase(cur_num: Int) {
        val num = Number(cur_num)
        database.child("NUMBER").child("test1").push().setValue(num)
    }
}
