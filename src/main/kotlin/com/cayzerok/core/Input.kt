package com.cayzerok.core

import com.cayzerok.entity.BulletNote
import com.cayzerok.entity.bulletList
import com.cayzerok.render.player
import com.cayzerok.world.World
import com.cayzerok.world.layerList
import com.cayzerok.world.tiles
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFWScrollCallback
import org.lwjgl.glfw.GLFW.glfwSetScrollCallback




val xBuffer = BufferUtils.createDoubleBuffer(1)
val yBuffer = BufferUtils.createDoubleBuffer(1)
private var layer:Int = 0

fun getInput(window:Long) {

    glfwGetCursorPos(input.window, xBuffer, yBuffer)

    var cx = xBuffer.get(0).toFloat()
    var cy = -yBuffer.get(0).toFloat()

    if (cx < World.scale/2) {
        cx = World.scale/2
        glfwSetCursorPos(window, cx.toDouble(), -cy.toDouble())
    }

    if (cx > mainWindow.width-World.scale/2){
        cx = mainWindow.width-World.scale/2
        glfwSetCursorPos(window, cx.toDouble(), -cy.toDouble())
    }

    if (-cy < World.scale/2) {
        cy = -World.scale/2
        glfwSetCursorPos(window, cx.toDouble(), -cy.toDouble())
    }
    if (-cy > mainWindow.height-World.scale/2) {
        cy = -mainWindow.height+World.scale/2
        glfwSetCursorPos(window, cx.toDouble(), -cy.toDouble())
    }

    cursorPos.x = (cx-mainCamera.camPosition.x-mainWindow.width/2)
    cursorPos.y = (cy-mainCamera.camPosition.y+mainWindow.height/2)

    if (input.isKeyDown(GLFW_KEY_LEFT_CONTROL)&&input.isKeyPressed(GLFW_KEY_R))
        showWays = !showWays

    if( input.isKeyPressed(GLFW_KEY_E)) {
        if (player.invTileAngle == 0.0) {
            player.invTileAngle = 270.0
        } else player.invTileAngle -= 90.0
    }
    if( input.isKeyPressed(GLFW_KEY_Q)) {
        if (player.invTileAngle == 270.0) {
            player.invTileAngle = 0.0
        } else player.invTileAngle += 90.0
    }

    if( input.isKeyPressed(GLFW_KEY_SPACE)) {
        bulletList.add(BulletNote(Vector3f(player.position), Math.atan2((-cursorPos.y-player.position.y).toDouble(), (-cursorPos.x-player.position.x).toDouble()).toFloat()))}


    if( input.isKeyPressed(GLFW_KEY_ESCAPE)) {glfwSetWindowShouldClose(window,true)}
    if( input.isKeyDown(GLFW_KEY_W)) {
        player.move(0f,-15f,0f)
    }
    if( input.isKeyDown(GLFW_KEY_A)) {
        player.move(15f,0f,0f)
    }
    if( input.isKeyDown(GLFW_KEY_S)) {
        player.move(0f,15f,0f)
    }
    if( input.isKeyDown(GLFW_KEY_D)) {
        player.move(-15f,0f,0f)
    }

    if (!showWays) {
        when {
            input.isKeyPressed(GLFW_KEY_UP) -> if (layer + 1 in 0..layerList.lastIndex) layer += 1
            input.isKeyPressed(GLFW_KEY_DOWN) -> if (layer - 1 in 0..layerList.lastIndex) layer -= 1
        }

        if (input.isMouseButtonDown(0)) {
            layerList[layer].setTile(player.invTile, (cursorPos.x / (World.scale*2) + 0.5).toInt(), (-cursorPos.y / (World.scale*2) + 0.5).toInt(), player.invTileAngle)
        }
        if (input.isMouseButtonDown(1)) {
            layerList[layer].setTile(null, (cursorPos.x / (World.scale*2) + 0.5).toInt(), (-cursorPos.y / (World.scale*2) + 0.5).toInt())
        }
        if (input.scroll != 0.0) {
            if (tiles[player.invTile + input.scroll.toInt()] != null)
                player.invTile += input.scroll.toInt()
            input.scroll = 0.0
        }
    } else {
        if (input.isMouseButtonDown(0)) {
            World.setWays(true,(cursorPos.x / (World.scale*2) + 0.5).toInt(),(-cursorPos.y / (World.scale*2) + 0.5).toInt())
        }
        if (input.isMouseButtonDown(1)) {
            World.setWays(false,(cursorPos.x / (World.scale*2) + 0.5).toInt(),(-cursorPos.y / (World.scale*2) + 0.5).toInt())
        }
    }
}

class Input(val window: Long) {
    var keys = BooleanArray(GLFW_KEY_LAST,{false})
    var mouse = BooleanArray(GLFW_MOUSE_BUTTON_LAST,{false})

    var scroll = 0.0

    fun updateScroll() {

        glfwSetScrollCallback(window, GLFWScrollCallback.create { _, _, yoffset ->
            scroll = yoffset
        })
    }

    fun isKeyDown(key:Int) : Boolean {
        return glfwGetKey(window,key) == 1
    }

    fun isKeyPressed(key:Int) : Boolean {
        return (isKeyDown(key) && !keys[key])
    }

    fun isKeyReleased(key:Int) : Boolean {
        return (!isKeyDown(key) && keys[key])
    }

    fun isMouseButtonDown(key:Int) : Boolean {
        return glfwGetMouseButton(window,key) == 1
    }

    fun isMouseButtonPressed(key:Int) : Boolean {
        return (isMouseButtonDown(key) && !mouse[key])
    }

    fun isMouseButtonReleased(key:Int) : Boolean {
        return (!isMouseButtonDown(key) && mouse[key])
    }

    fun update() {
        keys.forEachIndexed { index,_ ->
            keys[index] = isKeyDown(index)
        }
        mouse.forEachIndexed{ index,_ ->
            mouse[index] = isMouseButtonDown(index)
        }
        glfwPollEvents()
    }

}