import java.net.Socket
import java.util.*

fun main() {
    var url = "www.leagueoflegends.com"
    val client = Socket(url, 80)
    val r = client.outputStream.write("GET / HTTP/1.0\r\nHost: $url\r\n\r\n ".toByteArray())
    val scanner = Scanner(client.inputStream)
    while (scanner.hasNextLine()) {
        println(scanner.nextLine())
    }
    client.close()
}