package com.example.demo.app.graphic

class Pixel(var rgb: Int) {
    var opacity: Int = (rgb and 0xFF000000.toInt()) shr(24)
    var red: Int = (rgb and 0xFF0000) shr(16)
    var green: Int = (rgb and 0xFF00) shr(8)
    var blue: Int = rgb and 0xFF

    companion object {
        fun grayPix(color: Int): Pixel? {
            return Pixel(color, color, color, 255)
        }
    }

    fun grayPix(color: Double): Pixel? {
        return grayPix(color.toInt())
    }

    constructor(red: Int, green: Int, blue: Int, opacity: Int) : this(((opacity shl(24)) and 0xFF000000.toInt()) or ((red shl(16)) and 0xFF0000) or (green shl(8) and 0xFF00) or (blue and 0xFF))

    fun Pixel(rgb: Int) {
        this.rgb = rgb
    }

    fun Pixel(pixel: Pixel) {
        rgb = pixel.rgb
    }
}