package tw.taicca.tccf.util

import tw.taicca.tccf.model.LocalizedString
import com.google.gson.*
import java.lang.reflect.Type

class LocalizedStringDeserializer : JsonDeserializer<LocalizedString>, JsonSerializer<LocalizedString> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalizedString {
        if (json?.isJsonPrimitive!!) return LocalizedString.fromUntranslated(json.asString)

        val obj = json.asJsonObject
        val ls = LocalizedString()
        obj.entrySet().map {
            ls.addTranslation(it.key, it.value.asString)
        }
        return ls
    }

    override fun serialize(src: LocalizedString?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (src == null) return JsonNull.INSTANCE
        if (src.isUntranslated) return JsonPrimitive(src.untranslatedValue)

        val obj = JsonObject()
        for ((key, value) in src.getTranslations()) {
            obj.add(key, JsonPrimitive(value))
        }
        return obj
    }
}
