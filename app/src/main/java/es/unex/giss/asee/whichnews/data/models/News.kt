package es.unex.giss.asee.whichnews.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class News(
    @PrimaryKey(autoGenerate = true)
    var newsId: Long?,
    val url: String?,
    val title: String?,
    val description: String?,
    val content: String?,
    val publishedAt: String?,
    val sourceId: String?,
    val sourceName: String?,
    val author: String?,
    val urlToImage: String?,
    @ColumnInfo(name="is_saved") var isSaved: Boolean = false,
    @ColumnInfo(name="is_favourite") var isFavourite: Boolean = false

// pensar en la posibilidad de incluir la categoría para mostrar la etiqueta en summary_item
)
