package com.lazday.crudroomimagekotlin.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.crudroomimagekotlin.R
import com.lazday.crudroomimagekotlin.helper.ImageConverter
import com.lazday.crudroomimagekotlin.room.DataModel
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(adapterListener: AdapterListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var listData: List<DataModel> = arrayListOf()
    private val listener: AdapterListener = adapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataModel: DataModel, listener: AdapterListener) {
            itemView.textView.text = dataModel.title
            ImageConverter.convertImageFromPath(
                imagePath = dataModel.image, imageView = itemView.imageView)
        }
    }

    /**
     * Activity uses this method to update todoList with the help of LiveData
     * */
    fun setData(dataModel: List<DataModel>) {
        this.listData = dataModel
        notifyDataSetChanged()
    }

    /**
     * RecycleView touch event callbacks
     * */
    interface AdapterListener {
        fun onDeleteClicked(dataModel: DataModel)
        fun onViewClicked(dataModel: DataModel)
    }
}
