package com.example.plugintest

class ReleaseInfoExtension{
    String versionName
    String versionCode
    String versionInfo
    String fileName

    @Override
    String toString() {
        return "name:${versionName} code:${versionCode}"
    }

}