package com.android.shawnclisby.thetodolist.other

import androidx.room.TypeConverter
import com.android.shawnclisby.thetodolist.data.local.models.Priority
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(stamp: Long?): Date? = stamp?.let { Date(it) }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromPriority(priority: Priority): String =
        when (priority) {
            Priority.HIGHEST -> "HIGHEST"
            Priority.HIGH -> "HIGH"
            Priority.MEDIUM -> "MEDIUM"
            Priority.LOW -> "LOW"
            Priority.NONE -> "NONE"
        }

    @TypeConverter
    fun toPriority(priorityString: String): Priority =
        when (priorityString) {
            "HIGHEST" -> Priority.HIGHEST
            "HIGH" -> Priority.HIGH
            "MEDIUM" -> Priority.MEDIUM
            "LOW" -> Priority.LOW
            else -> Priority.NONE
        }

    @TypeConverter
    fun fromListOfIds(ids: List<String>?): String {
        val idsString = StringBuilder("")
        ids?.let { it.forEach { id -> idsString.append("$id,") } }
        return idsString.toString()
    }

    @TypeConverter
    fun toListOfIds(idsString: String): List<String>? {
        return if (idsString.isEmpty() || idsString.isBlank())
            null
        else
            idsString.split(",")
    }

}