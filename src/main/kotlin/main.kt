import com.mashape.unirest.http.Unirest
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val props = Properties()
    props.load(File("settings.properties").inputStream())
    val tokenException = IllegalStateException("Cannot find valid token in settings.properties file")

    if (!props.containsKey("token")) {
        throw tokenException
    }

    val baseDir = File(".")
    val files = baseDir.listFiles { file, name -> name.endsWith(".pdf") }

    files.forEach {
        val response = Unirest.post("https://edustor.ru/api/documents/upload")
                .header("token", props.getProperty("token"))
                .field("file", it, "application/pdf")
                .asString()
        println("Uploaded ${it.name}. Status: ${response.status}. Message: ${response.status}")
        when(response.status) {
            200 -> it.delete()
            403 -> throw tokenException
        }
    }
}