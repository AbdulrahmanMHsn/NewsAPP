package amhsn.data.mapper

import amhsn.data.entities.ArticleData
import amhsn.data.entities.NewsRequestData
import amhsn.data.entities.NewsResponseData
import amhsn.data.entities.SourceData
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.entities.Source


fun NewsRequest.toNewsRequestData(): NewsRequestData {
    return NewsRequestData(country,q)
}

fun SourceData.toSourceDomain():Source{
    return Source(id, name)
}

fun Source.toSourceData():SourceData{
    return SourceData(id, name)
}

fun ArticleData.toArticleDomain():Article{
    return Article(author,content,description,publishedAt,source.toSourceDomain(),title, url, urlToImage)
}

fun Article.toArticleData():ArticleData{
    return ArticleData(author,description, publishedAt, title, source.toSourceData(),title, url, urlToImage)
}

fun NewsResponseData.toNewsResponseDomain(): NewsResponse {
    return NewsResponse(articleData.map { it.toArticleDomain() },status, totalResults)
}

fun NewsResponse.toNewsResponseData(): NewsResponseData {
    return NewsResponseData(articleData = articles.map { it.toArticleData() })
}