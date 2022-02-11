import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20-M1"
    `maven-publish`
}

group = "com.andreikingsley"
version = "0.1.2-dev-1.8-df-0.1"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.AndreiKingsley:ggdsl:0.1.2-dev-1.8-feature-1.1")
    implementation("org.jetbrains.kotlinx:dataframe:0.8.0-rc-5")
}

tasks.test {
    useJUnitPlatform()
}


tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xcontext-receivers"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.andreikingsley"
            artifactId = "ggdsl"
            version = "0.1.2-dev-1.8-df-0.1"

            from(components["java"])
        }
    }
}
