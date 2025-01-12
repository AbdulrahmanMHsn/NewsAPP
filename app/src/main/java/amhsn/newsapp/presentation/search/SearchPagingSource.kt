package amhsn.newsapp.presentation.search

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.SearchUseCase
import androidx.paging.PagingSource
import androidx.paging.PagingState


class SearchPagingSource(
    private val searchUseCase: SearchUseCase,
    private val newsRequest: NewsRequest
) : PagingSource<Int, Article>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = searchUseCase.invoke(newsRequest, position)
            val nextKey = if (response.getOrNull().isNullOrEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + 1
            }
            LoadResult.Page(
                data = response.getOrNull() ?: emptyList(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (t: Throwable) {
            return LoadResult.Error(t)
        }
    }
}