package dev.trigam.kuub.client.render.system.scene;

import dev.trigam.kuub.client.render.element.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    private Map< Integer, Element > elements = new HashMap<>();

    public Scene () {

    }

    public void addElement ( int id, Element element ) {
        this.elements.put( id, element );
    }
    public void updateElement ( int id, Element element ) {
        this.elements.replace( id, element );
    }
    public Element getElement ( int id ) {
        return this.elements.get( id );
    }

    public List< Element > getElements () { return this.elements.values().stream().toList(); }

}
