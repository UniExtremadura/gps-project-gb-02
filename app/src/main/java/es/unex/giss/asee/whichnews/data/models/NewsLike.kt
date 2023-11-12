package es.unex.giss.asee.whichnews.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_like")
data class NewsLike(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val newsId: String,
    val userId: String
)