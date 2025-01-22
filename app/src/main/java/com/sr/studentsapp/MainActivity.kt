package com.sr.studentsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sr.studentsapp.adapter.StudentsAdapter
import com.sr.studentsapp.model.Model
import com.sr.studentsapp.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private var studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_add_student_button)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initStudents()
        onCreateStudentButton()

    }
    private fun onCreateStudentButton() {
        val addStudentButton: Button = findViewById(R.id.main_activity_add_student_button)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initStudents() {
        Model.shared.getAllStudents { students ->
            studentsList.clear()
            studentsList.addAll(students)

            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            studentsAdapter = StudentsAdapter(studentsList) { student ->
                openStudentDetails(student)
            }
            recyclerView.adapter = studentsAdapter

            studentsAdapter.notifyDataSetChanged()
        }
    }

    private fun openStudentDetails(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("student", student)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        Model.shared.getAllStudents { students ->
            studentsList.clear()
            studentsList.addAll(students)
            studentsAdapter.notifyDataSetChanged()

        }
    }
}

