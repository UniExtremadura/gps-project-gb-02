package es.unex.giss.asee.whichnews.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Review(
    @PrimaryKey(autoGenerate = true) val reviewId: Long,
    // TODO: añadir el id de la noticia
    val userId: Long,
    val username: String,
    val content: String,
): Serializable