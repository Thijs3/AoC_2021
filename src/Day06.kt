import java.math.BigInteger

fun main() {

    val dp = MutableList(257) { MutableList(9) { BigInteger.ZERO } }

    for (i in 0..256) {
        for (j in 8 downTo 0) {
            if (i <= j) {
                dp[i][j] = BigInteger.ONE
            } else {
                dp[i][j] = dp[i - j - 1][6] + dp[i - j - 1][8]
            }
        }
    }

    fun part1(input: List<Int>, days: Int): BigInteger =
        input.sumOf { dp[days][it] }

    fun part2(input: List<Int>, days: Int): BigInteger =
        part1(input, days)

    val input = readIntsOneLine("Day06")
    val testInput = readIntsOneLine("Day06_test")
    check(part1(testInput, 80) == BigInteger("5934"))
    check(part2(testInput, 256) == BigInteger("26984457539"))
    println(part1(input, 80))
    println(part2(input, 256))
}
