import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    data class TargetZone(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)
    fun readTZ(input: String): TargetZone {
        val output = input.split(": ")[1].split(", ")
            .map { it.split("=")[1].split("..") }
            .flatten()
            .map { it.toInt() }
        return TargetZone(output[0], output[1], output[2], output[3])
    }

    fun part1(input: String): Int = (- readTZ(input).minY).let { it * (it - 1) / 2 }

    fun part2(input: String): Int {
        val targetZone = readTZ(input)
        var answer = 0
        val maxSteps = maxOf(abs(targetZone.minY * 2), (targetZone.maxX))
        val xRange = targetZone.minX..targetZone.maxX
        val yRange = targetZone.minY..targetZone.maxY
        val startX = floor(sqrt(targetZone.minX.toDouble())).toInt()
        for (x in startX..(targetZone.maxX + 1)) {
            for (y in (targetZone.minY)..(-(targetZone.minY-1))) {
                for (step in 0..maxSteps) {
                    val preX = if (step > x) (x / 2.00) * (x + 1) else (step / 2.00) * (x + (x - (step - 1)))
                    val px = preX.toInt()
                    val py = ((step / 2.00) * (y + (y - (step - 1)))).toInt()
                    if (px in xRange && py in yRange) {
                        answer += 1
                        break
                    }
                    if (py < targetZone.minY || x > targetZone.maxX) {
                        break
                    }
                }
            }
        }
        return answer
    }

    val input = readInput("Day17").first()
    val testInput = readInput("Day17_test").first()
    check(part1(testInput) == 45)
    check(part2(testInput) == 112)
    println(part1(input))
    println(part2(input))
}
