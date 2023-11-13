package es.unex.giss.asee.whichnews.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.database.ReviewDao
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.data.models.Review
import es.unex.giss.asee.whichnews.databinding.ActivityReviewsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class ReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewsBinding
    private lateinit var reviewDao: ReviewDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el DAO de reseñas
        val db = WhichNewsDatabase.getInstance(applicationContext)
        reviewDao = db.reviewDao()

        // Inicializar RecyclerView
        val recyclerView = binding.recyclerViewReviews
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar listeners
        setUpListeners(recyclerView)
    }

    private suspend fun getUserId(username: String): Long {
        val userDao = WhichNewsDatabase.getInstance(applicationContext).userDao()
        val user = userDao.findByName(username)
        return user?.userId ?: -1L // Si el usuario no existe, retorna -1 o un valor predeterminado.
    }

    private suspend fun getCurrentUserId(): Long {
        // Supongamos que ya tienes el nombre de usuario del usuario autenticado
        val currentUsername = "nombre_de_usuario"
        return getUserId(currentUsername)
    }

    private fun setUpListeners(recyclerView: RecyclerView) {
        val etReview = binding.etReview
        val btnSubmit = binding.btnSubmit

        btnSubmit.setOnClickListener {
            val reviewText = etReview.text.toString().trim()

            if (isValidReview(reviewText)) {
                GlobalScope.launch {
                    val userId = getCurrentUserId()

                    // Insertar la reseña en la base de datos
                    reviewDao.insertReview(Review(0, userId, "nombre_de_usuario", reviewText))

                    runOnUiThread {
                        recyclerView.adapter = ReviewsAdapter(updatedReviews)
                    }
                }

                // Limpiar el campo de entrada después de enviar la reseña
                etReview.text.clear()
            } else {
                // La reseña no es válida, mostrar un Toast con el mensaje de error
                runOnUiThread {
                    showToast("La reseña debe tener entre 1 y 100 caracteres.")
                }
            }
        }
    }

    private fun isValidReview(reviewText: String): Boolean {
        // Verificar que la reseña tenga al menos 1 carácter y menos de 100 caracteres
        return reviewText.length in 1..100
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}