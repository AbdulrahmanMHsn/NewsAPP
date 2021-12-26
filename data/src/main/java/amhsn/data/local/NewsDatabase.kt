package amhsn.data.local

import amhsn.data.entities.NewsResponseData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converter::class)
@Database(entities = [NewsResponseData::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        private var instance: NewsDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): NewsDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, NewsDatabase::class.java,
                    "news_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }

    }

}