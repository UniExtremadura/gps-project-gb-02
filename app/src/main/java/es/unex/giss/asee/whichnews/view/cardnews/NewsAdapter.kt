package es.unex.giss.asee.whichnews.view.cardnews
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.databinding.NewsItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class NewsAdapter(
    private var newsList: List<News>,
    private val onClickItem: (News) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(
        private val binding: NewsItemBinding,
        private val onClickItem: (News) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        private val cardManager = context?.let { CardNewsManager(it) }
        fun bind(news: News, totalItems: Int) {
            with(binding){
                // Asignar valores a las vistas
                tvTitle.text = news.title
                tvDescription.text = news.description

                context?.let{
                    // Imagen de la noticia
                    Glide.with(context)
                        .load(news.urlToImage)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivImage)
                }

                // Mostrar el conteo de likes
                if(news.isFavourite) {tvLikeCounter.text = "1"} else {tvLikeCounter.text = "0"}

                // Configurar el clic en el botón de "like"
                ivLike.setOnClickListener {
                    cardManager?.onClickLike(news)
                    if(news.isFavourite) {tvLikeCounter.text = "1"} else {tvLikeCounter.text = "0"}
                }

                // Configurar el clic al pulsar en el resto de items del card_view
                root.setOnClickListener{
                    onClickItem(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding, onClickItem, context)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news, newsList.size)
    }

    fun updateData(favNews: List<News>) {
        // Actualizamos la lista que usa el adaptador
        newsList = favNews
        notifyDataSetChanged()
    }
}
