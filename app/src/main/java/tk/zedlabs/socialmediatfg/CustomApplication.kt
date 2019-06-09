package tk.zedlabs.socialmediatfg

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(
                TwitterAuthConfig(
                resources.getString(R.string.Twitter_CONSUMER_KEY),
                resources.getString(R.string.Twitter_CONSUMER_SECRET))
            )
            .debug(true)
            .build()
        Twitter.initialize(config)
    }
}