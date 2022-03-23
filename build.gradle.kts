plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("net.mamoe.mirai-console") version "2.10.1"
}

group = "ray.mintcat.jx3"
version = "1.0.0"

repositories {
    maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    mavenCentral()
}

dependencies {
    api("org.jsoup:jsoup:1.14.3")
    api("com.sun.mail:javax.mail:1.6.2")
}

buildscript {

    repositories {
        maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
        mavenCentral()
    }


    dependencies {
        classpath("org.jsoup:jsoup:1.14.3")
        classpath("com.sun.mail:javax.mail:1.6.2")
    }
}