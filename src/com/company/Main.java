package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 5000;
    public static int bossDamage = 40;
    public static String bossDefenceType = "";

    public static int[] heroesHealth = {400, 350, 300};
    public static int[] heroesDamage = {45, 30, 25};
    public static String[] heroesAttackType = {"Copy sharingan", "Shadow cloning technique", "Simple flame technique"};

    public static int healerHealth = 1050; // HP лекаря
    public static int healerRestores = 35; // Восстанавливает HP героев

    public static int tankHealth = 900; // HP Гиганта
    public static int tankDamage = 10;
    public static String tankDefenceType[] = {"Activated protection", "Deactivation of protection", "Deactivation of protection"}; // Восстанавливает HP героев

    public static int dodgerHealth = 300; // HP Рок Ли
    public static int dodgerDamage = 20;
    public static String dodgerDefenceType[] = {"Activating evasion", "Deactivating evasion"};

    public static void main(String[] args) {
        fightInfo();
        System.out.println("\nFight start!");
        int b = 0;

        while (!isFinished()) {
            System.out.println("Round " + (b = b + 1) + "!\n____________________________");
            round();
        }
    }

    public static void round() {

        changeBossDefence();
        bossHit();
        heroesHit();
        fightInfo();

    }


    public static void changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static boolean isFinished() {
        if (bossHealth <= 0) {
            System.out.println("\n-----------------------------------\n----------| Heroes won! |----------\n-----------------------------------");
            return true;
        }
        if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0 && tankHealth <= 0 && dodgerHealth <= 0) {
            System.out.println("\n---------------------------------\n----------| Boss won! |----------\n---------------------------------");
            return true;
        }
        return false;
    }

    public static void bossHit() {
        Random random = new Random();
        String tankDefence = tankDefenceType[random.nextInt(3)];

        int randomIndex = random.nextInt(dodgerDefenceType.length);
        String DodgeEvasion = dodgerDefenceType[randomIndex];

        if (DodgeEvasion.equals("Activating evasion") && dodgerHealth > 0){
            System.out.println("Rock Lee dodger the hit");
        }
        else if (DodgeEvasion.equals("Deactivating evasion") && dodgerHealth >0){
            dodgerHealth = dodgerHealth - bossDamage;
        }
        else{
            dodgerHealth = 0;
        }

        if (tankHealth <= 0) {
            tankHealth = 0;
        }
        else{
            tankHealth = tankHealth - bossDamage;
        }
        if (healerHealth <=0){
            healerHealth = 0;
        }

        if (tankDefence.equals("Activated protection") && tankHealth > 400) {
            tankHealth = tankHealth - (bossDamage * 4);
            System.out.println("The tank took the hit");
        }
        else if (tankDefence.equals("Deactivation of protection")) {



            for (int i = 0; i < heroesHealth.length; i++) {



                if (heroesHealth[i] > 0 && bossHealth > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;

                        healerHealth = 0;
                    } else {

                        if (heroesHealth[i] - bossDamage < 0) {
                            heroesHealth[i] = 0;
                        } else {
                            heroesHealth[i] = heroesHealth[i] - bossDamage;

                            healerHealth = healerHealth - bossDamage;
                        }
                    }
                }
            }
        }
    }


    public static void heroesHit() {


        if (tankHealth > 0) {
            bossHealth = bossHealth - tankDamage;
        }else
        {
            tankHealth =0;
        }
        if (dodgerHealth > 0){
            bossHealth = bossHealth - dodgerDamage;
        }

        if (healerHealth > 0) {
            System.out.println("Healer restore heroes - " + healerRestores);

            for (int i = 0; i < heroesDamage.length; i++) {





                if (heroesHealth[i] > 0 && bossHealth > 0) {


                    if (bossDefenceType.equals(heroesAttackType[i])) {
                        Random random = new Random();
                        int koeff = random.nextInt(5) + 2;
                        if (bossHealth - heroesDamage[i] * koeff < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i] * koeff;

                            heroesHealth[i] = heroesHealth[i] + healerRestores;
                            tankHealth = tankHealth + healerRestores;

                        }
                        System.out.println(heroesAttackType[i] + " critical hit " + heroesDamage[i] * koeff);
                    } else {
                        if (bossHealth - heroesDamage[i] < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i];
                            heroesHealth[i] = heroesHealth[i] + healerRestores;
                            tankHealth = tankHealth + healerRestores;

                        }
                    }
                    bossHealth = bossHealth - heroesDamage[i];


                }
            }
        } else{
            for (int i = 0; i < heroesDamage.length; i++) {
                if (heroesHealth[i] > 0 && bossHealth > 0) {


                    if (bossDefenceType.equals(heroesAttackType[i])) {
                        Random random = new Random();
                        int koeff = random.nextInt(5) + 2;
                        if (bossHealth - heroesDamage[i] * koeff < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i] * koeff;
                        }
                        System.out.println(heroesAttackType[i] + " critical hit " + heroesDamage[i] * koeff);
                    } else {
                        if (bossHealth - heroesDamage[i] < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i];

                            bossHealth = bossHealth - tankDamage;
                        }
                    }
                    bossHealth = bossHealth - heroesDamage[i];
                }
            }
        }
    }

    public static void fightInfo() {
        System.out.println("____________________________");
        System.out.println("Evil:\nZabuza: " + bossHealth + " ");
        System.out.println("\nHeroes:\nKakashi-sensei: " + heroesHealth[0]);
        System.out.println("Naruto: " + heroesHealth[1]);
        System.out.println("Sasuke: " + heroesHealth[2]);
        System.out.println("Sakura: " + healerHealth);
        System.out.println("Tank: " + tankHealth);
        System.out.println("Rock Lee: " + dodgerHealth);
        System.out.println("____________________________");
    }
}