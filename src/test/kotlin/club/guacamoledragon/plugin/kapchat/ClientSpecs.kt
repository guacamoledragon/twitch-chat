package club.guacamoledragon.plugin.kapchat

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.shouldBeFalse
import org.jetbrains.spek.api.shouldBeTrue

class ClientSpecs : Spek() {
    init {
        given("a client with channel set to MrsViolence") {
            val client = Client("MrsViolence")

            client.onConnect {
                on("connecting") {
                    it("should connect") {
                        shouldBeTrue(client.socket.connected())
                    }
                }
            }

            client.onDisconnect {
                on("disconnect") {
                    it("should disconnect") {
                        shouldBeFalse(client.socket.connected())
                    }
                }
            }
        }
    }
}