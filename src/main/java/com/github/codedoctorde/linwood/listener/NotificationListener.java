package com.github.codedoctorde.linwood.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class NotificationListener {
    @SubscribeEvent
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot())
            return;
        
    }
}
