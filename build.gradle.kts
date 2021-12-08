import com.google.protobuf.gradle.*
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.0"
    id("org.jetbrains.kotlin.kapt") version "1.6.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.0"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("io.micronaut.application") version "3.0.1"
    id("com.google.protobuf") version "0.8.15"
}

version = "0.1"
group = "br.com.yureshell"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("br.com.yureshell.*")
    }
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor:3.2.0")
    implementation("io.micronaut:micronaut-runtime:3.2.0")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa:3.2.0")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime:3.1.1")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:3.0.0")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari:4.0.4")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.7")
    runtimeOnly("com.h2database:h2:2.0.202")
    implementation("io.micronaut:micronaut-validation:3.2.0")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")

    testImplementation("io.micronaut:micronaut-http-client:3.2.0")

}


application {
    mainClass.set("br.com.yureshell.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        if (osdetector.os == "osx") {
            artifact = "com.google.protobuf:protoc:3.14.0:osx-x86_64"
        } else {
            artifact = "com.google.protobuf:protoc:3.14.0"
        }
    }
}

//protobuf {
//    protoc {
//        artifact = "com.google.protobuf:protoc:3.14.0:osx-x86_64"
//    }
//    plugins {
//        id("grpc") {
//            artifact = "io.grpc:protoc-gen-grpc-java:1.39.0"
//        }
//    }
//    generateProtoTasks {
//        ofSourceSet("main").forEach {
//            it.plugins {
//                // Apply the "grpc" plugin whose spec is defined above, without options.
//                id("grpc")
//            }
//        }
//    }
//}
