package com.tayloru.uav.mav.types;

public class MAVComponentStatus{

    private boolean present = false;
    private boolean enabled = false;
    private boolean health = false;

    private int component_index;

    public MAVComponentStatus(int index){
        component_index = index;
    }

    public boolean parse(long p, long e, long h){
        boolean np = ((p & (1 << component_index)) != 0);
        boolean ne = ((e & (1 << component_index)) != 0);
        boolean nh = ((h & (1 << component_index)) != 0);
        boolean result = (np ^ present) || (ne ^ enabled) || (nh ^ health);
        present = np;
        enabled = ne;
        health = nh;
        return result;
    }

    /**
     * Returns whether or not the specified component is present in the system.
     */
    public boolean present(){
        return present;
    }

    /**
     * Returns whether or not the specified component is enabled.
     */
    public boolean enabled(){
        return enabled;
    }

    /**
     * Returns false if the component is in "Bad health".
     */
    public boolean health(){
        return health;
    }
}

