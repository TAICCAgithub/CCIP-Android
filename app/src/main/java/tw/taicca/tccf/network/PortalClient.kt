package tw.taicca.tccf.network

import tw.taicca.tccf.model.Event
import tw.taicca.tccf.model.EventConfig
import tw.taicca.tccf.util.JsonUtil
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class PortalClient {
    companion object {
        private const val API_BASE_URL = "https://taiccagithub.github.io"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(JsonUtil.GSON))
                .build()
        }
        private val sConfService: ConfService by lazy {
            retrofit.create(ConfService::class.java)
        }

        fun get() = sConfService

        interface ConfService {
            @GET("/events/")
            fun getEvents(): Call<List<Event>>

            @GET("/events/{event_id}/")
            fun getEventConfig(@Path("event_id") eventId: String): Call<EventConfig>
        }
    }
}
