package com.example.userpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AdminDashboard : AppCompatActivity() {

    private lateinit var restaurantName: EditText
    private lateinit var restaurantAddress: EditText
    private lateinit var uploadImageViews: List<ImageView>
    private lateinit var uploadImageButton: Button

    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference
    private val imageUris = mutableListOf<Uri?>(null, null, null)

    private val IMAGE_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        // Initialize views
        restaurantName = findViewById(R.id.restaurantName)
        restaurantAddress = findViewById(R.id.restaurantAddress)

        uploadImageViews = listOf(
            findViewById(R.id.uploadImageView1),
            findViewById(R.id.uploadImageView2),
            findViewById(R.id.uploadImageView3)
        )

        uploadImageButton = findViewById(R.id.btnUploadImages)

        // Initialize Firebase Database and Storage references
        database = FirebaseDatabase.getInstance().reference.child("Restaurants")
        storage = FirebaseStorage.getInstance().reference.child("restaurant_images")

        uploadImageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                selectImage(index)
            }
        }

        uploadImageButton.setOnClickListener {
            saveRestaurantData()
        }
    }

    private fun selectImage(imageIndex: Int) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, IMAGE_REQUEST_CODE + imageIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data?.data != null) {
            val imageIndex = requestCode - IMAGE_REQUEST_CODE
            if (imageIndex in imageUris.indices) {
                imageUris[imageIndex] = data.data
                uploadImageViews[imageIndex].setImageURI(imageUris[imageIndex])
            }
        }
    }

    private fun saveRestaurantData() {
        val name = restaurantName.text.toString()
        val address = restaurantAddress.text.toString()

        if (name.isNotEmpty() && address.isNotEmpty()) {
            val restaurantId = database.push().key ?: return
            val restaurantData = HashMap<String, Any>().apply {
                this["name"] = name
                this["address"] = address
            }

            val uploadTasks = imageUris.mapIndexedNotNull { index, uri ->
                uri?.let { uploadImageToFirebase(it, restaurantId, "image${index + 1}", restaurantData) }
            }

            if (uploadTasks.isNotEmpty()) {
                Tasks.whenAllSuccess<Uri>(*uploadTasks.toTypedArray()).addOnSuccessListener { uris ->
                    // All uploads completed successfully
                    database.child(restaurantId).setValue(restaurantData)
                        .addOnCompleteListener { task ->
                            showToast(task.isSuccessful, "Restaurant Data Saved!", "Failed to save restaurant data")
                        }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Save data immediately if no images
                database.child(restaurantId).setValue(restaurantData)
                    .addOnCompleteListener { task ->
                        showToast(task.isSuccessful, "Restaurant Data Saved!", "Failed to save restaurant data")
                    }
            }
        } else {
            Toast.makeText(this, "Please enter all details!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToFirebase(
        imageUri: Uri,
        restaurantId: String,
        imageField: String,
        restaurantData: HashMap<String, Any>
    ): Task<Uri> {
        val filePath = storage.child(restaurantId).child("$imageField.jpg")
        return filePath.putFile(imageUri).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            if (!task.isSuccessful) {
                throw task.exception ?: Exception("Upload failed")
            }
            filePath.downloadUrl
        }.addOnSuccessListener { uri ->
            restaurantData[imageField] = uri.toString()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showToast(isSuccessful: Boolean, successMessage: String, failureMessage: String) {
        Toast.makeText(this, if (isSuccessful) successMessage else failureMessage, Toast.LENGTH_SHORT).show()
    }
}
