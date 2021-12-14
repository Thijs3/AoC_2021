import java.math.BigInteger

fun main() {
    data class Rule(val input: Pair<Char, Char>, val output: Pair<Pair<Char, Char>, Pair<Char, Char>>)
    fun readPolymer(input: List<String>): String = input[0]

    fun readRules(input: List<String>): List<Rule> {
        val ruleLines = input.filter { it.contains("->") }
        val rulePairs = ruleLines.map { it.split("->").map { s -> s.trim() } }
        val rules = rulePairs.map {
            Rule(
                Pair(it[0][0], it[0][1]),
                Pair(Pair(it[0][0], it[1][0]), Pair(it[1][0], it[0][1]))
            )
        }
        return rules
    }

    fun initialize(polymer: String, rules: List<Rule>): Map<Pair<Char, Char>, BigInteger> {
        val polymerPairs = polymer.zipWithNext()
        val allPairs = listOf(
            polymerPairs, rules.map { it.input },
            rules.map { it.output.first }, rules.map { it.output.second }
        )
            .flatten().toSet().toList()
        val counts = allPairs.associateWith { BigInteger.ZERO }.toMutableMap()
        for (pair in polymerPairs) counts[pair] = counts[pair]!!.plus(BigInteger.ONE)
        return counts.toMap()
    }

    fun applyRules(counts: Map<Pair<Char, Char>, BigInteger>, rules: List<Rule>): Map<Pair<Char, Char>, BigInteger> {
        val copyCounts = mutableMapOf<Pair<Char, Char>, BigInteger>()
        for (c in counts) copyCounts[c.key] = c.value
        for (rule in rules) {
            if (counts[rule.input]!! != BigInteger.ZERO) {
                copyCounts[rule.output.first] = copyCounts[rule.output.first]!!.plus(counts[rule.input]!!)
                copyCounts[rule.output.second] = copyCounts[rule.output.second]!!.plus(counts[rule.input]!!)
                copyCounts[rule.input] = copyCounts[rule.input]!!.minus(counts[rule.input]!!)
            }
        }
        return copyCounts.toMap()
    }

    fun recurseRules(times: Int, counts: Map<Pair<Char, Char>, BigInteger>, rules: List<Rule>): Map<Pair<Char, Char>, BigInteger> {
        var c = counts
        for (i in 0 until times) {
            c = applyRules(c, rules)
        }
        return c
    }

    fun computeResult(counts: Map<Pair<Char, Char>, BigInteger>): BigInteger {
        val letters = mutableMapOf<Char, BigInteger>()
        for (count in counts) {
            letters[count.key.first] = count.value + (letters[count.key.first] ?: BigInteger.ZERO)
            letters[count.key.second] = count.value + (letters[count.key.second] ?: BigInteger.ZERO)
        }
        val result = letters.map { it.value.plus(BigInteger.ONE).div(BigInteger.TWO) }
        return result.maxOf { it } - result.minOf { it }
    }

    fun solve(input: List<String>, steps: Int): BigInteger {
        val polymer = readPolymer(input)
        val rules = readRules(input)
        val counts = initialize(polymer, rules)
        val countsAfter = recurseRules(steps, counts, rules)
        return computeResult(countsAfter)
    }

    fun part1(input: List<String>): BigInteger = solve(input, 10)

    fun part2(input: List<String>): BigInteger = solve(input, 40)

    val input = readInput("Day14")
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588.toBigInteger())
    check(part2(testInput) == 2188189693529.toBigInteger())
    println(part1(input))
    println(part2(input))
}
