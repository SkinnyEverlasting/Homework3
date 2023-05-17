package com.example.homework3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework3.Model.Student
import com.example.homework3.databinding.ActivitySchoolBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SchoolActivity : AppCompatActivity() {

    val schoolInfoDatabase = FirebaseDatabase.getInstance().reference.child("school")
    val adapter = StudentsInfoAdapter()
    private lateinit var binding: ActivitySchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.StudentsInfo.adapter = adapter
        listenToDatabaseChanges()
        redirectToAddStudentActivity()
    }

    private fun redirectToAddStudentActivity() {
        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun listenToDatabaseChanges() {
        schoolInfoDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val schoolName = snapshot.child(SCHOOL_NAME).getValue(String::class.java) ?: ""
                val studentsList = snapshot.child(STUDENTS_LIST).children.map { it.getValue(Student::class.java)!! }.toTypedArray()
                binding.SchoolNameTitle.text = schoolName
                adapter.submitStudentList(studentsList)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SchoolActivity, "Error Occured", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val SCHOOL_NAME = "name"
        const val STUDENTS_LIST = "students"
    }
}