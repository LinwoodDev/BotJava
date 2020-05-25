package com.github.codedoctorde.linwood.game.mode.whatisit;

import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.internal.entities.ReceivedMessage;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItEvents {
    private final WhatIsIt whatIsIt;

    public WhatIsItEvents(WhatIsIt whatIsIt){
        this.whatIsIt = whatIsIt;
    }
    @SubscribeEvent
    public void onGuess(ReceivedMessage message){
        if(whatIsIt.getTextChannelId() != message.getTextChannel().getIdLong()){
            return;
        }
        if(whatIsIt.getRound() == null)
            return;
        if(message.getAuthor().getIdLong() == whatIsIt.getRound().getWriterId()){

        }
    }

    public WhatIsIt getWhatIsIt() {
        return whatIsIt;
    }
}
