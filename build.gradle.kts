import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.mi"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.kotlin.link")
    mavenCentral()
}

dependencies {
    implementation("space.kscience:kmath-core:0.3.0-dev-18")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}