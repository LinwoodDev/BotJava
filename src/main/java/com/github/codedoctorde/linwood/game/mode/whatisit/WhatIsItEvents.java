package com.github.codedoctorde.linwood.game.mode.whatisit;

import com.github.codedoctorde.linwood.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
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
    @SubscribeEvent
    public void onJoin(MessageReactionAddEvent event){
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        var entity = Main.getInstance().getDatabase().getServerById(session, event.getGuild().getIdLong());
        var bundle = whatIsIt.getBundle(session);
        if(event.getChannel().getIdLong() != whatIsIt.getTextChannelId() || event.getMember() == null ||
                event.getMessageIdLong() != whatIsIt.getWantWriterMessageId())
            return;
        if(event.getMember().getUser().isBot())
            return;
        switch (event.getReactionEmote().getEmote().getName()){
            case "\uD83D\uDD90️":
                whatIsIt.wantWriter(session, event.getMember());
                break;
            case "\uD83D\uDEAB":
                if(entity.isGameMaster(event.getMember(), event.getTextChannel()))
                    whatIsIt.finishGame();
                else
                    event.getMember().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(bundle.getString("NoPermission")).queue());
            default:
                event.getReaction().removeReaction().queue();
        }
        session.close();
    }
    @SubscribeEvent
    public void onLeave(MessageReactionRemoveEvent event){
        var session = Main.getInstance().getDatabase().getSessionFactory().openSession();
        if(event.getChannel().getIdLong() != whatIsIt.getTextChannelId() || event.getMember() == null)
            return;
        if(event.getChannel().getIdLong() != whatIsIt.getTextChannelId() || event.getMember() == null ||
                event.getMessageIdLong() != whatIsIt.getWantWriterMessageId())
            return;
        if(event.getMember().getUser().isBot())
            return;
        if(event.getReactionEmote().getEmote().getName().equals("\uD83D\uDD90")){
            whatIsIt.removeWriter(session, event.getMember());
        }
        session.close();
    }

    public WhatIsIt getWhatIsIt() {
        return whatIsIt;
    }
}
