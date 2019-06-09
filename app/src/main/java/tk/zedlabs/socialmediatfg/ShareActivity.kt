package tk.zedlabs.socialmediatfg

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : AppCompatActivity() {

    lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        user = intent.extras.get("user") as UserModel
        setData(user)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         when (item?.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun setData(user: UserModel) {
        nameTextView.text = user.name
        userNameTextView.text =
            if (user.socialNetwork == SocialNetwork.Twitter)  "@${user.userName}"
            else user.userName
        connectedWithTextView.text =
            if (user.socialNetwork == SocialNetwork.Twitter) "${connectedWithTextView.text} Twitter"
            else "${connectedWithTextView.text} Facebook"

        val imageUrl = user.profilePictureUrl

        Glide
            .with(this)
            .load("$imageUrl")
            .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
            .into(profileImageView)

        if (user.socialNetwork == SocialNetwork.Twitter)  facebookIcon.visibility = View.GONE
        else    twitterIcon.visibility = View.GONE
    }
}
