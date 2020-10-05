package tw.taicca.tccf.ui.sessiondetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import tw.taicca.tccf.model.Speaker
import java.util.*

class SpeakerImageAdapter(fm: FragmentManager, speakers: List<Speaker>) : FragmentStatePagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()

    init {
        for (speaker in speakers) {
            mFragmentList.add(SpeakerFragment.newInstance(speaker))
        }
    }

    override fun getCount() = this.mFragmentList.size

    override fun getItem(position: Int) = mFragmentList[position]
}
