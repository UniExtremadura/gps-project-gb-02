package es.unex.giss.asee.whichnews.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.giss.asee.whichnews.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    //latenit se utiliza para inicializar una variable más tarde
    //binding sirve para conectar la capa de usuario con el backend
    //en kotlin no se permiten variables nulas, en cuyo caso, hay que indicarlo expresamente
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {  //el primer método de ciclo de vida de un activity es el onCreate()
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding =
            ActivityLoginBinding.inflate(layoutInflater) //se establece el layout de nuestra activity
        setContentView(binding.root)

        //views initialization and listeners
        setUpUI() //configurar la interfaz de usuario, darle valores a las vistas que tenemos en la interfaz
        setUpListeners() //configurar los listeners, es decir, por si hay que declarar algún manejador de algún evento sobre esa vistaç
    }

    private fun setUpUI() {
        //get attributes from xml using binding
        //no se inicializa nuestras vistas de ninguna manera
    }

    private fun setUpListeners() {

    }

}