package org.example

class WeightedDirectedGraph<VertexType> : Graph<VertexType> {
    private var vertices: MutableSet<VertexType> = mutableSetOf()
    private var edges: MutableMap<VertexType, MutableMap<VertexType, Double>> = mutableMapOf()

    override fun getVertices(): Set<VertexType> = vertices

    override fun clear() {
        vertices = mutableSetOf()
        edges = mutableMapOf()
    }

    override fun getEdges(from: VertexType): Map<VertexType, Double> =
        edges[from] ?: mutableMapOf()

    override fun addEdge(from: VertexType, to: VertexType, cost: Double) {
        if (!vertices.contains(from) || !vertices.contains(to)) {
            return
        }
        edges[from]?.also { currentAdjacent ->
            currentAdjacent[to] = cost
        } ?: run {
            edges[from] = mutableMapOf(to to cost)
        }
    }

    fun addVertex(v: VertexType): Boolean {
        if (vertices.contains(v)) {
            return false
        }
        vertices.add(v)
        return true
    }

    /**
     * Helper function for [dijkstra], traces the path back to the start
     * from the destination to find the shortest path. Runs recursively,
     * adding the previous vertex to [path], until the previous vertex
     * is [null], which indicates the start has been reached.
     *
     * @param path The path to the destination, adds elements backwards
     * starting with the destination
     * @param prev The map of vertices to the previous vertex, determined
     * by [dijkstra]
     * @return The path from the start to the destination
     */
    private fun tracePath(
        path: MutableList<VertexType>,
        prev: MutableMap<VertexType, VertexType?>
    ): List<VertexType> {
        prev[path.first()]?.also {
            path.add(0, it)
            return tracePath(path, prev)
        } ?: run {
            return path
        }
        return path
    }

    /**
     * Applies Dijkstra's algorithm, finding the shortest path from [start]
     * to [dest].
     *
     * @param start The start vertex
     * @param dest The destination vertex
     * @return The shortest path from [start] to [dest], or [null] if
     * no path exists
     */
    fun dijkstra(start: VertexType, dest: VertexType): List<VertexType>? {
        if (start == dest) return mutableListOf(dest)

        val prev = mutableMapOf<VertexType, VertexType?>()
        val dist = mutableMapOf<VertexType, Double>()
        val queue = PriorityQueue<VertexType>()
        for (v in vertices) {
            prev[v] = null
            dist[v] = Double.POSITIVE_INFINITY
            queue.addWithPriority(v, Double.POSITIVE_INFINITY)
        }
        prev[start] = null
        dist[start] = 0.0
        queue.addWithPriority(start, 0.0)

        while (!queue.isEmpty()) {
            val curr = queue.next()!!
            for ((neighbor, weight) in getEdges(curr)) {
                val distToNeighbor = dist[curr]!! + weight
                if (distToNeighbor < dist[neighbor]!!) {
                    dist[neighbor] = distToNeighbor
                    queue.adjustPriority(neighbor, distToNeighbor)
                    prev[neighbor] = curr
                }
            }
        }
        return if (prev[dest] == null)
            null
        else
            tracePath(mutableListOf(dest), prev)
    }

    /**
     * Applies Dijkstra's algorithm, finding the cost shortest path from
     * [start] to [dest].
     *
     * @param start The start vertex
     * @param dest The destination vertex
     * @return The cost of the shortest path from [start] to [dest],
     * or [null] if no path exists
     */
    fun dijkstraCost(start: VertexType, dest: VertexType): Double? {
        dijkstra(start, dest)?.let { path ->
            var cost = 0.0
            for ((idx, v) in path.dropLast(1).withIndex()) {
                cost += getEdges(v)[path[idx + 1]]!!
            }
            return cost
        } ?: return null
    }
}