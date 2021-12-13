import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInts(name: String) = readInput(name).map { it.toInt() }

fun readIntsOneLine(name: String) = readInput(name)[0].split(',').map { it.toInt() }
fun readBigIntsOneLine(name: String) = readInput(name)[0].split(',').map { it.toBigInteger() }

fun readStrAndInt(name: String) = readInput(name)
    .map { it.split(" ") }
    .map { line -> Pair(line[0], line[1].toInt()) }

fun readIntsCommaSeparated(inputLines: List<String>): List<Int> = inputLines
    .first()
    .split(",")
    .map { number ->
        number.toInt()
    }
fun readGrid(name: String): List<List<Int>> = readInput(name).map { line -> line.chunked(1).map { it.toInt() } }

fun readMutableGrid(name: String): MutableList<MutableList<Int>> = readInput(name).map { line ->
    line.chunked(1)
        .map { it.toInt() }.toMutableList()
}.toMutableList()
/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
