package com.example.mvvmnoteapp.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnoteapp.R
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.databinding.MainLayoutAdapterBinding
import com.example.mvvmnoteapp.utils.HEALTHY
import com.example.mvvmnoteapp.utils.HIGH
import com.example.mvvmnoteapp.utils.HOME
import com.example.mvvmnoteapp.utils.LEARNING
import com.example.mvvmnoteapp.utils.LOW
import com.example.mvvmnoteapp.utils.NORMAL
import com.example.mvvmnoteapp.utils.WORK
import javax.inject.Inject

class MainAdapter @Inject constructor(): RecyclerView.Adapter<MainAdapter.ViewModelMain>() {

    private lateinit var binding: MainLayoutAdapterBinding

    inner class ViewModelMain: RecyclerView.ViewHolder(binding.root) {
        fun getItems(data: NoteEntity) {
            binding.apply {
                Log.d("TAG", "getItems: $data")
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
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModelMain {
        binding = MainLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModelMain()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewModelMain, position: Int) {
        holder.getItems(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    private val differItems = object: DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this, differItems)
}