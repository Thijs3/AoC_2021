import java.math.BigInteger

fun main() {
    val dp = MutableList(257) { MutableList(9) { BigInteger.ZERO } }

    for (i in 0..256) {
        for (j in 0..8) {
            if (i <= j) dp[i][j] = BigInteger.ONE else dp[i][j] = dp[i - j - 1][6] + dp[i - j - 1][8]
        }
    }

    fun solve(input: List<Int>, days: Int): BigInteger = input.sumOf { dp[days][it] }

    val input = readIntsOneLine("Day06")
    val testInput = readIntsOneLine("Day06_test")
    check(solve(testInput, 80) == BigInteger("5934"))
    check(solve(testInput, 256) == BigInteger("26984457539"))
    println(solve(input, 80))
    println(solve(input, 256))
}
