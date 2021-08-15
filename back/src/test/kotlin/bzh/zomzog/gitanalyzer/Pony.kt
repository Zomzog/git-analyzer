package bzh.zomzog.gitanalyzer

import kotlin.experimental.and

//fun main(args : Array<String>) {
//    val input = generateSequence(::readLine)
//    val lines = input.toList()
//
//    System.err.println(lines)
//    val count = mutableListOf(lines).removeAt(0).filter { it -> it.length > 5 }
//            .filter {
//                try {
//                    Integer.parseInt(it.takeLast(5))
//                    true
//                } catch (e: NumberFormatException) {
//                    false
//                }
//            }
//            .count()
//
//    println(count)
//}
//
//fun main(args : Array<String>) {
//    val input = generateSequence(::readLine)
//    val lines = input.toList()
//
//    System.err.println(lines)
//    val total = lines[0].toInt()
//    val count = mutableListOf(lines).removeAt(0)
//            .map { it.split(":") }
//            .map { it[0].toInt() }
//            .filter { it > 8 }
//            .filter { it < 20 }
//            .count()
//    if (count > total/2) {
//        println("OK")
//    } else {
//        println("SUSPICIOUS")
//    }
//}


//fun main(args : Array<String>) {
//    val input = generateSequence(::readLine)
//    val lines = input.toList()
//
//    val l = lines.toMutableList()
//    val total = l.removeAt(0).toInt()
//    System.err.println(l)
//    val all = l
//            .map { it.split(" ") }
//            .groupBy({it[1].toInt()}, {it[0].toInt()})
//
//    System.err.println(all)
//    val level1 = all[0]!!
//    System.err.println(level1)
//    val level2 = level1.flatMap { all.getOrDefault(it, emptyList()) }
//    System.err.println(level2)
//    val level3 = level2.flatMap { all.getOrDefault(it, emptyList()) }
//    val level4 = level3.flatMap { all.getOrDefault(it, emptyList()) }
//    val level5 = level4.flatMap { all.getOrDefault(it, emptyList()) }
//    val level6 = level5.flatMap { all.getOrDefault(it, emptyList()) }
//    val level7 = level6.flatMap { all.getOrDefault(it, emptyList()) }
//    val level8 = level7.flatMap { all.getOrDefault(it, emptyList()) }
//    val level9 = level8.flatMap { all.getOrDefault(it, emptyList()) }
//    println("1 ${level1.size} ${level2.size} ${level3.size} ${level4.size} ${level5.size} ${level6.size} ${level7.size} ${level8.size} ${level9.size}")
//}

fun main(args: Array<String>) {
//    val input = generateSequence(::readLine)
    val lines = listOf("5 4", "11 22 33 44 55", "1 3", "0 1", "2 2", "2 4")
    val expected = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0"

    System.err.println(lines)
    val yolo = doStuff(lines)

    System.err.println(expected == yolo)

    println(yolo)


    System.err.println("-------------------")

    val line2 = listOf("5 10", "2 78 49 47 94", "1 3", "1 1", "0 2", "0 4", "0 1", "4 4", "0 1", "0 1", "0 1", "4 4")
    val expected2 = "0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 4 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0"
    cache.clear()
    val doStuff = doStuff(line2)
    System.err.println(doStuff)
    System.err.println(expected2)
    System.err.println(expected2 == doStuff)
}

private fun doStuff(lines: List<String>): String {
    val l = lines.toMutableList()
    val (N, M) = l.removeAt(0).split(" ")
    val keys = l.removeAt(0).split(" ").map { it.toInt() }

    val result = l.map { it.split(" ").map { i -> i.toInt() } }
            .sortedBy { it[1] - it[0] }
            .map { xor(keys, it[0], it[1]) }
    System.err.println(result)


    val yolo = (0..255).map {
        result.filter { r -> it == r }
                .count()
    }.joinToString(" ")
    return yolo
}

val cache = mutableMapOf<Int, MutableMap<Int, Int>>()

fun xor(keys: List<Int>, L: Int, R: Int): Int {
    val startCache = cache.getOrPut(L, { mutableMapOf(L to keys[L]) })
    val endCache = startCache[R]
    return if (null != endCache) {
        endCache
    } else {
        val alreadyCalculated = startCache.keys.filter { it <= R }.max()!!
        val partialResult = xor(keys, alreadyCalculated + 1, R)
        val result = startCache[alreadyCalculated]!!.xor(partialResult)
        startCache[R] = result
        result
    }
}

//fun xor(keys: List<Int>, L: Int, R: Int): Int {
//    val startCache = cache.getOrPut(L, { mutableMapOf() })
//    val endCache = startCache.get(R)
//    if (null != endCache) {
//        return endCache
//    } else {
//        var currentXor = keys[L]
//        var currentStart = L
//
//        val alreadyCalculated = startCache.keys.filter { it < R }.max()
//        if (null != alreadyCalculated) {
//            currentXor = startCache[alreadyCalculated]!!
//            currentStart = alreadyCalculated + 1
//        }
//        var partialXor = keys[currentStart]
//        (currentStart + 1..R).forEach {
//            partialXor = partialXor.xor(keys[it])
//        }
//        cache.getOrPut(currentStart, { mutableMapOf() })[R] = partialXor
//        return if (currentStart != L) {
//            currentXor.xor(partialXor)
//        } else {
//            partialXor
//        }
//    }
//}


