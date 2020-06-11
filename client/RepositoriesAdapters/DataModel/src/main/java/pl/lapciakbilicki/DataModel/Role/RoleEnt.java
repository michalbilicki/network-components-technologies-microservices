package pl.lapciakbilicki.DataModel.Role;

import pl.lapciakbilicki.DataModel.Entity;
import pl.lapciakbilicki.DataModel.IsIdentified;

public class RoleEnt implements IsIdentified, Entity {

    private String id;
    private String name;

    public RoleEnt() {

    }

    public RoleEnt(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
