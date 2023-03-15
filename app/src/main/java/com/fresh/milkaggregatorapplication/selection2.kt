package com.fresh.milkaggregatorapplication


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class selection2 : AppCompatActivity(), PaymentResultListener {

    var a: TextView? = null
    var r: TextView? = null
    var s: TextView? = null
    var product: TextView? = null
    var add: TextView? = null
    var quantity: TextView? = null
    var email: TextView? = null
    var addressget: TextView? = null
    var message1: TextView? = null
    var address1: TextView? = null
    var st: TextView? = null
    var x: TextView? = null
    var st1: TextView? = null
    var cg: ImageView? = null
    var dec: ImageView? = null
    var inc: ImageView? = null
    var count: TextView? = null

    lateinit var grand: TextView
    lateinit var opencart: LinearLayout

    private lateinit var viewmodal: ViewModal

    private var amountEdt: TextView? = null
    var name: TextView? = null
    var mobile: TextView? = null
    var address: TextView? = null
    var back: ImageView? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    var paymentDialog: BottomSheetDialog? = null
    var FailureDialog: BottomSheetDialog? = null
    var myDialog: BottomSheetDialog? = null
    var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection2)
        myDialog = BottomSheetDialog(this)
        paymentDialog = BottomSheetDialog(this)
        paymentDialog!!.setContentView(R.layout.dialog)
        paymentDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        FailureDialog = BottomSheetDialog(this)
        FailureDialog!!.setContentView(R.layout.dialogfai)
        FailureDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        email = findViewById(R.id.email)
        addressget = findViewById(R.id.addressget)
        message1 = findViewById(R.id.message1)
        address1 = findViewById(R.id.address1)

        grand = findViewById(R.id.grand)





        amountEdt = findViewById(R.id.edit1)
        product = findViewById(R.id.whole)
        quantity = findViewById(R.id.quantity)
        back = findViewById(R.id.backm)


        val sharedPreferences = applicationContext.getSharedPreferences("myKey", MODE_PRIVATE)

        val value2 = sharedPreferences.getString("pincode", "")
        email!!.text = value2
        val value3 = sharedPreferences.getString("y", "")
        addressget!!.text = value3
        val value4 = sharedPreferences.getString("amount2", "")
        val a1 = (value4!!.toFloat() * 6)
        quantity!!.text = a1.toString()



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




        opencart = findViewById(R.id.opencart)

