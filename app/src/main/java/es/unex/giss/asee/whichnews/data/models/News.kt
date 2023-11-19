package es.unex.giss.asee.whichnews.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Room requiere una clave primaria
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String
    // pensar en la posibilidad de incluir la categoría para mostrar la etiqueta en summary_item
)
