package com.example.courtbooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import org.json.JSONObject


class MainActivity: AppCompatActivity() {

    lateinit var profileImage: ImageView
    lateinit var userNameTV: TextView
    lateinit var userEmailTV: TextView
    lateinit var realFacebookButton: LoginButton
    lateinit var customFacebookButton: Button
    lateinit var callBackManager: CallbackManager
    lateinit var toBookingButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        profileImage = findViewById(R.id.profileImage)
        userNameTV = findViewById(R.id.usernameTextView)
        userEmailTV = findViewById(R.id.emailTextView)
        realFacebookButton = findViewById(R.id.realFacebookButton)
        customFacebookButton = findViewById(R.id.fakeFacebookButton)
        callBackManager = CallbackManager.Factory.create()
        toBookingButton = findViewById(R.id.toMainScreenButton)

        // make the button invisible
        toBookingButton.visibility = View.INVISIBLE
        toBookingButton.setOnClickListener {
            val toMainScreen = Intent(this@MainActivity, MainScreenActivity::class.java)
            startActivity(toMainScreen)
        }

        realFacebookButton.setPermissions(listOf("public_profile", "email"))

        checkLoginStatus()

        customFacebookButton.setOnClickListener {
            if(AccessToken.getCurrentAccessToken() == null) {
                // login
                realFacebookButton.performClick()
            } else {
                // logout
                disconnectFromFacebook()
            }
        }

//        realFacebookButton.registerCallback(callBackManager,  object : FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult?) {
//
//            }
//
//            override fun onCancel() {
//
//            }
//
//            override fun onError(error: FacebookException?) {
//                Toast.makeText(this@MainActivity, "Login Failed. May be check your internet connection", Toast.LENGTH_SHORT).show()
//            }
//        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    var tokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
            if (currentAccessToken == null) {
                // after the user log out
                profileImage.setImageResource(0)
                userNameTV.text = ""
                userEmailTV.text = ""
                customFacebookButton.text = "Sign in with Facebook"
                toBookingButton.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity, "Log out successful", Toast.LENGTH_SHORT).show()
            } else {
                // after login
                loadUserData(currentAccessToken)

                customFacebookButton.text = "Log out"
                toBookingButton.visibility = View.VISIBLE

                val toMainScreen = Intent(this@MainActivity, MainScreenActivity::class.java)
                startActivity(toMainScreen)
            }
        }
    }

    fun loadUserData(newAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(newAccessToken, object : GraphRequest.GraphJSONObjectCallback {
            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                try {

                    for (permission in newAccessToken.permissions) {
                        if (permission.equals("public_profile")) {
                            // get user's name
                            Log.i("Login", "Get user's name")
                            val firstName = `object`?.getString("first_name")
                            val lastName = `object`?.getString("last_name")
                            userNameTV.text = "$firstName $lastName"

                            // get user's profile picture
                            Log.i("Login", "Get user's profile picture")
                            val id = `object`?.getString("id")
                            val imageURL = "https://graph.facebook.com/$id/picture?type=normal"

                            val requestOptions = RequestOptions()
                            requestOptions.dontAnimate()
                            Glide.with(this@MainActivity).load(imageURL).into(profileImage)

                        } else if (permission.equals("email")) {
                            // get user's email
                            Log.i("m", "read email")
                            val email = `object`?.getString("email")
                            userEmailTV.text = email
                        }
                    } // end for loop

                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            } // end onCompleted

        })

        val parameter = Bundle()
        parameter.putString("fields", "email, first_name, last_name")
        request.parameters = parameter
        request.executeAsync()
    }

    fun checkLoginStatus(){
        if (AccessToken.getCurrentAccessToken() != null){
            // setup the screen if the user already login (token is still valid)
            loadUserData(AccessToken.getCurrentAccessToken())
            customFacebookButton.text = "Log out"
            toBookingButton.visibility = View.VISIBLE
        }
    }

    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return  // already logged out
        }
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE,
            GraphRequest.Callback { LoginManager.getInstance().logOut() }).executeAsync()
    }

}

