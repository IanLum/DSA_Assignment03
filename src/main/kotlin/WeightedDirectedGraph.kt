package org.example

class WeightedDirectedGraph<VertexType> : Graph<VertexType> {
    private var vertices: MutableSet<VertexType> = mutableSetOf()
    private var edges: MutableMap<VertexType, MutableMap<VertexType, Double>> = mutableMapOf()

    override fun getVertices(): Set<VertexType> {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun getEdges(from: VertexType): Map<VertexType, Double> {
        TODO("Not yet implemented")
    }

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
}