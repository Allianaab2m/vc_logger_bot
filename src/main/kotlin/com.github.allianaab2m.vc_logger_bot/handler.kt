package com.github.allianaab2m.vc_logger_bot

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun messageSendWhenVoiceUpdated (event: GuildVoiceUpdateEvent){
    val eventMemberName = event.entity.user.name
    val oldVoiceChannel = event.oldValue
    val newVoiceChannel = event.newValue
    val textChannel = event.guild.textChannels.find { textChannel -> textChannel.name == "ききせん" }

    // 入室
    if (oldVoiceChannel == null && newVoiceChannel != null) {
        val joinEmbed = EmbedBuilder()
            .setTitle(eventMemberName+ "が入室しました")
            .addField(
                "入室時間",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                true
            )
            .setColor(0x00ff00)
            .setFooter("このログを削除したい場合は下の🚫ボタンを押してください")
            .build()
        textChannel?.sendMessageEmbeds(joinEmbed)?.complete()?.addReaction("🚫")?.queue()
    } else if (oldVoiceChannel != null && newVoiceChannel == null){ //退室
        val leaveEmbed = EmbedBuilder()
            .setTitle(eventMemberName + "が退室しました")
            .addField("退室時間",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
            true)
            .setColor(0xff0000)
            .setFooter("このログを削除したい場合は下の🚫マークを押してください")
            .build()
        textChannel?.sendMessageEmbeds(leaveEmbed)?.complete()?.addReaction("🚫")?.queue()
    } else {
        return
    }
}

fun messageReactionAdded(event: GuildMessageReactionAddEvent){
    if (event.user.isBot){
        return
    }
    if (event.reactionEmote.emoji == "🚫"){
        if (event.channel.retrieveMessageById(event.messageId).complete()?.author?.name == event.jda.selfUser.name) {
            event.channel.deleteMessageById(event.messageId).queue()
        }
    }
}