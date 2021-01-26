package com.github.codedoctorde.linwood.game.commands;

import com.github.codedoctorde.linwood.core.commands.Command;
import com.github.codedoctorde.linwood.core.commands.CommandEvent;
import com.github.codedoctorde.linwood.game.entity.GameEntity;
import com.github.codedoctorde.linwood.game.mode.tictactoe.TicTacToeWorld;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author CodeDoctorDE
 */
public class TicTacToeCommand extends Command {
    @Override
    public boolean onCommand(final CommandEvent event) {
        var args = event.getArguments();
        if(args.length > 2)
            return false;
        int rounds = 5;
        var entity = event.getEntity();
        var member = event.getMember();
        assert member != null;
        if(!event.getGuildEntity(GameEntity.class).isGameMaster(member)){
            event.reply(translate(entity, "NoPermission")).queue();
            return true;
        }
        if(event.getArguments().length != 0)
            try {
                rounds = Integer.parseInt(args[0]);
            }catch(Exception e){
                event.reply(translate(entity, "NoNumber")).queue();
                return true;
            }
        if(rounds > 50 || rounds < 1){
            event.reply(translate(entity, "Invalid")).queue();
            return true;
        }
        // Main.getInstance().getGameManager().startGame(entity.getGuildId(), new WhatIsIt(rounds, event.getMessage().getChannel().getIdLong()));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(new TicTacToeWorld().render(), "png", stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        event.reply(translate(entity, "Success")).addFile(stream.toByteArray(), "TicTacToe.png").queue();
        return true;
    }
    public TicTacToeCommand() {
        super(
                "tictactoe",
                "tic-tac-toe",
                "ttt"
        );
    }
}
