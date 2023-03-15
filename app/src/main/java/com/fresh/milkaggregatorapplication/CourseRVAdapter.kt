package com.fresh.milkaggregatorapplication


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CourseRVAdapter
internal constructor() : ListAdapter<CourseModal, CourseRVAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val items = mutableListOf<CourseModal>()
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = getCourseAt(position)
        holder.courseNameTV.text = model!!.courseName
        holder.courseDescTV.text = model.courseDescription
        holder.courseDurationTV.text = model.courseDuration

        val pin3 = "FreshYard Country Eggs - Pack Of 6"
        val pin4 = "FreshYard Poultry Eggs - Pack Of 6"

        if (model.courseName.toString() == pin3) {
            holder.productimage.setImageResource(R.drawable.country)
        } else if (model.courseName.toString() == pin4 ) {
            holder.productimage.setImageResource(R.drawable.protein)
        }

        val total: Int = Integer.valueOf(model.courseDuration.toString())
        val qty: Int = Integer.valueOf(model.courseDescription.toString())

        holder.grand.text= (total + 1.80).toString()
        holder.pricex.text= (total / qty).toString()



        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, orderDetails::class.java)
            intent.putExtra("title", model.courseName)
            intent.putExtra("qty", model.courseDescription)
            intent.putExtra("price", model.courseDuration)
            view.context.startActivity(intent)
        }
    }

    fun getCourseAt(position: Int): CourseModal? {
        return getItem(position)
    }

    fun removeItem(position: Int) {
        items.removeAt(position);
        notifyItemRemoved(position)
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var courseNameTV: TextView
        var courseDescTV: TextView
        var courseDurationTV: TextView
        var productimage: ImageView
        var grand: TextView
        var pricex: TextView

        init {
            courseNameTV = itemView.findViewById(R.id.nameid)
            courseDescTV = itemView.findViewById(R.id.tvfirstName)
            courseDurationTV = itemView.findViewById(R.id.tvage)
            productimage = itemView.findViewById(R.id.productimage)
            grand = itemView.findViewById(R.id.grand)
            pricex = itemView.findViewById(R.id.pricex)

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
                    return oldItem.courseName == newItem.courseName && oldItem.courseDescription == newItem.courseDescription && oldItem.courseDuration == newItem.courseDuration
                }
            }
    }
}