package club.guacamoledragon.plugin.kapchat

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

const val SOCKET_URL: String = "https://tmi-relay.nightdev.com/"

// TODO: Implement proper logging
class Client(val channel: String) {
    val socket: Socket = IO.socket(SOCKET_URL)

    var messageHandler: (Message) -> Unit = { msg -> }
        set(handler) {
            socket.off(Socket.EVENT_MESSAGE)
            socket.on(Socket.EVENT_MESSAGE, { args ->
                val data = args[0]
                if (data is JSONObject) {
                    // TODO: Should I filter out bot messages?
                    handler(Message.parse(data))
                }
            })
        }

    var clearchatHandler: () -> Unit = {}
        set(handler) {
            socket.off("clearchat")
            socket.on("clearchat", {
                handler()
            })
        }

    private var onDisconnect = {}
    private var onJoinChannel = {}
    private var onConnect = {}

    init {
        socket.once("ohai", {
            onConnect()
            socket.emit("join", channel)
        })

        socket.on("much connect", {
            println("Waiting to join channel...")

            socket.once("joined", {
                println("Joined channel: $channel.")
                onJoinChannel()
            })
        })

        socket.on(Socket.EVENT_DISCONNECT, {
            println("You were disconnected from the socket server.")
            onDisconnect()
        })
    }

    fun connect(onConnect: () -> Unit = {}) {
        this.onConnect = onConnect
        socket.connect()
    }

    fun disconnect(onDisconnect: () -> Unit = {}) {
        this.onDisconnect = onDisconnect
        socket.disconnect()
    }
}