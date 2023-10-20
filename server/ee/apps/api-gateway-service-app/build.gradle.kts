group = "com.bytechef.api"
description = ""

springBoot {
    mainClass.set("com.bytechef.api.gateway.ApiGatewayApplication")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation(libs.org.springframework.cloud.spring.cloud.starter.loadbalancer)
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

    implementation(project(":server:ee:libs:core:discovery:discovery-redis"))

    testImplementation(project(":server:libs:test:test-int-support"))
}