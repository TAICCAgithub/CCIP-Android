package tw.taicca.tccf.extension

import tw.taicca.tccf.model.EventConfig
import tw.taicca.tccf.model.FeatureType

fun EventConfig.getFastPassUrl(): String? {
    return features.find { it.feature == FeatureType.FAST_PASS }?.url
}
