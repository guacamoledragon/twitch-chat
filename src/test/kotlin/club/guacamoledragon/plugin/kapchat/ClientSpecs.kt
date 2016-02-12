package club.guacamoledragon.plugin.kapchat

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.shouldBeFalse
import org.jetbrains.spek.api.shouldBeTrue

class ClientSpecs : Spek() {
    init {
        given("a client with channel set to MrsViolence") {
            val client = Client("MrsViolence")

            on("connecting") {
                client.connect {
                    it("should connect") {
                        shouldBeTrue(client.socket.connected())
                    }
                }
            }

            on("disconnect") {
                client.disconnect {
                    it("should disconnect") {
                        shouldBeFalse(client.socket.connected())
                    }
                }
            }
        }
    }
}