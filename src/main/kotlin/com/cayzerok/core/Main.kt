package com.cayzerok.core

import org.lwjgl.glfw.GLFW.glfwTerminate
val winMultiple:Int = 15
data class Section(
        val x:Int,
        val y:Int
)
fun main(args: Array<String>) {
    try {
        coreStart()
    } finally {
        glfwTerminate()
    }
}