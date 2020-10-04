package tw.taicca.tccf

import android.app.Application
import tw.taicca.tccf.util.NotificationOpenedHandler
import com.onesignal.OneSignal

class CCIPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.startInit(this).setNotificationOpenedHandler(NotificationOpenedHandler(this)).init()
    }
}
