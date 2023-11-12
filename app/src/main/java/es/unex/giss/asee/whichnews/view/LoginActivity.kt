package es.unex.giss.asee.whichnews.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.databinding.ActivityLoginBinding
import es.unex.giss.asee.whichnews.utils.CredentialCheck
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    //latenit se utiliza para inicializar una variable más tarde
    //binding sirve para conectar la capa de usuario con el backend
    //en kotlin no se permiten variables nulas, en cuyo caso, hay que indicarlo expresamente
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: WhichNewsDatabase

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                //TODO get data from result and update IU
                val name = ""
                val password = ""
                Toast.makeText(
                    this@LoginActivity,
                    "New user ($name/$password) created",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {  //el primer método de ciclo de vida de un activity es el onCreate()
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityLoginBinding.inflate(layoutInflater) //se establece el layout de nuestra activity
        setContentView(binding.root)

        //database instance reference
        db = WhichNewsDatabase.getInstance(applicationContext)!!

        //views initialization and listeners
        setUpUI() //configurar la interfaz de usuario, darle valores a las vistas que tenemos en la interfaz
        setUpListeners() //configurar los listeners, es decir, por si hay que declarar algún manejador de algún evento sobre esa vistaç
    }

    private fun setUpUI() {
        //get attributes from xml using binding
        //no se inicializa nuestras vistas de ninguna manera
    }

    private fun setUpListeners() {
        // With is used
        with(binding) {
            btLogin.setOnClickListener {checkLogin() }
            btJoin.setOnClickListener {navigateToJoinActivity() }
        }

    }

    private fun navigateToHomeActivity(user: User, msg: String) {

        //Se crea un nuevo intent para ir de una pantalla a otra
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra(WelcomeActivity.LOGIN_USER, user) //Le pasamos el usuario para que lo muestre por pantalla
        startActivity(intent)
    }

    private fun navigateToJoinActivity() {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkLogin(){
        val check = CredentialCheck.login(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )
        if (!check.fail){
            lifecycleScope.launch{
                val user = db?.userDao()?.findByName(binding.etUsername.text.toString())
                if (user != null) {
                    val check = CredentialCheck.passwordOk(binding.etPassword.text.toString(), user.password)
                    if (check.fail) notifyInvalidCredentials(check.msg)
                    else navigateToHomeActivity(user!!, check.msg)
                }
                else notifyInvalidCredentials("Invalid username")
            }
        }
        else notifyInvalidCredentials(check.msg)
    }

}