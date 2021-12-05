import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInts(name: String) = readInput(name).map { it.toInt() }

fun readStrAndInt(name: String) = readInput(name)
    .map { it.split(" ") }
    .map { line -> Pair(line[0], line[1].toInt()) }

fun readIntsCommaSeparated(inputLines: List<String>): List<Int> = inputLines
    .first()
    .split(",")
    .map { number ->
        number.toInt()
    }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
