package com.example.mvvmnoteapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnoteapp.R
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.databinding.MainLayoutAdapterBinding
import com.example.mvvmnoteapp.utils.AdapterDiffer
import com.example.mvvmnoteapp.utils.DELETE
import com.example.mvvmnoteapp.utils.EDIT
import com.example.mvvmnoteapp.utils.HEALTHY
import com.example.mvvmnoteapp.utils.HIGH
import com.example.mvvmnoteapp.utils.HOME
import com.example.mvvmnoteapp.utils.LEARNING
import com.example.mvvmnoteapp.utils.LOW
import com.example.mvvmnoteapp.utils.NORMAL
import com.example.mvvmnoteapp.utils.WORK
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainAdapter @Inject constructor(@ApplicationContext private val context: Context): RecyclerView.Adapter<MainAdapter.ViewModelMain>() {

    private lateinit var binding: MainLayoutAdapterBinding
    private var emptyList = emptyList<NoteEntity>()

    inner class ViewModelMain: RecyclerView.ViewHolder(binding.root) {
        fun getItems(data: NoteEntity) {
            binding.apply {
                txtTitle.text = data.title
                txtDesc.text = data.desc

                when(data.category) {
                    HEALTHY -> { imgCategorise.setImageResource(R.drawable.healthy) }
                    LEARNING -> { imgCategorise.setImageResource(R.drawable.learning) }
                    WORK -> { imgCategorise.setImageResource(R.drawable.work) }
                    HOME -> { imgCategorise.setImageResource(R.drawable.home) }
                }

                when(data.priority) {
                    HIGH -> {cardAdapter.setBackgroundResource(R.drawable.bg_high)}
                    NORMAL -> {cardAdapter.setBackgroundResource(R.drawable.bg_normal)}
                    LOW -> {cardAdapter.setBackgroundResource(R.drawable.bg_low)}
                }

                // make pop up menu
                imgMenuItem.setOnClickListener {
                    val popupMenu = PopupMenu(context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_item_adapter, popupMenu.menu)
                    popupMenu.show()
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId) {
                            R.id.menu_edit -> {
                                itemsClick?.let {
                                    it(data, EDIT)
                                }

                            }
                            R.id.menu_delete -> {
                                itemsClick?.let {
                                    it(data, DELETE)
                                }

                            }
                        }

                        return@setOnMenuItemClickListener true
                    }

                }
            }
        }

    }

    private var itemsClick: ((NoteEntity, String) -> Unit)? = null

    fun getClick(listener: (NoteEntity, String) -> Unit) {
        itemsClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelMain {
        binding = MainLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModelMain()
    }

    override fun getItemCount(): Int {
        return emptyList.size
    }

    override fun onBindViewHolder(holder: ViewModelMain, position: Int) {
        holder.getItems(emptyList[position])
        holder.setIsRecyclable(false)
    }

    fun setData(items: List<NoteEntity>) {
        val adapterDiffer = AdapterDiffer(emptyList, items)
        val differ = DiffUtil.calculateDiff(adapterDiffer)
        emptyList = items
        differ.dispatchUpdatesTo(this)
    }
}