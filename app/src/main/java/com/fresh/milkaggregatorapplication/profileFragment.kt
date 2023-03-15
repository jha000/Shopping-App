package com.fresh.milkaggregatorapplication


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File


class profileFragment : Fragment() {

    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val myDialog = BottomSheetDialog((activity)!!)
        val bphoneDialog = BottomSheetDialog((activity)!!)
        val bmailDialog = BottomSheetDialog((activity)!!)
        val baddressDialog = BottomSheetDialog((activity)!!)
        val upiDialog = BottomSheetDialog((activity)!!)
        val fab = view.findViewById<View>(R.id.floatingActionButton) as ImageView
        val user = view.findViewById<View>(R.id.user) as TextView
        val name = view.findViewById<View>(R.id.name) as TextView
        val phone = view.findViewById<View>(R.id.number) as TextView
        val email = view.findViewById<View>(R.id.email) as TextView
        val address = view.findViewById<View>(R.id.address) as TextView
        val nameEdit = view.findViewById<View>(R.id.nameEdit) as ImageView
        val phoneEdit = view.findViewById<View>(R.id.phoneEdit) as ImageView
        val emailEdit = view.findViewById<View>(R.id.emailEdit) as ImageView
        val addEdit = view.findViewById<View>(R.id.addEdit) as ImageView
        val addEdit1 = view.findViewById<View>(R.id.addEdit1) as ImageView
        val upi = view.findViewById<View>(R.id.upi) as TextView
        val cover = view?.findViewById<View>(R.id.profile_image) as ImageView

//        val animation = AnimationUtils.loadAnimation(context, R.anim.shake)
//        view.startAnimation(animation)

        nameEdit.setOnClickListener {
            myDialog.setContentView(R.layout.bottom)
            val one = myDialog.findViewById<View>(R.id.one) as EditText
            val b1 = myDialog.findViewById<View>(R.id.b1) as Button
            one.requestFocus()
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            b1.setOnClickListener { view ->

                val x = one.text.toString()
                name.text = x
                user.text = x
                val value = one.text.toString().trim { it <= ' ' }
                val sharedPref = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("value", value)
                editor.apply()
                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                myDialog.dismiss()
            }
            myDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
        }
        phoneEdit.setOnClickListener {
            bphoneDialog.setContentView(R.layout.bphone)
            val too = bphoneDialog.findViewById<View>(R.id.too) as EditText
            val b2 = bphoneDialog.findViewById<View>(R.id.b2) as Button
            too.requestFocus()
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            b2.setOnClickListener { view ->
                val x = too.text.toString()
                phone.text = x
                val value = too.text.toString().trim { it <= ' ' }
                val sharedPref =
                    requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("x", value)
                editor.apply()
                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                bphoneDialog.dismiss()
            }
            bphoneDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bphoneDialog.show()
        }
        emailEdit.setOnClickListener {
            bmailDialog.setContentView(R.layout.bemail)
            val three = bmailDialog.findViewById<View>(R.id.three) as EditText
            val b3 = bmailDialog.findViewById<View>(R.id.b3) as Button
            three.requestFocus()
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            b3.setOnClickListener { view ->
                val x = three.text.toString()
                email.text = x
                val value = three.text.toString().trim { it <= ' ' }
                val sharedPref =
                    requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("value2", value)
                editor.apply()
                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                bmailDialog.dismiss()
            }
            bmailDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bmailDialog.show()
        }
        addEdit.setOnClickListener {
            val four: EditText?
            val b4: Button?
            baddressDialog.setContentView(R.layout.baddress)
            four = baddressDialog.findViewById<View>(R.id.four) as EditText
            b4 = baddressDialog.findViewById<View>(R.id.b4) as Button
            four.requestFocus()
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            b4.setOnClickListener(View.OnClickListener { view ->
                val x = four.text.toString()
                address.text = x
                val value = four.text.toString().trim { it <= ' ' }
                val sharedPref =
                    requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("y", value)
                editor.apply()
                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                baddressDialog.dismiss()
            })
            baddressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            baddressDialog.show()
        }

        addEdit1.setOnClickListener {
            val four: EditText?
            val b4: Button?
            upiDialog.setContentView(R.layout.upi)
            four = upiDialog.findViewById<View>(R.id.four) as EditText
            b4 = upiDialog.findViewById<View>(R.id.b4) as Button
            four.requestFocus()
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            b4.setOnClickListener(View.OnClickListener { view ->
                val x = four.text.toString()
                val y = phone.text.toString()

                upi.text = x

                val value = four.text.toString().trim { it <= ' ' }
                val sharedPref =
                    requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("upi", value)
                editor.apply()

                firebaseDatabase = FirebaseDatabase.getInstance()
                databaseReference = firebaseDatabase!!.reference
                databaseReference!!.child("UPI Id's")
                    .child(y)
                    .setValue(x)

                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                upiDialog.dismiss()
            })
            upiDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            upiDialog.show()
        }

        val sharedPreferences =
            requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val value = sharedPreferences.getString("value", "")
        name.text = value
        val value1 = sharedPreferences.getString("x", "")
        phone.text = value1
        val value2 = sharedPreferences.getString("value2", "")
        email.text = value2
        val value3 = sharedPreferences.getString("y", "")
        address.text = value3
        val x = name.text.toString()
        user.text = x
        val upiget = sharedPreferences.getString("upi", "")
        upi.text = upiget

        val base64: String? = sharedPreferences.getString("image", null)
        if (base64 != null && base64.isNotEmpty()) {
            val byteArray = Base64.decode(base64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            cover.setImageBitmap(bitmap)
        }




        val logout = view.findViewById<Button>(R.id.logout)
        val userf = FirebaseAuth.getInstance().currentUser
        if (userf != null) {
            // User is signed in
            logout.text = "LOGOUT"
        }
        logout.setOnClickListener {
            if (userf != null) {
                val user = FirebaseAuth.getInstance()
                user.signOut()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Logout Successful",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        fab.setOnClickListener {
            ImagePicker.with(this@profileFragment)
                .crop()
                .compress(1024)
                .maxResultSize(
                    1080,
                    1080
                )
                .start()
        }



        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImg = data!!.data
        val cover = view?.findViewById<View>(R.id.profile_image) as ImageView
        cover.setImageURI(selectedImg)

        val bitmap = (cover.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

        val sharedPreferences = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("image", base64)
        editor.apply()
    }
}