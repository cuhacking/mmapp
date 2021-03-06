package com.cuhacking.mmapp.layers

import com.cuhacking.mmapp.expressions.Expression
import com.cuhacking.mmapp.expressions.toMapbox

@JsExport
public actual class FillLayer actual constructor(
    actual override val id: String,
    actual override val sourceId: String,
    actual override val properties: Map<String, Expression>,
    actual override val filter: Expression?,
) : Layer {

    @JsName("toJsObject")
    public fun toJsObject(): dynamic {
        val obj = js("{}")
        obj.id = id
        obj.source = sourceId
        obj.type = "fill"

        obj.paint = js("{}")
        paintProperties.forEach {
            when (val property = properties[it]) {
                null -> {}
                else -> obj.paint[it] = property.toMapbox()
            }
        }

        obj.layout = js("{}")
        layoutProperties.forEach {
            when (val property = properties[it]) {
                null -> {}
                else -> obj.paint[it] = property.toMapbox()
            }
        }

        return obj
    }

    public companion object {
        private val paintProperties = listOf(
            "fill-antialias",
            "fill-color",
            "fill-opacity",
            "fill-outline-color",
            "fill-pattern",
            "fill-translate",
            "fill-translate-anchor"
        )
        private val layoutProperties = listOf("fill-sort-key", "visibility")
    }
}
