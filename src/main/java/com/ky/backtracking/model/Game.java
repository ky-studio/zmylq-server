package com.ky.backtracking.model;

public enum Game {

    Default, HomeWork,LXGame,DigTresureBox,PandaGame,Pintu,NinjiaGame,ProgramGame,
    sushedamensuo,LockBook, xlsold_wcsdgame, PhoneLock, Computer, BinaryLock, GuitarGame;

    public static boolean IsSearchGame(int gamecode) {
        if (gamecode == PandaGame.ordinal() || gamecode == Pintu.ordinal() || gamecode == PhoneLock.ordinal() ||
        gamecode == Computer.ordinal() || gamecode == BinaryLock.ordinal() || gamecode == GuitarGame.ordinal()) {
            return true;
        } else  {
            return false;
        }
    }

}; // 所有的游戏(Default保留)
