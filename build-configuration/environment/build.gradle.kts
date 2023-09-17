plugins {
    `kotlin-dsl`
}


dependencies{
    implementation("io.github.cdsap:talaiot-standard:2.0.1")
    api("io.github.cdsap.talaiot:talaiot:2.0.1")
}


gradlePlugin {
    plugins {
        register("talaiotCustomPublin") {
            id = "io.github.acme.talaiot"
            implementationClass = "io.github.acme.CustomTalaiotPlugin"
        }
    }
}
