package com.cayzerok.core

import com.cayzerok.render.player
import com.cayzerok.world.World

fun mainLoop() {
 camPID.stabileCam(player.position)
 World.correctCamera()
}