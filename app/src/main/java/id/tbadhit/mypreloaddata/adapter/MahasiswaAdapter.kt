package id.tbadhit.mypreloaddata.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.tbadhit.mypreloaddata.R
import id.tbadhit.mypreloaddata.databinding.ItemMahasiswaRowBinding
import id.tbadhit.mypreloaddata.model.MahasiswaModel
import java.util.*
import kotlin.collections.ArrayList

class MahasiswaAdapter: RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    private val listMahasiswa = ArrayList<MahasiswaModel>()

    fun setData(listMahasiswa: ArrayList<MahasiswaModel>) {
        if (listMahasiswa.size > 0) {
            this.listMahasiswa.clear()
        }

        this.listMahasiswa.addAll(listMahasiswa)

        notifyDataSetChanged()
    }

    inner class MahasiswaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMahasiswaRowBinding.bind(itemView)
        fun bind(mahasiswa: MahasiswaModel) {
            binding.apply {
                txtName.text = mahasiswa.name
                txtNim.text = mahasiswa.nim

                val random = Random()
                val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
                imageView.setColorFilter(color)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa_row, parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        holder.bind(listMahasiswa[position])
    }

    override fun getItemCount(): Int = listMahasiswa.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()
}