package es.unex.giss.asee.whichnews.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithNews(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "newsId",
        associateBy = Junction(UserNewsCrossRef::class)
    )
    val news: List<News>
)
