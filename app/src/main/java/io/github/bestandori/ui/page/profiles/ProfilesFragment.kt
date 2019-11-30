package io.github.bestandori.ui.page.profiles

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVGImageView
import io.github.bestandori.R
import io.github.bestandori.data.model.toAssetFilename
import io.github.bestandori.ui.widget.DestFragment
import io.github.bestandori.util.Status
import kotlinx.android.synthetic.main.fragment_profiles.*
import kotlinx.android.synthetic.main.item_profile.view.*

class ProfilesFragment : DestFragment() {
    private val viewModel by viewModels<ProfilesViewModel>()
    private lateinit var recyclerAdapter: RecyclerAdapter

    override val title: String
        get() = "Profiles"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profiles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerAdapter = RecyclerAdapter(requireContext()) {
            viewModel.switchProfile(it.id)
        }
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        addProfileFab.setOnClickListener {
            viewModel.addProfile()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getProfiles().observe(this, Observer {
            if (it.status == Status.SUCCESSFUL) {
                recyclerAdapter.profiles = it.data!!
            }
        })

        viewModel.reload()
    }
}

private class RecyclerAdapter(private val context: Context, private val onItemClick: (Profile) -> Unit) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var profiles: List<Profile> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false))
    }

    override fun getItemCount(): Int = profiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profiles[position]
        holder.nameTextView.text = profile.name
        holder.serverImageView.setImageAsset(profile.server.toAssetFilename())
        holder.summaryTextView.text = context.getString(R.string.text_profileCardsCount, profile.cardsCount)
        holder.activeIndicatorView.visibility = if (profile.active) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener {
            onItemClick(profile)
        }
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serverImageView: SVGImageView = itemView.serverImageView
        val nameTextView: TextView = itemView.nameTextView
        val summaryTextView: TextView = itemView.summaryTextView
        val activeIndicatorView: View = itemView.activeIndicatorView
    }
}
