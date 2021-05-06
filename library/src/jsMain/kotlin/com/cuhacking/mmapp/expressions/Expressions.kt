package com.cuhacking.mmapp.expressions

@Suppress("SpreadOperator")
internal fun Expression.toMapbox(): Array<Any> = when (this) {
    is ColorExpression -> arrayOf("literal", hexColor)
    is ExpressionLiteral -> arrayOf("literal", literal)
    else -> arrayOf(operator!!, *arguments.map(Expression::toMapbox).toTypedArray())
}
