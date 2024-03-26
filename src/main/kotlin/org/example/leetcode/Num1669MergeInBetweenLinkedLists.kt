package org.example.leetcode


class Num1669MergeInBetweenLinkedLists {

    /**
     * 1. 문제
     *  - listNode1, 2 가 주어짐.
     *  - listNode1 의 a,b 번째를 삭제한다.
     *  - 그 사이에 listNode2 를 채운다.
     *
     * 제약 조건
     * 3 <= list1.length <= 104
     * 1 <= a <= b < list1.length - 1
     * 1 <= list2.length <= 104
     *
     * 2. 풀이 (brute-force)
     *  - list1 을 순회하면서 ListNode result 에 추가한다.
     *  - list1 a 를 만나면 list2 를 result 에 추가한다.
     *  - list1 b 까지 순회하고, 나머지를 result 에 추가한다.
     *  - 시간복잡도 O(n)
     *
     */
    fun mergeInBetween(
        list1: ListNode,
        a: Int,
        b: Int,
        list2: ListNode
    ): ListNode {
        // a 는 1이상이니 list1[0] 인덱스를 처음에 넣어준다.
        val result = ListNode(list1.`val`)
        // tail 은 항상 result 의 마지막 next 를 가리켜야 함. tail 을 사용하는 이유는 매번 끝까지 조회하는 것은 비용이 크기 때문
        var tail = result
        // idx 는 listnode1 의 index 를 의미. a 는 1이상이니 idx 는 1부터 시작
        var idx = 1

        // list1 의 current listnode
        var current = list1

        // list1 을 순회하면서 list2 를 넣는다.
        while (current.next != null) {
            // list2 를 추가할 조건
            if (a <= idx && idx <= b) {
                // 마지막을 list2 에 연결시켜줌
                tail.next = list2
                // 마지막은 list2 의 마지막을 가리켜야 함
                tail = getLastListNode(list2)
                // a 부터 b 까지 제거할 예정이니 b+1 까지 current 를 순회
                while (idx <= b) {
                    current = current.next!!
                    idx++
                }
            }

            // tail.next -> result 마지막 next 가리킴
            tail.next = ListNode(current.next!!.`val`)
            // tail.next 는 result 마지막의 next 의미. 그것을 다시 tail 붙여서 result 마지막 next 를 가리킴
            tail = tail.next!!

            current = current.next!!
            idx++
        }
        return result
    }

    // listNode 마지막 return
    fun getLastListNode(listNode: ListNode): ListNode {
        var tmp = listNode
        while (tmp.next != null) {
            tmp = tmp.next!!
        }
        return tmp
    }
}


class ListNode(var `val`: Int) {
    var next: ListNode? = null

    override fun toString(): String {
        return "ListNode(`val`=$`val`, next=$next)"
    }

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

fun main() {

    val a = ListNode(10)
    val b = ListNode(1)
    val c = ListNode(13)
    val d = ListNode(6)
    val e = ListNode(9)
    val f = ListNode(5)

    val list1 = a
    a.next = b
    b.next = c
    c.next = d
    d.next = e
    e.next = f

    val a1 = ListNode(1000000)
    val b1 = ListNode(1000001)
    val c1 = ListNode(1000002)

    val list2 = a1
    a1.next = b1
    b1.next = c1

    val r1 = ListNode(10)
    val r2 = ListNode(1)
    val r3 = ListNode(13)
    val r4 = ListNode(1000000)
    val r5 = ListNode(1000001)
    val r6 = ListNode(1000002)
    val r7 = ListNode(5)

    val result = r1
    r1.next = r2
    r2.next = r3
    r3.next = r4
    r4.next = r5
    r5.next = r6
    r6.next = r7

    println(result.equals(Num1669MergeInBetweenLinkedLists().mergeInBetween(list1, 3, 4, list2)))
}