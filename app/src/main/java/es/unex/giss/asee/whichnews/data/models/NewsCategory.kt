package es.unex.giss.asee.whichnews.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsCategory")
data class NewsCategory(
    @PrimaryKey(autoGenerate = true)
    val name: String,
    val newsId: Long
)
