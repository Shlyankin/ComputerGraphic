package com.example.demo.view

import com.example.demo.app.Model
import com.example.demo.app.graphic.Util
import javafx.embed.swing.SwingFXUtils
import tornadofx.*
import java.awt.image.BufferedImage
import java.io.File
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainView : View("Computer Graphic") {
    override val root = scrollpane() {
        vbox {
            text("Кролик") {}
            hbox {
                imageview() {
                    var im = BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB)
                    val file = File("C:\\Users\\user\\IdeaProjects\\ComputerGraphic1\\src\\main\\kotlin\\com\\example\\demo\\app\\files\\file.obj")
                    var model = Model.fromFile(file)
                    var points2d = model.get2dPoints()
                    for (i in 0 until points2d.size)
                        points2d[i] = arrayOf(points2d[i][0] * 2500 + 250, -points2d[i][1] * 2500 + 250)
                    for (point in points2d)
                        im.setRGB(point[0].toInt(), point[1].toInt(), 255 * 256 * 256 + 256 * 255 + 255)
                    im = im.getSubimage(0, 0, 500, 250)
                    image = SwingFXUtils.toFXImage(im, null)
                }
            }
            text("Простой алгоритм") {}
            hbox {
                imageview() {
                    var im = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)
                    for (i in 0 until 13) {
                        val point1 = arrayOf(100, 100)
                        val point2 = arrayOf((100 + cos(i * 2 * PI / 13) * 95).toInt(), (100 + sin(i * 2 * PI / 13) * 95).toInt())
                        Util.firstSimpleDrawLine(im, point1, point2)
                    }
                    image = SwingFXUtils.toFXImage(im, null)
                }
            }

            text("Усовершенствованный алгоритм")
            hbox {
                imageview() {
                    var im = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)
                    for (i in 0 until 13) {
                        val point1 = arrayOf(100, 100)
                        val point2 = arrayOf((100 + cos(i * 2 * PI / 13) * 95).toInt(), (100 + sin(i * 2 * PI / 13) * 95).toInt())
                        Util.secondSimpleDrawLine(im, point1, point2)
                    }
                    image = SwingFXUtils.toFXImage(im, null)
                }

                imageview() {
                    var im = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)
                    for (i in 0 until 13) {
                        val point1 = arrayOf(100, 100)
                        val point2 = arrayOf((100 + cos(i * 2 * PI / 13) * 95).toInt(), (100 + sin(i * 2 * PI / 13) * 95).toInt())
                        Util.thirdSimpleDrawLine(im, point1, point2)
                    }
                    image = SwingFXUtils.toFXImage(im, null)
                }
            }

            text("Брезенхема")
            hbox {
                imageview() {
                    var im = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)
                    for (i in 0 until 13) {
                        val point1 = arrayOf(100, 100)
                        val point2 = arrayOf((100 + cos(i * 2 * PI / 13) * 95).toInt(), (100 + sin(i * 2 * PI / 13) * 95).toInt())
                        Util.fourthDrawLine(im, point1, point2)
                    }
                    image = SwingFXUtils.toFXImage(im, null)
                }
            }

            text("опять Кролик")
            hbox {
                scrollpane {

                    imageview() {
                        var im = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
                        val file = File("C:\\Users\\user\\IdeaProjects\\ComputerGraphic1\\src\\main\\kotlin\\com\\example\\demo\\app\\files\\file.obj")
                        val model = Model.fromFile(file)
                        val points2d = model.get2dPoints()
                        val points2dInt = ArrayList<Array<Int>>()
                        for (i in 0 until points2d.size)
                            points2dInt.add((arrayOf((points2d[i][0] * 4000 + 500).toInt(), (-points2d[i][1] * 4000 + 500).toInt())))
                        val polygons = model.polygons
                        if (polygons != null) {
                            for (polygon in polygons) {
                                Util.fourthDrawLine(im, points2dInt[polygon[0]], points2dInt[polygon[1]])
                                Util.fourthDrawLine(im, points2dInt[polygon[1]], points2dInt[polygon[2]])
                                Util.fourthDrawLine(im, points2dInt[polygon[2]], points2dInt[polygon[0]])
                            }
                        }
                        im = im.getSubimage(0, 0, 1000, 500)
                        image = SwingFXUtils.toFXImage(im, null)
                    }
                }
            }

            text("Треугольник цветной")
            hbox {
                scrollpane {
                    imageview() {
                        var im = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
                        val points = ArrayList<Array<Double>>().apply {
                            this.add(arrayOf(10.0, 90.0, 0.0))
                            this.add(arrayOf(90.0, 90.0, 0.0))
                            this.add(arrayOf(50.0, 10.0, 0.0))
                        }
                        val polygons = ArrayList<Array<Int>>().apply {
                            this.add(arrayOf(0, 1, 2))
                        }
                        Util.fillTriangle(im, points[0], points[1], points[2], color = Util.getColor(124, 0, 255))
                        image = SwingFXUtils.toFXImage(im, null)
                    }
                }
            }

            text("Кролик цветной")
            hbox {
                scrollpane {
                    imageview() {
                        var im = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
                        val file = File("C:\\Users\\user\\IdeaProjects\\ComputerGraphic1\\src\\main\\kotlin\\com\\example\\demo\\app\\files\\file.obj")
                        val model = Model.fromFile(file)
                        val points = model.points
                        val points2d = model.get2dPoints()
                        for (i in 0 until points2d.size)
                            points2d[i] = arrayOf((points2d[i][0] * 4000 + 500), (-points2d[i][1] * 4000 + 500))
                        val polygons = model.polygons
                        if (polygons != null) {
                            for (polygon in polygons) {
                                Util.fillTriangle(im, points2d[polygon[0]], points2d[polygon[1]], points2d[polygon[2]],
                                        color = Util.getColor(
                                                (Math.random() * 255).toInt(),
                                                (Math.random() * 255).toInt(),
                                                (Math.random() * 255).toInt()))
                            }
                        }
                        im = im.getSubimage(0, 0, 1000, 500)
                        image = SwingFXUtils.toFXImage(im, null)
                    }
                }
            }


            text("Кролик с лицевой стороной")
            hbox {
                scrollpane {
                    imageview() {
                        var im = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
                        val file = File("C:\\Users\\user\\IdeaProjects\\ComputerGraphic1\\src\\main\\kotlin\\com\\example\\demo\\app\\files\\file.obj")
                        val model = Model.fromFile(file)
                        val points = model.points
                        val points2d = model.get2dPoints()
                        for (i in 0 until points2d.size)
                            points2d[i] = arrayOf((points2d[i][0] * 4000 + 500), (-points2d[i][1] * 4000 + 500))
                        val polygons = model.polygons
                        if (polygons != null) {
                            for (polygon in polygons) {
                                if (Util.absVector(points[polygon[0]], points[polygon[1]], points[polygon[2]])[2] > 0) {
                                    Util.fillTriangle(im, points2d[polygon[0]], points2d[polygon[1]], points2d[polygon[2]],
                                            color = Util.getColor(
                                                    (Math.random() * 255).toInt(),
                                                    (Math.random() * 255).toInt(),
                                                    (Math.random() * 255).toInt()))
                                }
                            }
                        }
                        im = im.getSubimage(0, 0, 1000, 500)
                        image = SwingFXUtils.toFXImage(im, null)
                    }
                }
            }

            text("Кролик с лицевой стороной")
            hbox {
                scrollpane {
                    imageview() {
                        var im = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
                        val file = File("C:\\Users\\user\\IdeaProjects\\ComputerGraphic1\\src\\main\\kotlin\\com\\example\\demo\\app\\files\\file.obj")
                        val model = Model.fromFile(file)
                        val points = model.points
                        val points2d = model.get2dPoints()
                        for (i in 0 until points2d.size)
                            points2d[i] = arrayOf((points2d[i][0] * 4000 + 500), (-points2d[i][1] * 4000 + 500))
                        val polygons = model.polygons
                        if (polygons != null) {
                            for (polygon in polygons) {
                                val intensity = Util.absVector(points[polygon[0]], points[polygon[1]], points[polygon[2]])[2]
                                if (intensity > 0) {
                                    Util.fillTriangle(im, points2d[polygon[0]], points2d[polygon[1]], points2d[polygon[2]],
                                            color = Util.getColor(
                                                    (-intensity * 255).toInt(),
                                                    (-intensity * 255).toInt(),
                                                    (-intensity * 255).toInt()))
                                }
                            }
                        }
                        im = im.getSubimage(0, 0, 1000, 500)
                        image = SwingFXUtils.toFXImage(im, null)
                    }
                }
            }
        }
    }
}