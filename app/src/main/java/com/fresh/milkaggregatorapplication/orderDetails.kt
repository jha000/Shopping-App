package com.fresh.milkaggregatorapplication


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.roundToInt

class orderDetails : AppCompatActivity() {

    private var tvFeedback: TextView? = null
    private var name: TextView? = null
    private var phone: TextView? = null
    private var address: TextView? = null
    private var call: TextView? = null
    private var rbStars: RatingBar? = null
    private var sent: Button? = null
    private var help: ImageView? = null
    private var pImage: ImageView? = null
    private var product: TextView? = null
    private var back: ImageView? = null
    private var namex1: TextView? = null
    private var priceqty: TextView? = null
    private var qty: TextView? = null
    private var price: TextView? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    var input: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        pImage = findViewById(R.id.pImage)
        namex1 = findViewById(R.id.namex1)
        priceqty = findViewById(R.id.priceqty)
        qty = findViewById(R.id.qty)
        price = findViewById(R.id.price)
        product = findViewById(R.id.ProductName)

        product!!.text = intent.getStringExtra("title");
        qty!!.text = intent.getStringExtra("qty");
        price!!.text = intent.getStringExtra("price");
        namex1!!.text =product!!.text


        val samount = price!!.text.toString()

        val total: Int = Integer.valueOf(price!!.text.toString())
        price!!.text= (total + 1.80).toString()

        val sqty = qty!!.text.toString()
        val amount = (samount.toFloat() / sqty.toFloat()).roundToInt()

        priceqty!!.text = amount.toString()


        val pin3 = "FreshYard Country Eggs - Pack Of 6"
        val pin4 = "FreshYard Poultry Eggs - Pack Of 6"

        if (product!!.text==pin3)
        {
            pImage!!.setImageResource(R.drawable.country)
        }
        else if (product!!.text==pin4)
        {
            pImage!!.setImageResource(R.drawable.protein)
        }

        input = findViewById(R.id.input)
        tvFeedback = findViewById(R.id.tvFeedback)
        rbStars = findViewById(R.id.rbStars)
        sent = findViewById(R.id.btnSend)
        name = findViewById(R.id.namex)
        phone = findViewById(R.id.phonex)
        address = findViewById(R.id.addressx)
        back = findViewById(R.id.backm)

        back!!.setOnClickListener(View.OnClickListener {
            super@orderDetails.onBackPressed()
        })

        help = findViewById(R.id.help)
        call = findViewById(R.id.call)

        call!!.setOnClickListener(View.OnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:6300686049")
            startActivity(callIntent)
        })

        help!!.setOnClickListener(View.OnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:6300686049")
            startActivity(callIntent)
        })

        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val value = sharedPreferences.getString("value", "")
        name!!.text = value
        val value1 = sharedPreferences.getString("x", "")
        phone!!.text = value1
        val value2 = sharedPreferences.getString("y", "")
        address!!.text = value2
        rbStars!!.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (rating == 0f) {
                    tvFeedback!!.text = "Rate your overall experince"

                }
                else if (rating == 0.5f || rating == 1f) {
                    tvFeedback!!.text = "Very Dissatisfied"

                }
                else if (  rating.toDouble() == 1.5 || rating == 2f ) {
                    tvFeedback!!.text = "Dissatisfied"
                } else if ( rating == 3f || rating.toDouble() == 2.5) {
                    tvFeedback!!.text = "OK"
                } else if (rating == 4f || rating.toDouble() == 3.5) {
                    tvFeedback!!.text = "Satisfied"
                } else if (rating == 5f || rating.toDouble() == 4.5) {
                    tvFeedback!!.text = "Very Satisfied"
                }
            }
        sent!!.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@orderDetails, "Feedback Sent", Toast.LENGTH_SHORT).show()
            val getNo = phone!!.text.toString()
            val getName = name!!.text.toString()
            val getRating = tvFeedback!!.text.toString()
            val getInput = input!!.text.toString()
            val hashMap = HashMap<String, Any>()
            hashMap["Mobile"] = getNo
            hashMap["Name"] = getName
            hashMap["rating"] = getRating
            hashMap["input"] = getInput
            firebaseDatabase = FirebaseDatabase.getInstance()
            databaseReference = firebaseDatabase!!.reference
            val userId = databaseReference!!.push().key
            databaseReference!!.child("Feedbacks")
                .child(userId!!)
                .setValue(hashMap)
        })
    }
}