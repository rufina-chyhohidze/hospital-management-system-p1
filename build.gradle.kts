plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "be.kdg.programming3"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.webjars.npm:bootstrap-icons:1.11.1")
	implementation("org.webjars:bootstrap:5.3.2")
	implementation("org.webjars:webjars-locator-core:0.48")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}
tasks.getByName("bootRun",JavaExec::class){
	standardInput = System.`in`
}

tasks.withType<Test> {
	useJUnitPlatform()
}
