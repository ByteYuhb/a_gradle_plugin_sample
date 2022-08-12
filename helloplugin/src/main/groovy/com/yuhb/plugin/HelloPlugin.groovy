package com.yuhb.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        println('this is a test plugin hello!!!')
        project.extensions.create('HelloPluginExt' ,HelloBean.class)
        project.tasks.create('releaseTask',ReleaseTask.class)
        def rTask = project.tasks.getByName('releaseTask')
        def cTask = project.tasks.getByName('clean')
        cTask.dependsOn(rTask)

    }
}
