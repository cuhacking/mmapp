package com.cuhacking.mmapp.sources

import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.Geometry

@ExperimentalJsExport
@JsExport
public actual class GeoJsonSource actual constructor(public actual val id: String, features: FeatureCollection?) {
    public val type: String = "geojson"
    public var data: dynamic = null

    public actual fun setGeoJson(json: String) {
        data = JSON.parse(json)
    }

    @JsName("setGeoJsonFeature")
    public actual fun setGeoJson(feature: Feature) {
        data = JSON.parse(feature.json)
    }

    @JsName("setGeoJsonGeometry")
    public actual fun setGeoJson(geometry: Geometry) {
        data = JSON.parse(geometry.json)
    }

    @JsName("setGeoJsonFeatureCollection")
    public actual fun setGeoJson(featureCollection: FeatureCollection) {
        data = JSON.parse(featureCollection.json)
    }

    @JsName("toJsObject")
    public fun toJsObject(): dynamic {
        val obj = js("{}")
        obj.type = type
        obj.id = id
        obj.data = data
        return obj
    }
}
