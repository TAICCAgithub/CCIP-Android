package tw.taicca.tccf.util

import tw.taicca.tccf.model.ConfSchedule
import tw.taicca.tccf.model.LocalizedString
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object JsonUtil {
    val GSON: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(LocalizedString::class.java, LocalizedStringDeserializer())
        .registerTypeAdapter(ConfSchedule::class.java, ConfScheduleDeserializer())
        .create()

    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return GSON.fromJson(json, clazz)
    }

    fun <T> fromJson(json: String, typeOfT: Type): T {
        return GSON.fromJson(json, typeOfT)
    }

    fun toJson(obj: Any): String {
        return GSON.toJson(obj)
    }
}
