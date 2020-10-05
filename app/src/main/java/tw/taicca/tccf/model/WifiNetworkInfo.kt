package tw.taicca.tccf.model

import com.google.gson.annotations.SerializedName

data class WifiNetworkInfo(
    @SerializedName("SSID")
    val ssid: String,
    @SerializedName("password")
    val password: String?
)
