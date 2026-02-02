package hr.algebra.androidzero.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.androidzero.PRODUCT_PROVIDER_CONTENT_URI // Tvoj URI
import hr.algebra.androidzero.R
import hr.algebra.androidzero.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        // Klik na ikonu (zastavicu) mijenja status "pročitano/kupljeno"
        holder.ivRead.setOnClickListener {
            updateItem(position)
        }
    }

    private fun updateItem(position: Int) {
        val item = items[position]
        item.read = !item.read

        // Ažuriranje u bazi preko tvog ProductProvidera
        context.contentResolver.update(
            ContentUris.withAppendedId(PRODUCT_PROVIDER_CONTENT_URI, item._id!!),
            ContentValues().apply {
                put(Item::read.name, item.read)
            },
            null,
            null
        )
        notifyItemChanged(position)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvItem = itemView.findViewById<TextView>(R.id.tvItem)
        private val tvPrice = itemView.findViewById<TextView>(R.id.tvDate) // Koristimo tvDate ID za prikaz cijene
        private val tvExplanation = itemView.findViewById<TextView>(R.id.tvExplanation)
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        fun bind(item: Item){
            tvItem.text = item.title
            // Umjesto datuma, tvoji artikli imaju cijenu
            tvPrice.text = "${item.price} €"
            tvExplanation.text = item.explanation

            // Postavljanje ikonice ovisno o statusu
            ivRead.setImageResource(if(item.read) R.drawable.green_flag else R.drawable.red_flag)

            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.placeholder_image)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
        }
    }
}