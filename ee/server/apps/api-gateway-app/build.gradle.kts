group = "com.bytechef.api"
description = ""

springBoot {
    mainClass.set("com.bytechef.api.gateway.ApiGatewayApplication")
}

dependencies {
    implementation("org.apache.commons:commons-lang3")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation(project(":server:libs:core:jackson-config"))

    implementation(project(":ee:server:libs:core:discovery:discovery-redis"))

    runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("org.springframework.boot:spring-boot-starter-data-redis")
    runtimeOnly("org.springframework.cloud:spring-cloud-starter-loadbalancer")

    testImplementation(project(":server:libs:test:test-int-support"))
}
