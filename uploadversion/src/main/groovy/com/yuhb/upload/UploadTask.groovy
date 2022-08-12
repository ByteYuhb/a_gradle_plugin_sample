package com.yuhb.upload

import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.annotations.NotNull

class UploadTask extends DefaultTask{
    String url = 'http://127.0.0.1/api/v3/upload/version'
    @TaskAction
    void upload(){
        //1.获取版本信息
        def version = getCurrentVersion()
        //2.发送版本信息
        def response = sendAndReceive(version)
        //3.处理响应：将版本信息以及响应写入到本地文件中
//        checkResponse(response)

    }
    //1.获取版本信息
    def getCurrentVersion(){
        def name = project.extensions.versionInfo.versionName
        def code = project.extensions.versionInfo.versionCode
        def info = project.extensions.versionInfo.versionUpdateInfo
        println "name:$name code:$code info:$info"
        return new VersionInfo(versionName: name,
                     versionCode: code,
                     versionUpdateInfo: info)
    }
    //2.发送版本信息
    void sendAndReceive(VersionInfo version){
        OkHttpClient client = new OkHttpClient()
        FormBody body = new FormBody.Builder()
                .add('versionName',version.versionName)
                .add('versionCode',""+version.versionCode)
                .add('versionUpdateInfo',version.versionUpdateInfo)
                .build()
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body)

        def call1 = client.newCall(builder.build())
        call1.enqueue(new Callback() {
            @Override
            void onFailure(@NotNull Call call, @NotNull IOException e) {
                println "push version fail:reason:"+e.message
            }

            @Override
            void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                checkResponse(response);
            }
        })
    }
    //3.处理响应：将版本信息以及响应写入到本地文件中
    void  checkResponse(response){
        println "response:"+new String(response.body().bytes())
        
    }
}
