package com.cayzerok.core

import org.lwjgl.glfw.GLFW.*
import java.awt.event.MouseWheelEvent

fun getInput(window:Long) {
    if( glfwGetMouseButton(window,0) == 1) {glfwSetWindowShouldClose(window,true)}
    if( glfwGetKey(window, GLFW_KEY_RIGHT) == 1) {mainCamera.addPosition(10f,0f,0f)}
    if( glfwGetKey(window, GLFW_KEY_LEFT) == 1) {mainCamera.addPosition(-10f,0f,0f)}
    if( glfwGetKey(window, GLFW_KEY_UP) == 1)  {mainCamera.addPosition(0f,10f,0f)}
    if( glfwGetKey(window, GLFW_KEY_DOWN) == 1) {mainCamera.addPosition(0f,-10f,0f)}
    if( glfwGetKey(window, GLFW_KEY_SPACE) == 1)  {println(mainCamera.getPosition())}
}