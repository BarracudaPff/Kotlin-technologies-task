package tasks.task3

fun main(args: Array<String>) {
    val list = listOf<Long>(1, 2, 3, 4)
    println(solve(list, -4, 8))
}

fun solve(vals: List<Long>, l: Int, k: Int): List<List<Long>> {
    var res: List<List<Long>> = listOf()
    for (i: Int in l..k) {
        res += if (i<1 || i>vals.size)
            listOf()
        else
            listOf(help(vals, i))
    }
    return res
}

fun help(vals: List<Long>, k: Int): List<Long> {
    var res: List<Long> = listOf()
    if (k > vals.size)
        return listOf()
    for (i: Int in vals.indices) {
        res = if (k == 1)
            res + listOf(vals[i])
        else
            res + help(vals.drop(i + 1), k - 1).map { it * vals[i] }
    }

    return res
}