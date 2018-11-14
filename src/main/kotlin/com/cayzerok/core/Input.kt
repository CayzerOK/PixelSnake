package com.cayzerok.core

import com.cayzerok.render.player
import com.cayzerok.world.World
import org.lwjgl.glfw.GLFW.*

fun getInput(window:Long) {
    if( input.isKeyPressed(GLFW_KEY_ESCAPE)) {glfwSetWindowShouldClose(window,true)}
    if( input.isKeyDown(GLFW_KEY_W)) {player.position.add(0f,-1f,0f)}
    if( input.isKeyDown(GLFW_KEY_A)) {player.position.add(1f,0f,0f)}
    if( input.isKeyDown(GLFW_KEY_S)) {player.position.add(0f,1f,0f) }
    if( input.isKeyDown(GLFW_KEY_D)) {player.position.add(-1f,0f,0f)}
}



class Input(val window: Long) {

    var keys = BooleanArray(GLFW_KEY_LAST,{false})
    var mouse = BooleanArray(GLFW_MOUSE_BUTTON_LAST,{false})

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
        keys.forEachIndexed { index,it ->
            keys[index] = isKeyDown(index)
        }
        mouse.forEachIndexed{ index,it ->
            mouse[index] = isMouseButtonDown(index)
        }
        glfwPollEvents()
    }

}


