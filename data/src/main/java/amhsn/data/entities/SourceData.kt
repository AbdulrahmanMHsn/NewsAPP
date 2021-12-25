package amhsn.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SourceData(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)