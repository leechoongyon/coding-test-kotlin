package org.example.leetcode

/**
 * 1. 문제 이해
 *  - String s 가 주어질 때, 앞뒤로 동일한 알파벳만 자를수 있음.
 *  - cac 이면 1번 앞뒤로 c 를 잘라서 1번 자를수 있음.
 *  - 남아 있는 알파벳 개수를 구하는 것임. 문제를 완전 잘못이해했네
 *
 *  제약 조건
 *  1 <= s.length <= 105
 *  s only consists of characters 'a', 'b', and 'c'.
 *
 * 2. brute-force
 *  - 앞뒤로 자르고 남은 문자수를 구하는 것임.
 *  -
 *
 */

class Num1750 {
    fun minimumLength(s: String): Int {
        var left = 0
        var right = s.length - 1
        // 처음에 같아야 자를수 있음
        while (left < right && s[left] == s[right]) {
            val c = s[left]
            // c 를 가지고 어디까지 자를수 있는지 체크.
            while (left <= right && c == s[left]) {
                left += 1
            }
            // c 를 가지고 어디까지 자를수 있는지 체크.
            while (left <= right && c == s[right]) {
                right -= 1
            }
        }
        return right - left + 1
    }
}

fun main() {
    println(Num1750().minimumLength("cac") == 1)
    println(Num1750().minimumLength("cc") == 0)
    println(Num1750().minimumLength("ca") == 2)
    println(Num1750().minimumLength("aabccabba") == 3)
    println(Num1750().minimumLength("ccc") == 0)
    println(Num1750().minimumLength("cabaabac") == 0)
    println(Num1750().minimumLength("cabacbac") == 2)
}


