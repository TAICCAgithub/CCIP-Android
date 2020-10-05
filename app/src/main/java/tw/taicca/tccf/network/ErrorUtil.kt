package tw.taicca.tccf.network

import tw.taicca.tccf.model.Error
import tw.taicca.tccf.util.JsonUtil
import retrofit2.Response
import java.io.IOException

class ErrorUtil {
    companion object {
        fun parseError(response: Response<*>): Error {
            return try {
                val body = response.errorBody()?.string() ?: return Error()
                JsonUtil.fromJson(body, Error::class.java)
            } catch (e: IOException) {
                Error()
            }
        }
    }
}
