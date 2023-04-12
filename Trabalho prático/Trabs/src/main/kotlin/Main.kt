import java.io.DataOutputStream
import java.net.Socket

fun main() {
    connect()
}

fun connect() {
    try {
        val soc = Socket("193.137.128.84", 80)
        println( soc.localAddress)
        val dout = DataOutputStream(soc.getOutputStream())
        dout.writeUTF("1")

        dout.flush()
        dout.close()
        soc.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}