package tw.taicca.tccf.ui.announcement

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tw.taicca.tccf.R
import tw.taicca.tccf.extension.asyncExecute
import tw.taicca.tccf.extension.setOnApplyWindowInsetsListenerCompat
import tw.taicca.tccf.network.CCIPClient
import tw.taicca.tccf.util.PreferenceUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AnnouncementFragment : Fragment(), CoroutineScope {
    companion object {
        private const val EXTRA_URL = "EXTRA_URL"
        fun newInstance(url: String): AnnouncementFragment = AnnouncementFragment().apply {
            arguments = Bundle().apply { putString(EXTRA_URL, url) }
        }
    }

    private lateinit var announcementView: RecyclerView
    private lateinit var announcementEmptyView: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mActivity: Activity
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private val baseUrl by lazy { requireArguments().getString(EXTRA_URL)!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_announcement, container, false)

        announcementView = view.findViewById(R.id.announcement)
        announcementEmptyView = view.findViewById(R.id.announcement_empty)
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer)

        mActivity = requireActivity()
        mJob = Job()
        announcementView.layoutManager = LinearLayoutManager(mActivity)
        announcementView.itemAnimator = DefaultItemAnimator()
        announcementView.setOnApplyWindowInsetsListenerCompat { v, insets, insetsCompat ->
            v.updatePadding(bottom = insetsCompat.systemGestureInsets.bottom)
            insets
        }
        announcementEmptyView.setOnApplyWindowInsetsListenerCompat { v, insets, insetsCompat ->
            v.updateLayoutParams<LinearLayout.LayoutParams> {
                this.bottomMargin = insetsCompat.systemGestureInsets.bottom
            }
            insets
        }

        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
        launch {
            try {
                CCIPClient.withBaseUrl(baseUrl).announcement(PreferenceUtil.getToken(mActivity)).asyncExecute().run {
                    if (isSuccessful && !body().isNullOrEmpty()) {
                        announcementView.adapter = AnnouncementAdapter(mActivity, body()!!)
                    } else {
                        announcementEmptyView.visibility = View.VISIBLE
                    }
                }
            } catch (t: Throwable) {
                Toast.makeText(mActivity, R.string.offline, Toast.LENGTH_LONG).show()
            } finally {
                swipeRefreshLayout.isRefreshing = false
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}
