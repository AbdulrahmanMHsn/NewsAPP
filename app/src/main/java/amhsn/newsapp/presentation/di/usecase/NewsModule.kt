package amhsn.newsapp.presentation.di.usecase

import amhsn.domain.repository.NewsRepo
import amhsn.domain.usecase.GetNewsUseCase
import amhsn.domain.usecase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Singleton
    @Provides
    fun provideGetNews(newsRepo: NewsRepo): GetNewsUseCase {
        return GetNewsUseCase(newsRepo)
    }

    @Singleton
    @Provides
    fun provideSearch(newsRepo: NewsRepo): SearchUseCase{
        return SearchUseCase(newsRepo)
    }

}