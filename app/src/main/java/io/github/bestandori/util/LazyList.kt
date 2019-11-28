package io.github.bestandori.util

/**
 * List that creates elements only on getting it.
 * NOTE: This List is not thread-safe.
 */
class LazyList<T> private constructor(private val delegate: MutableList<T?>, private val elementCreator: (Int) -> T) : List<T?> by delegate {
    constructor(size: Int, elementCreator: (Int) -> T): this(
        (1..size).toList().map<Int, T?> { null }.toMutableList(),
        elementCreator
    )

    override fun get(index: Int): T {
        var e = delegate[index]
        if (e == null) {
            v(TAG, "get: Invoke elementCreator[$index]")
            e = elementCreator(index)
            delegate[index] = e
        }
        return e!!
    }

    companion object {
        private const val TAG = "LazyList"
    }
}

fun <T> lazyListOf(vararg creators: () -> T): LazyList<T> = LazyList(creators.size) { index ->
    creators[index]()
}
