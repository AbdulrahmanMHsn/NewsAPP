package amhsn.newsapp.presentation.di

import amhsn.data.local.ArticleDao
import amhsn.data.remote.api_service.NewsAPI
import amhsn.data.repository.NewsRepoImpl
import amhsn.domain.repository.NewsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class Repository {

    @Provides
    @Singleton
    fun provideNewsRepo(
        newsAPI: NewsAPI,
        dao: ArticleDao
    ): NewsRepo {
        return NewsRepoImpl(newsAPI,dao)
    }
}
