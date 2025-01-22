package com.sr.studentsapp.model;

import java.io.Serializable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class Student(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val phone: String,
    var isChecked: Boolean
) :Serializable