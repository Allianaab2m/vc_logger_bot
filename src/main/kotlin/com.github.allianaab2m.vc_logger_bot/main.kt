package com.github.allianaab2m.vc_logger_bot

import com.jagrosh.jdautilities.command.CommandClientBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

class BotClient: ListenerAdapter(){
    lateinit var jda: JDA
    private val commandPrefix = "/"

    fun main(token: String){
        val commandClient = CommandClientBuilder()
            .setPrefix(commandPrefix)
            .setOwnerId("")
            .build()

        jda = JDABuilder.create(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS)
            .addEventListeners(commandClient, this)
            .build()
    }

    override fun onReady(event: ReadyEvent) {
        println("Bot Ready!${event.jda.selfUser.name}")
    }

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        println(event)
        messageSendWhenVoiceUpdated(event)
    }

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        println(event)
        messageReactionAdded(event)
    }
}

fun main(args: Array<String>) {
    val bot = BotClient()
    bot.main(args[0])
}
