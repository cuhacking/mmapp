package com.cuhacking.mmapp.sources

import cocoapods.Mapbox.MGLShapeSource
import cocoapods.Mapbox.MGLShapeSourceMeta
import cocoapods.Mapbox.MGLSource
import cocoapods.Mapbox.MGLShape
import com.cuhacking.mmapp.utils.toMapbox
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.FeatureCollection.Companion.toFeatureCollection
import io.github.dellisd.spatialk.geojson.Geometry
import kotlinx.serialization.json.Json

public actual class GeoJsonSource actual constructor(public actual val id: String, features: FeatureCollection?) {

    public val internalSource: MGLShapeSource = MGLShapeSource(id, null, null)

    public actual fun setGeoJson(json: String) {
        setGeoJson(json.toFeatureCollection())
    }

    public actual fun setGeoJson(feature: Feature) {
        internalSource.shape = feature.toMapbox() as MGLShape
    }

    public actual fun setGeoJson(geometry: Geometry) {
        internalSource.shape = geometry.toMapbox() as MGLShape
    }

    public actual fun setGeoJson(featureCollection: FeatureCollection) {
        internalSource.shape = featureCollection.toMapbox()
    }
}