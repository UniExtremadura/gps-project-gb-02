package es.unex.giss.asee.whichnews.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.giss.asee.whichnews.databinding.ActivityJoinBinding
import kotlinx.coroutines.launch

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityJoinBinding.inflate(layoutInflater) //se establece el layout de nuestra activity
        setContentView(binding.root)

        //views initialization and listeners
        setUpUI()
        setUpListeners()

    }

    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun setUpListeners() {


    }
}