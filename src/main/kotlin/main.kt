import com.mashape.unirest.http.Unirest
import java.io.File

/**
 * Created by wutiarn on 14.03.16.
 */
fun main(args: Array<String>) {
    val file = File("/Users/wutiarn/edustor")
    val files = file.listFiles { file, name -> name.endsWith(".pdf") }

    files.forEach {
        val response = Unirest.post("http://wutiarn.ru/api/documents/upload")
                .header("token", "a7933bb1-7d01-4db0-91b6-419412dd85c9")
                .field("file", it, "application/pdf")
                .asString()
        println("Uploaded ${it.name}. Status: ${response.status}. Message: ${response.status}")
        if (response.status == 200) it.delete()
    }
}