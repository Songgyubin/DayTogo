dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter() // Warning: this repository is going to shut down soon
        maven("https://naver.jfrog.io/artifactory/maven/")
        maven("https://jitpack.io")
    }
}


rootProject.name = "DayTogo"
include(
    ":app",
    ":data",
    ":domain"
)
include(":common")
