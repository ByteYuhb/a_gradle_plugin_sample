package com.example.plugintest
import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println "this poejct name is testPlugin"
        project.extensions.create('ReleaseInfo',ReleaseInfoExtension)
        project.tasks.create('releaseInfoTask',ReleaseInfoTask)
        def curTask = project.tasks.getByName('releaseInfoTask')
        def cleanTask = project.getRootProject().tasks.getByName('clean')
        cleanTask.dependsOn(curTask)
        project.afterEvaluate {

        }
    }
}