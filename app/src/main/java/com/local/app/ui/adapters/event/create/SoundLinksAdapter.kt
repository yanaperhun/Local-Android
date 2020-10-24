package com.local.app.ui.adapters.event.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.local.app.databinding.ItemAddNewSoundLinkBinding
import com.local.app.databinding.ItemSoundLinkBinding
import java.util.*

class SoundLinksAdapter : RecyclerView.Adapter<SoundLinksAdapter.VHBase>() {
    private val ADD_BUTTON = 10

    val links = LinkedList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHBase {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == ADD_BUTTON) {
            return VHAddItem(ItemAddNewSoundLinkBinding.inflate(inflater, parent, false))
        } else {
            return VHSoundLink(ItemSoundLinkBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return links.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == links.size) return ADD_BUTTON
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: VHBase, position: Int) {

        when (holder) {
            is VHAddItem -> {
                holder.binding.etInputLink.setOnEditorActionListener(
                    TextView.OnEditorActionListener { _, keyCode, _ ->
                        if (keyCode == EditorInfo.IME_ACTION_DONE) {
                            links.add(holder.binding.etInputLink.text.toString())
                            holder.binding.etInputLink.setText("")
                            holder.binding.etInputLink.clearFocus()
                            //                            notifyItemInserted(links.size - 1)
                            notifyDataSetChanged()
                            return@OnEditorActionListener true
                        }
                        false
                    })
            }

            is VHSoundLink -> {
                holder.binding.tvLink.text = links[position]
                holder.binding.btnDelete.setOnClickListener {
                    val adapterPos = holder.adapterPosition
                    links.removeAt(adapterPos)
                    notifyDataSetChanged()
                }
            }
        }

    }

    open class VHBase(view: View) : RecyclerView.ViewHolder(view) {

    }

    class VHSoundLink(val binding: ItemSoundLinkBinding) : VHBase(binding.root)

    class VHAddItem(val binding: ItemAddNewSoundLinkBinding) : VHBase(binding.root)

}