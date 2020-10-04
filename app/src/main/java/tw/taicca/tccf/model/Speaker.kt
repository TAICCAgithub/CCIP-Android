package tw.taicca.tccf.model

import android.content.Context
import tw.taicca.tccf.util.LocaleUtil
import java.util.*

data class Speaker(
    val id: String,
    val avatar: String,
    val zh: Zh_,
    val en: En_
) {
    fun getSpeakerDetail(context: Context): SpeakerDetail {
        return if (LocaleUtil.getCurrentLocale(context).language == Locale("zh").language) {
            zh
        } else {
            en
        }
    }
}
