package es.unex.giss.asee.whichnews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    companion object{
        val LOGIN_USER = "LOGIN_USER"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityWelcomeBinding.inflate(layoutInflater) //se establece el layout de nuestra activity
        setContentView(binding.root)
        val user = intent.getSerializableExtra(LOGIN_USER) as User
        binding.textView2.text = "Welcome back, ${user.user}"
        


    }
}