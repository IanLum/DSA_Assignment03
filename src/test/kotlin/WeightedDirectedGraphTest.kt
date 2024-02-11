import org.example.WeightedDirectedGraph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class WeightedDirectedGraphTest {

    @Test
    fun getVertices() {
        val g = WeightedDirectedGraph<Int>()
        assertEquals(emptySet<Int>(), g.getVertices())
        g.addVertex(1)
        assertEquals(setOf(1), g.getVertices())
        g.addVertex(2)
        assertEquals(setOf(1, 2), g.getVertices())
    }

    @Test
    fun clear() {
        val g = WeightedDirectedGraph<Int>()
        g.addVertex(1)
        g.addVertex(2)
        g.addEdge(1,2,3.0)
        g.clear()
        assertEquals(emptySet<Int>(), g.getVertices())
        assertEquals(emptyMap<Int, Double>(), g.getEdges(1))
    }

    @Test
    fun getEdges() {
        val g = WeightedDirectedGraph<Int>()
        g.addVertex(1)
        g.addVertex(2)
        g.addEdge(1,2,3.0)
        assertEquals(mapOf(2 to 3.0), g.getEdges(1))
        g.addVertex(3)
        g.addEdge(1,3,4.0)
        assertEquals(mapOf(2 to 3.0, 3 to 4.0), g.getEdges(1))
    }

    @Test
    fun addEdge() {
        val g = WeightedDirectedGraph<Int>()
        g.addVertex(1)
        g.addVertex(2)
        g.addEdge(1,2,3.0)
        // check directionality
        assertEquals(mapOf(2 to 3.0), g.getEdges(1))
        assertEquals(emptyMap<Int, Double>(), g.getEdges(2))
        // check edge overwrite
        g.addEdge(1,2,4.0)
        assertEquals(mapOf(2 to 4.0), g.getEdges(1))
        // check multiple edges
        g.addVertex(3)
        g.addEdge(1,3,5.0)
        assertEquals(mapOf(2 to 4.0, 3 to 5.0), g.getEdges(1))
    }

    @Test
    fun addVertex() {
        val g = WeightedDirectedGraph<Int>()
        g.addVertex(1)
        assertEquals(setOf(1), g.getVertices())
        g.addVertex(2)
        assertEquals(setOf(1, 2), g.getVertices())
    }
}