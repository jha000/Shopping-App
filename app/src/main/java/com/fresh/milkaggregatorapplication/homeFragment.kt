package com.fresh.milkaggregatorapplication

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.*


class homeFragment : Fragment() {
    lateinit var intent: Intent
    lateinit var intent2: Intent
    lateinit var card1: CardView
    lateinit var card2: CardView
    lateinit var rImage: ImageView
    lateinit var content: TextView
    lateinit var no: TextView
    lateinit var amount1: TextView
    lateinit var amount2: TextView
    lateinit var rank: TextView
    lateinit var address: TextView
    lateinit var city: TextView
    lateinit var set: TextView

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 100

    lateinit var refer: TextView
    lateinit var cart_badge: TextView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var viewmodal: ViewModal
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        intent = Intent(activity, selection::class.java)
        intent2 = Intent(activity, selection2::class.java)
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container)
        shimmerFrameLayout.startShimmer()
        rImage = view.findViewById(R.id.rImage)
        content = view.findViewById(R.id.contentTemplete)
        card1 = view.findViewById(R.id.card11)
        card2 = view.findViewById(R.id.card111)
        refer = view.findViewById(R.id.refer)
        rank = view.findViewById(R.id.rank)
        no = view.findViewById(R.id.no)
        amount1 = view.findViewById(R.id.amount1)
        amount2 = view.findViewById(R.id.amount2)
        address = view.findViewById(R.id.address)
        city = view.findViewById(R.id.city)

        val cart1 = view.findViewById<ImageView>(R.id.cart)

        cart1.setOnClickListener{
            val i= Intent(activity, cart::class.java)
            startActivity(i)
        }

        cart_badge = view.findViewById(R.id.cart_badge)

        cart_badge.setOnClickListener{
            val i= Intent(activity, cart::class.java)
            startActivity(i)
        }


//
//        val animation = AnimationUtils.loadAnimation(context, R.anim.shake)
//        view.startAnimation(animation)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireActivity())

        getLastLocation()

        val strike1 = view.findViewById<TextView>(R.id.strike1)
        strike1.paintFlags = strike1.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        val strike2 = view.findViewById<TextView>(R.id.strike2)
        strike2.paintFlags = strike2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val price1 = remoteConfig.getString("price1")
                val price2 = remoteConfig.getString("price2")
                val dummy1 = remoteConfig.getString("dummy1")
                val dummy2 = remoteConfig.getString("dummy2")

                amount1.text=price1
                amount2.text=price2
                strike1.text=dummy1
                strike2.text=dummy2


            } else {
                // Failed to fetch or activate remote config
            }
        }

        val sharedPreferences =
            requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val value2 = sharedPreferences.getString("saveCode", null)
        no.text = value2
        val cartvalue = sharedPreferences.getString("items", null)
        cart_badge.text = cartvalue

        refer.setOnClickListener(View.OnClickListener { view1: View? ->
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val shareMessage1 = no.text.toString()
                var shareMessage = "\n is your referral code\n\n"
                shareMessage = """
                ${shareMessage1}${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Fresh Yard")
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (ignored: Exception) {
            }
        })
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.reference

        val getImage = databaseReference.child("Image")
        getImage.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(
                    dataSnapshot: DataSnapshot
                ) {
                    val link = dataSnapshot.getValue(
                        String::class.java
                    )
                    Picasso.get().load(link).into(rImage)
                    shimmerFrameLayout.hideShimmer()
                }

                override fun onCancelled(
                    databaseError: DatabaseError
                ) {
                }
            })
        val getText = databaseReference.child("Content")
        getText.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(
                    dataSnapshot: DataSnapshot
                ) {
                    val text = dataSnapshot.getValue(
                        String::class.java
                    )
                    content.setText(text)
                    shimmerFrameLayout.hideShimmer()
                }

                override fun onCancelled(
                    databaseError: DatabaseError
                ) {
                }
            })

        val keyName = no.text.toString()
        val databaseRef = firebaseDatabase.reference.child("top 5").child(keyName);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val text = dataSnapshot.getValue(
                        String::class.java
                    )
                    rank.setText(text)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        card1.setOnClickListener(View.OnClickListener {

            val valuexx = amount1.text.toString().trim { it <= ' ' }
            val sharedPref = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("amount1", valuexx)
            editor.apply()

            startActivity(intent)

        })
        card2.setOnClickListener(View.OnClickListener {

            val valuexxx = amount2.text.toString().trim { it <= ' ' }
            val sharedPref = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("amount2", valuexxx)
            editor.apply()

            startActivity(intent2)
        })


        return view
    }



    companion object {
        private const val EDIT_COURSE_REQUEST = 2
        const val ADD_COURSE_REQUEST = 1
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                            val addresses: List<Address>? =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)

                            address.text = addresses!![0].subLocality + ", " + addresses[0].locality + ", " + addresses[0].adminArea

                            city.text =  addresses[0].postalCode

                            val value = city.text.toString().trim { it <= ' ' }
                            val sharedPref = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("pincode", value)
                            editor.apply()

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
        } else {
            askPermission()
        }
    }
//
    private fun askPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }


    override fun onResume() {
        super.onResume()
        getLastLocation()
        val sharedPreferences =
            requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val cartvalue = sharedPreferences.getString("items", null)
        cart_badge.text = cartvalue

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()

            } else {
                Toast.makeText(
                    requireActivity(),
                    "Please provide the required permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}