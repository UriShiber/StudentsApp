package com.sr.studentsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sr.studentsapp.R
import com.sr.studentsapp.model.Student

class StudentsAdapter(
    private val students: MutableList<Student>,
    private val onStudentClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflator = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_row, parent, false)
        return StudentViewHolder(inflator)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.student_row_name_text_view)
        private val studentId: TextView = itemView.findViewById(R.id.student_row_id_text_view)
        private val checkBox: CheckBox = itemView.findViewById(R.id.student_row_check_box)

        fun bind(student: Student) {
            studentName.text = student.name
            studentId.text = student.id
            checkBox.isChecked = student.isChecked

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
            }

            itemView.setOnClickListener {
                onStudentClick(student)
            }
        }
    }
}
