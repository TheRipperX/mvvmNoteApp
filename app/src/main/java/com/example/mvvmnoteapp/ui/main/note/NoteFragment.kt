package com.example.mvvmnoteapp.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.databinding.FragmentNoteBinding
import com.example.mvvmnoteapp.utils.BUNDLE
import com.example.mvvmnoteapp.utils.EDIT
import com.example.mvvmnoteapp.utils.SAVE
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
    // type edit or save note
    private var type = ""
    private var saveOrEdit = true

    //set list to spinner
    private val catList = mutableListOf<String>()
    private val proList = mutableListOf<String>()

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
        noteId = arguments?.getInt(BUNDLE) ?: 0

        saveOrEdit = if (noteId == 0) {
            type = SAVE
            true
        }else {
            type = EDIT
            false
        }

        binding.apply {
            //set list priority and category
            viewModelFragment.setPriority()
            viewModelFragment.setCategory()

            //set spinner category
            viewModelFragment.categoryList.observe(viewLifecycleOwner) {
                catList.addAll(it)
                spinnerCategorise.setItemsSpinner(it) {str->
                    categoryText = str
                }
            }
            //set spinner priority
            viewModelFragment.priorityList.observe(viewLifecycleOwner) {
                proList.addAll(it)
                spinnerPriority.setItemsSpinner(it) {str->
                    priorityText = str
                }
            }

            //close fragments
            imgClose.setOnClickListener { dismiss() }
            //set items note
            if (type == EDIT) {
                viewModelFragment.findIdNotes(noteId)

                viewModelFragment.noteIds.observe(viewLifecycleOwner) {
                    edtTitle.setText(it.title)
                    edtDec.setText(it.desc)
                    //set spinner
                    spinnerCategorise.setSelection(getIndex(catList, it.category))
                    spinnerPriority.setSelection(getIndex(proList, it.priority))
                }
            }

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
                viewModelFragment.saveOrEditNote(saveOrEdit, noteEntity)
                dismiss()

            }
        }

    }

    private fun getIndex(list: MutableList<String>, item: String) : Int {
        var index = 0
        for (i in list.indices) {
            if (list[i] == item) {
                index = i
                break
            }
        }
        return index
    }

}