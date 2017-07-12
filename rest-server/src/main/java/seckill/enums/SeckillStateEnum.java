package seckill.enums;

import java.util.Enumeration;


public enum SeckillStateEnum {
    //enumeration constants
    SUCCESS(1, "Seckill successful"),
    END(0, "Seckill ends"),
    REPEATED_SECKILL(-1, "Seckill repeated"),
    INNER_ERROR(-2, "System error"),
    DATA_MODIFIED(-3, "Data modified");

    private int stateCode;
    private String stateInfo;

    SeckillStateEnum(int stateCode, String stateInfo) {
        this.stateCode = stateCode;
        this.stateInfo = stateInfo;
    }

    public int getStateCode() {
        return stateCode;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * A utility method for checking what state a state code represents
     * @param index
     * @return
     */
    static public SeckillStateEnum stateOf(int index) {
        for (SeckillStateEnum state : values()) {
            if (state.getStateCode() == index) {
                return state;
            }
        }
        return null;
    }

}
