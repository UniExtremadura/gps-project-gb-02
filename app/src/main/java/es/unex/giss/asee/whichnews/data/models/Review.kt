package es.unex.giss.asee.whichnews.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Review(
    @PrimaryKey(autoGenerate = true) val reviewId: Long?,
    val newsId: Long,
    val userId: Long,
    val content: String,
): Serializable