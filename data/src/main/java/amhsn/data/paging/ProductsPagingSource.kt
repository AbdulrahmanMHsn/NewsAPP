package amhsn.data.paging

import amhsn.data.repository.NewsRepoImpl
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.GetNewsUseCase
import androidx.paging.PagingSource
import androidx.paging.PagingState


class ProductsPagingSource(
    private val getNewsUseCase: GetNewsUseCase,
    private val newsRequest: NewsRequest
) : PagingSource<Int, Article>() {

    private val STARTING_PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = getNewsUseCase.invoke(newsRequest,position)

            val nextKey = if (response.articles.isEmpty()) {
                    null
                } else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + 1
                }
            LoadResult.Page(
                data = if (response.articles.isNullOrEmpty()) {
                    emptyList()
                } else {
                    response.articles
                },
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (t: Throwable) {
            return LoadResult.Error(t)
        }
    }
}