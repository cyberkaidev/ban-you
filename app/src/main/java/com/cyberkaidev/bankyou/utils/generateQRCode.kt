package com.cyberkaidev.bankyou.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

fun generateQRCode(value: String, color: Int, background: Int): Bitmap {
    val matrix = QRCodeWriter().encode(value, BarcodeFormat.QR_CODE, 300, 300)
    val width = matrix.width
    val height = matrix.height

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (yAxis in 0 until height) {
        for (xAxis in 0 until width) {
            bitmap.setPixel(
                xAxis,
                yAxis,
                if (matrix.get(xAxis, yAxis)) color else background
            )
        }
    }

    return bitmap
}