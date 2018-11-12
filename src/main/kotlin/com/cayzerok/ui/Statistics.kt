package com.cayzerok.ui

object Statistics {
    var frameStart: Double? = null
    var frameEnd: Double? = null
    var frameTime: Double? = null
    var millis: Double = 0.0
    private var frames: Int = 0
    var frameRate: Int = 0

    fun ReadFrameRate() {
        frameTime = frameEnd!! - frameStart!!
        millis = millis + frameTime!!
        frames++
        if (millis >= 1000) {
            millis = 0.0
            frameRate = frames
            frames = 0
            println("FPS: $frameRate")
            println("FrameTime: $frameTime")
        }
    }
}
