import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun
import java.util.Properties

plugins {
    id("java")
    id("jacoco")
    id("idea")
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.gorylenko.gradle-git-properties") version "2.4.1"
    id("org.sonarqube") version "3.0"
    id("com.github.sherter.google-java-format") version "0.9"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

group = "com.sivalabs"
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

ext {
    set("springCloudVersion", "2021.0.3")
    set("testcontainersVersion", "1.17.3")
    set("bootstrapVersion", "5.1.3")
    set("jqueryVersion", "3.6.0")
    set("fontAwesomeVersion", "6.1.0")
    set("commonsLangVersion", "3.12.0")
    set("commonsIoVersion", "2.11.0")
    set("opencsvVersion", "5.6")
    set("archunitVersion", "0.23.1")
    set("datafakerVersion", "1.4.0")
    set("seleniumVersion", "4.2.1")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")

    implementation("org.flywaydb:flyway-core")
    compileOnly("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.webjars:bootstrap:${property("bootstrapVersion")}")
    implementation("org.webjars:jquery:${property("jqueryVersion")}")
    implementation("org.webjars:font-awesome:${property("fontAwesomeVersion")}")

    implementation("org.apache.commons:commons-lang3:${property("commonsLangVersion")}")
    implementation("commons-io:commons-io:${property("commonsIoVersion")}")
    implementation("com.opencsv:opencsv:${property("opencsvVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.tngtech.archunit:archunit-junit5:${property("archunitVersion")}")
    testImplementation("net.datafaker:datafaker:${property("datafakerVersion")}")
    testImplementation("org.seleniumhq.selenium:selenium-java:${property("seleniumVersion")}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}


jib {
    from {
        image = "eclipse-temurin:17-jre-focal"
    }
    to {
        image = "sivaprasadreddy/video-library"
        tags = setOf("latest")
    }
    container {
        jvmFlags = listOf("-Xms512m", "-Xdebug")
        mainClass = "com.sivalabs.videolibrary.VideoLibraryApplication"
        args = listOf()
        ports = listOf("8080/tcp")
    }
}

tasks.named<BootJar>("bootJar") {
    launchScript()
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "sivaprasadreddy/video-library"
}

tasks.named<BootRun>("bootRun") {
    args = listOf("--spring.profiles.active=default")
}

gitProperties {
    failOnNoGitDirectory = false
    keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.user.name", "git.commit.message.full")
}

googleJavaFormat {
    toolVersion = "1.12.0"
    options(mapOf("style" to "AOSP"))
}

tasks.named("check") {
    dependsOn("verifyGoogleJavaFormat")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(FAILED, STANDARD_ERROR, SKIPPED)
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

tasks.test {
    configure<JacocoTaskExtension> {
        excludes = listOf(
            "com/sivalabs/videolibrary/*Application.*"
        )
    }
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
    }
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.3".toBigDecimal()
            }
        }
    }
}

tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}

file("sonar-project.properties").bufferedReader().apply {
    val sonarProperties = Properties()
    sonarProperties.load(this)
    sonarProperties.forEach { key, value ->
        run {
            sonarqube {
                properties {
                    property(key.toString(), value)
                }
            }
        }
    }
}