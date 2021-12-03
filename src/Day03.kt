fun main() {
    fun part1(input: List<String>): Int {
        val l = input[0].length
        var epsilonRate = ""
        var gamma = ""
        for (pos in 0 until l) {
            var countOnes = 0
            var countZeros = 0
            for (row in input) {
                if (row[pos] == '0') countZeros += 1 else countOnes += 1
            }
            if (countOnes > countZeros) {
                gamma += '1'
                epsilonRate += '0'
            } else {
                gamma += '0'
                epsilonRate += '1'
            }
        }
        return Integer.parseInt(epsilonRate, 2) * Integer.parseInt(gamma, 2)
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

