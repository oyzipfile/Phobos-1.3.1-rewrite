/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.combat.autocrystal.modes;

public enum AntiFriendPop {
    None{

        @Override
        public boolean shouldCalc(AntiFriendPop type) {
            return false;
        }
    }
    ,
    Place{

        @Override
        public boolean shouldCalc(AntiFriendPop type) {
            return type == Place;
        }
    }
    ,
    Break{

        @Override
        public boolean shouldCalc(AntiFriendPop type) {
            return type == Break;
        }
    }
    ,
    All{

        @Override
        public boolean shouldCalc(AntiFriendPop type) {
            return true;
        }
    };


    public abstract boolean shouldCalc(AntiFriendPop var1);
}

