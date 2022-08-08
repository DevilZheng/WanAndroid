package com.stew.kb_navigation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.stew.kb_navigation.R
import com.stew.kb_navigation.bean.Navi

class NaviRVAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var diff: AsyncListDiffer<Navi>

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_sys_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = diff.currentList[position]
        (holder as MyViewHolder).name.text = data.name
        holder.flex.removeAllViews()
        for (item in data.articles){
            val layout = LayoutInflater.from(holder.itemView.context).inflate(R.layout.item_flex, null, false)
            val t = layout.findViewById<TextView>(R.id.tx_flex)
            t.text = item.title
            holder.flex.addView(layout)
        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    fun setData(list: List<Navi>) {
        diff.submitList(list)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var name: TextView = item.findViewById(R.id.tx_sys_rv)
        var flex: FlexboxLayout = item.findViewById(R.id.flex_sys)
    }

    class MyCallback : DiffUtil.ItemCallback<Navi>() {
        override fun areItemsTheSame(
            oldItem: Navi,
            newItem: Navi
        ): Boolean {
            return oldItem.cid == newItem.cid
        }

        override fun areContentsTheSame(
            oldItem: Navi,
            newItem: Navi
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}