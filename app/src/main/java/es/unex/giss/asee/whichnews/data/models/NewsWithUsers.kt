package es.unex.giss.asee.whichnews.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NewsWithUsers(
    @Embedded val news: News,
    @Relation(
        parentColumn = "newsId",
        entityColumn = "userId",
        associateBy = Junction(UserNewsCrossRef::class)
    )
    val users: List<User>
)
