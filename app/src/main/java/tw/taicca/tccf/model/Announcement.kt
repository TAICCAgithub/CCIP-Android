package tw.taicca.tccf.model

data class Announcement(
    val datetime: Int,
    val msgEn: String,
    val msgZh: String,
    val uri: String
)
