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
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import org.json.JSONObject

class MainActivity: AppCompatActivity() {

    lateinit var profileImage: ImageView
    lateinit var userNameTV: TextView
    lateinit var userEmailTV: TextView
    lateinit var facebookLoginButton: LoginButton
    lateinit var callBackManager: CallbackManager
    lateinit var toBookingButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileImage = findViewById(R.id.profileImage)
        userNameTV = findViewById(R.id.usernameTextView)
        userEmailTV = findViewById(R.id.emailTextView)
        facebookLoginButton = findViewById(R.id.loginFacebookButton)
        callBackManager = CallbackManager.Factory.create()
        toBookingButton = findViewById(R.id.toMainScreenButton)

        // make the button invisible
        toBookingButton.visibility = View.INVISIBLE
        toBookingButton.setOnClickListener {
            val toMainScreen = Intent(this@MainActivity, MainScreenActivity::class.java)
            startActivity(toMainScreen)
        }

        facebookLoginButton.setPermissions(listOf("public_profile", "email"))

        facebookLoginButton.registerCallback(callBackManager,  object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@MainActivity, "Login Failed. May be check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    var tokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
            if (currentAccessToken == null) {
                // the user log out
                profileImage.setImageResource(0)
                userNameTV.text = ""
                userEmailTV.text = ""
                toBookingButton.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity, "Log out successful", Toast.LENGTH_SHORT).show()
            } else {
                loadUserData(currentAccessToken)
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
                    Log.i("m", "read email")
                    val firstName = `object`?.getString("first_name")
                    val lastName = `object`?.getString("last_name")
                    val email = `object`?.getString("email")
                    val id = `object`?.getString("id")
                    val imageURL = "https://graph.facebook.com/" + id + "/picture?type=normal"

                    Log.i("b", "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL")
                    Log.i("k", "$firstName $lastName")
                    Log.i("k", `object`?.toString())
                    userNameTV.text = "$firstName $lastName"
                    userEmailTV.text = email
                    val requestOptions = RequestOptions()
                    requestOptions.dontAnimate()

                    Glide.with(this@MainActivity).load(imageURL).into(profileImage)

                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            }

        })


        val parameter = Bundle()
        parameter.putString("fields", "email, first_name, last_name")
        request.parameters = parameter
        request.executeAsync()
    }

}

