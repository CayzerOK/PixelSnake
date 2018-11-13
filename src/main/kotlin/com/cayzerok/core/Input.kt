package com.cayzerok.core

import com.cayzerok.render.zoom
import org.lwjgl.glfw.GLFW.*

fun getInput(window:Long) {
    if( input.isKeyPressed(GLFW_KEY_ESCAPE)) {glfwSetWindowShouldClose(window,true)}
    if( input.isKeyDown(GLFW_KEY_RIGHT)) {mainCamera.move(-10f,0f,0f)}
    if( input.isKeyDown(GLFW_KEY_LEFT)) {mainCamera.move(10f,0f,0f)}
    if( input.isKeyDown(GLFW_KEY_UP)) {mainCamera.move(0f,-10f,0f)}
    if( input.isKeyDown(GLFW_KEY_DOWN)) {mainCamera.move(0f,10f,0f)}
    if( input.isMouseButtonReleased(0)) { red = 0f}
    if( input.isMouseButtonPressed(0)) { red = 0.5f}
    if( input.isKeyPressed(GLFW_KEY_SPACE)) { println("SPACE!")}
    if( input.isKeyReleased(GLFW_KEY_SPACE)) { println("I AM IN SPACE!")}
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


