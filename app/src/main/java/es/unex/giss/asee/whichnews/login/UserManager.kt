package es.unex.giss.asee.whichnews.login

import android.content.Context
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase

object UserManager {

    fun saveCurrentUser(context: Context, user: User?) {
        val sharedPrefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        if (user != null) {
            // Guardar detalles del usuario
            editor.putString("username", user.name)
        } else {
            // Limpiar detalles del usuario si es nulo
            editor.remove("username")
        }

        editor.apply()
    }

    suspend fun loadCurrentUser(context: Context) : User? {
        val sharedPrefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null)
        if (username != null) {
            // Obtener instancia de la base de datos
            val database = WhichNewsDatabase.getInstance(context)

            // Usar el DAO para buscar al usuario
            return database.userDao().find(username)
        }
        return null
    }

    suspend fun clearData(context: Context){
        val sharedPrefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        editor.remove("username")
    }
}
