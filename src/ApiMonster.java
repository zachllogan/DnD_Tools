import java.util.ArrayList;

public class ApiMonster
{
    private String name;
    private String size;
    private String type;
    private String subtype;
    private String alignment;
    private String armor_class;
    private String armor_desc;
    private String hit_points;
    private String hit_dice;
    private Movement speed;
    private String strength;
    private String dexterity;
    private String constitution;
    private String intelligence;
    private String wisdom;
    private String charisma;
    private String strength_save;
    private String dexterity_save;
    private String constitution_save;
    private String intelligence_save;
    private String wisdom_save;
    private String charisma_save;
    private String perception;
    private Skills skills;
    private String damage_vulnerabilities;
    private String damage_resistances;
    private String damage_immunities;
    private String condition_immunities;
    private String senses;
    private String languages;
    private String challenge_rating;
    private ArrayList<Action> actions;
    private ArrayList<Action> reactions;
    private String legendary_desc;
    private ArrayList<Action> legendary_actions;
    private ArrayList<Action> special_abilities;


    private static class Movement
    {
        private String walk;
        private String swim;
        private String fly;
        private String burrow;
        private String climb;
    }

    private static class Skills
    {
        private String athletics;
        private String acrobatics;
        private String sleight_of_hand;
        private String stealth;
        private String arcana;
        private String history;
        private String investigation;
        private String nature;
        private String religion;
        private String animal_handling;
        private String insight;
        private String medicine;
        private String perception;
        private String survival;
        private String deception;
        private String intimidation;
        private String performance;
        private String persuasion;
    }

    private static class Action
    {
        private String name;
        private String desc;
    }
}
