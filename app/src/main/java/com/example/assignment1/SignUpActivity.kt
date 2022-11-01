package com.example.assignment1

/*TODO: Видаляй зайві імпорти :)*/
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

import com.example.assignment1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    /*TODO: Котлін сам розуміє, що за тип ти йому дав, можна не задавати тип даних.*/

    private lateinit var binding: ActivitySignUpBinding
    private val MIN_PASSWORD_LEN: Int = 8
    /*TODO: Вже не р2р, можна менше коментарів :)*/
    /**
     * key to get or password view state from or to preference
     */
    /*TODO: Усі стрінги зберігаються в ресурсах або у константі.*/
    private val PASSWORD_KEY: String = "password";

    /**
     * name of struct that will be keep email login and remember me views states
     */
    private val LOGIN_PREFERENCES = "email_password_pref"

    /**
     * key to get or put email view state from or to preference
     */
    private val EMAIL_KEY: String = "email";

    /**
     * key to get or put remember me view state from or to preference
     */
    private val REMEMBER_ME_KEY: String = "remember_me"

    /**
     * key name of the extra string of email
     */
    private val EMAIL_EXTRA_KEY: String = "SignUpActivityEmail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()
        loginClickedListener()

        loadLoginData()
    }

    /**
     * Load saved in preferences data in email and password edit_text and remember me check box
     * states
     */
    private fun loadLoginData() {
        val loginSharedPref = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        if (loginSharedPref.getBoolean(REMEMBER_ME_KEY, false)) {
            val email = loginSharedPref.getString(EMAIL_KEY, "")
            binding.emailEditText.setText(email)
            val password = loginSharedPref.getString(PASSWORD_KEY, "");
            binding.passwordEditText.setText(password);
            binding.rememberMeCheckBox.isChecked = true
        } else {
            binding.emailEditText.setText("")
            binding.passwordEditText.setText("");
            binding.rememberMeCheckBox.isChecked = false
        }
    }

    /**
     * set listener to the login button that change activity on the profile activity
     * if user wrote a valid email address
     */
    private fun loginClickedListener() {
        binding.loginBtn.setOnClickListener {
            if (validEmail() == null && validPassword() == null) {
                saveLoginDataInPreferences()
                createProfileActivityIntent()
            }
        }
    }

    private fun createProfileActivityIntent() {
        val intent: Intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(EMAIL_EXTRA_KEY, binding.emailEditText.text.toString());
        startActivity(intent)
    }

    /**
     * saves login password and remember me views states in preferences
     */
    private fun saveLoginDataInPreferences() {
        val emailPasswordData = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE)
        val editor = emailPasswordData.edit()

        editor.putString(EMAIL_KEY, binding.emailEditText.text.toString())
        editor.putString(PASSWORD_KEY, binding.passwordEditText.text.toString())
        editor.putBoolean(REMEMBER_ME_KEY, binding.rememberMeCheckBox.isChecked)

        editor.apply()
    }

    /**
     *  if the user wrote a valid password length in the edit text view continue
     * else set helper text param as error
     */
    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }

        }
    }

    /**
     *  checks if the user wrote a valid password im password edit text view
     */
    private fun validPassword(): String? {
        val writtenPassword = binding.passwordEditText.text
        if (writtenPassword != null && writtenPassword.length < MIN_PASSWORD_LEN) {
            return "Error: at least $MIN_PASSWORD_LEN characters"
        }
        return null
    }

    /**
     * if the user wrote a valid email in the edit text view continue
     * else set helper text param as invalid email
     */
    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail();
            }
        }
    }

    /**
     * checks if the user wrote a valid email im email edit text view
     */
    private fun validEmail(): String? {
        val writtenEmail = binding.emailEditText.text.toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(writtenEmail).matches()) {
            return "Invalid e-mail"
        }
        return null;
    }

}

