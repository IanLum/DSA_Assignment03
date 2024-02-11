package org.example

class PriorityQueue<T>: MinPriorityQueue<T> {
    private var heap = MinHeap<T>()
    override fun isEmpty(): Boolean = heap.isEmpty()

    override fun next(): T? = heap.getMin()

    override fun adjustPriority(elem: T, newPriority: Double) {
        heap.adjustHeapNumber(elem, newPriority)
    }

    override fun addWithPriority(elem: T, priority: Double) {
        heap.insert(elem, priority)
    }
}