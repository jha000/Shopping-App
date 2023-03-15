package com.fresh.milkaggregatorapplication


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class orderFragment : Fragment() {

    private var coursesRV: RecyclerView? = null
    private var test: ImageView? = null
    private var no: TextView? = null
    private var viewmodal: ViewModal? = null
//
//    var list: ArrayList<User>? = null
    var database: DatabaseReference? = null
//    var myAdapter: TransferAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_order, container, false)

//        val animation = AnimationUtils.loadAnimation(context, R.anim.shake)
//        view.startAnimation(animation)

        coursesRV = view.findViewById<View>(R.id.recyclerView) as RecyclerView

        no = view.findViewById<View>(R.id.no) as TextView
        test = view.findViewById<View>(R.id.empty) as ImageView

        val sharedPreferences =
            requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val value2 = sharedPreferences.getString("x", null)
        no!!.text = value2

        coursesRV!!.layoutManager = LinearLayoutManager(activity)
        coursesRV!!.setHasFixedSize(true)
//
//        database = FirebaseDatabase.getInstance().getReference("test").child(no!!.text.toString())
//
//        list = java.util.ArrayList()
//        myAdapter = TransferAdapter(requireActivity(), list!!)

        val adapter = CourseRVAdapter()
        coursesRV!!.adapter = adapter

//        list!!.clear()
//
//        database!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (dataSnapshot in snapshot.children) {
//                    val user = dataSnapshot.getValue(User::class.java)
//                    list!!.add(user!!)
//                }
//                myAdapter!!.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })

        viewmodal = ViewModelProviders.of(this)[ViewModal::class.java]
        viewmodal!!.allCourses.observe(requireActivity()) { models ->
            adapter.submitList(models)
            if(adapter.currentList.isEmpty()){
                test!!.visibility=View.VISIBLE
                coursesRV!!.visibility=View.GONE
            }
            else{
                test!!.visibility=View.GONE
                coursesRV!!.visibility=View.VISIBLE

            }

        }





        return view
    }

}