import org.example.PriorityQueue
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PriorityQueueTest {

    @Test
    fun isEmpty() {
        val q = PriorityQueue<Int>()
        assertTrue(q.isEmpty())
        q.addWithPriority(1, 1.0)
        assertFalse(q.isEmpty())
        q.next()
        assertTrue(q.isEmpty())
    }

    @Test
    operator fun next() {
        val q = PriorityQueue<Int>()
        assertNull(q.next())
        q.addWithPriority(1, 1.0)
        assertEquals(1, q.next())
        assertNull(q.next())
    }

    @Test
    fun adjustPriority() {
        val q = PriorityQueue<Int>()
        q.addWithPriority(1, 1.0)
        q.addWithPriority(2, 2.0)
        q.adjustPriority(2, 0.5)
        assertEquals(2, q.next())
        assertEquals(1, q.next())
    }

    @Test
    fun addWithPriority() {
        val q = PriorityQueue<Int>()
        q.addWithPriority(2, 2.0)
        q.addWithPriority(1, 1.0)
        q.addWithPriority(3, 3.0)
        assertEquals(1, q.next())
        assertEquals(2, q.next())
        assertEquals(3, q.next())
    }
}