package com.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json { prettyPrint = true; isLenient = true })
    }

    val client = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(Json { prettyPrint = true; isLenient = true })
        }
    }

    routing {
        post("/sendMessageToDiscord") {
            // Parse the JSON body into DiscordWebhookMessage object
            val message = call.receive<DiscordWebhookMessage>()
            val response = sendMessageToDiscord(client, message.content)
            call.respondText("Message sent to Discord: $response")
        }
    }
}

@Serializable
data class DiscordWebhookMessage(val content: String)

suspend fun sendMessageToDiscord(client: HttpClient, message: String): String {
    val webhookUrl = "https://discord.com/api/webhooks/1281958355530944686/rqI6o_1M3JrSPyC3ry2pSP2T8gAzuprasUVjtPpdH5Ie9XyJXYCYGJ9b-x8ST1HNVV_2" // Replace with your actual Webhook URL

    // Post the message to Discord
    val response: HttpResponse = client.post(webhookUrl) {
        contentType(ContentType.Application.Json)
        setBody(DiscordWebhookMessage(content = message)) // Sending message as JSON
    }

    // Check if the response is successful
    if (response.status.isSuccess()) {
        return "Message successfully sent!"
    } else {
        return "Failed to send message: ${response.status}"
    }
}
