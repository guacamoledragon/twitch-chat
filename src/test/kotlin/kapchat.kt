import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

fun main(args: Array<String>) {
    val channel = "dasmehdi"
    val socket = IO.socket("https://tmi-relay.nightdev.com/")
    socket.on("ohai", {
        println("Connected.")
        socket.emit("join", channel)
    })

    socket.on("much connect", {
        println("Waiting to join channel...")

        socket.once("joined", {
            println("Joined channel: $channel.")
        })
    })

    socket.on(Socket.EVENT_MESSAGE, { args ->
        val data = args[0]
        if(data is JSONObject) {
            println(data.toString(2))
        }
    })

    socket.on("clearchat", {
        // TODO: call fn that clears chat.
    })

    socket.on(Socket.EVENT_DISCONNECT, {
        println("You were disconnected from the socket server.")
    })

    socket.connect()
}
