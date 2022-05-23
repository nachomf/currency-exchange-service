import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.buildpack.platform.build.PullPolicy

plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
}

group = "com.spring-cloud"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2021.0.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("com.h2database:h2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")

	//implementation("org.springframework.amqp:spring-rabbit")

	implementation("io.github.resilience4j:resilience4j-kotlin:1.7.0")
	implementation("io.github.resilience4j:resilience4j-spring-boot2:1.7.0")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

//create docker image with command ./gradlew bootBuildImage
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage"){
	imageName = "nachoemf/mmv2-${project.name}:${project.version}"
	pullPolicy = PullPolicy.IF_NOT_PRESENT
}

tasks.withType<Test> {
	useJUnitPlatform()
}
