package com.cayzerok.network

import com.cayzerok.entity.player
import com.cayzerok.render.playerList
import org.joml.Vector3f
import java.util.*

fun keyFrame(keyBytes:ByteArray) {
    val players = keyBytes.size/28
    for (index in 0 until players) {
        val userBytes = keyBytes.copyOfRange(index*28,index*28+28)
        val uuid = getUUIDFromBytes(userBytes.copyOfRange(0,16))
        val player = playerList.first { it.uuid == uuid }
        player.setPos(Vector3f(
                getFloat(userBytes.copyOfRange(16,20)),
                getFloat(userBytes.copyOfRange(20,24)),
                getFloat(userBytes.copyOfRange(24,28)))
        )
    }
}

fun removeChar(bytes:ByteArray) {
    val nullPlayer = playerList.first{it.uuid == getUUIDFromBytes(bytes)}
    nullPlayer.setPos(Vector3f(-1000f,-1000f,0f))
    nullPlayer.uuid = null
    nullPlayer.HP = 100
    nullPlayer.mustShoot = false
    nullPlayer.angle = 0f
    nullPlayer.moveKeys = booleanArrayOf(false,false,false,false)
}

fun addChar(bytes:ByteArray) {
    val newUUID = getUUIDFromBytes(bytes)
    if (player.uuid != newUUID) {
        val newPlayer = playerList.first { it.uuid == null }
        newPlayer.uuid = newUUID
        println("New user. UUID: ${newPlayer.uuid}")
    }
}

fun genericFrame(frameData:ByteArray) {
    val players = frameData.size/26
    for (index in 0 until players) {
        val userData = deserializePlayerData(frameData.copyOfRange(index * 26, index * 26 + 26))
        try {
            val thisPlayer = playerList.first { it.uuid == userData.uuid }
            if (thisPlayer.gun.cells>userData.cells){
                thisPlayer.gun.isShooting = true
            } else {thisPlayer.gun.cells = userData.cells}
            thisPlayer.HP = userData.HP
            if (thisPlayer.uuid != player.uuid) {
                thisPlayer.moveKeys = userData.moveKeys
                thisPlayer.angle = userData.angle
            }
        }catch (e:NoSuchElementException) {
            println("ERROR: UUID not found")
            println("Excepted: "+userData.uuid)
            println("Found:")
            playerList.filter { it.uuid != null }.forEach {
                println(it.uuid)
            }
        }

    }
}