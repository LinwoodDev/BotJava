package com.github.codedoctorde.linwood.entity;

public enum GuildPlan {
    COMMUNITY, PRO, PRIVATE;
    public int getPrefixLimit(){
        switch (this){
            case COMMUNITY:
                return 3;
            case PRO:
                return 10;
            case PRIVATE:
                return -1;
        }
        return -1;
    }
}
