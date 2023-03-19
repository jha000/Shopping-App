package com.fresh.milkaggregatorapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class cartAdapter
internal constructor() : ListAdapter<CourseModal, cartAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val items = mutableListOf<CourseModal>()
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.cartitem, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = getCourseAt(position)
        holder.courseNameTV.text = model!!.courseName
        holder.courseDescTV.text = model.courseDescription
        holder.courseDurationTV.text = model.courseDuration
        holder.namett.text=model.namett
        holder.phonett.text=model.phonett
        holder.addresstt.text=model.addresstt



        val pin3 = "FreshYard Country Eggs - Pack Of 6"
        val pin4 = "FreshYard Poultry Eggs - Pack Of 6"

        if (model.courseName.toString() == pin3) {
            holder.productimage.setImageResource(R.drawable.country)
        } else if (model.courseName.toString() == pin4 ) {
            holder.productimage.setImageResource(R.drawable.protein)
        }

        val total: Int = Integer.valueOf(model.courseDuration.toString())
        val qty: Int = Integer.valueOf(model.courseDescription.toString())

        holder.pricex.text= (total / qty).toString()


//        holder.deleteBtn.setOnClickListener {
//            removeItem(position)
//        }

    }

    fun getCourseAt(position: Int): CourseModal? {
        return getItem(position)
    }

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        for (course in currentList) {
            totalPrice += course.courseDuration!!.toDouble()
        }
        return totalPrice
    }

//
//    fun removeItem(position: Int) {
//        submitList(currentList.filterIndexed { index, _ -> index != position })
//    }




    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var courseNameTV: TextView
        var courseDescTV: TextView
        var courseDurationTV: TextView
        var namett: TextView
        var phonett: TextView
        var addresstt: TextView
        var pricex: TextView
        var productimage: ImageView
        var deleteBtn: ImageView
        private var i=1



        init {
            courseNameTV = itemView.findViewById(R.id.nameid)
            courseDescTV = itemView.findViewById(R.id.tvfirstName)
            courseDurationTV = itemView.findViewById(R.id.tvage)
            namett = itemView.findViewById(R.id.namett)
            phonett = itemView.findViewById(R.id.phonett)
            addresstt = itemView.findViewById(R.id.addresstt)
            pricex = itemView.findViewById(R.id.pricex)
            productimage = itemView.findViewById(R.id.productimage)
            deleteBtn = itemView.findViewById(R.id.deleteBtn)


            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(model: CourseModal?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<CourseModal> =
            object : DiffUtil.ItemCallback<CourseModal>() {
                override fun areItemsTheSame(oldItem: CourseModal, newItem: CourseModal): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: CourseModal,
                    newItem: CourseModal
                ): Boolean {
                    return oldItem.courseName == newItem.courseName && oldItem.courseDescription == newItem.courseDescription && oldItem.courseDuration == newItem.courseDuration && oldItem.namett == newItem.namett && oldItem.phonett == newItem.phonett && oldItem.addresstt == newItem.addresstt
                }
            }
    }
}