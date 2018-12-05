package com.cayzerok.core

import com.cayzerok.render.Transform
import com.cayzerok.ui.Statistics
import org.lwjgl.glfw.GLFW.glfwTerminate

const val speedMultiple = 1
        //var path = Transform()::class.java.protectionDomain.codeSource.location.path.dropLast(14)
        val path = "./src/main/resources/"
fun main(args: Array<String>) {
    println(path)
    try {
        coreStart()
    } finally {
        glfwTerminate()
    }
}

fun stabileFloat(number:Float): Float {
    return number*Statistics.frameTime*speedMultiple/100
}