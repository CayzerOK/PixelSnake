package com.cayzerok.ui

import com.cayzerok.render.player
import com.cayzerok.world.World
import org.joml.Vector3f

object Statistics {
    var lastFrame: Float = 0.0f
    var thisFrame: Float = 0.0f
    var frameTime:Float = 0.0f
    var milis: Float = 0.0f
    private var frames: Int = 0
    var frameRate: Int = 0

    fun readFrameRate() {
        lastFrame = thisFrame
        thisFrame = System.nanoTime()*0.000001.toFloat()
        frameTime = thisFrame - lastFrame
        milis += frameTime
        frames++
        if (milis >= 1000) {
            milis = 0.0f
            frameRate = frames
            frames = 0
            println("FPS: $frameRate")
            println("FrameTime: $frameTime")
//            println("${player.boxes[0]}  ${player.boxes[1]}  ${player.boxes[2]}  ${player.boxes[3]}  ${player.boxes[4]}")
//            println("${player.boxes[5]}  ${player.boxes[6]}  ${player.boxes[7]}  ${player.boxes[8]}  ${player.boxes[9]}")
//            println("${player.boxes[10]} ${player.boxes[11]} ${player.boxes[12]} ${player.boxes[13]} ${player.boxes[14]}")
//            println("${player.boxes[15]} ${player.boxes[16]} ${player.boxes[17]} ${player.boxes[18]} ${player.boxes[19]}")
//            println("${player.boxes[20]} ${player.boxes[21]} ${player.boxes[22]} ${player.boxes[23]} ${player.boxes[24]}")
        }
    }
}