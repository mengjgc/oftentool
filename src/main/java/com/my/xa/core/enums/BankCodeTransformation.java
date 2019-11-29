package com.my.xa.core.enums;

/**
 * 银行code值
 */
public enum BankCodeTransformation {

    TSZ0001("0001","中国银行","104100000004"),
    TSZ0002("0002","农业银行","103100000026"),
    TSZ0003("0003","工商银行","102100099996"),
    TSZ0004("0004","建设银行","105100000017"),
    TSZ0005("0005","交通银行","301290000007"),
    TSZ0006("0006","光大银行","303100000006"),
    TSZ0007("0007","民生银行","305100000013"),
    TSZ0008("0008","平安银行","313584099990"),
    TSZ0009("0009","邮储银行","403100000004"),
    TSZ0010("0010","广发银行","306581000003"),
    TSZ0011("0011","中信银行","302100011000"),
    TSZ0012("0012","华夏银行","304100040000"),
    TSZ0013("0013","浦发银行","310290000013"),
    TSZ0014("0014","兴业银行","309391000011"),
    TSZ0015("0015","招商银行","308584000013"),
    TSZ0017("0017","杭州银行","313331000014"),
    TSZ0018("0018","上海银行","325290000012"),
    TSZ0019("0019","北京银行","313100000013"),
    TSZ9999("9999","恒丰银行","315456000105");

    private String code360;
    private String simpleBankName;
    private String BankCode;

    BankCodeTransformation(String code360, String simpleBankName, String bankCode) {
        this.code360 = code360;
        this.simpleBankName = simpleBankName;
        BankCode = bankCode;
    }


    public String getCode360() {
        return code360;
    }

    public String getSimpleBankName() {
        return simpleBankName;
    }

    public String getBankCode() {
        return BankCode;
    }
}
