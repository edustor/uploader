import com.mashape.unirest.http.Unirest
import java.io.File
import java.io.FileNotFoundException

/**
 * Created by wutiarn on 14.03.16.
 */
fun main(args: Array<String>) {
    val file = File(args[0])

    if (file.exists() == false) throw FileNotFoundException("Specified file isn't found")

    val result = Unirest.post("http://wutiarn.ru/api/documents/upload")
            .header("token", "a7933bb1-7d01-4db0-91b6-419412dd85c9")
            .field("file", file, "application/pdf")
            .asString()

    println("Status: ${result.status}. Message: ${result.body}")
}