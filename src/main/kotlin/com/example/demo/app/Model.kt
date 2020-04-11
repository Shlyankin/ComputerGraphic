package com.example.demo.app

import java.io.File

data class Model(var points: ArrayList<Array<Double>>, var polygons: ArrayList<Array<Int>>? = null) {
    fun get3dPoint(i : Int) = points[i]
    fun get2dPoint(i : Int) = Pair(points[i][0], points[i][1])
    fun get2dPoints() : ArrayList<Array<Double>> {
        var points2d = ArrayList<Array<Double>>()
        points.forEach {
            points2d.add(arrayOf(it[0], it[1]))
        }
        return points2d
    }

    companion object {

        fun fromFile(file : File) : Model {
            val modelList = ArrayList<Array<Double>>()
            val polygons = ArrayList<Array<Int>>()
            file.forEachLine {
                if(it.substringBefore(' ') == "v") {
                    val xyz = it.substring(2).split(' ')
                    modelList.add(arrayOf(xyz[0].toDouble(), xyz[1].toDouble(), xyz[2].toDouble()))
                }
                if(it.substringBefore(' ') == "f") {
                    val data = it.substring(2).split(' ')
                    val polygon = arrayOf(
                            data[0].split('/')[0].toInt() - 1,
                            data[1].split('/')[0].toInt() - 1,
                            data[2].split('/')[0].toInt() - 1)
                    polygons.add(polygon)
                }
            }
            return Model(modelList, polygons)
        }

    }
}