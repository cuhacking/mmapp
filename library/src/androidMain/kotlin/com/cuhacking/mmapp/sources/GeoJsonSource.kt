package com.cuhacking.mmapp.sources

import com.cuhacking.mmapp.utils.toMapbox
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.Geometry

public actual class GeoJsonSource actual constructor(
    @get:JvmName("getId_") public actual val id: String,
    features: FeatureCollection?
) : GeoJsonSource(id) {
    public actual fun setGeoJson(feature: Feature) {
        setGeoJson(feature.toMapbox())
    }

    public actual fun setGeoJson(geometry: Geometry) {
        setGeoJson(geometry.toMapbox())
    }

    public actual fun setGeoJson(featureCollection: FeatureCollection) {
        setGeoJson(featureCollection.toMapbox())
    }
}