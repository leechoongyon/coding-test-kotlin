package org.example.leetcode

/**
 * 1. 문제
 *  - binary array nums 주어짐
 *  - goal integer 가 주어짐
 *  - nums 의 subarray 로 goal 을 만족시켜야 함.
 *  - subarray 는 연속적인 숫자
 *  - goal 을 만족시키는 부분집합의 개수가 answer
 *
 * 1.1 제약조건
 *  1 <= nums.length <= 3 * 104
 *  nums[i] is either 0 or 1.
 *  0 <= goal <= nums.length
 *
 * 1.2 예시
 * Example 1:
 *
 * Input: nums = [1,0,1,0,1], goal = 2
 * Output: 4
 * Explanation: The 4 subarrays are bolded and underlined below:
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 *
 * Example 2:
 *
 * Input: nums = [0,0,0,0,0], goal = 0
 * Output: 15
 *
 * 1.3 예외케이스
 *  - [1], goal = 0 or 1
 *  - [0], goal = 0
 *  - [0], goal = 1 --> 이게 예외케이스일거 같은데? output = 0 이 나와야 함
 *
 * 2. 풀이
 *  - for 문 2번 돌면서 output++ 하기
 *  - 시간복잡도 n 제곱
 *
 * 3. 개선사항
 *  - cache 를 쓸 수 있을거 같은데
 */

class Num930 {
    // 기존
    fun numSubarraysWithSum1(nums: IntArray, goal: Int): Int {
        var output = 0
        var sum = 0
        for (i in 0 until nums.size) {
            sum += nums[i]
            if (sum == goal) {
                output++
            }

            for (j in i + 1 until nums.size) {
                sum += nums[j]
                if (sum == goal) {
                    output++
                }
            }
            sum = 0
        }
        return output
    }

    // 개선
    /**
     * j 가 i 부터 시작하면 중복 코드 삭제가 되네
     */
    fun numSubarraysWithSum2(nums: IntArray, goal: Int): Int {
        var output = 0
        for (i in 0 until nums.size) {
            var sum = 0 // 각 루프마다 sum을 초기화
            for (j in i until nums.size) {
                sum += nums[j]
                if (sum == goal) {
                    output++
                }
            }
        }
        return output
    }

    /**
     * 기존 sliding window 를 사용하면 목표에 도달하면 left pointer 를 앞으로 당김.
     * 이럴 경우 원하는 goal 을 못구할 수 있음. 왜냐하면 순회하다 0을 만나면 left 를 이동할 필요 없이 만족하기 때문
     *  -> 여기까진 이해됐음
     *
     * atMost(nums, goal) - atMost(nums, goal - 1) 의미하는 바는
     * goal 보다 작은 부분배열을 제거해서 합이 goal 부분배열을 구하는 것
     *
     * atMost(nums, goal) -> nums 중에 goal 이하의 부분배열의 개수를 전부 구함
     * atMost(nums, goal - 1) -> nums 중에 goal - 1 이하의 부분배열의 개수를 전부 구함
     *
     * atMost(nums, goal) - atMost(nums, goal - 1) -> goal 과 일치하는 부분배열의 개수만 남음
     *
     */
    fun numSubarraysWithSum(nums: IntArray, goal: Int): Int {
        return atMost(nums, goal) - atMost(nums, goal - 1)
    }

    /**
     *  atMost function 은 nums 를 순회하면서 부분배열의 개수를 구함
     *  순회하면서 goal 이 초과하는건 제외시킴. 동시에 pointer 를 앞으로 이동시킴
     *  이동시켜야하는 이유는 window 를 유지한 상태에서 다음것을 구해야하니
     *
     *  여기서 알아둬야하는 점은 sliding window 의 개념이 포인터를 이동시키면서 원하는 것을 구하는 것이고
     *  원하는 것을 찾았을 때, pointer (tail) 를 움직이는 것.
     *  또 하나의 pointer (head) 는 순회하는 것
     */
    private fun atMost(nums: IntArray, goal: Int): Int {
        var tail = 0   // sliding window 에서 조건을 만족했을 때, 움직이는 것
        var sum = 0
        var result = 0
        for (head in nums.indices) {
            sum += nums[head]

            // goal 을 초과하는건 구하지 않는다. tail 은 head 를 넘어설수 없음.
            while (sum > goal && tail <= head) {
                sum -= nums[tail]   // goal 을 초과하는건 필요없으니 제거한다.
                tail++              // goal 을 초과했으니 pointer 를 앞으로 당긴다.
            }
            // 하위배열 총 개수를 구한다.
            result += head - tail + 1
        }
        return result
    }

    fun numSubarraysWithSum5(nums: IntArray, goal: Int): Int {
        var goalMutable = goal
        var i = 0
        var count = 0
        var res = 0
        for (j in nums.indices) {
            if (nums[j] == 1) {
                goalMutable--
                count = 0
            }

            while (goalMutable == 0 && i <= j) {
                goalMutable += nums[i]
                i++
                count++
                if (i > j - goalMutable + 1)
                    break
            }

            while (goalMutable < 0) {
                goalMutable += nums[i]
                i++
            }

            res += count
        }
        return res
    }
}

fun main() {
    println(4 == Num930().numSubarraysWithSum(intArrayOf(1, 0, 1, 0, 1), 2))
//    println(15 == Num930().numSubarraysWithSum(intArrayOf(0, 0, 0, 0, 0), 0))
}