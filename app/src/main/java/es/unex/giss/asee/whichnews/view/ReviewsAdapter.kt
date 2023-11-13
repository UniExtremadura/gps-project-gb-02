package es.unex.giss.asee.whichnews.view
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.R// importación del paquete de recursos del proyecto
import es.unex.giss.asee.whichnews.data.models.Review
class ReviewsAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_username: TextView = itemView.findViewById(R.id.tv_username)
        val item_review: TextView = itemView.findViewById(R.id.tv_review)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentReview = reviews[position]

        holder.item_username.text = currentReview.username
        holder.item_review.text = currentReview.content
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}
