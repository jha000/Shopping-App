package com.fresh.milkaggregatorapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.math.roundToInt

class selection : AppCompatActivity() {

    var a: TextView? = null
    var r: TextView? = null
    var s: TextView? = null
    var product: TextView? = null
    var quantity: TextView? = null
    var email: TextView? = null
    var addressget: TextView? = null
    var x: TextView? = null
    var cg: ImageView? = null
    var dec: ImageView? = null
    var inc: ImageView? = null
    var count: TextView? = null
    var mobileget: TextView? = null
    var nameget: TextView? = null
    lateinit var opencart: LinearLayout
    lateinit var message1: TextView

    private lateinit var viewmodal: ViewModal

    private var amountEdt: TextView? = null
    var name: TextView? = null
    var address: TextView? = null
    var back: ImageView? = null
    var paymentDialog: BottomSheetDialog? = null
    var FailureDialog: BottomSheetDialog? = null
    var myDialog: BottomSheetDialog? = null
    var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        myDialog = BottomSheetDialog(this)
        paymentDialog = BottomSheetDialog(this)
        paymentDialog!!.setContentView(R.layout.dialog)
        paymentDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        FailureDialog = BottomSheetDialog(this)
        FailureDialog!!.setContentView(R.layout.dialogfai)
        FailureDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        email = findViewById(R.id.email)
        addressget = findViewById(R.id.addressget)
        mobileget = findViewById(R.id.mobileget)
        nameget = findViewById(R.id.nameget)
        message1 = findViewById(R.id.message1)

        amountEdt = findViewById(R.id.edit1)
        product = findViewById(R.id.whole)
        quantity = findViewById(R.id.quantity)
        back = findViewById(R.id.backm)


        val sharedPreferences = applicationContext.getSharedPreferences("myKey", MODE_PRIVATE)

        val value2 = sharedPreferences.getString("pincode", "")
        email!!.text = value2
        val value3 = sharedPreferences.getString("y", "")
        addressget!!.text = value3
        val value5 = sharedPreferences.getString("x", "")
        mobileget!!.text = value5
        val value6 = sharedPreferences.getString("value", "")
        nameget!!.text = value6
        val value4 = sharedPreferences.getString("amount1", "")
        val a1 = (value4!!.toFloat() * 6)
        quantity!!.text = a1.toString()


        opencart = findViewById(R.id.opencart)

        opencart.setOnClickListener{


            val i = Intent(this, cart::class.java)
            startActivity(i)

            val courseName = product!!.text.toString()
            val courseDesc = count!!.text.toString()
            val courseDuration = amountEdt!!.text.toString()
            val namett = nameget!!.text.toString()
            val phonett = mobileget!!.text.toString()
            val addresstt = addressget!!.text.toString()
//            if (courseName.isEmpty() || courseDesc.isEmpty() || courseDuration.isEmpty() || namett.isEmpty()) {
//                return@setOnClickListener
//            }

            saveCourse(courseName, courseDesc, courseDuration,namett,phonett,addresstt)

            finish()

        }


        back!!.setOnClickListener(View.OnClickListener {
            finish()
        })

        x = findViewById<View>(R.id.edit1) as TextView
        inc = findViewById<View>(R.id.inc) as ImageView
        dec = findViewById<View>(R.id.dec) as ImageView
        count = findViewById<View>(R.id.count) as TextView
        cg = findViewById<View>(R.id.ceggs) as ImageView

        message1.text=count!!.text.toString()

        val mul = quantity!!.text.toString()
        x!!.text = (mul.toFloat() * i).roundToInt().toString()

        inc!!.setOnClickListener(View.OnClickListener {
            i++
            count!!.text = i.toString()
            val mul = quantity!!.text.toString()
            x!!.text = (mul.toFloat() * i).roundToInt().toString()
            message1.text=count!!.text.toString()
        })

        dec!!.setOnClickListener {

            if (i > 1) {
                i--
                count!!.text = i.toString()
                val mul = quantity!!.text.toString()
                x!!.text = (mul.toFloat() * i).roundToInt().toString()
                message1.text=count!!.text.toString()
            }
        }


        val adapter = cartAdapter()
        viewmodal = ViewModelProviders.of(this).get(ViewModal::class.java)

        viewmodal.allCourses.observe(this) { models ->
            adapter.submitList(models)
        }
    }


    private fun saveCourse(courseName: String, courseDescription: String, courseDuration: String, namett: String,  phonett: String, addresstt: String) {

        val model = CourseModal(courseName, courseDescription, courseDuration,namett,phonett, addresstt )
        viewmodal.insert(model)

    }



}