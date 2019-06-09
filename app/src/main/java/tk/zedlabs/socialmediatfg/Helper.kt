package tk.zedlabs.socialmediatfg

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.User

object Helper {

    fun getFacebookUserProfileWithGraphApi(context: Context) {

        if (AccessToken.getCurrentAccessToken() != null){
            val activity = context as Activity
            val request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken()
            ) { jsonObject, _ ->
                Log.i("JSON: ", jsonObject.toString())
                val email = jsonObject?.get("email")?.toString() ?: ""
                val name = jsonObject.get("name").toString()
                val profileObjectImage = jsonObject?.getJSONObject("picture")?.
                                                            getJSONObject("data")?.get("url").toString()
                val user = UserModel(name, email, profileObjectImage, SocialNetwork.Facebook)
                startShareActivity(context, user)
            }

            val parameters = Bundle()
            parameters.putString("fields", "id,name,link,picture.type(large), email")
            request.parameters = parameters
            request.executeAsync()
        }
    }

    fun startShareActivity(context: Context, user: UserModel) {
        val activity = context as Activity
        val intent = Intent(context, ShareActivity::class.java)
        intent.putExtra("user", user)
        activity.startActivity(intent)
        activity.finish()
    }

    fun getTwitterUserProfileWthTwitterCoreApi(
        context: Context, session: TwitterSession) {


        TwitterCore.getInstance().getApiClient(session).accountService
            .verifyCredentials(true, true, false)
            .enqueue(object : Callback<User>() {
                override fun success(result: Result<User>) {
                    val name = result.data.name
                    val userName = result.data.screenName
                    val profileImageUrl = result.data.profileImageUrl.replace("_normal", "")
                    Log.i("JSONt: ", "$name  $userName  $profileImageUrl")
                    val user = UserModel(name, userName, profileImageUrl, SocialNetwork.Twitter)
                    startShareActivity(context, user)
                }

                override fun failure(exception: TwitterException) {
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            })
    }
}