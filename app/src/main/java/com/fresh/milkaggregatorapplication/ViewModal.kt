package com.fresh.milkaggregatorapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModal(application: Application) : AndroidViewModel(application) {

    private val repository: CourseRepository

    private val _courseList = MutableLiveData<List<CourseModal>>()
    val courseList: LiveData<List<CourseModal>> = _courseList


    val allCourses: LiveData<List<CourseModal>>

    init {

        repository = CourseRepository(application)
        allCourses = repository.allCourses
    }

    fun insert(model: CourseModal?) {
        repository.insert(model)
    }

    fun update(model: CourseModal?) {
        repository.update(model)
    }

    fun delete(model: CourseModal?) {
        repository.delete(model)
    }

    fun deleteAllCourses() {
        repository.deleteAllCourses()
    }

    fun removeCourseAt(position: Int) {
        val tempList = ArrayList(courseList.value ?: emptyList())
        tempList.removeAt(position)
        _courseList.value = tempList
    }
}