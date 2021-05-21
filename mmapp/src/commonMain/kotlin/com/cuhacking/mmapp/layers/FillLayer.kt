package com.cuhacking.mmapp.layers

import com.cuhacking.mmapp.expressions.Expression
import com.cuhacking.mmapp.expressions.colorLiteral
import com.cuhacking.mmapp.expressions.literal

public expect class FillLayer(
    id: String,
    sourceId: String,
    properties: Map<String, Expression>,
    filter: Expression?
) : Layer {
    override val id: String
    override val sourceId: String
    override val properties: Map<String, Expression>
    override val filter: Expression?
}

public fun fillLayer(
    id: String,
    sourceId: String,
    filter: Expression? = null,
    properties: FillLayerDsl.() -> Unit = {}
): FillLayer =
    FillLayerDsl(id, sourceId, filter).apply(properties).create()

public class FillLayerDsl internal constructor(
    public val id: String,
    public val sourceId: String,
    public val filter: Expression?
) {
    private val properties: HashMap<String, Expression> = hashMapOf()

    public fun fillAntialias(boolean: Boolean) {
        fillAntialias(literal(boolean))
    }

    public fun fillAntialias(expression: Expression) {
        properties["fill-antialias"] = expression
    }

    public fun fillColor(hexColor: String) {
        fillColor(colorLiteral(hexColor))
    }

    public fun fillColor(expression: Expression) {
        properties["fill-color"] = expression
    }

    public fun fillOpacity(opacity: Double) {
        fillOpacity(literal(opacity))
    }

    public fun fillOpacity(expression: Expression) {
        properties["fill-opacity"] = expression
    }

    public fun fillOutlineColor(hexColor: String) {
        fillOutlineColor(colorLiteral(hexColor))
    }

    public fun fillOutlineColor(expression: Expression) {
        properties["fill-outline-color"] = expression
    }

    public fun fillPattern(pattern: String) {
        fillPattern(literal(pattern))
    }

    public fun fillPattern(expression: Expression) {
        properties["fill-pattern"] = expression
    }

    public fun fillSortKey(key: Double) {
        fillSortKey(literal(key))
    }

    public fun fillSortKey(expression: Expression) {
        properties["fill-sort-key"] = expression
    }

    public fun fillTranslate(translate: List<Double>) {
        fillTranslate(literal(translate.toDoubleArray()))
    }

    public fun fillTranslate(expression: Expression) {
        properties["fill-translate"] = expression
    }

    public fun fillTranslateAnchor(anchor: FillTranslateAnchor) {
        fillTranslateAnchor(literal(anchor.value))
    }

    public fun fillTranslateAnchor(expression: Expression) {
        properties["fill-translate-anchor"] = expression
    }

    public fun visibility(visibility: Visibility) {
        visibility(literal(visibility.value))
    }

    public fun visibility(expression: Expression) {
        properties["visibility"] = expression
    }

    public fun create(): FillLayer = FillLayer(id, sourceId, properties, filter)
}

public enum class FillTranslateAnchor(public val value: String) {
    MAP("map"),
    VIEWPORT("viewport")
}
