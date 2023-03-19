package com.fresh.milkaggregatorapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
class CourseModal
    (
    @ColumnInfo
    var courseName: String?,

    @ColumnInfo
    var courseDescription: String?,

    @ColumnInfo
    var courseDuration: String?,

    @ColumnInfo
    var namett: String?,

    @ColumnInfo
    var phonett: String?,

    @ColumnInfo
    var addresstt: String?,
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}