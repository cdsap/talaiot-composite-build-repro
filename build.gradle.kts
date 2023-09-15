import io.github.cdsap.talaiot.plugin.TalaiotPluginExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("io.github.cdsap.talaiot:talaiot:1.0")
    }
}

plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id ("io.github.cdsap.talaiot")
}

configure<TalaiotPluginExtension>() {

}
