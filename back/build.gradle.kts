import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.2.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "bzh.zomzog"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

extra["junit-jupiter.version"] = "5.7.0-M1"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://repo.spring.io/milestone")
	maven("https://repo.spring.io/snapshot")
}

dependencies {
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.springframework.fu:spring-fu-kofu:0.4.0")

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

	implementation("org.springframework.boot:spring-boot-starter-mustache")

	implementation("org.eclipse.jgit:org.eclipse.jgit:5.8.1.202007141445-r")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("io.mockk:mockk:1.10.0")
	testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.22")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
