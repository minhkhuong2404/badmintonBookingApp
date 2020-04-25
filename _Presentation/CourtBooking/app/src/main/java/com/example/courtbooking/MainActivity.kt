package com.example.courtbooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var profileImage: ImageView
    lateinit var userNameTV: TextView
    lateinit var userEmailTV: TextView
    lateinit var loginButton: LoginButton
    lateinit var callBackManager: CallbackManager
    lateinit var toBookingButton: Button
    lateinit var fullname : String
    lateinit var fbid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callBackManager = CallbackManager.Factory.create()
        profileImage = findViewById(R.id.profileImage)
        userNameTV = findViewById(R.id.usernameTextView)
        loginButton = findViewById(R.id.realFacebookButton)
        toBookingButton = findViewById(R.id.toMainScreenButton)
        loginButton.setPermissions(listOf("public_profile", "email"))

        toBookingButton.setOnClickListener {
            val toMainScreen = Intent(this@MainActivity, MainScreenActivity::class.java)
            startActivity(toMainScreen)
        }

        // if user logged in
        if (AccessToken.getCurrentAccessToken() != null) {
            // load user data
            loadUserData(AccessToken.getCurrentAccessToken())
        } else {
            // make the button invisible
            toBookingButton.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun loadUserData(newAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(newAccessToken, object : GraphRequest.GraphJSONObjectCallback {
            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                try {
                    for (permission in newAccessToken.permissions) {
                        if (permission.equals("public_profile")) {
                            // get user's name
                            val firstName = `object`?.getString("first_name")
                            val middleName = `object`?.getString("middle_name")
                            val lastName = `object`?.getString("last_name")
                            if (!firstName.equals(null) && !middleName.equals(null) && !lastName.equals(null)) {
                                fullname = "$firstName $middleName $lastName"
                                userNameTV.text = fullname
                            }

                            // get user's profile picture
                            val id = `object`?.getString("id")
                            fbid = "$id"
                            val imageURL = "https://graph.facebook.com/$id/picture?type=normal"

                            val requestOptions = RequestOptions()
                            requestOptions.dontAnimate()
                            Glide.with(this@MainActivity).load(imageURL).into(profileImage)

                        } else if (permission.equals("email")) {
                            // get user's email
                            Log.i("m", "read email")
                            val email = `object`?.getString("email")
                            //userEmailTV.text = email
                        }
                    } // end for loop

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

    // remove user's data when logged out
    var tokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {
            if (currentAccessToken == null) {
                // after the user log out, remove information from the activity
                profileImage.setImageResource(0)
                userNameTV.text = ""

                // hide button
                toBookingButton.visibility = View.INVISIBLE
            } else {
                // load user data
                loadUserData(AccessToken.getCurrentAccessToken())
                // if logged in, show button
                toBookingButton.visibility = View.VISIBLE
            }
        }
    }
}

