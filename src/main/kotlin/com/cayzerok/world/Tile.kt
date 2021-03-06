package com.cayzerok.world

import org.joml.Vector2f
import java.lang.IllegalStateException

val tiles = Array<Tile?>(255) {null}

class Tile(var id:Int, var texture:String,val center:Vector2f? = null, val hExtend:Vector2f? = null) {
    init {
        if (tiles[id] != null) {
            throw IllegalStateException("Tile id [$id] is already used.")
        } else {
            tiles[id] = this
        }
    }
}


object TileList{
    val stone0 = Tile(1,"stone0")
    val stone1 = Tile(2,"stone1")
    val stone2 = Tile(3,"stone2")
    val stone3 = Tile(4,"stone3")
    val grass0 = Tile(5, "grass0")
    val grass1 = Tile(6, "grass1")
    val grass2 = Tile(7, "grass2")
    val grass3 = Tile(8, "grass3")
    val sand0 = Tile(9, "sand0")
    val sand1 = Tile(10, "sand1")
    val sand2 = Tile(11, "sand2")
    val sand3 = Tile(12, "sand3")
    val waterTile = Tile(13, "water")
    val aim = Tile(14, "aim")
    val waypoint = Tile(15,"waypoint")
    val intWall0 = Tile(16, "interior_wall", Vector2f(1.6f,0f), Vector2f(World.scale/5,World.scale))
    val intWall1 = Tile(17, "interior_wall1", Vector2f(1.6f, 1.6f), Vector2f(World.scale/5,World.scale/5))
}

