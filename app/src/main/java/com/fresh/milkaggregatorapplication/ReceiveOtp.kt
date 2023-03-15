package com.fresh.milkaggregatorapplication


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ReceiveOtp : AppCompatActivity() {

    private var inputotp: EditText? = null
    private var verificationid: String? = null
    var text: TextView? = null
    var rcode: TextView? = null
    lateinit var countdown: TextView
    lateinit var linearLayout2: LinearLayout
    var userno: TextView? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_otp)
        text = findViewById(R.id.notaken)
        countdown = findViewById(R.id.countdown)
        userno = findViewById(R.id.notaken)
        inputotp = findViewById(R.id.inputotp)
        rcode = findViewById(R.id.code)
        linearLayout2 = findViewById(R.id.linearLayout2)

        inputotp!!.requestFocus()

        val enterCode = findViewById<EditText>(R.id.referinput)
        val message = findViewById<TextView>(R.id.message)
        val resend = findViewById<TextView>(R.id.textresendotp)



        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")

                val sec = millisUntilFinished / 1000 % 60
                countdown.setText("00:" + f.format(sec))
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                countdown.visibility = View.GONE
                linearLayout2.visibility = View.VISIBLE
            }
        }.start()


        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(
            SlideModel(
                R.drawable.greenbac1,
                "Wake Up to the Power of Protein with Our Fresh Eggs and Dairy"
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.greenbac1,
                "Fuel Your Body and Mind with the Best Eggs & Dairy Products on the Market"
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.greenbac1,
                "From Our Farms to Your Table - Fresh Eggs & Dairy for a Better Breakfast"
            )
        )


        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

        val intent = intent
        val new_name = intent.getStringExtra(MainActivity.EXTRA_NAME)
        text!!.setText(new_name)

        val progressBar = findViewById<ProgressBar>(R.id.Progressbarverifyotp)
        val buttonverify = findViewById<Button>(R.id.buttonverify)


        verificationid = getIntent().getStringExtra("verfication")
        buttonverify.setOnClickListener { v ->
            val imm =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)

            if (enterCode!!.getText().toString().trim { it <= ' ' }.isNotEmpty()) {
                val code = enterCode.text.toString()
                val firebaseDatabase = FirebaseDatabase.getInstance()
                val databaseReference = firebaseDatabase.reference
                val getCodes = databaseReference.child("Referral code")

                getCodes.addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(
                            dataSnapshot: DataSnapshot
                        ) {
                            if (dataSnapshot.child(code).exists()) {

                                databaseReference.child("Referral count")
                                    .child(code)
                                    .setValue(ServerValue.increment(1))

                                message.text = "*Referral code applied successfully"
                                message.setTextColor(Color.rgb(44, 133, 107))
                                message.visibility = View.VISIBLE

                                val value = message.text.toString().trim { it <= ' ' }
                                val sharedPref = getSharedPreferences("myKey", Context.MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                editor.putString("msg", value)
                                editor.apply()

                                if (!inputotp!!.getText().toString().trim { it <= ' ' }.isEmpty()) {
                                    val code = inputotp!!.getText().toString()
                                    if (verificationid != null) {
                                        progressBar.visibility = View.VISIBLE
                                        buttonverify.visibility = View.GONE
                                        val phoneAuthCredential = PhoneAuthProvider.getCredential(
                                            verificationid!!, code
                                        )
                                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                            .addOnCompleteListener { task ->
                                                progressBar.visibility = View.GONE
                                                buttonverify.visibility = View.VISIBLE
                                                if (task.isSuccessful) {

                                                    if(rcode!!.text.isEmpty()){
                                                        val r = Random()
                                                        val numbers = 100000 + (r.nextFloat() * 899900).toInt()

                                                        val value = numbers.toString().trim { it <= ' ' }
                                                        val value2 = userno!!.text.toString().trim { it <= ' ' }
                                                        val sharedPref =
                                                            getSharedPreferences("myKey", AppCompatActivity.MODE_PRIVATE)
                                                        val editor = sharedPref.edit()
                                                        editor.putString("saveCode", value)
                                                        editor.putString("x", value2)
                                                        editor.apply()

                                                        val getNo = userno!!.text.toString()
                                                        val getCode = numbers.toString()
                                                        val hashMap = java.util.HashMap<String, Any>()
                                                        hashMap["number"] = getNo
                                                        hashMap["code"] = getCode
                                                        val firebaseDatabase = FirebaseDatabase.getInstance()
                                                        val databaseReference = firebaseDatabase.reference
                                                        databaseReference.child("Users")
                                                            .child(getNo)
                                                            .setValue(hashMap)

                                                        databaseReference.child("Referral code")
                                                            .child(getCode)
                                                            .setValue(getCode)
                                                    }



                                                    val intent = Intent(applicationContext, dashboard::class.java)
                                                    intent.flags =
                                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(intent)
                                                    Toast.makeText(this@ReceiveOtp, "otp received", Toast.LENGTH_SHORT)
                                                        .show()
                                                } else {
                                                    Toast.makeText(
                                                        this@ReceiveOtp,
                                                        "Enter the Correct otp",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    }
                                } else {
                                    Toast.makeText(this@ReceiveOtp, "Please enter the otp", Toast.LENGTH_SHORT).show()
                                }


                            } else {
                                message.text = "*Invalid Referral Code"
                                message.visibility = View.VISIBLE
                            }
                        }

                        override fun onCancelled(
                            databaseError: DatabaseError
                        ) {
                        }
                    })
            }


          else{
                if (!inputotp!!.getText().toString().trim { it <= ' ' }.isEmpty()) {
                    val code = inputotp!!.getText().toString()
                    if (verificationid != null) {
                        progressBar.visibility = View.VISIBLE
                        buttonverify.visibility = View.GONE
                        val phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationid!!, code
                        )
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener { task ->
                                progressBar.visibility = View.GONE
                                buttonverify.visibility = View.VISIBLE
                                if (task.isSuccessful) {

                                    if(rcode!!.text.isEmpty()){
                                        val r = Random()
                                        val numbers = 100000 + (r.nextFloat() * 899900).toInt()

                                        val value = numbers.toString().trim { it <= ' ' }
                                        val value2 = userno!!.text.toString().trim { it <= ' ' }
                                        val sharedPref =
                                            getSharedPreferences("myKey", AppCompatActivity.MODE_PRIVATE)
                                        val editor = sharedPref.edit()
                                        editor.putString("saveCode", value)
                                        editor.putString("x", value2)
                                        editor.apply()

                                        val getNo = userno!!.text.toString()
                                        val getCode = numbers.toString()
                                        val hashMap = java.util.HashMap<String, Any>()
                                        hashMap["number"] = getNo
                                        hashMap["code"] = getCode
                                        val firebaseDatabase = FirebaseDatabase.getInstance()
                                        val databaseReference = firebaseDatabase.reference
                                        databaseReference.child("Users")
                                            .child(getNo)
                                            .setValue(hashMap)

                                        databaseReference.child("Referral code")
                                            .child(getCode)
                                            .setValue(getCode)
                                    }


                                    val intent = Intent(applicationContext, dashboard::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    Toast.makeText(this@ReceiveOtp, "otp received", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(
                                        this@ReceiveOtp,
                                        "Enter the Correct otp",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this@ReceiveOtp, "Please enter the otp", Toast.LENGTH_SHORT).show()
                }

                findViewById<View>(R.id.textresendotp).setOnClickListener {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        this@ReceiveOtp,
                        object : OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}
                            override fun onVerificationFailed(e: FirebaseException) {
                                Toast.makeText(this@ReceiveOtp, e.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onCodeSent(
                                newVerification: String,
                                forceResendingToken: ForceResendingToken
                            ) {
                                verificationid = newVerification
                                Toast.makeText(this@ReceiveOtp, "OTP sent again", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                }
            }


        }

        val sharedPreferences = getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val value2 = sharedPreferences.getString("msg", "")
        message.text = value2
        val value5 = sharedPreferences.getString("saveCode", null)
        rcode!!.text = value5


        if (message.text == "*Referral code applied successfully") {
            enterCode.visibility = View.GONE
            message.setTextColor(Color.rgb(44, 133, 107))
            message.visibility = View.VISIBLE
        }
    }



    companion object {
        private const val TAG = "ReceiveOtp"
    }
}