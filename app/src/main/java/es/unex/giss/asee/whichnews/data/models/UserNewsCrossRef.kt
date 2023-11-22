package es.unex.giss.asee.whichnews.data.models
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["userId", "newsId"],
    foreignKeys = [
        ForeignKey(
            entity = News::class,
            parentColumns = ["newsId"],
            childColumns = ["newsId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserNewsCrossRef(
    val userId: String,
    val newsId: Long
)
