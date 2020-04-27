package com.example.courtbooking

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.*
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import org.json.JSONObject
import java.io.File


class LoginActivity : AppCompatActivity() {
    lateinit var callBackManager: CallbackManager
    // View vars
    lateinit var profileImage: ImageView
    lateinit var fullnameTextView: TextView
    lateinit var loginButton: LoginButton
    lateinit var toBookingButton: Button
    var cacheFilename = "user.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Facebook
        callBackManager = CallbackManager.Factory.create()
        val accessToken = AccessToken.getCurrentAccessToken()

        // View
        profileImage = findViewById(R.id.profileImage)
        fullnameTextView = findViewById(R.id.usernameTextView)
        loginButton = findViewById(R.id.realFacebookButton)
        loginButton.setPermissions(listOf("public_profile", "email"))
        toBookingButton = findViewById(R.id.toMainScreenButton)
        toBookingButton.setOnClickListener {
            // Preparing to next activity
            val toMainScreen = Intent(this@LoginActivity, SelectionActivity::class.java)
            // To next activity
            startActivity(toMainScreen)
        }

        // if user logged in
        if (accessToken != null) {
            // load user data
            if (isNetworkAvailable(this)) {
                // load user data from internet
                loadUserDataFb(accessToken)
            } else {
                // load user data cache
                loadUserDataCache()
            }
        } else {
            // make the button invisible
            toBookingButton.visibility = View.INVISIBLE
        }
    }

    fun isNetworkAvailable(activity: AppCompatActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    // token tracker: listen to token changes
    var tokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {
            if (currentAccessToken == null) {
                // after the user log out, clear views
                profileImage.setImageResource(0)
                fullnameTextView.text = ""
                // clear user cache
                clearUserDataCache()
                // hide button
                toBookingButton.visibility = View.INVISIBLE
            } else {
                // load user data
                loadUserDataFb(AccessToken.getCurrentAccessToken())
                // if logged in, show button
                toBookingButton.visibility = View.VISIBLE
            }
        }
    }

    // user logged in, load data from fb graph api
    fun loadUserDataFb(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(accessToken, object : GraphRequest.GraphJSONObjectCallback {
            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                try {
                    var id = ""
                    var email = ""
                    var fullname = ""
                    var imageURL = ""

                    for (permission in accessToken.permissions) {
                        if (permission.equals("public_profile")) {
                            // get user's id, name, profile image
                            val firstName = `object`?.getString("first_name")
                            val middleName = `object`?.getString("middle_name")
                            val lastName = `object`?.getString("last_name")
                            fullname = "$firstName $middleName $lastName"
                            id = `object`?.getString("id").toString()
                            imageURL = "https://graph.facebook.com/$id/picture?type=normal"

                            Log.i("load from fb", "before update view")
                            // update to views
                            fullnameTextView.text = fullname
                            Glide.with(this@LoginActivity).load(imageURL).skipMemoryCache(true)
                                .into(profileImage)
                            Log.i("load from fb", "after update view")
                        } else if (permission.equals("email")) {
                            email = `object`?.getString("email").toString()
                        }
                    } // end for loop
                    cacheUserData(id, email, fullname, imageURL)
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            } // end onCompleted
        })

        val parameter = Bundle()
        parameter.putString("fields", "email, first_name, middle_name, last_name")
        request.parameters = parameter
        request.executeAsync()
    }

    fun cacheUserData(id: String?, email: String?, fullname: String?, imageURL: String?) {
        // prepare content
        var userObj = JSONObject()
        userObj.put("id", id)
        userObj.put("email", email)
        userObj.put("fullname", fullname)
        userObj.put("imageURL", imageURL)
        var json = userObj.toString()
        // prepare cache file
        File.createTempFile(cacheFilename, null, this.cacheDir)
        val cacheFile = File(this.cacheDir, cacheFilename)
        // write cache
        cacheFile.writeText(json, Charsets.UTF_8)
    }

    fun loadUserDataCache() {
        // read cache
        val cacheFile = File(this.cacheDir, cacheFilename)
        val json = cacheFile.readText(Charsets.UTF_8)
        // get user's data
        val userObj = JSONObject(json)
        val id = userObj.getString("id")
        val imageURL = userObj.get("imageURL")
        // update views
        fullnameTextView.text = userObj.getString("fullname")
        Glide.with(this@LoginActivity).load(imageURL).into(profileImage)
    }

    fun clearUserDataCache() {
        // delete cache file
        val cacheFile = File(this.cacheDir, cacheFilename)
        cacheFile.delete()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}

