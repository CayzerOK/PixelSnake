package com.cayzerok.core

import com.cayzerok.ui.Statistics
import org.lwjgl.glfw.GLFW.glfwTerminate
val speedMultiple = 1

fun main(args: Array<String>) {
    try {
        coreStart()
    } finally {
        glfwTerminate()
    }
}

fun stabileFloat(number:Float): Float {
    return number*Statistics.frameTime*speedMultiple/100
}