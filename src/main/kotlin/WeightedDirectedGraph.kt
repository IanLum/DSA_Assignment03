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
    fun dijkstra(start: VertexType, dest: VertexType): List<VertexType>? {
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
}