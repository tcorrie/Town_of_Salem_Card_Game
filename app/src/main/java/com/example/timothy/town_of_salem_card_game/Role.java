package com.example.timothy.town_of_salem_card_game;

public class Role{
    String keyword;
    String alignment;
    boolean canBeRoleBlocked;
    String sheriffResult;
    int uses; // Set to 99 if a role that has an ability that can be used unlimited. Otherwise, it's 1-4 depending on game
    int virtualValue; // On the card.

    public Role(String keyword_, String alignment_, boolean canberoleblocked_, String sheriffresult_, int virtualvalue_){
        this.keyword=keyword_;
        this.alignment=alignment_;
        this.canBeRoleBlocked=canberoleblocked_;
        this.sheriffResult=sheriffresult_;
        this.uses=99;
        this.virtualValue=virtualvalue_;
    }

    // Get methods, for the most part. AKA how to access the attributes
    public String getKeyword(){return this.keyword;}
    public String getAlignment(){return this.alignment;}
    public boolean canBeRoleBlocked(){return this.canBeRoleBlocked;}
    public String getSheriffResult(){return this.sheriffResult;}
    public int getUses(){return this.uses;}
    public int getVirtualValue(){return this.virtualValue;}

    // Set methods, or changing attributes-- and the like
    public void setUses(int i){this.uses=i;}
    public void useUse(){this.uses-=1;}


    public String toString(){return keyword;}


}
