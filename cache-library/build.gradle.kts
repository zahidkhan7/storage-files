plugins {
	id("java-library")
}

group = "com.zad.excellence.idp.core"
version = "1.0"
description = "Core Project for Cache Library"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

