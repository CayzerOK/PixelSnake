package com.cayzerok.world

import java.lang.IllegalStateException

val tiles = Array<Tile?>(255, {null})

class Tile(var id:Int, var texture:String, val solid:Boolean = false) {
    init {
        if (tiles[id] != null) {
            throw IllegalStateException("Tile id [$id] is already used.")
        } else {
            tiles[id] = this
        }
    }
}


object TileList{
    val stone0 = Tile(1,"stone0",true)
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
}



