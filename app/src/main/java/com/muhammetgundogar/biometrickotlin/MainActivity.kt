package com.muhammetgundogar.biometrickotlin



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

    //This app will show you how to use fingerprint in your apps

class MainActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo : BiometricPrompt.PromptInfo



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this,executor,
                object : BiometricPrompt.AuthenticationCallback(){

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        notifyUser("Authentication error $errString")
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        notifyUser("Authentication Succeeded!!")
                        textViewWelcome.visibility = View.VISIBLE
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        notifyUser("Authentication Failed")
                    }

        })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint App")
                .setSubtitle("Authentication is required")
                .setDescription("With giving your fingerprint you can see welcome :) text")
                .setNegativeButtonText("Cancel")
                .build()

    buttonShowWelcome.setOnClickListener {
        biometricPrompt.authenticate(promptInfo)
        buttonShowWelcome.visibility = View.INVISIBLE
    }


    }
  // if you have many toasts in your app you can write a function to make it easier.
    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
