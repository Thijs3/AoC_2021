import kotlin.math.abs

fun main() {
    data class Beacon(val coordinates: MutableList<Int>)
    data class Rotation(val X: List<Int>, val Y: List<Int>, val Z: List<Int>)
    data class Scanner(val beacons: MutableList<Beacon>)

    val allRotations: List<Rotation> = listOf(
        Rotation(listOf(1, 0, 0), listOf(0, 1, 0), listOf(0, 0, 1)),
        Rotation(listOf(1, 0, 0), listOf(0, -1, 0), listOf(0, 0, -1)),
        Rotation(listOf(1, 0, 0), listOf(0, 0, 1), listOf(0, -1, 0)),
        Rotation(listOf(1, 0, 0), listOf(0, 0, -1), listOf(0, 1, 0)),
        Rotation(listOf(-1, 0, 0), listOf(0, 1, 0), listOf(0, 0, -1)),
        Rotation(listOf(-1, 0, 0), listOf(0, -1, 0), listOf(0, 0, 1)),
        Rotation(listOf(-1, 0, 0), listOf(0, 0, 1), listOf(0, 1, 0)),
        Rotation(listOf(-1, 0, 0), listOf(0, 0, -1), listOf(0, -1, 0)),
        Rotation(listOf(0, 1, 0), listOf(-1, 0, 0), listOf(0, 0, 1)),
        Rotation(listOf(0, 1, 0), listOf(1, 0, 0), listOf(0, 0, -1)),
        Rotation(listOf(0, 1, 0), listOf(0, 0, -1), listOf(-1, 0, 0)),
        Rotation(listOf(0, 1, 0), listOf(0, 0, 1), listOf(1, 0, 0)),
        Rotation(listOf(0, -1, 0), listOf(1, 0, 0), listOf(0, 0, 1)),
        Rotation(listOf(0, -1, 0), listOf(0, 0, 1), listOf(-1, 0, 0)),
        Rotation(listOf(0, -1, 0), listOf(-1, 0, 0), listOf(0, 0, -1)),
        Rotation(listOf(0, -1, 0), listOf(0, 0, -1), listOf(1, 0, 0)),
        Rotation(listOf(0, 0, 1), listOf(0, 1, 0), listOf(-1, 0, 0)),
        Rotation(listOf(0, 0, 1), listOf(-1, 0, 0), listOf(0, -1, 0)),
        Rotation(listOf(0, 0, 1), listOf(0, -1, 0), listOf(1, 0, 0)),
        Rotation(listOf(0, 0, 1), listOf(1, 0, 0), listOf(0, 1, 0)),
        Rotation(listOf(0, 0, -1), listOf(0, 1, 0), listOf(1, 0, 0)),
        Rotation(listOf(0, 0, -1), listOf(1, 0, 0), listOf(0, -1, 0)),
        Rotation(listOf(0, 0, -1), listOf(0, -1, 0), listOf(-1, 0, 0)),
        Rotation(listOf(0, 0, -1), listOf(-1, 0, 0), listOf(0, 1, 0)),

    )

    fun readScanners(input: List<String>): List<Scanner> {
        val temp = mutableListOf<List<String>>()
        var start = 1
        var end: Int
        for (i in input.withIndex()) {
            if (i.value.isBlank()) {
                end = i.index
                temp.add(input.subList(start, end))
                start = i.index + 2
            }
        }
        temp.add(input.subList(start, input.lastIndex + 1))
        return temp.map { scanner ->
            Scanner(
                scanner.map { beacon ->
                    Beacon(beacon.split(",").map { it.toInt() }.toMutableList())
                }
                    .toMutableList()
            )
        }
    }

    fun rotate(beacon: Beacon, rotation: Rotation): Beacon {
        val coordinates = beacon.coordinates.toList().toMutableList()
        val (x, y, z) = coordinates
        coordinates[0] = x * rotation.X[0] + y * rotation.Y[0] + z * rotation.Z[0]
        coordinates[1] = x * rotation.X[1] + y * rotation.Y[1] + z * rotation.Z[1]
        coordinates[2] = x * rotation.X[2] + y * rotation.Y[2] + z * rotation.Z[2]
        return Beacon(coordinates)
    }

    fun findShift(beacon: Beacon, other: Beacon): List<Int> = listOf(
        other.coordinates[0] - beacon.coordinates[0],
        other.coordinates[1] - beacon.coordinates[1],
        other.coordinates[2] - beacon.coordinates[2]
    )

    fun shift(beacon: Beacon, shift: List<Int>) = Beacon(beacon.coordinates.mapIndexed { index, i -> i + shift[index] }.toMutableList())
    fun contains(beacons: List<Beacon>, beacon: Beacon): Boolean {
        val coordinates = beacons.map { it.coordinates }
        return (beacon.coordinates in coordinates)
    }

    fun findRotation(rotations: List<Rotation>, beacons: List<Beacon>, oldBeacons: List<Beacon>): Pair<List<Beacon>, List<Int>> {
        rotations.forEach { rotation ->
            val copyBeacons = beacons.map { rotate(it, rotation) }
            copyBeacons.forEach { new ->
                oldBeacons.forEach { old ->
                    val shift = findShift(new, old)
                    val shiftedBeacons = copyBeacons.map { copied -> shift(copied, shift) }
                    val count = shiftedBeacons.count { newBeacon -> contains(oldBeacons, newBeacon) }
                    if (count >= 12) return Pair(shiftedBeacons, shift)
                }
            }
        }
        return Pair(listOf(), listOf())
    }

    fun manhattanDistance(s1: List<Int>, s2: List<Int>): Int =
        s1.foldIndexed(0) { index, acc, _ -> acc + abs(s1[index] - s2[index]) }

    val shifts = mutableListOf(listOf(0, 0, 0))
    fun part1(input: List<String>): Int {
        val scanners = readScanners(input).toMutableList()
        while (scanners.size > 1) {
            for (scanner in scanners) {
                if (scanner != scanners[0]) {
                    val (result, shift) = findRotation(allRotations, scanner.beacons, scanners[0].beacons)
                    if (result.isNotEmpty()) {
                        shifts.add(shift)
                        scanners[0].beacons.addAll(result)
                        val copy = scanners[0].beacons.toSet().toMutableList()
                        scanners[0].beacons.clear()
                        scanners[0].beacons.addAll(copy)
                        scanners.remove(scanner)
                        println("scanners left: " + scanners.size)
                        println(scanners.map { it.beacons.size })
                        break
                    }
                }
            }
        }

        return scanners[0].beacons.size
    }

    fun largestManhattanDistance(shifts: List<List<Int>>): Int {
        val result = mutableListOf<Int>()
        for (shift in shifts) {
            for (other in shifts) {
                result.add(manhattanDistance(shift, other))
            }
        }
        return result.maxOf { it }
    }

    fun part2(): Int =
        largestManhattanDistance(shifts).also { shifts.clear() }.also { shifts.add(listOf(0, 0, 0)) }

    val input = readInput("Day19")
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 79)
    check(part2() == 3621)
    println(part1(input))
    println(part2())
}
