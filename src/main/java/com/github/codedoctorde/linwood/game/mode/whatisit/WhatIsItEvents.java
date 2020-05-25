package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.internal.entities.ReceivedMessage;

import java.text.MessageFormat;

/**
 * @author CodeDoctorDE
 */
public class WhatIsItEvents {
    private final WhatIsIt whatIsIt;

    public WhatIsItEvents(WhatIsIt whatIsIt){
        this.whatIsIt = whatIsIt;
    }
    @SubscribeEvent
    public void onGuess(MessageReceivedEvent event){
        if(event.getChannel().getIdLong() != whatIsIt.getTextChannelId() || event.getMember() == null)
            return;
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var message = event.getMessage();
        if(whatIsIt.getTextChannelId() != message.getTextChannel().getIdLong()) return;
        var round = whatIsIt.getRound();
        if(round != null)
            if(round.isGuesser(event.getMember()))
                message.delete().queue();
            else if(message.getContentStripped().contains(round.getWord())) {
                message.delete().queue();
                if (message.getAuthor().getIdLong() != round.getWriterId()) {
                    var points = round.guessCorrectly(event.getMember());
                    event.getChannel().sendMessage(MessageFormat.format(whatIsIt.getBundle(session).getString("Guess"), event.getAuthor().getName(), points)).queue();
                }
            }
        session.close();
    }

    public WhatIsIt getWhatIsIt() {
        return whatIsIt;
    }
}
