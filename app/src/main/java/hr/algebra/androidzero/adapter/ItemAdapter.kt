package hr.algebra.androidzero.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.androidzero.ITEM_POS
import hr.algebra.androidzero.ItemPagerActivity
import hr.algebra.androidzero.PRODUCT_PROVIDER_CONTENT_URI // Koristimo tvoj novi URI
import hr.algebra.androidzero.R
import hr.algebra.androidzero.framework.startActivity
import hr.algebra.androidzero.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false) // layout 'item.xml' mora postojati
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        // Klik na artikl otvara detalje (Pager)
        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(ITEM_POS, position)
        }

        // Dugi klik briše artikl
        holder.itemView.setOnLongClickListener {
            deleteItem(position)
            true
        }
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        // Brišemo iz baze preko ContentProvidera
        context.contentResolver.delete(
            ContentUris.withAppendedId(PRODUCT_PROVIDER_CONTENT_URI, item._id!!),
            null,
            null
        )
        // Brišemo lokalno spremljenu sliku s mobitela
        if (item.picturePath.isNotEmpty()) {
            File(item.picturePath).delete()
        }

        items.removeAt(position)
        notifyItemRemoved(position) // Bolje nego notifyDataSetChanged za performanse
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvItem = itemView.findViewById<TextView>(R.id.tvItem)
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)

        fun bind(item: Item){
            tvItem.text = item.title

            // Picasso učitava sliku iz datoteke koju je Fetcher spremio
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.placeholder_image) // Promijeni u svoj placeholder ako nemaš R.drawable.nasa
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
        }
    }
}