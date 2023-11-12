package es.unex.giss.asee.whichnews.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.databinding.ActivityLoginBinding
import es.unex.giss.asee.whichnews.databinding.ActivityPasswordForgetBinding

class PasswordForgetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordForgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityPasswordForgetBinding.inflate(layoutInflater) //se establece el layout de nuestra activity
        setContentView(binding.root)

        setUpListeners() //configurar los listeners, es decir, por si hay que declarar algún manejador de algún evento sobre esa vistaç
    }

    private fun setUpListeners(){
        with(binding){
            btRecover.setOnClickListener {
                navigateToLoginActivity()
            }
        }
    }

    private fun navigateToLoginActivity(){
        Toast.makeText(this, "Recovery email sent.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}