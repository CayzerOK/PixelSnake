package com.cayzerok.core

import com.cayzerok.guns.mustShoot
import com.cayzerok.render.player
import com.cayzerok.ui.Timer
import com.cayzerok.world.gson
import io.ktor.network.sockets.Datagram
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.io.core.BytePacketBuilder
import kotlinx.io.core.writeFully
import java.net.InetSocketAddress

val packet = BytePacketBuilder()

suspend fun send(message: Array<Any>) {
    val output = clientSocket!!.outgoing
    if (player.uuid == null) {
        packet.writeStringUtf8("CMD_UUID")
        output.send(Datagram(packet.build(), InetSocketAddress("127.0.0.1", 8080)))
    } else {
        val outJSON = gson.toJson(outputCell(player.uuid!!, message))
        packet.writeFully(outJSON.toByteArray())
        output.send(Datagram(packet.build(), InetSocketAddress("127.0.0.1", 8080)))
    }
}

@ObsoleteCoroutinesApi
suspend fun catch() {
    val call = clientSocket!!.incoming.receiveOrNull()
    if (call != null) {
        val inCell = gson.fromJson(call.packet.readText(), outputCell::class.java)
        if (player.uuid == null) player.uuid = inCell.user
        println("CATCH")
    }
}

private val networkTimer = Timer(33)
private var networkPoint = 0
@ObsoleteCoroutinesApi
fun networkLoop() = runBlocking {
    networkTimer.calculate()
    launch { withTimeout(1L) { catch()} }
    if (networkTimer.points != networkPoint) {
        networkPoint = networkTimer.points
        if (networkPoint > 5) {
            networkPoint = 0
            networkTimer.`break`()
        }

        val newMesa = arrayOf(
                player.moveKeys[0],
                player.moveKeys[1],
                player.moveKeys[2],
                player.moveKeys[3],
                mustShoot,
                player.angle)

        launch { send(newMesa) }
    }
}