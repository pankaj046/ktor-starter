package app.pankaj.utils

/**
 * A wrapper class for HTTP request parameters.
 *
 * @property name the parameter name
 */
open class Param(private val name: String = randomString(5)) {
    override fun toString(): String = name

    /**
     * Converts the receiver string to the specified type, or returns null if the conversion fails.
     *
     * @return the converted value, or null if the conversion fails
     */
    inline fun <reified T: Any> String?.toTypeOrNull(): T? {
        return when (T::class) {
            Int::class -> this?.toIntOrNull()
            Double::class -> this?.toDoubleOrNull()
            Float::class -> this?.toFloatOrNull()
            Long::class -> this?.toLongOrNull()
            Boolean::class -> this?.toBooleanStrictOrNull()
            String::class -> this
            else -> null
        } as? T
    }
}

/**
 * A subclass of [Param] that is used for query parameters.
 *
 * @property name the parameter name
 */
class QueryParam(name: String): Param(name)


private val charPool: List<Char> = ('a' .. 'z') + ('A' .. 'Z') + ('0' .. '9')
fun randomString(length: Int): String {
    if (length == 0) return ""
    if (length == 1) return charPool.random().toString()

    return List(length) { charPool.random() }.joinToString("")
}
