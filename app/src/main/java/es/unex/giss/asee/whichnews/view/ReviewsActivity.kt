package es.unex.giss.asee.whichnews.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.data.models.Review
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
     }

}