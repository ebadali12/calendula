
package es.usc.citius.servando.calendula.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import es.usc.citius.servando.calendula.CalendulaActivity
import es.usc.citius.servando.calendula.R
import es.usc.citius.servando.calendula.util.LogUtil

class MainPrefsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val TAG = "MainPrefsFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        LogUtil.d(TAG, "onCreatePreferences called")
        setPreferencesFromResource(R.xml.pref_main, rootKey)
    }

    override fun onResume() {
        super.onResume()
        (activity as CalendulaActivity).supportActionBar?.setTitle(R.string.title_activity_settings)
    }

}