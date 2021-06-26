package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myapplication.CurrentPlayer;
import com.example.myapplication.databinding.PlayerCellBinding

class PlayerAdapter(
    var entries: MutableList<CurrentPlayer>,
    val context: Context
) : BaseAdapter() {
    var layoutInflater: LayoutInflater
    private var _binding: PlayerCellBinding? = null
    private val binding get() = _binding!!

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int { //number of elements to display
        return entries.size
    }

    override fun getItem(index: Int): CurrentPlayer { //item at index
        return entries.get(index)
    }

    override fun getItemId(index: Int): Long { //itemId for index
        return index.toLong()
    }

    override fun getView(
        index: Int, oldView: View?,
        viewGroup: ViewGroup?
    ): View {
        var view: View
        if (oldView == null) { //check if we get a view to recycle
            _binding = PlayerCellBinding.inflate(
                layoutInflater,
                viewGroup, false
            )
            view = binding.root
        } else {  //if yes, use the oldview
            view = oldView
        }
        val entry = getItem(index) //get the data for this index
        binding.regPlayerList.text = entry.name
        return view
    }
}