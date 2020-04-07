package com.example.courtbooking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class MainActivity: AppCompatActivity() {

    lateinit var facebookLoginButton: LoginButton
    lateinit var callBackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        facebookLoginButton = findViewById(R.id.loginFacebookButton)
        callBackManager = CallbackManager.Factory.create()

        facebookLoginButton.registerCallback(callBackManager,  FacebookCallback1<LoginResult>())
    }

    // use inner class so the this@MainActivity can be a context
    inner class FacebookCallback1<T> : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            val goToMainScreen = Intent(this@MainActivity, MainScreenActivity::class.java)
            startActivity(goToMainScreen)
            Toast.makeText(this@MainActivity, "Log in successful", Toast.LENGTH_SHORT).show()
            Log.i("Main Activity", "Log in by facebook account")
        }

        override fun onCancel() {}

        override fun onError(error: FacebookException?) {}

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}


