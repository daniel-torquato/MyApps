pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApps"
include(":app")
include(":domain:impl")
include(":domain:api")
include(":data:impl")
include(":domain:di")
include(":data:di")
include(":data:di")
include(":presentation:components")
include(":presentation:viewmodel")
include(":test:macrobench")
