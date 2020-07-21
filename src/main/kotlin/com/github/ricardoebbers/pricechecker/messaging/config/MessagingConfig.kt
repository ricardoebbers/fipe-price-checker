package com.github.ricardoebbers.pricechecker.messaging.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MessagingConfig(
        @Value("\${config.mq.check-price.queue}")
        private val checkPriceQueueName: String,
        @Value("\${config.mq.check-price.exchange}")
        private val checkPriceExchange: String,
        @Value("\${config.mq.check-price.routingKey}")
        private val checkPriceRoutingKey: String,
        @Value("\${config.mq.create-vehicle.queue}")
        private val createVehicleQueueName: String,
        @Value("\${config.mq.create-vehicle.exchange}")
        private val createVehicleExchange: String,
        @Value("\${config.mq.create-vehicle.routingKey}")
        private val createVehicleRoutingKey: String
) {
    @Bean
    fun checkPriceQueue() = Queue(checkPriceQueueName, false)

    @Bean
    fun checkPriceExchange() = DirectExchange(checkPriceExchange)

    @Bean
    fun checkPriceBinding(@Qualifier("checkPriceQueue") queue: Queue,
                          @Qualifier("checkPriceExchange") exchange: DirectExchange
    ): Binding = BindingBuilder.bind(queue).to(exchange).with(checkPriceRoutingKey)

    @Bean
    fun createVehicleQueue() = Queue(createVehicleQueueName, false)

    @Bean
    fun createVehicleExchange() = DirectExchange(createVehicleExchange)

    @Bean
    fun createVehicleBinding(@Qualifier("createVehicleQueue") queue: Queue,
                             @Qualifier("createVehicleExchange") exchange: DirectExchange
    ): Binding = BindingBuilder.bind(queue).to(exchange).with(createVehicleRoutingKey)

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        val mapper = jacksonObjectMapper()
        mapper.registerModules(Jdk8Module(), JavaTimeModule(), KotlinModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return Jackson2JsonMessageConverter(mapper)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }

}
