package com.fresh.milkaggregatorapplication

import android.app.Activity
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject


class cart : AppCompatActivity() , PaymentResultListener {

    private var coursesRV: RecyclerView? = null
    private var viewmodal: ViewModal? = null
    private var test: LinearLayout? = null
    private var delete: TextView? = null
    lateinit var grand: TextView
    var message1: TextView? = null
    var address1: TextView? = null
    var email: TextView? = null
    var name: TextView? = null
    var mobile: TextView? = null
    var address: TextView? = null
    var addressget: TextView? = null
    var deleteBtn: ImageView? = null
    private var amountEdt: TextView? = null
    var st: TextView? = null
    var st1: TextView? = null
    var addmore: TextView? = null
    val adapter = cartAdapter()
    var database: DatabaseReference? = null
    var paymentDialog: BottomSheetDialog? = null
    var FailureDialog: BottomSheetDialog? = null
    var myDialog: BottomSheetDialog? = null
    var layout: LinearLayout? = null
    var totalPrice = 0.0
    private val dataList = mutableListOf<CourseModal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        myDialog = BottomSheetDialog(this)
        paymentDialog = BottomSheetDialog(this)
        paymentDialog!!.setContentView(R.layout.dialog)
        paymentDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        FailureDialog = BottomSheetDialog(this)
        FailureDialog!!.setContentView(R.layout.dialogfai)
        FailureDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        layout = findViewById<View>(R.id.confirm) as LinearLayout
        coursesRV = findViewById<View>(R.id.recyclerView) as RecyclerView
        test = findViewById<View>(R.id.empty) as LinearLayout
        addmore = findViewById<View>(R.id.addmore) as TextView
        amountEdt = findViewById(R.id.edit1)
        message1 = findViewById(R.id.message1)
        address1 = findViewById(R.id.address1)
        grand = findViewById(R.id.grand)
        email = findViewById(R.id.email)
        addressget = findViewById(R.id.addressget)
        delete = findViewById(R.id.delete)
        deleteBtn = findViewById(R.id.deleteBtn)

        deleteBtn!!.setOnClickListener{


            viewmodal = ViewModelProviders.of(this)[ViewModal::class.java]
            viewmodal!!.allCourses.observe(this) { models ->

                adapter.submitList(models)

                viewmodal!!.deleteAllCourses()
                test!!.visibility = View.VISIBLE
                coursesRV!!.visibility = View.GONE
                layout!!.visibility=View.GONE
                deleteBtn!!.visibility=View.GONE
                delete!!.visibility=View.GONE

            }
        }

        delete!!.setOnClickListener{


            viewmodal = ViewModelProviders.of(this)[ViewModal::class.java]
            viewmodal!!.allCourses.observe(this) { models ->

                adapter.submitList(models)

                viewmodal!!.deleteAllCourses()

                test!!.visibility = View.VISIBLE
                coursesRV!!.visibility = View.GONE
                layout!!.visibility=View.GONE
                delete!!.visibility=View.GONE
                deleteBtn!!.visibility=View.GONE

            }
        }

        addmore!!.setOnClickListener{
            val intent = Intent(this, dashboard::class.java)
            intent.putExtra("fragment_to_load", "home")
            startActivity(intent)
            finish()
        }




        val sharedPreferences = applicationContext.getSharedPreferences("myKey", MODE_PRIVATE)
        val value2 = sharedPreferences.getString("pincode", "")
        email!!.text = value2
        val value3 = sharedPreferences.getString("y", "")
        addressget!!.text = value3

        if (addressget!!.text.isNotEmpty()) {
            address1!!.text = addressget!!.text.toString()
            message1!!.text = "*Delivery Address"
            message1!!.setTextColor(Color.rgb(80, 101, 173))
        } else {
            address1!!.text = "Set Delivery Address"
            message1!!.text = "*Delivery Address is not set"
            message1!!.setTextColor(Color.rgb(231, 13, 13))
        }

        val baddressDialog = BottomSheetDialog((this))


