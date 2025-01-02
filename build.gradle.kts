plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	jacoco
	id("org.sonarqube") version "5.1.0.4882"
	id("com.github.ben-manes.versions") version "0.51.0"
	id("org.openapi.generator") version "7.9.0"
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

val springDocOpenApiVersion = "2.6.0"
val openApiToolsVersion = "0.2.6"
val micrometerVersion = "1.4.0"
val p4paActivitiesVersion = "1.16.0"
val postgresJdbcVersion = "42.7.4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("io.micrometer:micrometer-tracing-bridge-otel:$micrometerVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiVersion")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.openapitools:jackson-databind-nullable:$openApiToolsVersion")

  // p4pa activities library
  implementation("it.gov.pagopa.payhub:p4pa-payhub-activities:$p4paActivitiesVersion")

  //postgres jdbc
  implementation("org.postgresql:postgresql:$postgresJdbcVersion")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	//	Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-core")
	testImplementation ("org.projectlombok:lombok")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
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
	dependsOn("openApiGenerate")
}

configure<SourceSetContainer> {
	named("main") {
		java.srcDir("$projectDir/build/generated/src/main/java")
	}
}

springBoot {
	mainClass.value("it.gov.pagopa.pu.worker.WorkerApplication")
}

openApiGenerate {
  generatorName.set("spring")
  inputSpec.set("$rootDir/openapi/template-payments-java-repository.openapi.yaml")
  outputDir.set("$projectDir/build/generated")
  apiPackage.set("it.gov.pagopa.template.controller.generated")
  modelPackage.set("it.gov.pagopa.template.model.generated")
  configOptions.set(mapOf(
    "dateLibrary" to "java8",
    "requestMappingMode" to "api_interface",
    "useSpringBoot3" to "true",
    "interfaceOnly" to "true",
    "useTags" to "true",
    "generateConstructorWithAllArgs" to "false",
    "generatedConstructorWithRequiredArgs" to "false",
    "additionalModelTypeAnnotations" to "@lombok.Data @lombok.Builder @lombok.AllArgsConstructor @lombok.RequiredArgsConstructor"
  ))
}
