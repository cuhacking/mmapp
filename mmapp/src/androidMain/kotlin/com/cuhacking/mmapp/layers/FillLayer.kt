package com.cuhacking.mmapp.layers

import com.cuhacking.mmapp.expressions.Expression
import com.cuhacking.mmapp.expressions.toMapbox
import com.mapbox.mapboxsdk.style.layers.PropertyValue
import com.mapbox.mapboxsdk.style.layers.FillLayer as MapboxFillLayer

public actual class FillLayer actual constructor(
    @get:JvmName("mapboxLayerId")
    actual override val id: String,
    @get:JvmName("mapboxSourceId")
    actual override val sourceId: String,
    actual override val properties: Map<String, Expression>,
    actual override val filter: Expression?,
) : Layer, MapboxFillLayer(id, sourceId) {
    init {
        properties.map { (key, value) ->
            PropertyValue(key, value.toMapbox())
        }.forEach {
            setProperties(it)
        }
    }
}
