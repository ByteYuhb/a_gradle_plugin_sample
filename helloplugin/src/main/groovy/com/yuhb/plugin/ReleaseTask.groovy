package com.yuhb.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReleaseTask extends DefaultTask{
    @TaskAction
    void doAction(){
        def x = project.extensions.HelloPluginExt.name
        println("ReleaseTask:name"+x)
    }
}
