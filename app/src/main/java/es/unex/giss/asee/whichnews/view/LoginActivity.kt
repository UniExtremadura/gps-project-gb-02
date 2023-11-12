package es.unex.giss.asee.whichnews.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import es.unex.giss.asee.whichnews.utils.CredentialCheck
import es.unex.giss.asee.whichnews.databinding.ActivityLoginBinding
import es.unex.giss.asee.whichnews.view.WelcomeActivity
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.view.JoinActivity

class LoginActivity : AppCompatActivity() {

    //latenit se utiliza para inicializar una variable más tarde
    //binding sirve para conectar la capa de usuario con el backend
    //en kotlin no se permiten variables nulas, en cuyo caso, hay que indicarlo expresamente
    private lateinit var binding: ActivityLoginBinding

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

        //views initialization and listeners
        setUpUI() //configurar la interfaz de usuario, darle valores a las vistas que tenemos en la interfaz
        setUpListeners() //configurar los listeners, es decir, por si hay que declarar algún manejador de algún evento sobre esa vista
    }

    private fun setUpUI() {
        //get attributes from xml using binding
        //no se inicializa nuestras vistas de ninguna manera
    }

    private fun setUpListeners() {
        with(binding) {//with nos permite pasar un parámetro y a partir de ese parámetro,
            // acceder a las propiedades del objeto sin poner el nombre del objeto

            btLogin.setOnClickListener {//hace referencia al botón del layout
                val check = CredentialCheck.login(etUsername.text.toString(), etPassword.text.toString())

                if (check.fail) notifyInvalidCredentials(check.msg)

                // TODO: Fix Error here
                // else navigateToHomeActivity(User(etUsername.text.toString(), etPassword.text.toString()), check.msg)

            } //esta estructura nos permite asociar un nétodo de cuerpo directamente, en el que definimos el manejador de se evento

            btJoin.setOnClickListener {//hace referencia al botón del layout
                navigateToJoinActivity()
            }

        }

    }

    private fun navigateToHomeActivity(user: User, msg: String) {

        //SE CREA NUEVO INTENT PARA IR DE UNA PANTALLA A OTRA
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra("LOGIN_USER", user)
        startActivity(intent)
    }

    private fun navigateToJoinActivity() {
        //TODO go to join activity
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}