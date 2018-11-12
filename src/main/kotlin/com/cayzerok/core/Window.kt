package com.cayzerok.core

import com.cayzerok.ui.*
import com.cayzerok.render.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

object mainWindow {
    var isFullSchreen = false
    val monitor:Long = if (isFullSchreen) {glfwGetPrimaryMonitor()} else {0}
    val width = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.width()} else {720}
    val height = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.height()} else {480}
}
val mainCamera = Camera()
var input = Input(0)
val shader = Shader()

var red = 0f

fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }

    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val win = glfwCreateWindow(mainWindow.width, mainWindow.height, "Snake Bizzare Adventure", mainWindow.monitor, 0)
    glfwShowWindow(win)
    glfwMakeContextCurrent(win)

    input = Input(win)

    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
    mainCamera.use(mainWindow.width,mainWindow.height)

    initialize()

    shader.init("shader")
    shader.bind()

    while (!glfwWindowShouldClose(win)) {
        Statistics.readFrameRate()
        glClearColor(red, 0f, 0f, 0f)
        glClearDepth(1.0)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        shader.setUniform("sampler", 0f)
        shader.setUniform("projection", mainCamera.getProjection().mul(projection))
        getInput(win)
        input.update()
        mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        glfwSwapBuffers(win)
    }
}