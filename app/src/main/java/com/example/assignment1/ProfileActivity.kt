package com.example.assignment1

/*TODO:Зайві імпорти, компілятор скаже дякую :)*/
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.assignment1.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val EMAIL_EXTRA_KEY: String = "SignUpActivityEmail"
    /*TODO: Не використовуєтсья? Видаляй.*/
    private val USER_ICON_ID:Int = R.drawable.varric

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater);
        setContentView(binding.root)

        setEmailAsUserName()
        setEditProfileBtnListener()
    }

    /*TODO: Це теж можна в окремий клас :)*/
    private fun setEditProfileBtnListener() {
        binding.editProfileBtn.setOnClickListener() {
            binding.personIconImageView.setImageResource(R.drawable.varric)
        }
    }

    /**
     * Create new string from written email by copping all symbols until @ character
     * and changes userNameTextView with new string
     */
    /*TODO: Можна винести в окремий клас, як утіліту. І взагалі звикай виносити такі штуки
            як валідація, або парсинг у окремі класи. А також інший зайвий код.*/
    private fun setEmailAsUserName() {
        val userEmailAddress: String = intent.getStringExtra(EMAIL_EXTRA_KEY).toString()

        var userName = StringBuilder(userEmailAddress)

        userName.delete(userName.indexOf("@"), userName.length)

        binding.userNameTextView.text = userName

    }
}