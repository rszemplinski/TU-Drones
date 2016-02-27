package com.tayloru.uav.mav.types;

/**
 * Class that represents a MAVLink parameter and its value.
 */
public class MAVParam {

    private String name;
    private int index;
    private short type;
    private float value;
    private long timestamp;

    /**
     * Constructs a MAVLink parameter.
     * @param name Parameter name.
     * @param index On-board parameter index.
     * @param type Parameter type.
     * @param value Parameter value.
     */
    public MAVParam(String name, int index, short type, float value){
        this.name = name;
        this.index = index;
        this.type = type;
        this.value = value;
        timestamp = System.currentTimeMillis();
    }

    /**
     * Updates the parameter value.
     * @param newVal New value.
     */
    public void update(float newVal){
        value = newVal;
        timestamp = System.currentTimeMillis();
    }

    /**
     * Gets the textual name of this parameter.
     * @return
     */
    public String name(){
        return name;
    }

    /**
     * Gets the on-board index representing this parameter.
     * @return
     */
    public int index(){
        return index;
    }

    /**
     * Gets the type-code of this parameter. Can be decoded with MAV_PARAM_TYPE enum.
     * @return
     */
    public short type(){
        return type;
    }

    /**
     * Gets the current value of this parameter as of this objects timestamp.
     * @return
     */
    public float value(){
        return value;
    }

    /**
     * Returns the last time the value of this parameter was pulled from the device
     * and set.
     * @return
     */
    public long timestamp(){
        return timestamp;
    }
}