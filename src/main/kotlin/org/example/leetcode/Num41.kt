package org.example.leetcode

import java.util.*

/**
 *
 * 1. 문제
 * - 정렬되지 않은 nums 주어짐
 * - nums 에 없는 제일 작은 양수를 return
 * - 시간복잡도는 O(n) 이고, 공간 복잡도는 O(1) 로 풀기
 *
 *  Constraints:
 *  1 <= nums.length <= 10의5승
 * -2의31승 <= nums[i] <= 2의31승 - 1
 *
 *
 * 2. 풀이
 *  - 순회하면서 0 보다 크면서 smallest 를 찾는다. int min 에 저장
 *  -
 */


class FirstMissingPositive {
    fun firstMissingPositive2(nums: IntArray): Int {
        val hashMap = HashMap<Int, Boolean>()

        for (num in nums) {
            if (num > 0) {
                hashMap[num] = true
            }
        }

        var missingNum = 1
        while (hashMap.containsKey(missingNum)) {
            missingNum++
        }

        return missingNum
    }

    /**
     * 제약 조건을 보면 1<= nums.length <= 10의 5승임
     * 숫자가 연속적으로 나와도 10의 5승 범위를 벗어나는 값이 나올수가 없음
     *
     * 전체 방향은 기존 nums 를 재활용하는 것이다.
     */
    fun firstMissingPositive(nums: IntArray): Int {
        val numsSize = nums.size
        // 범위 이외의 값을 -1 처리
        for (i in nums.indices) {
            if (nums[i] < 1 || numsSize < nums[i]) {
                nums[i] = -1
            }
        }

        // 범위 안의 값이 존재한다면 nums 에 0 으로 표시한다.
        // 예를 들면, [3,4,5] 가 nums 라면 nums[3] = nums[4] = nums[5] = 0 이다.
        // 0 이라는 것은 값이 존재한다는 것
        for (i in nums.indices) {
            marking(nums, nums[i])
        }

        // 순회하면서 nums 가 0 이 아닌 것은 반환한다.
        for (i in nums.indices) {
            if (nums[i] != 0) {
                return i + 1
            }
        }

        // nums 가 전부 채워졌다는건 10의 5승 다음 숫자를 의미
        return numsSize + 1
    }

    /**
     * 순회한 num 이 nums 범위안에 있으면 nums[num-1] = 0 으로 표시
     * ex) num 3 이고, nums[3-1] = 0 이면 3 은 존재하는 숫자라는 의미
     * nums[0] = 0 이면 1은 존재하는 숫자
     */
    fun marking(nums: IntArray, num: Int) {
        if (num > 0) {
            val tmp = nums[num - 1]
            nums[num - 1] = 0
            marking(nums, tmp)  // 중요. 기존에 존재하던 값은 위치를 찾아준다.
        }
    }
}

fun main() {
    println(3 == FirstMissingPositive().firstMissingPositive(intArrayOf(1, 2, 0)))
    println(2 == FirstMissingPositive().firstMissingPositive(intArrayOf(3, 4, -1, 1)))
    println(1 == FirstMissingPositive().firstMissingPositive(intArrayOf(7, 8, 9, 11, 12)))
}