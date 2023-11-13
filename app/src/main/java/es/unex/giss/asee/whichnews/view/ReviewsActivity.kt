package es.unex.giss.asee.whichnews.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.databinding.ActivityReviewsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class ReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar RecyclerView
        val recyclerView = binding.recyclerViewReviews
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Configurar listeners
        setUpListeners(recyclerView)
    }

    private fun setUpListeners(recyclerView: RecyclerView) {
        val etReview = binding.etReview
        val btnSubmit = binding.btnSubmit

        btnSubmit.setOnClickListener {
            val reviewText = etReview.text.toString().trim()

            if (isValidReview(reviewText)) {
                // La reseña no es válida, mostrar un Toast con el mensaje de error
                runOnUiThread {
                    showToast("Reseña correcta.")
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