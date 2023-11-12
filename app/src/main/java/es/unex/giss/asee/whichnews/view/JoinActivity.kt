package es.unex.giss.asee.whichnews.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.unex.giss.asee.whichnews.utils.CredentialCheck
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.databinding.ActivityJoinBinding
import es.unex.giss.asee.whichnews.view.WelcomeActivity
import es.unex.giss.asee.whichnews.data.models.User

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    private fun setUpUI() {
        //get attributes from xml using binding
        //no se inicializa nuestras vistas de ninguna manera
    }

    private fun setUpListeners() {
        with(binding) {//with nos permite pasar un parámetro y a partir de ese parámetro,
            // acceder a las propiedades del objeto sin poner el nombre del objeto

            btSign.setOnClickListener {//hace referencia al botón del layout
                val check = CredentialCheck.login(etUser.text.toString(), etPassword.text.toString())

                if (check.fail) notifyInvalidCredentials(check.msg)

                // TODO: Fix Error here
                // else navigateToHomeActivity(User(etUser.text.toString(), etPassword.text.toString()), check.msg)

            } //esta estructura nos permite asociar un nétodo de cuerpo directamente, en el que definimos el manejador de se evento

            btJoin.setOnClickListener {//hace referencia al botón del layout

                // TODO: This method doesn't exists.
                //navigateToJoinActivity()
            }

        }
    }


    private fun navigateToHomeActivity(user: User, msg: String) {

        //SE CREA NUEVO INTENT PARA IR DE UNA PANTALLA A OTRA
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra("LOGIN_USER", user)
        //startActivity(intent)
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}