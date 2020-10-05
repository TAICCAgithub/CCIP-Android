package tw.taicca.tccf.model

import android.content.Context
import tw.taicca.tccf.util.LocaleUtil
import java.util.*

data class SessionType(
    val id: String,
    val zh: Zh,
    val en: En
) {
    fun getDetails(context: Context) =
        if (LocaleUtil.getCurrentLocale(context).language == Locale("zh").language) zh else en

    interface LocalizedDetail {
        val name: String
    }

    data class Zh(override val name: String) : LocalizedDetail
    data class En(override val name: String) : LocalizedDetail
}
