package com.example.mobiletest2.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

fun makeSpannableString(text:String,startIndex:Int,endIndex:Int): SpannableString {
    val spannable = SpannableString(text)
    spannable.setSpan(
        ForegroundColorSpan(Color.WHITE),
        startIndex,
        endIndex,
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return spannable
}