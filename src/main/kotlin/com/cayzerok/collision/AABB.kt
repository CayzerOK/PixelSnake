package com.cayzerok.collision

import org.joml.Vector2f

class AABB(val center: Vector2f, val halfExtent: Vector2f) {

    fun getCollision(box: AABB): Collision {
        val distance = box.center.sub(center, Vector2f())
        distance.x = Math.abs(distance.x)
        distance.y = Math.abs(distance.y)

        distance.sub(halfExtent.add(box.halfExtent, Vector2f()))

        return Collision(distance, distance.x < 0 && distance.y < 0)
    }

    fun getCollision(point: Vector2f): Collision {
        val distance = point.sub(center)
        distance.x = Math.abs(distance.x)
        distance.y = Math.abs(distance.y)

        distance.sub(halfExtent)

        return Collision(distance, distance.x < 0 && distance.y < 0)
    }

    fun correctPosition(box: AABB, data: Collision) {
        val correctionDistance = box.center.sub(center, Vector2f())
        if (data.distance.x > data.distance.y) {
            if (correctionDistance.x > 0) {
                center.add(data.distance.x, 0f)
            } else {
                center.add(-data.distance.x, 0f)
            }
        } else {
            if (correctionDistance.y > 0) {
                center.add(0f, data.distance.y)
            } else {
                center.add(0f, -data.distance.y)
            }
        }
    }
}

class Collision(var distance: Vector2f, var isIntersecting: Boolean)
