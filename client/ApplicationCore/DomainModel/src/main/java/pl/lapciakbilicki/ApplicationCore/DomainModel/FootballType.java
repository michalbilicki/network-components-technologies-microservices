package pl.lapciakbilicki.ApplicationCore.DomainModel;

import java.util.HashMap;

public class FootballType extends SportsFacilityType{

    public FootballType(boolean fullSize, double widthOfGoal, double heightOfGoal){
        HashMap<String, String> properties = new HashMap<>();
        properties.put("fullSize", String.valueOf(fullSize));
        properties.put("widthOfGoal", String.valueOf(widthOfGoal));
        properties.put("heightOfGoal", String.valueOf(heightOfGoal));
        this.setProperties(properties);
        this.setTypeName(this.getClass().getSimpleName());
    }

}
