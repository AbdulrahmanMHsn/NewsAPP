package amhsn.newsapp.presentation.di

import amhsn.data.local.ArticleDao
import amhsn.data.local.NewsDatabase
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalSerializationApi
class Room {

    @Singleton
    @Provides
    fun provideNewsDao(@ApplicationContext context: Context): ArticleDao {
        return NewsDatabase.getInstance(context).articleDao()
    }

}
