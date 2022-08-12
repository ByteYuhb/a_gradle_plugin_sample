package com.yuhb.upload

import org.gradle.api.Plugin
import org.gradle.api.Project

import javax.print.DocFlavor.STRING

class UploadVersionPlugin implements Plugin<Project>{
    final String EXTENSIVE = 'versionInfo'
    final String TASK_NAME = 'uploadTask'
    @Override
    void apply(Project project) {
        println "begin:now this is a ${project.name} 's upload plugin"
        //1.在插件中引入extensions中的字段，就是我们Project中配置的扩展字段
        project.extensions.create(EXTENSIVE,VersionInfo.class)
        //2.创建待处理的Task
        project.tasks.create(TASK_NAME,UploadTask.class)
        //3.将uploadTask任务挂架到Project的生命周期中
        def build = project.tasks.getByName('clean')
        def uploadTask = project.tasks.getByName(TASK_NAME)
        //这里使用dependsOn强依赖任务关系
        build.dependsOn(uploadTask)

    }
}
