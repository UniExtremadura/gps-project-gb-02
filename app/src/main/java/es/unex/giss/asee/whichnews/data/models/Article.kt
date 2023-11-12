package es.unex.giss.asee.whichnews.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Room requiere una clave primaria
    @ColumnInfo(name = "source_id")
    val sourceId: String?,
    @ColumnInfo(name = "source_name")
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    val content: String
)
