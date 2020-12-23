package com.github.codedoctorde.linwood;


import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.main.MainAddon;

/**
 * @author CodeDoctorDE
 */
public class LinwoodBot {
    public static void main(String[] args) {
        var bot = new Linwood(System.getenv("DISCORD_TOKEN"));
        bot.registerModules(new MainAddon());
    }
}