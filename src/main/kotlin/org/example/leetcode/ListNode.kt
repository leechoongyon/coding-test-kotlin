package org.example.leetcode

class ListNode(var `val`: Int) {
    var next: ListNode? = null

//    override fun toString(): String {
//        return "ListNode(`val`=$`val`, next=$next)"
//    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListNode

        if (`val` != other.`val`) return false
        return next == other.next
    }

    override fun hashCode(): Int {
        var result = `val`
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }
}

fun getListNodeSize(head: ListNode?): Int {
    var size = 1
    var current = head
    while (current?.next != null) {
        current = current.next
        size++
    }
    return size
}

fun addNode(head: ListNode?, `val`: Int): ListNode {
    val newNode = ListNode(`val`)
    if (head == null) {
        return newNode
    }
    var current = head
    while (current?.next != null) {
        current = current.next
    }
    current?.next = newNode

    return head
}

fun findNode(head: ListNode?, position: Int): ListNode? {
    var current = head
    var count = 0

    while (current != null) {
        if (count == position) {
            return current
        }
        count++
        current = current.next
    }
    return null
}

fun main() {
    var head = ListNode(1)
    addNode(head, 2)
    addNode(head, 3)
    println(head)
    println(getListNodeSize(head))
}