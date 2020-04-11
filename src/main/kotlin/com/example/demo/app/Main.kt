package com.example.demo.app

import java.io.File
import java.io.FileNotFoundException

fun main(args : Array<String>) {
    var model : Model
    try {
        val file = File("C:\\Users\\user\\IdeaProjects\\ComputerGraphic1\\src\\main\\kotlin\\com\\example\\demo\\app\\files\\file.obj")
        model = Model.fromFile(file)
    } catch (e : FileNotFoundException) {
        println(e.message)
    }
}