package es.unex.giss.asee.whichnews.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.databinding.FolderItemBinding
import java.util.Locale.Category

class FolderAdapter (
    private val folderList: List<Category>,
    private val onClick: (Category) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    class FolderViewHolder(
        private val binding: FolderItemBinding,
        private val onClick: (Category) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(folder: Category, totalItems: Int) {
            with(binding){
                // Asignar valores a las vistas
                tvFolderName.text = folder.name
                // implementar la gestión de likes
                // tvLikeCounter.text = news.likeCounter.toString()

                // Configurar el clic en el botón de "like"
                cvFolder.setOnClickListener {
                    // Llamar al listener si está configurado
                    onClick(folder)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FolderItemBinding.inflate(inflater, parent, false)
        return FolderViewHolder(binding, onClick, context)
    }

    override fun getItemCount() = folderList.size

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val category = folderList[position]
        holder.bind(category, folderList.size)
    }
}