package com.github.codedoctorde.linwood;


import com.github.codedoctorde.linwood.core.Linwood;
import com.github.codedoctorde.linwood.main.MainAddon;

/**
 * @author CodeDoctorDE
 */
public class LinwoodApp {
    public static void main(String[] args) {
        var bot = new Linwood(args[0]);
        bot.registerModules(new MainAddon());
    }
}
