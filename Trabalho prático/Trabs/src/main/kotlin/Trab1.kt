import java.net.Socket
import java.util.*

fun main() {
    val port = 80
    val url = "www.isel.pt"//"193.137.128.195"//"www.youtube.com"
    val client = Socket(url, port)
    val http = "HTTP/1.0"
    makeRequest(client, url, http)
    client.close()
}

fun makeRequest(client: Socket, link: String, http: String) {
    var url = link
    client.outputStream.write("GET / $http\r\nHost: $url\r\n\r\n ".toByteArray())
    var response = emptyArray<String>()
    val scanner = Scanner(client.getInputStream())

    // recolhe e imprime a resposta obtida
    while (scanner.hasNextLine()) {
        response += scanner.nextLine()
        if (response.last() == "") break
        println(response.last())
    }

    // quando se faz um segundo request por alguma razao a resposta está vazia pelo que a funçao para de executar
    if (response.isEmpty()) return

    // verifica qual o valor do primeiro digito do codigo de resposta e faz uma açao dependente deste valor
    when (response[0].split(" ")[1][0]) {
        '1', '2' -> return
        '3' -> {
            for (i in response) {
                val s = i.split(" ")
                if (s[0] == "Location:") {
                    url = s[1]
                    break
                }
            }
            makeRequest(client, url, http)
        }

        '4' -> println("Try again with a different request.")
        '5' -> {
            val lesser = if (http.last().code > '0'.code) http.last().code - '0'.code - 1 else http.last()
            makeRequest(client, url, "HTTP/1.$lesser")
        }
    }
}