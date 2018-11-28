package com.cayzerok.render

import com.cayzerok.ui.Statistics

class Animation(val amount:Int, name:String) {

    val frames = Array<Texture?>(amount) {null}
    val animFrameTime = 1000/amount
    init {
        for (i in 0 until amount)
            frames[i] = Texture("$name/$i")
    }

    fun getTex(): Texture? {
        var texIndex = Statistics.milis/animFrameTime
        if (texIndex>amount) texIndex = amount-1f
        return frames[texIndex.toInt()]
    }
}

class Bar(val amount:Int, name:String, maxData: Int = 100) {
    private val frames = Array<Texture?>(amount) {null}
    private val frameInd = maxData/amount
    init {
        for (i in 0 until amount)
            frames[i] = Texture("$name/$i")
    }

    fun getTex(data:Int): Texture? {
        var texIndex = data/frameInd
        if (texIndex>amount) texIndex = amount-1
        return frames[texIndex]
    }
}