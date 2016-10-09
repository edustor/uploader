package ru.wutiarn.edustor.uploader

import com.mashape.unirest.http.Unirest
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val props = Properties()
    val propsFile = File("edustor-uploader.properties")

    File("/Users/wutiarn/Desktop/temp.txt").writeText("URL: ${propsFile.absoluteFile}")

    props.load(propsFile.inputStream())
    val tokenException = IllegalStateException("Cannot find valid token in settings.properties file")

    if (!props.containsKey("token")) {
        throw tokenException
    }

    val baseDir = File(props.getProperty("dir", "."))
    val files = baseDir.listFiles { file, name -> name.endsWith(".pdf") }

    files.forEach {
        val response = Unirest.post("https://edustor.ru/api/documents/upload")
                .header("Authorization", props.getProperty("token"))
                .field("file", it, "application/pdf")
                .asString()
        println("Uploaded ${it.name}. Status: ${response.status}. Message: ${response.status}")
        when(response.status) {
            200 -> it.delete()
            403 -> throw tokenException
        }
    }
}