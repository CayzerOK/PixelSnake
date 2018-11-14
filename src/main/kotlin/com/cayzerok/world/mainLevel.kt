package com.cayzerok.world

import com.cayzerok.core.*
import com.cayzerok.render.player
import com.cayzerok.render.render
import org.joml.Matrix4f
import org.joml.Vector3f
var worldProj = Matrix4f().setTranslation(Vector3f(0f)).scale(World.scale)

object World{
    val width = 16
    val height = 16
    var scale = 50f

    private val sheet:Array<Int?> = Array(width*height,{null})

    fun renderIt() {
        for (y in 0 until height)
            for (x in 0 until width) {
                if (sheet[x+y*width] != null) {
                    render.renderTile(sheet[x+y*width]!!,x.toFloat(),-y.toFloat(), shader, worldProj, mainCamera)
                }
            }
    }

    fun setTile(tile:Tile,x:Int,y:Int) {
        sheet[x+y*width] = tile.id
    }

    fun getTile(x:Int,y:Int): Tile? {
        try {
            return tiles[sheet[x+y*width]!!]
        } catch (e:ArrayIndexOutOfBoundsException) {return null}
    }

    fun correctCamera() {
        val w = -width*scale*2
        val h = height*scale*2

        if(mainCamera.camPosition.x> -(mainWindow.width/2)+ scale)
            mainCamera.camPosition.x = -(mainWindow.width/2)+ scale
        if(mainCamera.camPosition.x < w+(mainWindow.width/2)+ scale)
            mainCamera.camPosition.x = w+(mainWindow.width/2)+ scale

        if(mainCamera.camPosition.y < mainWindow.height/2-scale)
            mainCamera.camPosition.y = mainWindow.height/2-scale
        if(mainCamera.camPosition.y > h-mainWindow.height/2-scale)
            mainCamera.camPosition.y = h-mainWindow.height/2-scale

//        if(player.position.x> scale)
//            player.position.x = scale
//        if(player.position.x < w+scale)
//            player.position.x = w+scale
//
//        if(player.position.y < -scale)
//            player.position.y = -scale
//        if(player.position.y > h-scale)
//            player.position.y = h-scale
    }


}