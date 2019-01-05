package com.cayzerok.world

import com.cayzerok.core.MainWindow
import com.cayzerok.core.mainCamera
import com.cayzerok.core.path
import com.cayzerok.render.render
import com.google.gson.Gson
import org.joml.Matrix4f
import org.joml.Vector3f
import java.io.File
import java.io.FileNotFoundException

val layerList = listOf(
        Layer("main1"),
        Layer("main2"),
        Layer("main3"),
        Layer("main4")
)
val gson = Gson()
object World{
    const val width = 64
    const val height = 64
    var scale = MainWindow.height/10.toFloat()
    var projection = Matrix4f().setTranslation(Vector3f(0f)).scale(World.scale)!!

    val w = -World.width*World.scale*2
    val h = World.height*World.scale*2

    fun correctCamera() {
        if(mainCamera.camPosition.x> -(MainWindow.width/2)+ World.scale)
            mainCamera.camPosition.x = -(MainWindow.width/2)+ World.scale
        if(mainCamera.camPosition.x < w+(MainWindow.width/2)+ World.scale)
            mainCamera.camPosition.x = w+(MainWindow.width/2)+ World.scale

        if(mainCamera.camPosition.y < MainWindow.height/2-World.scale)
            mainCamera.camPosition.y = MainWindow.height/2-World.scale
        if(mainCamera.camPosition.y > h- MainWindow.height/2-World.scale)
            mainCamera.camPosition.y = h- MainWindow.height/2-World.scale
    }
}

class Layer(private val name:String){

    private var sheet:Array<Int> = Array(World.width*World.height) {0}
    private var angleSheet:Array<Double?> =  Array(World.width*World.height) {0.0}

    fun renderIt() {
        for (y in 0 until World.height)
            for (x in 0 until World.width) {
                if (sheet[x+y*World.width] != 0) {
                    render.renderTile(sheet[x + y * World.width],x.toFloat(),-y.toFloat(), angle = angleSheet[x+y*World.width]!!)
                }
            }
    }

    fun setTile(tile: Int, x:Int, y:Int, angle:Double = 0.0) {
        sheet[x+y*World.width] = tile
        angleSheet[x+y*World.width] = angle
    }

    fun getAngle(x:Int,y:Int): Double? {
        return try {
            angleSheet[x+y*World.width]
        } catch (e:ArrayIndexOutOfBoundsException) {
            null
        }
    }

    fun getTile(x:Int,y:Int): Tile? {
        return try {
            tiles[sheet[x+y*World.width]]
        } catch (e:ArrayIndexOutOfBoundsException) {
            null
        }
    }


    fun loadWorld() {
        try {
            sheet = gson.fromJson(File(path+"levels/"+name+".lvl").readText(), Array<Int>::class.java)
            angleSheet = gson.fromJson(File(path+"levels/"+name+".ang").readText(),Array<Double?>::class.java)
        } catch (e:FileNotFoundException){}

    }
    fun saveWorld() {
        val lvlString = gson.toJson(sheet)
        val angleString = gson.toJson(angleSheet)
        File(path+"levels/"+name+".lvl").writeText(lvlString)
        File(path+"levels/"+name+".ang").writeText(angleString)
    }
}