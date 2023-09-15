buildscript {

    dependencies {
        classpath("io.github.cdsap.talaiot:talaiot:1.0")
//        classpath("io.github.cdsap.talaiot.plugin:base-plugin:1.0")
//        classpath("io.github.cdsap.talaiot.plugin:talaiot-standard:1.0")
    }
}
//import io.github.cdsap.talaiot.entities.ExecutionReport
//import io.github.cdsap.talaiot.metrics.BuildMetrics
//import io.github.cdsap.talaiot.metrics.TaskMetrics
//import io.github.cdsap.talaiot.plugin.TalaiotPlugin
//import io.github.cdsap.talaiot.plugin.TalaiotPluginExtension
//import io.github.cdsap.talaiot.publisher.Publisher
plugins {
    `java`
}
configure<io.github.cdsap.talaiot.plugin.TalaiotPluginExtension>() {

}
