package de.avpod

import EventGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private val logger = KotlinLogging.logger {}

fun main() {
    var key = 0;
    logger.info { "Application started" }
    val producerProps = mapOf(
        "bootstrap.servers" to "localhost:9093",
        "security.protocol" to "PLAINTEXT"
    )

    val objectMapper = ObjectMapper()
    val producer = KafkaProducer(producerProps, StringSerializer(), StringSerializer())
    val eventGenerator = EventGenerator()

    producer.use {
        while (true) {
            val producerRecord = ProducerRecord(
                "player-events",
                key++.toString(),
                objectMapper.writeValueAsString(eventGenerator.nextEvent())
                )
            val future = it.send(producerRecord)
            val recordMetadata = future.get()
            logger.info { "Message sent with offset: ${recordMetadata.offset()} partition: ${recordMetadata.partition()}" }

            logger("Sleep 10s")
            Thread.sleep(10000)
        }
    }
}

suspend fun <K, V> Producer<K, V>.asyncSend(record: ProducerRecord<K, V>) =
    suspendCoroutine<RecordMetadata> { continuation ->
        send(record) { metadata, exception ->
            exception?.let(continuation::resumeWithException)
                ?: continuation.resume(metadata)
        }
    }

