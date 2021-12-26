package amhsn.data.local

import amhsn.data.entities.ArticleData
import amhsn.data.entities.NewsResponseData
import amhsn.data.entities.SourceData
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
class Converter {
    @TypeConverter
    fun fromNews(news: List<ArticleData>): String {
        return Json.encodeToString(news)
    }

    @TypeConverter
    fun toNews(string: String): List<ArticleData> {
        return Json.decodeFromString(string)
    }


    @TypeConverter
    fun fromSource(source:SourceData): String {
        return Json.encodeToString(source)
    }

    @TypeConverter
    fun toSource(string: String): SourceData {
        return Json.decodeFromString(string)
    }
}