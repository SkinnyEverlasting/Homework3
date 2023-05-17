package com.example.homework3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework3.Model.Student
import com.example.homework3.databinding.StudentElementBinding

class StudentsInfoAdapter: RecyclerView.Adapter<StudentsInfoAdapter.ViewHolder>() {

    var students = arrayOf<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StudentElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(students[position])
    }

    fun submitStudentList(students: Array<Student>) {
        this.students = students
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: StudentElementBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(student: Student) {
            binding.StudentFullName.text = "%s %s".format(student.firstName, student.lastName)
            binding.StudentEmail.text = student.email
            binding.StudentPersonalNumber.text = student.personalNumber
            Glide.with(itemView.context).load(student.profilePicture).into(binding.StudentProfilePicture)
        }

    }
}