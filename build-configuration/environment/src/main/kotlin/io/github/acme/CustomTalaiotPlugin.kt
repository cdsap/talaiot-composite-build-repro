package io.github.acme

import io.github.cdsap.talaiot.plugin.TalaiotPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class CustomTalaiotPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.github.cdsap.talaiot")
            }
            configure<TalaiotPluginExtension> {
                publishers {
                    jsonPublisher = true
                }
            }
        }
    }
}
