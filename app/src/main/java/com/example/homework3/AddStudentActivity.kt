package com.example.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework3.Model.Student
import com.example.homework3.databinding.ActivityAddStudentBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddStudentActivity : AppCompatActivity() {

    val students = FirebaseDatabase.getInstance().reference.child("school").child("students")
    private lateinit var binding : ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addStudentInfo()
    }

    private fun addStudentInfo() {
        binding.addButton.setOnClickListener{
            val firstName = binding.FirstNameInputField.text.toString();
            val lastName = binding.LastNameInputField.text.toString();
            val personalNumber = binding.PersonalNumberInputField.text.toString();
            val profilePictureLink = binding.ProfilePictureInputField.text.toString();
            val email = binding.EmailInputField.text.toString();

            if(personalNumber.length != 13) {
                Toast.makeText(this@AddStudentActivity, "Personal Number Length Should Be 13", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!email.contains('@')) {
                Toast.makeText(this@AddStudentActivity, "Email Should Contain @ Symbol", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(firstName, lastName, personalNumber, profilePictureLink, email);
            students.child(student.personalNumber).setValue(student)
            finish();
        }
    }
}