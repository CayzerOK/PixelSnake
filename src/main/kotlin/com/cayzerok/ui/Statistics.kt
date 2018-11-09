package com.cayzerok.ui

object Statistics {
    var frameStart: Long? = null
    var frameEnd: Long? = null
    var frameTime: Long? = null
    private var millis: Long = 0
    private var frames: Int = 0
    var frameRate: Int = 0

    fun ReadFrameRate() {
        frameTime = frameEnd!! - frameStart!!
        millis = millis + frameTime!!
        frames++
        if (millis >= 1000) {
            millis = 0
            frameRate = frames
            frames = 0
            println("FPS: $frameRate")
            println("FrameTime: $frameTime")
        }
    }
}
