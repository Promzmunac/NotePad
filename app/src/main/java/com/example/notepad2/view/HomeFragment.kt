package com.example.notepad2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad2.viewmodel.UserViewModel
import com.example.notepad2.R
import com.example.notepad2.adapter.AdapterNote
import com.example.notepad2.adapter.swipeToDelete
import com.example.notepad2.adapter.swipeToEdit
import com.example.notepad2.databinding.FragmentHomeBinding
import com.example.notepad2.model.datamodel.Note

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: AdapterNote
    private lateinit var userViewModel: UserViewModel
    private var noteList = emptyList<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize ViewModel using ViewModelProvider
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe changes in allNote LiveData and update UI accordingly
        userViewModel.allNote.observe(viewLifecycleOwner) { notes ->
            noteList = notes
            adapter.setData(noteList)
            updateUI()
        }

        // Initialize Adapter and bind RecyclerView to it
        adapter = AdapterNote(requireContext())
        binding.noteRv.adapter = adapter
/*
        binding.noteRv.layoutManager = LinearLayoutManager(requireContext())*/

        binding.noteRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        updateUI()
        setUpSwipeToDelete()
        setUpSwipeToEdit()

        binding.floatingActionButton3.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_noteFragment)
        }

    }

    private fun updateUI() {
        // Set FAB visibility based on noteList and
        // Update hourResult and noteList size
        val empty = 0

        if (noteList.isEmpty()) {
            binding.floatingActionButton3.visibility = View.VISIBLE
            binding.startText.visibility = View.VISIBLE
            binding.hourResult.text = empty.toString()
            binding.dataCount.text = empty.toString()

        } else {
            binding.hourResult.text = Note.add(noteList).toString()
            binding.dataCount.text = noteList.size.toString()
            binding.startText.visibility = View.INVISIBLE
        }
    }

    private fun setUpSwipeToDelete() {
        // Set up swipe-to-delete and swipe-to-edit functionality

        val swipeToDelete = object : swipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                myDelete(position)
                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelperDelete = ItemTouchHelper(swipeToDelete)
        itemTouchHelperDelete.attachToRecyclerView(binding.noteRv)
    }

    private fun setUpSwipeToEdit() {
        val swipeToEdit = object : swipeToEdit() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                toEdit(position)
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(requireContext(), "Edited", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelperEdit = ItemTouchHelper(swipeToEdit)
        itemTouchHelperEdit.attachToRecyclerView(binding.noteRv)
    }

    private fun myDelete(position: Int) {
        val noteToDelete = noteList[position]
        // Delete the note using the ViewModel and livedata observe changes
        userViewModel.deleteNote(noteToDelete)
    }

    private fun toEdit(position: Int) {
        val currentNote = noteList[position]

        val bundle = bundleOf(
            "NOTETYPE" to "Edit",
            "TITLE" to currentNote.title,
            "TEXT" to currentNote.text,
            "POSITION" to position,
            "IMAGE" to currentNote.image,
            "COUNT" to currentNote.count,
            "ID" to currentNote.noteId
        )

        // Navigate to noteFragment and pass the bundle
        findNavController().navigate(R.id.action_homeFragment_to_noteFragment, bundle)
    }
}
