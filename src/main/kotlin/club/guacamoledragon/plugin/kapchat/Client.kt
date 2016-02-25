package club.guacamoledragon.plugin.kapchat

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import org.slf4j.LoggerFactory

const val SOCKET_URL: String = "https://tmi-relay.nightdev.com/"

class Client(val channel: String) {
    val logger = LoggerFactory.getLogger(this.javaClass)
    val socket: Socket = IO.socket(SOCKET_URL)

    private var onConnect: () -> Unit = {}

    private var onJoin: () -> Unit = {}

    private var onMessage: (Message) -> Unit = { msg -> }
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

    private var onClearChat: () -> Unit = {}
        set(handler) {
            socket.off("clearchat")
            socket.on("clearchat", {
                logger.info("Chat cleared.")
                handler()
            })
        }

    private var onDisconnect: () -> Unit = {}

    init {
        logger.trace("Socket server: $SOCKET_URL")

        socket.once("ohai", {
            onConnect()
            socket.emit("join", channel)
        })

        socket.on("much connect", {
            logger.info("Waiting to join channel...")

            socket.once("joined", {
                logger.info("Joined channel: $channel.")
                onJoin()
            })
        })

        socket.on(Socket.EVENT_DISCONNECT, {
            logger.info("You were disconnected from the socket server.")
            onDisconnect()
        })
    }

    fun onConnect(handler: () -> Unit): Client {
        this.onConnect = handler
        return this
    }

    fun onJoin(handler: () -> Unit): Client {
        this.onJoin = handler
        return this
    }

    fun onMessage(handler: (Message) -> Unit): Client {
        this.onMessage = handler
        return this
    }

    fun onClearChat(handler: () -> Unit): Client {
        this.onClearChat = handler
        return this
    }

    fun onDisconnect(handler: () -> Unit): Client {
        this.onDisconnect = handler
        return this
    }

    fun connect(): Client {
        socket.connect()
        return this
    }

    fun disconnect(): Client {
        socket.disconnect()
        return this
    }
}