package com.example.mond.coverapp

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.Toast
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import com.example.mond.coverapp.model.MockUserRepository


class SignUpActivity : AppCompatActivity() {

    private var mAuthTask: UserSignUpTask? = null
    private var userRepository: MockUserRepository = MockUserRepository.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun registerButtonClicked(view: View) {

        // Reset errors.
        email.error = null
        password.error = null
        username.error = null
        repassword.error = null
        fullname.error = null
        idnumber.error = null
        telnumber.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val userStr = username.text.toString()
        val passwordStr = password.text.toString()
        val repasswordStr = repassword.text.toString()
        val nameStr = fullname.text.toString()
        val idStr = idnumber.text.toString()
        val telStr = telnumber.text.toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = "Invalid Email"
            focusView = email
            cancel = true
        } else if (userStr.isEmpty()) {
            username.error = getString(R.string.error_field_required)
            focusView = username
            cancel = true
        } else if (TextUtils.isEmpty(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        } else if (!repasswordStr.equals(passwordStr)) {
            repassword.error = "Invalid re-password"
            focusView = repassword
            cancel = true
        } else if (nameStr.isEmpty()) {
            fullname.error = getString(R.string.error_field_required)
            focusView = fullname
            cancel = true
        } else if (idStr.length != 13) {
            idnumber.error = "Invalid ID number"
            focusView = idnumber
            cancel = true
        } else if (telStr.length != 10) {
            telnumber.error = "Invalid telephone number"
            focusView = telnumber
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            registerButton.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            mAuthTask = UserSignUpTask(emailStr, passwordStr, nameStr, idStr, telStr)
            mAuthTask!!.execute(null as Void?)
        }
    }


    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserSignUpTask internal constructor(private val mEmail: String,
                                                    private val mPassword: String,
                                                    private val mName: String,
                                                    private val mID: String,
                                                    private val mTel: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {

            try {
                // Simulate network access.
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }
            userRepository.register(mEmail, mPassword, mName, mID, mTel)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            registerButton.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            if (success == true) {
                val alertDialog = AlertDialog.Builder(
                        this@SignUpActivity).create()
                alertDialog.setTitle("Congratulation!");
                alertDialog.setMessage("Your account has been activated by administrator!");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", DialogInterface.OnClickListener { dialog, which -> finish() })
                alertDialog.show();
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            progressBar.visibility = View.INVISIBLE
            registerButton.visibility = View.VISIBLE
        }

    }

}
