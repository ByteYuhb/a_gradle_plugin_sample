package com.example.plugintest

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction


/**
 * 自动化写入版本信息 apk信息
 * */
class ReleaseInfoTask extends DefaultTask{
    ReleaseInfoTask(){
        setGroup('yuhb')
        description = '自动化写入版本信息'
    }
    @TaskAction
    void doAction(){
        updateInfo()
    }
    void updateInfo(){
        //获取外部传入数据
        def code = project.extensions.ReleaseInfo.versionCode
        def name = project.extensions.ReleaseInfo.versionName
        def info = project.extensions.ReleaseInfo.versionInfo
        def fileName = project.extensions.ReleaseInfo.fileName
        def file = project.file(fileName)
        if(!file.exists()){
            file.createNewFile()
        }
        //使用StringWriter接收xml数据
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        //文件中没有数据，则写入根节点和节点数据
        if(file.text!=null && file.text.size()<=0){
            xmlBuilder.releases{
                release{
                    versionCode(code)
                    versionName(name)
                    versionInfo(info)
                }
            }
            file.withWriter {Writer writer->
                writer.append(sw.toString())
                writer.close()
            }
        }else{
            //已有版本信息,将新增xml数据插入到根节点之前
            xmlBuilder.release{
                versionCode(code)
                versionName(name)
                versionInfo(info)
            }
            def lines = file.readLines()
            def lens = lines.size()-1
            file.withWriter('utf-8') {Writer writer->
                lines.eachWithIndex{ String line, int index ->
                    if(index!=lens){
                        writer.write(line+'\r\n')
                    }else if(index==lens){
                        writer.write('\r\r\n'+sw.toString()+'\r\n')
                        writer.write(lines.get(lens))
                    }
                }

            }
        }

    }

}