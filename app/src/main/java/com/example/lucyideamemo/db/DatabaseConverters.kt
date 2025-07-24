package com.example.lucyideamemo.db

import androidx.room.TypeConverter
import com.example.lucyideamemo.bean.Attachment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object DatabaseConverters {
    @TypeConverter
    fun jsonFromAttachments(attachments: List<Attachment>): String =
        Json.encodeToString(attachments)

    @TypeConverter
    fun attachmentsFromJson(json: String): List<Attachment> = Json.decodeFromString(json)
}