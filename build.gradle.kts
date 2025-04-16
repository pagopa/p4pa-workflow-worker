plugins {
  java
  id("org.springframework.boot") version "3.4.3"
  id("io.spring.dependency-management") version "1.1.7"
  jacoco
  id("org.sonarqube") version "6.0.1.5171"
  id("com.github.ben-manes.versions") version "0.51.0"
  id("org.openapi.generator") version "7.10.0"
  id("com.gorylenko.gradle-git-properties") version "2.5.0"
}

group = "it.gov.pagopa.payhub"
version = "0.0.1"
description = "p4pa-workflow-worker"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
  maven {
    name = "GitHubPackages"
    url = uri("https://maven.pkg.github.com/pagopa/p4pa-payhub-activities")
    credentials {
      username = "public"
      password = System.getenv("GITHUB_TOKEN")
    }
  }
}

val springDocOpenApiVersion = "2.8.5"
val openApiToolsVersion = "0.2.6"
val micrometerVersion = "1.4.3"
val bouncycastleVersion = "1.80"
val temporalVersion = "1.28.4"
val protobufJavaVersion = "4.30.2"
val guavaVersion = "33.4.0-jre"

val p4paActivitiesVersion = "1.99.1"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("io.micrometer:micrometer-tracing-bridge-otel:$micrometerVersion")
  implementation("io.micrometer:micrometer-registry-prometheus")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiVersion")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("org.openapitools:jackson-databind-nullable:$openApiToolsVersion")
  implementation("org.bouncycastle:bcprov-jdk18on:$bouncycastleVersion")
  implementation("it.gov.pagopa.payhub:p4pa-payhub-activities:$p4paActivitiesVersion") {
    exclude(group = "org.glassfish.jaxb", module = "jaxb-core")
    exclude(group = "com.google.protobuf", module = "protobuf-java")
    exclude(group = "com.google.guava", module = "guava")
  }
  // Temporal
  implementation("io.temporal:temporal-spring-boot-starter:$temporalVersion") {
    exclude(group = "com.google.protobuf", module = "protobuf-java")
    exclude(group = "com.google.guava", module = "guava")
  }
  implementation("com.google.protobuf:protobuf-java:$protobufJavaVersion")
  implementation("com.google.guava:guava:$guavaVersion")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  //	Testing
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.mockito:mockito-core")
  testImplementation("org.projectlombok:lombok")
}

tasks.withType<Jar> {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Test> {
  useJUnitPlatform()
  finalizedBy(tasks.jacocoTestReport)
}

val mockitoAgent = configurations.create("mockitoAgent")
dependencies {
  mockitoAgent("org.mockito:mockito-core") { isTransitive = false }
}
tasks {
  test {
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
  }
}

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.required = true
  }
}

val projectInfo = mapOf(
  "artifactId" to project.name,
  "version" to project.version
)

tasks {
  val processResources by getting(ProcessResources::class) {
    filesMatching("**/application.yml") {
      expand(projectInfo)
    }
  }
}

configurations {
  compileClasspath {
    resolutionStrategy.activateDependencyLocking()
  }
}

tasks.compileJava {
  dependsOn("dependenciesBuild")
}

tasks.register("dependenciesBuild") {
  group = "AutomaticallyGeneratedCode"
  description = "grouping all together automatically generate code tasks"

  dependsOn(
    "openApiGenerate"
  )
}

configure<SourceSetContainer> {
  named("main") {
    java.srcDir("$projectDir/build/generated/src/main/java")
  }
}

springBoot {
  buildInfo()
  mainClass.value("it.gov.pagopa.pu.worker.WorkerApplication")
}

openApiGenerate {
  generatorName.set("spring")
  inputSpec.set("$rootDir/openapi/p4pa-workflow-worker.openapi.yaml")
  outputDir.set("$projectDir/build/generated")
  apiPackage.set("it.gov.pagopa.pu.worker.controller.generated")
  modelPackage.set("it.gov.pagopa.pu.worker.dto.generated")
  configOptions.set(
    mapOf(
      "dateLibrary" to "java8",
      "requestMappingMode" to "api_interface",
      "useSpringBoot3" to "true",
      "interfaceOnly" to "true",
      "useTags" to "true",
      "useBeanValidation" to "true",
      "generateConstructorWithAllArgs" to "true",
      "generatedConstructorWithRequiredArgs" to "true",
      "additionalModelTypeAnnotations" to "@lombok.Builder"
    )
  )
}
