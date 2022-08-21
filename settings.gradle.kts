dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter() // Warning: this repository is going to shut down soon
        maven("https://naver.jfrog.io/artifactory/maven/")
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
        maven("https://jitpack.io")
    }
}


rootProject.name = "DayTogo"
include(
    ":presentation",
    ":data",
    ":domain"
)
