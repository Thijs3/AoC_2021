fun main() {
    fun part1(input: List<String>): Int {
        val l: Int = input[0].length
        val half: Int = input.size / 2
        var epsilonRate = ""
        var gamma = ""
        for (pos in 0 until l) {
            val countOnes = input.count { it[pos] == '1' }
            if (countOnes > half) {
                gamma += '1'
                epsilonRate += '0'
            } else {
                gamma += '0'
                epsilonRate += '1'
            }
        }
        return epsilonRate.toInt(2) * gamma.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val (copyInput) = input
        val oxygen = copyInput.foldIndexed(input) { i, acc, _ ->
            val (zeros, ones) = acc.partition { it[i] == '0' }
            if (zeros.size > ones.size) zeros else ones
        }.first().toInt(2)

        val co2 = copyInput.foldIndexed(input) { i, acc, _ ->
            if (acc.size == 1) acc else {
                val (zeros, ones) = acc.partition { it[i] == '0' }
                if (ones.size < zeros.size) ones else zeros
            }
        }.first().toInt(2)
        return oxygen * co2
    }

    val input = readInput("Day03")
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 27 * 36)
    check(part2(testInput) == 27 * 36)
    println(part1(input))
    println(part2(input))
}
