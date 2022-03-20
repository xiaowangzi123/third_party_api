package com.tts.shorts.demo.enums;

/**
 * @author :wyq
 * @date ：Created in 2022/3/20
 * @description :
 */
public enum VoiceEnum {
    KIYO_PLUS(1, "kiyo-plus", "可爱女生-精品"),
    JENNY_PLUS(2, "jenny-plus", "纯正美音-精品"),
    XIAOWEN_PLUS(3, "xiaowen-plus", "女播音员-精品"),
    XIAOFENG_PLUS(4, "xiaofeng-plus", "男播音员-精品"),
    XUANXUAN_PLUS(5, "xuanxuan-plus", "甜美女生-精品"),
    TIANTIAN_PLUS(6, "tiantian-plus", "天真男孩-精品"),
    TANGTANG_PLUS(7, "tangtang-plus", "活泼女孩-精品"),
    KIYO_BASE(8, "kiyo-base", "可爱女生-普通"),
    XIAOWEN_BASE(9, "xiaowen-base", "女播音员-普通"),
    XIAOFENG_BASE(10, "xiaofeng-base", "男播音员-普通"),
    XUANXUAN_BASE(11, "xuanxuan-base", "甜美女生-普通"),
    TIANTIAN_BASE(12, "tiantian-base", "天真男孩-普通"),
    TANGTANG_BASE(13, "tangtang-base", "活泼女孩-普通"),
    TINGLING_BASE(14, "lingling-base", "台湾女生-普通");

    //成员变量
    private int index;
    private String vcn;
    private String desc;

    VoiceEnum(int index, String vcn, String desc) {
        this.index = index;
        this.vcn = vcn;
        this.desc = desc;
    }

    public static String getVCN(int index) {
        for (VoiceEnum voice : VoiceEnum.values()) {
            if (index == voice.getIndex()) {
                return voice.vcn;
            }
        }
        return null;
    }

    public static String getDesc(int index) {
        for (VoiceEnum voice : VoiceEnum.values()) {
            if (index == voice.getIndex()) {
                return voice.desc;
            }
        }
        return null;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
