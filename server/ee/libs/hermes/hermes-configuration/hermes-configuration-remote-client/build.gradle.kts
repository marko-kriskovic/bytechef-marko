dependencies {
    api(project(":server:libs:hermes:hermes-configuration:hermes-configuration-api"))

    implementation("org.springframework:spring-context")

    implementation(project(":server:ee:libs:core:commons:commons-webclient"))
}