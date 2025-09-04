package dep.model

class DepModel(
    val method: Method,
    val dependency: Any,
) {
    enum class Method(val value: String) {
        IMPL("implementation"),
        DEBUG_IMPL("debugImplementation"),
        KSP("ksp"),
    }
}