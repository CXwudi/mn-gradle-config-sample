plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.8"
}

version = "0.1"
group = "com.example"

repositories {
    mavenCentral()
}
configurations {
    // for some reason, kapt is not extended from micronautBoms..., so manually adding it
    kapt.get().extendsFrom(micronautBoms.get())
}
dependencies {
    // defining the version through micronautBoms(platform())
    // note that "io.micronaut:micronaut-bom:3.8.9" can come from user's own gradle platform, or convention plugin.
    micronautBoms(platform("io.micronaut:micronaut-bom:3.8.9"))
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

application {
    mainClass.set("com.example.ApplicationKt")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    target {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }
}

graalvmNative.toolchainDetection.set(false)
micronaut {
    // uncomment this version.set() to see error pops up, even though a version is defined in micronautBoms
    version.set("1.0.0") // dummy version smaller than the one define in micronautBoms configuration just to satisfy the plugin
    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
}
