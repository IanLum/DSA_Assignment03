package org.example

import java.io.File

/**
 * Solve Project Euler problem 83 [https://projecteuler.net/problem=83]
 * Find the least cost path when traversing a matrix of integers.
 * Construct the matrix from a text file of comma separated numbers,
 * then apply Dijkstra's algorithm.
 *
 * @param filePath The path to the text file of numbers
 * @return The cost of the least cost path as a [Double]
 */
fun pathSum(filePath: String): Double {
    val lines = File(filePath)
        .readLines()
        .map{ it.split(",")}
    val g = WeightedDirectedGraph<Pair<Int, Int>>()
    // Add vertices
    for ((y, line) in lines.withIndex()) {
        for ((x, _) in line.withIndex()) {
            g.addVertex(Pair(x,y))
        }
    }
    // Add edges
    for ((y, line) in lines.withIndex()) {
        for ((x, weight) in line.withIndex()) {
            for (neighbor in arrayOf(
                Pair(x+1, y),
                Pair(x-1, y),
                Pair(x, y+1),
                Pair(x, y-1),
            )) {
                g.addEdge(neighbor, Pair(x,y), weight.toDouble())
            }
        }
    }
    return g.dijkstraCost(
        Pair(0,0),
        Pair(lines.size-1, lines.size-1)
    )!! + lines[0][0].toDouble()
}
fun main() {
    println("Test solution: ${pathSum("data/test.txt")}")
    println("Solution: ${pathSum("data/matrix.txt")}")
}