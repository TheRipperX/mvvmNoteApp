package com.example.mvvmnoteapp.ui.main.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mvvmnoteapp.R
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.databinding.FragmentNoteBinding
import com.example.mvvmnoteapp.utils.HEALTHY
import com.example.mvvmnoteapp.utils.HIGH
import com.example.mvvmnoteapp.utils.HOME
import com.example.mvvmnoteapp.utils.LEARNING
import com.example.mvvmnoteapp.utils.LOW
import com.example.mvvmnoteapp.utils.NORMAL
import com.example.mvvmnoteapp.utils.WORK
import com.example.mvvmnoteapp.utils.setItemsSpinner
import com.example.mvvmnoteapp.viewmodel.ViewModelFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNoteBinding

    // text spinner category
    private var categoryText = ""
    //text spinner priority
    private var priorityText = ""
    //noteEntity id
    private var noteId = 0

    @Inject
    lateinit var noteEntity: NoteEntity

    private val viewModelFragment: ViewModelFragment by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main()
    }

    private fun main() {

        binding.apply {
            //set list priority and category
            viewModelFragment.setPriority()
            viewModelFragment.setCategory()

            //set spinner category
            viewModelFragment.categoryList.observe(viewLifecycleOwner) {
                spinnerCategorise.setItemsSpinner(it) {str->
                    categoryText = str
                }
            }
            //set spinner priority
            viewModelFragment.priorityList.observe(viewLifecycleOwner) {
                spinnerPriority.setItemsSpinner(it) {str->
                    priorityText = str
                }
            }

            //close fragments
            imgClose.setOnClickListener { dismiss() }
            //btn save
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDec.text.toString()

                if (title.isEmpty() || desc.isEmpty() || categoryText.isEmpty() || priorityText.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "لطفا تمام ورودی ها را پر کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.desc = desc
                noteEntity.category = categoryText
                noteEntity.priority = priorityText

                //save note
                viewModelFragment.saveNote(true, noteEntity)
                dismiss()

            }
        }

    }

}