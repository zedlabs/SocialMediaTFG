package tk.zedlabs.socialmediatfg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val EMAIL = "email"
    val PUBLIC_PROFILE = "public_profile"
    val USER_PERMISSION = "user_friends"

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        facebookSetup(this)
        twitterSetup(this)
    }

    fun facebookSetup(context: Context) {

        callbackManager = CallbackManager.Factory.create()
        facebookLoginButton.setOnClickListener {
            facebookLoginButton.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE, USER_PERMISSION))
            facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Helper.getFacebookUserProfileWithGraphApi(context)
                }

                override fun onCancel() {

                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    fun twitterSetup(context: Context) {

        twitterLoginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                Helper.getTwitterUserProfileWthTwitterCoreApi(context, result!!.data)
            }

            override fun failure(exception: TwitterException) {
                Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)

    }
}
