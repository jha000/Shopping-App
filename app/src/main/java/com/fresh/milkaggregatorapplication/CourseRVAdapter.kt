package com.fresh.milkaggregatorapplication


import android.content.Context
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



    var list: ArrayList<User>? = null
    private var context: Context? = null




    private val items = mutableListOf<CourseModal>()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: User = list!!.get(position)

        holder.courseNameTV.text = user.getCourseName()
        holder.courseDescTV.text = user.getCourseDescription()
        holder.courseDurationTV.text = user.getCourseDuration()
        holder.namett.text = user.getNamett()
        holder.phonett.text = user.getPhonett()
        holder.addresstt.text = user.getAddresstt()



        val pin3 = "FreshYard Country Eggs - Pack Of 6"
        val pin4 = "FreshYard Poultry Eggs - Pack Of 6"

        if (user.courseName.toString() == pin3) {
            holder.productimage.setImageResource(R.drawable.country)
        } else if (user.courseName.toString() == pin4 ) {
            holder.productimage.setImageResource(R.drawable.protein)
        }

        val total: Int = Integer.valueOf(user.courseDuration.toString())
        val qty: Int = Integer.valueOf(user.courseDescription.toString())

        holder.grand.text= (total + 1.80).toString()
        holder.pricex.text= (total / qty).toString()



        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, orderDetails::class.java)
            intent.putExtra("title", user.courseName)
            intent.putExtra("qty", user.courseDescription)
            intent.putExtra("price", user.courseDuration)
            view.context.startActivity(intent)

        }
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
        var namett: TextView
        var phonett: TextView
        var addresstt: TextView
        private var i=1



        init {
            courseNameTV = itemView.findViewById(R.id.nameid)
            courseDescTV = itemView.findViewById(R.id.tvfirstName)
            courseDurationTV = itemView.findViewById(R.id.tvage)
            productimage = itemView.findViewById(R.id.productimage)
            grand = itemView.findViewById(R.id.grand)
            pricex = itemView.findViewById(R.id.pricex)
            namett = itemView.findViewById(R.id.namett)
            phonett = itemView.findViewById(R.id.phonett)
            addresstt = itemView.findViewById(R.id.addresstt)

//
//
//
//            courseDescTV.setOnClickListener{
//                i++
//                courseDescTV.text=i.toString()
//            }

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