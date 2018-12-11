package com.cayzerok.experemental

import com.cayzerok.render.Shader
import com.cayzerok.world.World
import com.cayzerok.world.layerList
import org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor
import org.lwjgl.glfw.GLFW.glfwGetVideoMode

object mainWindow {
    var window:Long = 0
    var isFullSchreen = false
    var monitor:Long = if (isFullSchreen) {glfwGetPrimaryMonitor()} else {0}
    var width = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.width()} else {1280}
    var height = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.height()} else {720}
}

var red = 0f
val shader = Shader("shader")

fun coreStart() {


    World.saveWays()
    layerList.forEach { it.saveWorld() }
}
