package pl.lapciakbilicki.ApplicationCore.DomainModel;

import java.util.HashMap;

public class BasketballType extends SportsFacilityType {

    public BasketballType(int numberOFBasket, double minHeightOfBasket, double maxHeightOfBasket){
        HashMap<String, String> properties = new HashMap<>();
        properties.put("numberOfBasket", String.valueOf(numberOFBasket));
        properties.put("minHeightOfBasket", String.valueOf(minHeightOfBasket));
        properties.put("maxHeightOfBasket", String.valueOf(maxHeightOfBasket));
        this.setTypeName(this.getClass().getSimpleName());
        this.setProperties(properties);
    }
}
