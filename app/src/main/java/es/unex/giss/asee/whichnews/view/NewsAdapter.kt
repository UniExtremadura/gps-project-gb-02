package es.unex.giss.asee.whichnews.view
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.data.models.News

class NewsAdapter(private val context: Context, private val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.SummaryViewHolder>() {

    class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return SummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val news = newsList[position]

        // Cargamos la imagen con Picasso
        Picasso.get().load(news.urlToImage).into(holder.imageView)

        holder.titleTextView.text = news.title
        holder.descriptionTextView.text = news.description
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
