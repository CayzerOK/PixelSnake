package com.cayzerok.world

import com.cayzerok.collision.AABB
import com.cayzerok.core.*
import com.cayzerok.render.*
import com.google.gson.Gson
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import java.io.File
import java.io.FileNotFoundException

val Land1 = Layer("main1")
val Land2 = Layer("main2")
val gson = Gson()
val layerList = listOf(Land1, Land2)

object World{
    val width = 64
    val height = 64
    var scale = 50f
    var projection = Matrix4f().setTranslation(Vector3f(0f)).scale(World.scale)
    var collBoxes = Array<Boolean?>(width*height,{false})


    fun getTileBB(x: Float, y: Float): Boolean? {
        try {
            return collBoxes[(x+y*World.width).toInt()]
        } catch (e:ArrayIndexOutOfBoundsException) {return null}
    }

    fun correctCamera() {
        val w = -World.width*World.scale*2
        val h = World.height*World.scale*2

        if(mainCamera.camPosition.x> -(mainWindow.width/2)+ World.scale)
            mainCamera.camPosition.x = -(mainWindow.width/2)+ World.scale
        if(mainCamera.camPosition.x < w+(mainWindow.width/2)+ World.scale)
            mainCamera.camPosition.x = w+(mainWindow.width/2)+ World.scale

        if(mainCamera.camPosition.y < mainWindow.height/2-World.scale)
            mainCamera.camPosition.y = mainWindow.height/2-World.scale
        if(mainCamera.camPosition.y > h-mainWindow.height/2-World.scale)
            mainCamera.camPosition.y = h-mainWindow.height/2-World.scale

        if(player.position.x> World.scale)
            player.position.x = World.scale
        if(player.position.x < w+World.scale)
            player.position.x = w+World.scale

        if(player.position.y < -World.scale)
            player.position.y = -World.scale
        if(player.position.y > h-World.scale)
            player.position.y = h-World.scale
    }
}

class Layer(val name:String){

    private var sheet:Array<Int?> = Array(World.width*World.height,{null})
    private var angleSheet:Array<Double?> =  Array(World.width*World.height,{0.0})

    fun renderIt() {
        for (y in 0 until World.height)
            for (x in 0 until World.width) {
                if (sheet[x+y*World.width] != null) {
                    render.renderTile(sheet[x+y*World.width]!!,x.toFloat(),-y.toFloat(), angle = angleSheet[x+y*World.width]!!)
                }
            }
    }

    fun setTile(tile: Int?, x:Int, y:Int, angle:Double = 0.0) {
        sheet[x+y*World.width] = tile
        angleSheet[x+y*World.width] = angle
        if (tile == null){
            World.collBoxes[x+y*World.width] = null
        }else if(tiles[tile]!!.solid)
            World.collBoxes[x+y*World.width] = true
        else World.collBoxes[x+y*World.width] = null
    }

    fun getTile(x:Int,y:Int): Tile? {
        try {
            return tiles[sheet[x+y*World.width]!!]
        } catch (e:ArrayIndexOutOfBoundsException) {return null}
    }




    fun loadWorld() {
        try {
            sheet = gson.fromJson(File(assets + "levels/" + name + ".lvl").readText(), Array<Int?>::class.java)
            for (y in 0 until World.height)
                for (x in 0 until World.width) {
                    if (sheet[x + y * World.width] != null) {
                        if (tiles[sheet[x + y * World.width]!!]?.solid == true) {
                            World.collBoxes[x + y * World.width] = AABB(Vector2f(x * 2.toFloat(), -y * 2.toFloat()), Vector2f(1f, 1f))
                        }
                    }
                }
            angleSheet = gson.fromJson(File(assets+"levels/"+name+".ang").readText(),Array<Double?>::class.java)
        } catch (e:FileNotFoundException){}

    }
    fun saveWorld() {
        val lvlString = gson.toJson(sheet)
        val angleString = gson.toJson(angleSheet)
        File(assets+"levels/"+name+".lvl").writeText(lvlString)
        File(assets+"levels/"+name+".ang").writeText(angleString)
    }
}