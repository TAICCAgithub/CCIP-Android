package tw.taicca.tccf.util

import android.content.Context
import tw.taicca.tccf.model.Session

object ScheduleUtil {
    fun getStarredSessions(context: Context): List<Session> {
        val sessions = PreferenceUtil.loadSchedule(context)?.sessions ?: return emptyList()
        val starredIds = PreferenceUtil.loadStarredIds(context)
        return sessions.filter { starredIds.contains(it.id) }
    }
}
