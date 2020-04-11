package com.example.demo.app.graphic

import tornadofx.*
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class Util {
    companion object {
        var colorWhite = getColor(255, 255, 255);

        fun getColor(r: Int, g: Int, b: Int) : Int {
            return r*256*256 + g*256 + b
        }

        fun upgradeFourthDrawLine(image: BufferedImage, point1: Array<Int>, point2: Array<Int>) {

        }

        private fun calculateCoord(point: Array<Double>, point0: Array<Double>, point1: Array<Double>, point2: Array<Double>) : Double {
            return ((point[1] - point2[1])*(point1[0] - point2[0]) - (point[0] - point2[0])*(point1[1] - point2[1])).toDouble() /
                    //------------------------------------------------------------------------------------------------------------
                    ((point0[1] - point2[1])*(point1[0] - point2[0]) - (point0[0] - point2[0])*(point1[1]-point2[1])).toDouble()
        }

        fun decartToBaricentric(point: Array<Double>, point0: Array<Double>, point1: Array<Double>, point2: Array<Double>) : Array<Double> {
            return arrayOf(
                    calculateCoord(point, point0, point1, point2),
                    calculateCoord(point, point1, point2, point0),
                    calculateCoord(point, point2, point0, point1)

            )
        }

        fun inTriangle(point: Array<Double>, point0: Array<Double>, point1: Array<Double>, point2: Array<Double>) : Boolean {
            val baricentric = decartToBaricentric(point, point0, point1, point2)
            return minOf(baricentric[0], baricentric[1], baricentric[2]) >= 0
        }

        fun fillTriangle(image: BufferedImage, point0: Array<Double>, point1: Array<Double>, point2: Array<Double>, color : Int = colorWhite) {
            val minx = minOf(point0[0], point1[0], point2[0]).toInt()
            val maxx = maxOf(point0[0], point1[0], point2[0]).toInt()
            val miny = minOf(point0[1], point1[1], point2[1]).toInt()
            val maxy = maxOf(point0[1], point1[1], point2[1]).toInt()
            for(i in minx..maxx) {
                for(j in miny..maxy) {
                    if(inTriangle(arrayOf(i.toDouble(), j.toDouble()), point0, point1, point2)) {
                        image.setRGB(i, j, color)
                    }
                }
            }
        }

        fun absVector(point0: Array<Double>, point1: Array<Double>, point2: Array<Double>) : Array<Double> {
            val d1 = arrayOf(point0[0] - point1[0], point0[1] - point1[1], point0[2] - point1[2])
            val d2 = arrayOf(point1[0] - point2[0], point1[1] - point2[1], point1[2] - point2[2])
            val diff = arrayOf(
                    (1 * d1[1] * d2[2] - 1 * d2[1] * d1[2]),
                    (1 * d2[0] * d1[2] - 1 * d1[0] * d2[2]),
                    (1 * d1[0] * d2[1] - 1 * d1[1] * d2[0])
            )
            val length = sqrt(diff[0].pow(2) + diff[1].pow(2) + diff[2].pow(2))
            return arrayOf(diff[0] / length, diff[1] / length, diff[2] / length)
        }

        // Брезенхема
        fun fourthDrawLine(image: BufferedImage, point1: Array<Int>, point2: Array<Int>, color : Int = colorWhite) {
            var point1 = point1
            var point2 = point2
            var steep = false
            if(abs(point1[0]-point2[0]) < abs(point1[1]-point2[1])) {
                point1 = arrayOf(point1[1], point1[0])
                point2 = arrayOf(point2[1], point2[0])
                steep = true
            }
            if(point1[0] > point2[0]) {
                val tmp = arrayOf(point1[0], point1[1])
                point1 = arrayOf(point2[0], point2[1])
                point2 = arrayOf(tmp[0], tmp[1])
            }
            val dx = point2[0] - point1[0]
            val dy = point2[1] - point1[1]
            val derror = abs(dy.toDouble() / dx.toDouble()  )
            var error = 0.0
            var y = point1[1]

            for(x in point1[0]..point2[0]) {
                if(steep)
                    image.setRGB(y, x, color)
                else
                    image.setRGB(x, y, color)
                error += derror
                if(error > 0.5) {
                    y += if(point2[1] > point1[1]) 1 else -1
                    error -= 1.0
                }
            }
        }

        fun thirdSimpleDrawLine(image: BufferedImage, point1: Array<Int>, point2: Array<Int>, color : Int = colorWhite) {
            var point1 = point1
            var point2 = point2
            var steep = false
            if(abs(point1[0]-point2[0]) < abs(point1[1]-point2[1])) {
                point1 = arrayOf(point1[1], point1[0])
                point2 = arrayOf(point2[1], point2[0])
                steep = true
            }
            if(point1[0] > point2[0]) {
                val tmp = arrayOf(point1[0], point1[1])
                point1 = arrayOf(point2[0], point2[1])
                point2 = arrayOf(tmp[0], tmp[1])
            }
            for (x in point1[0]..point2[0]) {

                val t = (x - point1[0]).toDouble() / (point2[0] - point1[0]).toDouble()
                val y = (point1[1] * (1.0 - t) + point2[1] * t).toInt()
                if(steep)
                    image.setRGB(y, x, color)
                else
                    image.setRGB(x, y, color)

            }
        }

        fun secondSimpleDrawLine(image: BufferedImage, point1: Array<Int>, point2: Array<Int>, color : Int = colorWhite) {
            for (x in point1[0]..point2[0]) {
                val t = (x - point1[0]).toDouble() / (point2[0] - point1[0]).toDouble()
                val y = (point1[1] * (1.0 - t) + point2[1] * t).toInt()
                image.setRGB(x, y, color)
            }
        }

        fun firstSimpleDrawLine(image: BufferedImage, point1: Array<Int>, point2: Array<Int>, color : Int = colorWhite) {
            var t = 0.0
            while(t < 1.0) {
                image.setRGB((point1[0]*(1.0 - t) + point2[0] * t).toInt(), (point1[1]*(1.0 - t) + point2[1]*t).toInt(), color)
                t += 0.01
            }
        }
    }

}