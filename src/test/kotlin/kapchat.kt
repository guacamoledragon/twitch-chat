import club.guacamoledragon.plugin.kapchat.Client

fun main(args: Array<String>) {
    val channel = "teamoxy"

    val kapchatClient = Client(channel)
    kapchatClient.connect()

    kapchatClient.messageHandler = { msg ->
        println("${msg.toString()}")
    }

    kapchatClient.messageHandler = { msg ->
        println("cheese")
    }
}
