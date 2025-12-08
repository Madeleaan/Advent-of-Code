package `2025`

import println
import readInput
import kotlin.collections.forEach
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>, pairs: Int): Int {
        val dists = mutableMapOf<Pair<Triple<Float, Float, Float>, Triple<Float, Float, Float>>, Float>()
        val groups = mutableMapOf<Triple<Float, Float, Float>, Int>()
        val sizes = mutableMapOf<Int, Int>()
        
        // Compute distances
        for (i in 0 ..< input.size - 1) {
            for (j in i + 1 ..< input.size) {
                val line1 = input[i].split(",").map { it.toFloat() }
                val pt1 = Triple(line1[0], line1[1], line1[2])
                
                val line2 = input[j].split(",").map { it.toFloat() }
                val pt2 = Triple(line2[0], line2[1], line2[2])
                
                val dist = sqrt((pt1.first - pt2.first).pow(2) + (pt1.second - pt2.second).pow(2) + (pt1.third - pt2.third).pow(2))
                dists[Pair(pt1, pt2)] = dist
            }
        }
        
        var nextGroupId = 0
        for (pts in dists.toList().sortedBy { it.second }.take(pairs).map { it.first }) {
            val pt1 = pts.first
            val pt2 = pts.second
            
            if (groups.contains(pt1) && groups.contains(pt2)) {
                // Merge two groups into one
                val grp1 = groups[pt1]!!
                val grp2 = groups[pt2]!!
                if (grp1 == grp2) continue
                
                groups.filter { it.value == grp2 }.forEach { 
                    groups[it.key] = grp1
                    sizes[grp1] = sizes[grp1]!! + 1
                    sizes[grp2] = sizes[grp2]!! - 1
                }
            } else if (groups.contains(pt1)) {
                // Add pt2 to pt1's group
                groups[pt2] = groups[pt1]!!
                sizes[groups[pt1]!!] = sizes[groups[pt1]!!]!! + 1
            } else if (groups.contains(pt2)) {
                // Add pt1 to pt2's group
                groups[pt1] = groups[pt2]!!
                sizes[groups[pt2]!!] = sizes[groups[pt2]!!]!! + 1
            } else {
                // Create a new group
                groups[pt1] = nextGroupId
                groups[pt2] = nextGroupId
                sizes[nextGroupId] = 2
                nextGroupId++
            }
        }
        
        return sizes.values.sortedDescending().take(3).reduce { a, b -> a * b }
    }

    fun part2(input: List<String>): Int {
        val dists = mutableMapOf<Pair<Triple<Float, Float, Float>, Triple<Float, Float, Float>>, Float>()
        val groups = mutableMapOf<Triple<Float, Float, Float>, Int>()
        val sizes = mutableMapOf<Int, Int>()

        // Compute distances
        for (i in 0 ..< input.size - 1) {
            for (j in i + 1 ..< input.size) {
                val line1 = input[i].split(",").map { it.toFloat() }
                val pt1 = Triple(line1[0], line1[1], line1[2])

                val line2 = input[j].split(",").map { it.toFloat() }
                val pt2 = Triple(line2[0], line2[1], line2[2])

                val dist = sqrt((pt1.first - pt2.first).pow(2) + (pt1.second - pt2.second).pow(2) + (pt1.third - pt2.third).pow(2))
                dists[Pair(pt1, pt2)] = dist
            }
        }

        var nextGroupId = 0
        for (pts in dists.toList().sortedBy { it.second }.map { it.first }) {
            val pt1 = pts.first
            val pt2 = pts.second

            if (groups.contains(pt1) && groups.contains(pt2)) {
                // Merge two groups into one
                val grp1 = groups[pt1]!!
                val grp2 = groups[pt2]!!
                if (grp1 == grp2) continue

                groups.filter { it.value == grp2 }.forEach {
                    groups[it.key] = grp1
                    sizes[grp1] = sizes[grp1]!! + 1
                    sizes[grp2] = sizes[grp2]!! - 1
                }
            } else if (groups.contains(pt1)) {
                // Add pt2 to pt1's group
                groups[pt2] = groups[pt1]!!
                sizes[groups[pt1]!!] = sizes[groups[pt1]!!]!! + 1
            } else if (groups.contains(pt2)) {
                // Add pt1 to pt2's group
                groups[pt1] = groups[pt2]!!
                sizes[groups[pt2]!!] = sizes[groups[pt2]!!]!! + 1
            } else {
                // Create a new group
                groups[pt1] = nextGroupId
                groups[pt2] = nextGroupId
                sizes[nextGroupId] = 2
                nextGroupId++
            }
            
            if (sizes.values.max() == input.size) {
                return (pt1.first * pt2.first).toInt()
            }
        }

        return -1
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("2025/Day08_test")
    check(part1(testInput, 10) == 40)
    check(part2(testInput) == 25272)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2025/Day08")
    part1(input, 1000).println()
    part2(input).println()
}