        address1!!.setOnClickListener {


            baddressDialog.setContentView(R.layout.baddress)
            val three = baddressDialog.findViewById<View>(R.id.four) as EditText
            val b3 = baddressDialog.findViewById<View>(R.id.b4) as Button
            three.requestFocus()
            val inputMethodManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            b3.setOnClickListener { view ->

                if (three.text.isNotEmpty()) {
                    address1!!.text = addressget!!.text.toString()
                    message1!!.text = "*Delivery Address"
                    message1!!.setTextColor(Color.rgb(80, 101, 173))
                } else {
                    address1!!.text = "Set Delivery Address"
                    message1!!.text = "*Delivery Address is not set"
                    message1!!.setTextColor(Color.rgb(231, 13, 13))
                }

                val x = three.text.toString()
                address1!!.text = x

                viewmodal = ViewModelProviders.of(this)[ViewModal::class.java]
                viewmodal!!.allCourses.observe(this) { models ->
                    adapter.submitList(models)

                        for (item in adapter.currentList) {
                            item.addresstt=address1!!.text.toString()
                        }

                        coursesRV!!.adapter?.notifyDataSetChanged()


                }

                val value = three.text.toString().trim { it <= ' ' }
                val sharedPref =
                    this.getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("y", value)
                editor.apply()

                val imm =
                    this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                baddressDialog.dismiss()
            }
            baddressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            baddressDialog.show()

        }



        coursesRV!!.layoutManager = LinearLayoutManager(this)
        coursesRV!!.setHasFixedSize(true)
        coursesRV!!.adapter = adapter



        viewmodal = ViewModelProviders.of(this)[ViewModal::class.java]
        viewmodal!!.allCourses.observe(this) { models ->
            adapter.submitList(models)

            val totalItems = viewmodal!!.allCourses.value?.size ?: 0
            val yy = totalItems.toString().trim { it <= ' ' }
            val sharedPref =
                this.getSharedPreferences("myKey", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("items", yy)
            editor.apply()


            if (adapter.currentList.isEmpty()) {
                test!!.visibility = View.VISIBLE
                coursesRV!!.visibility = View.GONE
                layout!!.visibility=View.GONE
                delete!!.visibility=View.GONE
                deleteBtn!!.visibility=View.GONE
            } else {
                test!!.visibility = View.GONE
                coursesRV!!.visibility = View.VISIBLE


                for (item in adapter.currentList) {
                    totalPrice += item.courseDuration!!.toDouble()
                    item.addresstt=address1!!.text.toString()
                }

                coursesRV!!.adapter?.notifyDataSetChanged()

                amountEdt!!.text = totalPrice.toString()

                grand.text = (totalPrice + 1.80).toString()

                val value = grand.text.toString().trim { it <= ' ' }
                editor.putString("grand", value)
                editor.apply()

            }

        }

    }

