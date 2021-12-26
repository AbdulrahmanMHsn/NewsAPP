package amhsn.data.local

import amhsn.data.entities.ArticleData
import amhsn.data.entities.NewsResponseData
import amhsn.domain.entities.Article
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert
    fun insert(article: NewsResponseData)


    @Query("Select * from articles")
    fun getNews():Flow<NewsResponseData>
}
