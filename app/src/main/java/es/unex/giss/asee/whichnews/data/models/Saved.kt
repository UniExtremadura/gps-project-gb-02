package es.unex.giss.asee.whichnews.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_news_favorite",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = News::class,
            parentColumns = ["id"],
            childColumns = ["newsId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Saved(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val newsId: Int
)
