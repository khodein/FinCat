package dep.base

import dep.model.DepModel
import org.gradle.api.artifacts.VersionCatalog

abstract class BaseDep {

    abstract operator fun invoke(
        libs: VersionCatalog,
    ): List<DepModel>

    private fun StringBuilder.appendPath(
        isDelimiter: Boolean,
        path: String
    ) {
        if (isDelimiter) append(DELIMITER)
        append(path)
    }

    protected fun getDependency(
        module: String,
        name: String? = null,
        version: String,
    ): String {
        return buildString {
            appendPath(
                isDelimiter = false,
                path = module
            )
            name?.let {
                appendPath(
                    isDelimiter = true,
                    path = name
                )
            }
            appendPath(
                isDelimiter = true,
                path = version
            )
        }
    }

    private companion object {
        const val DELIMITER = ":"
    }
}