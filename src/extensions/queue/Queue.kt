package extensions.queue

import extensions.doublylinkedlist.doublyLinkedListOf

class Queue<T> {

    private val linkedList = doublyLinkedListOf<T>()

    fun enqueue(element: T) = linkedList.add(element)
    fun dequeue(): T = linkedList.removeAt(0)
    override fun toString() = linkedList.toString()

}

fun <T> queueOf(vararg elements: T): Queue<T> {
    val queue = Queue<T>()
    for (element in elements) {
        queue.enqueue(element)
    }
    return queue
}