//        opencart.setOnClickListener{
//
//
////            val i = Intent(this, cartview::class.java)
////            startActivity(i)
////
////            val courseName = product!!.text.toString()
////            val courseDesc = count!!.text.toString()
////            val courseDuration = amountEdt!!.text.toString()
////            if (courseName.isEmpty() || courseDesc.isEmpty() || courseDuration.isEmpty()) {
////                return@setOnClickListener
////            }
////
////            saveCourse(courseName, courseDesc, courseDuration)
//
//        }


        back!!.setOnClickListener(View.OnClickListener {
            finish()
        })

        x = findViewById<View>(R.id.edit1) as TextView
        inc = findViewById<View>(R.id.inc) as ImageView
        dec = findViewById<View>(R.id.dec) as ImageView
        count = findViewById<View>(R.id.count) as TextView
        cg = findViewById<View>(R.id.ceggs) as ImageView

        val mul = quantity!!.text.toString()
        x!!.text = (mul.toFloat() * i).roundToInt().toString()

        inc!!.setOnClickListener(View.OnClickListener {
            i++
            count!!.text = i.toString()
            val mul = quantity!!.text.toString()
            x!!.text = (mul.toFloat() * i).roundToInt().toString()
            grand.text = ((mul.toFloat() * i) + 1.8).toString()
        })

        dec!!.setOnClickListener {

            if (i > 1) {
                i--
                count!!.text = i.toString()
                val mul = quantity!!.text.toString()
                x!!.text = (mul.toFloat() * i).roundToInt().toString()
                grand.text = ((mul.toFloat() * i) + 1.8).toString()
            }
        }

        val total: Int = Integer.valueOf(amountEdt!!.text.toString())
        grand.text = (total + 1.80).toString()

        val adapter = CourseRVAdapter()
        viewmodal = ViewModelProviders.of(this).get(ViewModal::class.java)

        viewmodal.allCourses.observe(this) { models ->
            adapter.submitList(models)
        }
    }


    fun status(v: View?) {

        myDialog!!.setContentView(R.layout.dialog)
        st = myDialog!!.findViewById<View>(R.id.status) as TextView?
        val value = name!!.text.toString().trim { it <= ' ' }
        val value1 = mobile!!.text.toString().trim { it <= ' ' }
        val value2 = address!!.text.toString().trim { it <= ' ' }
        val sharedPref = getSharedPreferences("myKey", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("value", value)
        editor.putString("x", value1)
        editor.putString("y", value2)
        editor.apply()
        val courseName = product!!.text.toString()
        val courseDesc = count!!.text.toString()
        val courseDuration = amountEdt!!.text.toString()
        if (courseName.isEmpty() || courseDesc.isEmpty() || courseDuration.isEmpty()) {
            return
        }

        saveCourse(courseName, courseDesc, courseDuration)
        val getName = name!!.text.toString()
        val getEmail = mobile!!.text.toString()
        val getAddress = address!!.text.toString()
        val getProduct = product!!.text.toString()
        val getAmount = amountEdt!!.text.toString()
        val getQty = count!!.text.toString()

        val hashMap = HashMap<String, Any>()
        hashMap["Name"] = getName
        hashMap["Mobile"] = getEmail
        hashMap["Address"] = getAddress
        hashMap["Product"] = getProduct
        hashMap["Amount"] = getAmount
        hashMap["Quantity"] = getQty

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.reference
        val userId = databaseReference!!.push().key
        databaseReference!!.child("My Orders")
            .child(userId!!)
            .setValue(hashMap)


        databaseReference!!.child("Poultry Eggs")
            .child(userId!!)
            .setValue(hashMap)


        st!!.setOnClickListener {
            myDialog!!.dismiss()
            paymentDialog!!.dismiss()
            super@selection2.onBackPressed()
        }


    }

    fun statusError(v: View?) {
        myDialog!!.setContentView(R.layout.dialogfai)
        st1 = myDialog!!.findViewById<View>(R.id.statusError) as TextView?
        val value = name!!.text.toString().trim { it <= ' ' }
        val value1 = mobile!!.text.toString().trim { it <= ' ' }
        val value2 = address!!.text.toString().trim { it <= ' ' }
        val sharedPref = getSharedPreferences("myKey", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("value", value)
        editor.putString("x", value1)
        editor.putString("y", value2)
        editor.apply()

        st1!!.setOnClickListener {
            myDialog!!.dismiss()
            FailureDialog!!.dismiss()
        }

    }

    fun ShowPopup(v: View?) {

        if (address1!!.text.isEmpty() || address1!!.text=="Set Delivery Address"){
            Toast.makeText(
                this@selection2,
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


                        val no = grand.text.toString()
                        add2!!.text = no
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
                        val intent = intent
                        if (intent.hasExtra(EXTRA_ID)) {
                            // if we get id for our data then we are
                            // setting values to our edit text fields.
                            product!!.text = intent.getStringExtra(EXTRA_COURSE_NAME)
                            quantity!!.text = intent.getStringExtra(EXTRA_DESCRIPTION)
                            amountEdt!!.text = intent.getStringExtra(EXTRA_DURATION)
                        }
                        txtclose!!.setOnClickListener { v ->
                            val imm =
                                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v.windowToken, 0)
                            progress!!.visibility = View.VISIBLE
                            txtclose.visibility = View.GONE

                            // on below line we are getting
                            // amount that is entered by user.
                            val samount = grand.text.toString()


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
                                checkout.open(this@selection2, `object`)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        myDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        myDialog!!.show()

                    } else {
                        Toast.makeText(
                            this@selection2,
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

    override fun onPaymentSuccess(s: String) {
        status(v = null)
    }

    override fun onPaymentError(i: Int, s: String) {
        statusError(v = null)
    }

    private fun saveCourse(courseName: String, courseDescription: String, courseDuration: String) {

        val data = Intent()
        data.putExtra(EXTRA_COURSE_NAME, courseName)
        data.putExtra(EXTRA_DESCRIPTION, courseDescription)
        data.putExtra(EXTRA_DURATION, courseDuration)
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }
        setResult(RESULT_OK, data)
        val model = CourseModal(courseName, courseDescription, courseDuration)
        viewmodal.insert(model)

    }

//    fun checkPincode(pincode: String): Boolean {
//        val remoteConfig = FirebaseRemoteConfig.getInstance()
//
//        val configSettings = FirebaseRemoteConfigSettings.Builder()
//            .setMinimumFetchIntervalInSeconds(3600)
//            .build()
//        remoteConfig.setConfigSettingsAsync(configSettings)
//
////        val defaultValues = HashMap<String, Any>()
////        defaultValues["pincode"] = email!!.text.toString()
////        remoteConfig.setDefaultsAsync(defaultValues)
//
//        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val pincode1 = remoteConfig.getString("pincode").split(",")
//
//                if (pincode1.contains(pincode)) {
//                    super@selection2.onBackPressed()
//                } else {
//                    // The pincode is invalid
//                }
//            } else {
//                // Failed to fetch or activate remote config
//            }
//        }
//
//        // Return false by default, since we don't know if the pincode is valid yet
//        return false
//    }


    companion object {
        const val EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID"
        const val EXTRA_COURSE_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_NAME"
        const val EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION"
        const val EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DURATION"
//        private const val ADD_COURSE_REQUEST = 1
    }
}