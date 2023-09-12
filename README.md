# Talaiot Composite Build Reproducer
This project reproduces the issues encountered when trying to include Talaiot as a composite build.

## Reproducer Required State
- Clone [Talaiot](https://github.com/cdsap/Talaiot) locally
- Switch branch to [`talaiot_2_0`](https://github.com/cdsap/Talaiot/tree/talaiot_2_0)
- Clone **this repo** locally
- Update path to your local Talaiot in [settings.gradle](https://github.com/ivanalvarado/talaiot-composite-build-repro/blob/main/settings.gradle#L29)

## Errors

### First Error
#### Configuration
In `common-local.gradle.kts`
```kts
buildscript {
    
    // ...

    dependencies {
        classpath("io.github.cdsap.talaiot:talaiot:1.0")
    }
}

import io.github.cdsap.talaiot.entities.ExecutionReport
import io.github.cdsap.talaiot.metrics.BuildMetrics
import io.github.cdsap.talaiot.metrics.TaskMetrics
import io.github.cdsap.talaiot.plugin.TalaiotPlugin
import io.github.cdsap.talaiot.plugin.TalaiotPluginExtension
import io.github.cdsap.talaiot.publisher.Publisher

configure<io.github.cdsap.talaiot.plugin.TalaiotPluginExtension>() {

}
```

#### Error
``` terminal
e: talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts:17:32: Unresolved reference: plugin
e: talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts:18:32: Unresolved reference: plugin
e: talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts:21:35: Unresolved reference: plugin
```

#### Fix
```kts
    dependencies {
        classpath("io.github.cdsap.talaiot:talaiot:1.0")
        classpath("io.github.cdsap.talaiot.plugin:base-plugin:1.0") // <-- Add this plugin to the classpath
    }
```

### Second Error
#### Configuration
Same as fix above, in `common-local.gradle.kts`:
```kts
buildscript {

    // ...
    
    dependencies {
        classpath("io.github.cdsap.talaiot:talaiot:1.0")
        classpath("io.github.cdsap.talaiot.plugin:base-plugin:1.0")
    }
}
// Same as above only thing changes is classpath plugin addition
```
#### Error
```terminal
e: talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts:18:39: Unresolved reference: TalaiotPlugin
e: talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts:19:39: Unresolved reference: TalaiotPluginExtension
e: talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts:22:42: Unresolved reference: TalaiotPluginExtension
```
#### Fix
Apply the following patch to Talaiot repo:
```patch
diff --git a/buildSrc/build.gradle.kts b/buildSrc/build.gradle.kts
index ab84b87..928154e 100644
--- a/buildSrc/build.gradle.kts
+++ b/buildSrc/build.gradle.kts
@@ -13,7 +13,7 @@ repositories {
 
 dependencies {
     implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
-    implementation("com.gradle.publish:plugin-publish-plugin:1.2.0")
+    implementation("com.gradle.publish:plugin-publish-plugin:0.12.0")
     implementation("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
     testImplementation("junit:junit:4.12")
 }
@@ -33,4 +33,4 @@ gradlePlugin {
             implementationClass = "io.github.cdsap.talaiot.buildplugins.TalaiotKotlinLibPlugin"
         }
     }
-}
\ No newline at end of file
+}
diff --git a/library/plugins/talaiot-standard/build.gradle.kts b/library/plugins/talaiot-standard/build.gradle.kts
index 2ecf5d5..6642a58 100644
--- a/library/plugins/talaiot-standard/build.gradle.kts
+++ b/library/plugins/talaiot-standard/build.gradle.kts
@@ -5,8 +5,8 @@ plugins {
 
 talaiotPlugin {
     idPlugin = "io.github.cdsap.talaiot"
-    artifact = "talaiot"
-    group = "io.github.cdsap"
+    artifact = "talaiot-standard"
+    group = io.github.cdsap.talaiot.buildplugins.Constants.DEFAULT_GROUP_PLUGIN
     mainClass = "io.github.cdsap.talaiot.plugin.TalaiotPlugin"
     version = io.github.cdsap.talaiot.buildplugins.Constants.TALAIOT_VERSION
     displayName = "Talaiot"
```
Then, in `common-local.gradle.kts`
```kts
buildscript {

    // ...

    dependencies {
        classpath("io.github.cdsap.talaiot:talaiot:1.0")
        classpath("io.github.cdsap.talaiot.plugin:base-plugin:1.0")
        classpath("io.github.cdsap.talaiot.plugin:talaiot-standard:1.0") // <-- Add this line
    }
}
```
### Third Error
With the configuration of fix to second error, we get the following error message:
```terminal
FAILURE: Build failed with an exception.

* Where:
Script 'talaiot-composite-build-repro/build-configuration/environment/talaiot/common-local.gradle.kts' line: 31

* What went wrong:
Extension of type 'TalaiotPluginExtension' does not exist. Currently registered extension types: [ExtraPropertiesExtension, BuildScanExtension, GradleEnterpriseExtension, TalaiotPluginExtension]
```
This configuration is currently on `main`.
