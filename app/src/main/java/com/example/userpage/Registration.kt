package com.example.userpage

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Registration : AppCompatActivity() {
    private lateinit var btnReg1: Button
    private lateinit var txtLogin1: TextView
    private lateinit var txtToggle: TextView
    private lateinit var username: EditText
    private lateinit var userPassword: EditText
    private lateinit var txtEmail: EditText
    private lateinit var userPhone: EditText

    private var currentUserId = 0

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        username = findViewById(R.id.username)
        userPassword = findViewById(R.id.userpassword)
        txtEmail = findViewById(R.id.txtemail)
        userPhone = findViewById(R.id.userphone)
        btnReg1 = findViewById(R.id.btnreg1)
        txtToggle = findViewById(R.id.txttoggle)

        // Initialize Firebase database reference
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        // Register button click event
        btnReg1.setOnClickListener {
            val name = username.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()
            val number = userPhone.text.toString().trim()

            if (validateInfo(name, email, password, number)) {
                checkUserExists(name, email, number)
            }
        }

        txtLogin1 = findViewById(R.id.signin)
        txtLogin1.setOnClickListener {
            startActivity(Intent(this, Signin::class.java))
        }

        txtToggle.setOnClickListener {
            if (txtToggle.text == "Show") {
                txtToggle.text = "Hide"
                userPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtToggle.text = "Show"
                userPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

    private fun checkUserExists(name: String, email: String, number: String) {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var usernameExists = false
                var emailExists = false
                var phoneNumberExists = false

                for (userSnapshot in dataSnapshot.children) {
                    val existingUsername = userSnapshot.child("name").getValue(String::class.java)
                    val userEmail = userSnapshot.child("email").getValue(String::class.java)
                    val userPhoneNumber = userSnapshot.child("phone_number").getValue(String::class.java)
                    if (existingUsername == name) usernameExists = true
                    if (userEmail == email) emailExists = true
                    if (userPhoneNumber == number) phoneNumberExists = true
                }

                if (usernameExists) {
                    username.error = "Username already exists"
                    username.requestFocus()
                } else if (emailExists) {
                    txtEmail.error = "Email already exists"
                    txtEmail.requestFocus()
                } else if (phoneNumberExists) {
                    userPhone.error = "Phone number already exists"
                    userPhone.requestFocus()
                } else {
                    saveUser(name, email, number)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Registration, "Database error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun saveUser(name: String, email: String, number: String) {
        val newUserId = databaseReference.push().key.toString() // Generate new unique key

        val userMap = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to userPassword.text.toString(),
            "phone_number" to number
        )

        databaseReference.child(newUserId).setValue(userMap)
            .addOnSuccessListener {
                Toast.makeText(this@Registration, "Successfully registered", Toast.LENGTH_LONG).show()
                val intent = Intent(this@Registration, Homepage2::class.java)
                intent.putExtra("userId", newUserId)
                intent.putExtra("username", name)
                intent.putExtra("userphone", number)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this@Registration, "Failure", Toast.LENGTH_LONG).show()
            }
    }

    private fun validateInfo(name: String, email: String, password: String, number: String): Boolean {
        if (name.isEmpty()) {
            username.error = "Name is required"
            username.requestFocus()
            return false
        }

        if (name.length > 12) {
            username.error = "User name is too lengthy"
            username.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.error = "Enter a valid email"
            txtEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            userPassword.error = "Password is required"
            userPassword.requestFocus()
            return false
        }

        if (password.length < 9) {
            userPassword.error = "Password must be at least 9 characters long"
            userPassword.requestFocus()
            return false
        }

        if (!password.matches(Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{9,}$"))) {
            userPassword.error = "Password must contain at least one special character, one number, and one letter"
            userPassword.requestFocus()
            return false
        }

        if (number.length != 10 || !Patterns.PHONE.matcher(number).matches()) {
            userPhone.error = "Enter a valid phone number"
            userPhone.requestFocus()
            return false
        }

        return true
    }
}