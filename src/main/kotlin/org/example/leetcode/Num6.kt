package org.example.leetcode

/**
 * 1. 문제 이해
 * - String s 가 주어지면 그것을 지그재그패턴으로 쓴다.
 * - PAYPALISHIRING 가 주어졌다면, 아래와 같이 지그재그로 표현해서 PAHNAPLSIIGYIR 추출해야 함
 * - numRows 는 높이를 의미함.
 *
 *  P   A   H   N
 *  A P L S I I G
 *  Y   I   R
 *
 *
 * 2. 제약 조건
 * - 1 <= s.length <= 1000
 * - s consists of English letters (lower-case and upper-case), ',' and '.'.
 * - 1 <= numRows <= 1000
 *
 * 3. 일반 풀이
 * - 규칙이 있을거 같은데.
 * - s[0] + s[0+4] + s[4+4] + s[12] -> s[16] 은 없음
 * - s[1] + s[3] + s[5] + s[7]] + s[9] + s[11] + s[13] -> s[15] 없음
 * - s[2] + s[6] + s[10] -> s[14] 없음
 *
 *
 * row = 3
 * P   A   H   N    -> 0 + 4 + 8 + 12 -> +4 반복
 * A P L S I I G    -> 1 + 3 + 5 + 7 + 9 + 11 + 13  -> +2 반복
 * Y   I   R        -> 2 + 6 + 10 -> +4 반복
 *
 *
 * PAYPALISHIRING rows = 4
 * P     I    N     -> 0 + 6 + 12   -> +6 반복
 * A   L S  I G     -> 1 + 5 + 7 + 11 + 13   -> +4, +2 반복
 * Y A   H R        -> 2 + 4 + 8 + 10       -> +2 , +4 반복
 * P     I          -> 3 + 9            -> +6 반복
 *
 *
 * 01234567890 rows = 5
 * 0     8       -> 0 + 8   -> +8 반복
 * 1   7 9         -> 1 + 7 + 9 -> +6, +2 반복
 * 2  6  0          -> 2 + 6 + 10 -> 4 반복
 * 3 5   11           -> 3 + 5 + 11 -> +2, +6 반복
 * 4     12             ->
 *
 *
 * 0123456789012345 rows = 6
 * 0       0
 * 1     9
 * 2   8
 * 3  7
 * 4 6
 * 5
 *
 *
 *
 * 규칙 나옴
 * 맨 위, 맨 아래는 내려간만큼 올라와야함. 즉, rows -1 만큼 올라갔다 내려가니 (rows - 1) * 2 반복
 * 한칸 내려가면 rows -2 만큼 내려갔다 올라와야 함.
 *
 * rows = 5 라고 가정
 * 첫번째 줄은 (rows-1) * 2 로 String s length 안넘을 떄까지 추출
 * -> 4 * 2 겠지.
 * 두번째 줄은 (rows-2) 만큼 내려갔다 올라오기. 그런 뒤, (rows - 4) 만큼 올라가기.
 * -> 3 * 2 내려갔다, 1 * 2 만큼 올라가야 함.
 * 세번째 줄은 (rows-3) 만큼 내려갔다 올라오기. (rows-3) 올라갔다 내려오기
 * -> 2 * 2 , 2 * 2
 * 네번째 줄은 (rows-4) 내려갔다 올라오기. (rows-2) 올라갔다 내려오기
 * -> 1 * 2 , 3 * 2
 * 다섯번째 줄은 (rows-1) * 2 로 String s length 안넘을 떄까지 추출
 * -> 마지막줄이니 +8 반복
 *
 */

class Num6 {
    // 1차 답안
    fun convert2(s: String, numRows: Int): String {
        if (s.length <= 1 || numRows == 1) {
            return s
        }
        val result = StringBuilder()
        val sLen = s.length - 1
        var sIdx = 0
        var downIdx = numRows
        var upIdx = 1
        for (row in 0 until numRows) {
            if (row > sLen) {
                break
            }
            result.append(s[row])
            sIdx = row

            // 처음과 끝을 찾고, (rows-1) * 2 반복
            if (row == 0 || row == numRows - 1) {
                while (true) {
                    // idx 역할은 현재 위치이며, 누적을 해줌
                    sIdx += (numRows - 1) * 2
                    if (sIdx <= sLen) {
                        result.append(s[sIdx])
                    } else {
                        break
                    }
                }
            } else {
                upIdx++
                downIdx--
                while (true) {
                    sIdx += (numRows - upIdx) * 2
                    if (sIdx <= sLen) {
                        result.append(s[sIdx])
                    } else {
                        break
                    }

                    sIdx += (numRows - downIdx) * 2
                    if (sIdx <= sLen) {
                        result.append(s[sIdx])
                    } else {
                        break
                    }
                }
            }
        }

        return result.toString()
    }

    // 개선 답안
    /**
     * 맨처음 맨끝은 사이클이 2 * numRows - 2 cycle 이 수행
     * next 는 current + cycleLen - 2 * row 규칙이 있네.
     * 즉, row -> next (존재하면) -> row + 2 * numRows - 2 반복되네.
     */
    fun convert(s: String, numRows: Int): String {
        if (s.length <= 1 || numRows == 1) {
            return s
        }

        val result = StringBuilder()
        val sLen = s.length
        val cycleLen = 2 * numRows - 2

        for (row in 0 until numRows) {
            // current 는 현재 s 의 위치
            var current = row
            // 현재 s 위치가 sLen 을 넘어서면 다음
            while (current < sLen) {
                result.append(s[current])
                // next 는 처음과 맨끝을 제외하면 있음
                val next = current + cycleLen - 2 * row
                if (row != 0 && row != numRows - 1 && next < sLen) {
                    result.append(s[next])
                }
                // cycleLen 을 구하는게 핵심 - cycleLen 은 현재 위치에서 2 * numrows - 2 네
                current += cycleLen
            }
        }

        return result.toString()
    }
}

fun main() {
//    println("PAHNAPLSIIGYIR" == Num6().convert("PAYPALISHIRING", 3))
//    println("PINALSIGYAHRPI" == Num6().convert("PAYPALISHIRING", 4))
//    println("A" == Num6().convert("A", 1))
//    println("AB" == Num6().convert("AB", 1))
//    println("ACB" == Num6().convert("ABC", 2))
    println("08179260354" == Num6().convert("01234567890", 5))
//    println("AB" == Num6().convert("AB", 3))
}