    fun ShowPopup(v: View?) {



        if (address1!!.text.isEmpty() || address1!!.text=="Set Delivery Address"){
            Toast.makeText(
                this@cart,
                "please set delivery address",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        else{

            val pincode = email!!.text.toString()

            val remoteConfig = FirebaseRemoteConfig.getInstance()

            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
            remoteConfig.setConfigSettingsAsync(configSettings)

            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val pincode1 = remoteConfig.getString("pincode").split(",")

                    if (pincode1.contains(pincode)) {

                        val txtclose: TextView?
                        val add2: TextView?
                        val progress: ProgressBar?
                        val back: ImageView?
                        var btnFollow: Button
                        myDialog!!.setContentView(R.layout.custompopup)
                        back = myDialog!!.findViewById<View>(R.id.arrow) as ImageView?

                        back!!.setOnClickListener { myDialog!!.dismiss() }
                        txtclose = myDialog!!.findViewById<View>(R.id.txtclose) as TextView?
                        progress = myDialog!!.findViewById<View>(R.id.progressbar) as ProgressBar?
                        add2 = myDialog!!.findViewById<View>(R.id.textadd) as TextView?


                        name = myDialog!!.findViewById<View>(R.id.entername) as TextView?
                        mobile = myDialog!!.findViewById<View>(R.id.entern0) as TextView?
                        address = myDialog!!.findViewById<View>(R.id.enteradd) as TextView?
                        val sharedPreferences =
                            applicationContext.getSharedPreferences("myKey", MODE_PRIVATE)
                        val nameSave = sharedPreferences.getString("value", "")
                        name!!.setText(nameSave)
                        val mobileSave = sharedPreferences.getString("x", "")
                        mobile!!.setText(mobileSave)
                        val addSave = sharedPreferences.getString("y", "")
                        address!!.setText(addSave)

                        val vv = sharedPreferences.getString("grand", "")
                        add2!!.text = vv

//
//                        val no = grand.text.toString()
//                        add2!!.text = no



                        txtclose!!.setOnClickListener { v ->
                            val imm =
                                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v.windowToken, 0)
                            progress!!.visibility = View.VISIBLE
                            txtclose.visibility = View.GONE


                            val xx = sharedPreferences.getString("grand", "")
                            add2.text = xx


                            // on below line we are getting
                            // amount that is entered by user.
                            val samount = add2.text.toString()

                            // rounding off the amount.
                            val amount = (samount.toFloat() * 100)


                            val smobile = mobile!!.text.toString()

//                val mail = email!!.text.toString()

                            // initialize Razorpay account.
                            val checkout = Checkout()

                            // set your id as below
                            checkout.setKeyID("rzp_live_3B17Ixk1cDL2Zz")

                            // set image
                            checkout.setImage(R.mipmap.ic_launcher)

                            // initialize json object
                            val `object` = JSONObject()
                            try {
                                // to put name
                                `object`.put("name", "Fresh Yard")

                                // put description
                                `object`.put("description", "Fresh Yard")

                                // to set theme color
                                `object`.put("theme.color", "#075E54")

                                // put the currency
                                `object`.put("currency", "INR")

                                // put amount
                                `object`.put("amount", amount)

                                // put mobile number
                                `object`.put("prefill.contact", smobile)

                                // put email
                                `object`.put("prefill.email", "")

                                // open razorpay to checkout activity
                                checkout.open(this@cart, `object`)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        myDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        myDialog!!.show()

                    } else {
                        Toast.makeText(
                            this@cart,
                            "order is not delivered to your area",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    // Failed to fetch or activate remote config
                }
            }

        }


    }


    fun status(v: View?) {

        myDialog!!.setContentView(R.layout.dialog)
        st = myDialog!!.findViewById<View>(R.id.status) as TextView?

        val getEmail = mobile!!.text.toString()

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("test").child(getEmail)

        val adapter: cartAdapter = coursesRV!!.getAdapter() as cartAdapter
        val data: List<CourseModal> = adapter.currentList

        for (i in data.indices) {
            val item: CourseModal = data[i]
            val userId = ref.push().key
            val itemRef = ref.child(userId!!)
            itemRef.setValue(item)
        }

        viewmodal = ViewModelProviders.of(this)[ViewModal::class.java]
        viewmodal!!.allCourses.observe(this) { models ->
            adapter.submitList(models)

            viewmodal!!.deleteAllCourses()

        }

        layout!!.visibility=View.GONE
        delete!!.visibility=View.GONE
        deleteBtn!!.visibility=View.GONE

        myDialog!!.setOnDismissListener(DialogInterface.OnDismissListener {
            val intent = Intent(this, dashboard::class.java)
            intent.putExtra("fragment_to_load", "home")
            startActivity(intent)
            finish()
        })


        st!!.setOnClickListener {
            val intent = Intent(this, dashboard::class.java)
            intent.putExtra("fragment_to_load", "home")
            startActivity(intent)
            finish()
        }


    }

    fun statusError(v: View?) {
        myDialog!!.setContentView(R.layout.dialogfai)
        st1 = myDialog!!.findViewById<View>(R.id.statusError) as TextView?
        st1!!.setOnClickListener {
            myDialog!!.dismiss()
            FailureDialog!!.dismiss()
        }

    }

     override fun onPaymentSuccess(s: String) {
        status(v = null)
    }

     override fun onPaymentError(i: Int, s: String) {
         statusError(v = null)
    }


}