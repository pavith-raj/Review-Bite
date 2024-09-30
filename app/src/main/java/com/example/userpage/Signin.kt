package com.example.userpage

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Signin : AppCompatActivity() {

    private lateinit var txtemail: EditText
    private lateinit var userpassword: EditText
    private lateinit var txttoggle: TextView
    private lateinit var btnsig1: Button
    private lateinit var txtreg1: TextView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)

        // Initialize Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        // Bind views
        txtemail = findViewById(R.id.txtemail)
        userpassword = findViewById(R.id.userpassword)
        txttoggle = findViewById(R.id.txttoggle)
        btnsig1 = findViewById(R.id.btnsig1)
        txtreg1 = findViewById(R.id.register)

        // Sign in button action
        btnsig1.setOnClickListener {
            val email = txtemail.text.toString()
            val password = userpassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email == "reviewbite@gmail.com" && password == "bite123") {
                // Navigate to Admin Dashboard if the credentials match
                startActivity(Intent(this@Signin, AdminDashboard::class.java))
                return@setOnClickListener
            }

            databaseReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (userSnapshot in dataSnapshot.children) {
                                val userPassword = userSnapshot.child("password").getValue(String::class.java)

                                if (userPassword != null && userPassword == password) {
                                    // Sign in successful
                                    val userId = userSnapshot.key
                                    val username = userSnapshot.child("name").getValue(String::class.java)
                                    val userphone = userSnapshot.child("phone_number").getValue(String::class.java)

                                    // Pass data to homepage2 and navigate
                                    val intent = Intent(this@Signin,Homepage2::class.java)
                                    intent.putExtra("userId", userId)
                                    intent.putExtra("username", username)
                                    intent.putExtra("userphone", userphone)
                                    startActivity(intent)

                                    Toast.makeText(this@Signin, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                                    return
                                } else {
                                    // Incorrect password
                                    userpassword.error = "Invalid password"
                                    userpassword.requestFocus()
                                    return // Exit after finding the incorrect password
                                }
                            }
                        } else {
                            // Email not found
                            txtemail.error = "Email not found"
                            txtemail.requestFocus()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@Signin, "Database error: ${databaseError.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }

        // Toggle password visibility
        txttoggle.setOnClickListener {
            if (txttoggle.text.toString() == "Show") {
                txttoggle.text = "Hide"
                userpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txttoggle.text = "Show"
                userpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            userpassword.setSelection(userpassword.text.length) // Keep the cursor at the end
        }

        // Navigate to registration activity
        txtreg1.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }
    }
}
