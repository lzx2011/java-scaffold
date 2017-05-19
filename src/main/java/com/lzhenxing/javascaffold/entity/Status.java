package com.lzhenxing.javascaffold.entity;

/**
 * ClassName: Status <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/2
 */
public enum Status {

    GOOD(0),

    BAD(1);

    private final int value;

    Status(int value){
        this.value = value;
    }

    public static Status findByValue(int value){

        switch(value){

            case 0: return GOOD;
            case 1: return BAD;

            default: return null;
        }
    }

    public int getValue() {
        return value;
    }
}
