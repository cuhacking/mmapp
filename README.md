# <u>M</u>ultiplatform <u>MAP</u> <u>P</u>roject

Kotlin Multiplatform Mapbox library.

### Supported Mapbox Versions

|[JS](https://github.com/mapbox/mapbox-gl-js)|[Android](https://github.com/mapbox/mapbox-gl-native-android)|[iOS](https://github.com/mapbox/mapbox-gl-native-ios)|
|:----:|:----:|:----:|
|2.2.0|9.6.1|6.3.0|

## mmapp Library
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.cuhacking.mmapp/mmapp?server=https%3A%2F%2Foss.sonatype.org%2F)

TODO

## Gradle Plugin
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.cuhacking.mmapp/gradle-plugin?server=https%3A%2F%2Foss.sonatype.org%2F)

Since Mapbox's Android and iOS SDKs require special authentication to download, an *optional* gradle plugin is available
to simplify the configuration of multiplatform projects with Mapbox dependencies. The mmapp library can still be used
without this plugin, provided you manually configure your setup.

This plugin will automatically add mapbox dependencies to your multiplatform targets for supported platforms (JS,
Android, and iOS), and also set up authentication for the Android and iOS SDKs.

> Note: Because the Mapbox-iOS-SDK cocoapod requires modifying a user's `.netrc` file, an extra line of local
> configuration must be added to enable the automatic configuration of authentication to download the iOS dependency.
> See below.

To use the plugin, apply it to your project:

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")

    // mmapp plugin
    id("com.cuhacking.mmapp")
}
```

The version of Mapbox dependencies can be overridden using the plugin configuration.

```kotlin
mmapp {
    androidSdkVersion = "9.6.1"
    iosSdkVersion = "~> 6.3.0"
    jsVersion = "2.2.0"
}
```

Lastly, add your Mapbox download key to your `local.properties` file.

```properties
mapbox.download.key=YOUR-DOWNLOAD-KEY-HERE
# Add this if you want the plugin to automatically configure your ~/.netrc file
mmapp.config.netrc=true
```
