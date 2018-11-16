package com.cayzerok.core

import org.joml.Vector3f

object PID {
    fun smoothCam(target: Vector3f) {
        val xS = when(target.x-mainCamera.camPosition.x){
            in -1..1 -> 2
            in 2..3 -> 3
            in -3..-2 -> 4
            else -> 5
        }

        val yS = when(target.y-mainCamera.camPosition.y) {
            in -1..1 -> 2
            in 2..3 -> 3
            in -3..-2 -> 4
            else -> 5
        }

        val xP= ((target.x-mainCamera.camPosition.x)/10)*xS
        val yP= ((target.y-mainCamera.camPosition.y)/10)*yS
        mainCamera.move(xP,yP,0f)
    }
}
