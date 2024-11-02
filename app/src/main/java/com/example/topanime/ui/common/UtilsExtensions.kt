package com.example.topanime.ui.common


import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide

const val MIN_PASSWORD_LENGTH = 5
const val ERROR_DEFAULT = "ERROR_DEFAULT"
const val INVALID_EMAIL = "INVALID_EMAIL"
const val INVALID_PASSWORD = "INVALID_PASSWORD"

fun ImageView.loadUrl(url : String){
    Glide.with(context).load(url).into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot : Boolean = true) : View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Context.toast(message : String?, duration : Int = Toast.LENGTH_SHORT){
    if(message == null)
        return
    Toast.makeText(this, message, duration).show()
}

fun View.hideKeyBoard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

var View.visible : Boolean
    get() = visibility == View.VISIBLE
    set(value){
        visibility = if(value) View.VISIBLE else View.GONE
    }

fun String.isValidEmail(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches() || this.isEmpty()

fun String.isValidPassword(): Boolean =
    this.length >= MIN_PASSWORD_LENGTH || this.isEmpty()

val Context.app : MyApp
    get() = applicationContext as MyApp

fun String.getEmailName(): String = this.substringBefore("@")
