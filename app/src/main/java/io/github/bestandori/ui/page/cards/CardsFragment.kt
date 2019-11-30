package io.github.bestandori.ui.page.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.bestandori.R
import io.github.bestandori.data.model.CardModel
import io.github.bestandori.ui.widget.DestFragment
import io.github.bestandori.util.Status
import kotlinx.android.synthetic.main.fragment_cards.*
import kotlinx.android.synthetic.main.item_card.view.*

/**
 * A simple [Fragment] subclass.
 */
class CardsFragment : DestFragment() {
    private val viewModel by viewModels<CardsViewModel>()
    private lateinit var recyclerAdapter: RecyclerAdapter

    override val title: String
        get() = "Cards"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerAdapter = RecyclerAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getCards().observe(this, Observer {
            when (it.status) {
                Status.INITIAL -> Unit
                Status.LOADING -> Toast.makeText(requireContext(), "Start loading...", Toast.LENGTH_SHORT).show()
                Status.SUCCESSFUL -> recyclerAdapter.cards = it.data!!
                Status.FAILED -> {
                    it.error?.printStackTrace()
                    Toast.makeText(requireContext(), "Loading failed with ${it.error}", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.reload()
    }
}

private class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var cards: List<CardModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.titleTextView.text = card.title
        holder.characterTextView.text = card.character
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.titleTextView
        val characterTextView: TextView = itemView.characterTextView
        val summaryTextView: TextView = itemView.summaryTextView
    }
}
