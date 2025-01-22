package com.sr.studentsapp.model

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.sr.studentsapp.model.dao.AppLocalDb
import com.sr.studentsapp.model.dao.AppLocalDbRepository
import java.util.concurrent.Executors

typealias StudentsCallback = (List<Student>) -> Unit
typealias EmptyCallback = () -> Unit

class Model private constructor() {
    private val database: AppLocalDbRepository = AppLocalDb.database
    private val executor = Executors.newSingleThreadExecutor() // side thread
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper()) // main thread

    // single tone
    companion object {
        val shared = Model()
    }

    fun getAllStudents(callback: StudentsCallback) {
        executor.execute{
            val students = database.studentDao().getAllStudents().toMutableList()
            mainHandler.post {
                callback(students)
            }
        }
    }

    fun add(student: Student, callback: EmptyCallback) {
        executor.execute{
            database.studentDao().insertStudents(student)
            Log.d("TAG", "Save student $student")
            mainHandler.post {
                callback()
            }
        }
    }

    fun edit(student: Student, callback: EmptyCallback) {
        executor.execute {
            database.studentDao().updateStudent(student)
            Log.d("TAG", "Updated student $student")
            mainHandler.post {
                callback()
            }
        }
    }

    fun delete(student: Student, callback: EmptyCallback) {
        executor.execute {
            database.studentDao().deleteStudent(student)
            Log.d("TAG", "Deleted student $student")
            mainHandler.post {
                callback()
            }
        }
    }
}