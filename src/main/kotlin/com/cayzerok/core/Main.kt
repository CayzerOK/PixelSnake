package com.cayzerok.core

import org.lwjgl.glfw.GLFW.glfwTerminate
val winMultiple:Int = 50

fun main(args: Array<String>) {
    try {
        coreStart()
    } finally {
        glfwTerminate()
    }
}