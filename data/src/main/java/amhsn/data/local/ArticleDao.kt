package amhsn.data.local

import amhsn.data.entities.NewsResponseData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert
    fun insert(article: NewsResponseData)


    @Query("Select * from articles")
    fun getNews():Flow<NewsResponseData>
}
