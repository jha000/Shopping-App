package com.fresh.milkaggregatorapplication

import android.content.Intent
import android.os.Bundle
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
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val i = Intent(this@MainActivity, dashboard::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        val imageList = ArrayList<SlideModel>()
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

        val inputmobile = findViewById<EditText>(R.id.editTextPhone3)
        val buttongetotp = findViewById<Button>(R.id.button2)

//        inputmobile.setOnClickListener{
//
//                buttongetotp.alpha = 1f
//
//        }


        val progressBar = findViewById<ProgressBar>(R.id.progressbarforotp)


        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Referral count")
        val top5Reference = database.getReference("top 5")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val valueMap = dataSnapshot.value as Map<String, Long>
                    val sortedValueMap =
                        valueMap.toList().sortedByDescending { (_, value) -> value }

                    for ((index, entry) in sortedValueMap.withIndex()) {
                        top5Reference.child(entry.first).setValue((index + 1).toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })





        buttongetotp.setOnClickListener { v ->
            val imm =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)

            if (!inputmobile.text.toString().trim { it <= ' ' }.isEmpty()) {
                if (inputmobile.text.toString().trim { it <= ' ' }.length == 10) {


                    progressBar.visibility = View.VISIBLE
                    buttongetotp.visibility = View.INVISIBLE
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputmobile.text.toString(),
                        60,
                        TimeUnit.SECONDS,
                        this@MainActivity,
                        object : OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                progressBar.visibility = View.GONE
                                buttongetotp.visibility = View.VISIBLE
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                progressBar.visibility = View.GONE
                                buttongetotp.visibility = View.VISIBLE
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onCodeSent(
                                verficationid: String,
                                forceResendingToken: ForceResendingToken
                            ) {
                                progressBar.visibility = View.GONE
                                buttongetotp.visibility = View.VISIBLE
                                val intent = Intent(applicationContext, ReceiveOtp::class.java)
                                val name = inputmobile.text.toString()
                                intent.putExtra(EXTRA_NAME, name)
                                intent.putExtra("mobile", inputmobile.text.toString())
                                intent.putExtra("verfication", verficationid)
                                startActivity(intent)
                            }
                        }
                    )
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter correct number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Enter Mobile number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
    }
}