package com.github.codedoctorde.linwood.apps.single.game.mode.tictactoe;

import com.github.codedoctorde.linwood.Linwood;
import com.github.codedoctorde.linwood.apps.single.SingleApplication;
import com.github.codedoctorde.linwood.entity.GuildEntity;
import com.github.codedoctorde.linwood.apps.single.SingleApplicationMode;
import com.github.codedoctorde.linwood.apps.single.game.engine.Board;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author CodeDoctorDE
 */
public class TicTacToe extends Board implements SingleApplicationMode {
    private final int maxRounds;
    private final long rootChannel;
    private SingleApplication game;
    private TicTacToeEvents events;
    private long textChannelId;
    private long ownerId;
    private final List<Long> players = new ArrayList<>();
    /*
    0\uFE0F\u20E3
    1\uFE0F\u20E3
    2\uFE0F\u20E3
    3\uFE0F\u20E3
    4\uFE0F\u20E3
    5\uFE0F\u20E3
    6\uFE0F\u20E3
    7\uFE0F\u20E3
    8\uFE0F\u20E3
    9\uFE0F\u20E3
     */

    public TicTacToe(int maxRounds, long rootChannel){
        this.maxRounds = maxRounds;
        this.rootChannel = rootChannel;
    }

    @Override
    public void start(SingleApplication app) {
        game = app;
        events = new TicTacToeEvents(this);
        Linwood.getInstance().getJda().getEventManager().register(events);
        var session = Linwood.getInstance().getDatabase().getSessionFactory().openSession();
        var guild = GuildEntity.get(session, game.getGuildId());
        Category category = null;
        if(guild.getGameEntity().getGameCategoryId() != null)
            category = guild.getGameEntity().getGameCategory();
        var bundle = getBundle(session);
        Category finalCategory = category;
        ChannelAction<TextChannel> action;
        action = finalCategory == null ?game.getGuild().createTextChannel(MessageFormat.format(bundle.getString("TextChannel"),game.getId())):
                finalCategory.createTextChannel(MessageFormat.format(bundle.getString("TextChannel"),game.getId()));
        session.close();
        action.queue((textChannel -> {
            this.textChannelId = textChannel.getIdLong();
            if(finalCategory != null)
                textChannel.getManager().setParent(finalCategory).queue();
            chooseNextPlayer();
        }));
    }

    private void chooseNextPlayer() {

    }

    private ResourceBundle getBundle(Session session) {
        return ResourceBundle.getBundle("locale.game.TicTacToe", Linwood.getInstance().getDatabase().getGuildById(session, game.getGuildId()).getLocalization());
    }


    @Override
    public void stop() {

    }

    @Override
    public void act() {

    }
}
