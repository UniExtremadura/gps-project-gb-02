package es.unex.giss.asee.whichnews.view
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import es.unex.giss.asee.whichnews.R// importación del paquete de recursos del proyecto
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.Review
import es.unex.giss.asee.whichnews.databinding.NewsItemBinding
import es.unex.giss.asee.whichnews.databinding.ReviewItemBinding
import es.unex.giss.asee.whichnews.login.UserManager
import es.unex.giss.asee.whichnews.view.cardnews.NewsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ReviewsAdapter(
    private var reviewsList: List<Review>,
    private val coroutineScope: CoroutineScope,
    private val context: Context?
) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(
        private val binding: ReviewItemBinding,
        private val coroutineScope: CoroutineScope,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review, totalItems: Int) {
            coroutineScope.launch { // Lanzar una corrutina
                val username = context?.let { UserManager.loadCurrentUser(it)?.name }
                with(binding) {
                    tvUsername.text = username
                    tvReview.text = review.content
                }
            }
        }
    }

    override fun getItemCount() = reviewsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReviewItemBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding, coroutineScope, context)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewsList[position]
        holder.bind(review, reviewsList.size)
    }

    fun updateData(updatedReviews: List<Review>) {
        reviewsList = updatedReviews
        notifyDataSetChanged()
    }
}
