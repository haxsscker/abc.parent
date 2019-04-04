package com.autoserve.abc.service.biz.enums;

/**
 * 易生支付配置
 * 
 * @author pp 2014-11-24 下午07:33:09
 */
public class EasyPayConfig {

    public static String PARTNER            = "100000000001264";                                                 //demo测试
    public static String KEY                = "ada466g1d5907cg51aedb2011412f4fd50a73d4ge561d8ga5549ec0107d83457"; //demo
    public static String CHART_SET          = "GBK";
    public static String SIGN_TYPE          = "MD5";
    public static String BATCH_VERSION      = "01";
    //公钥的路径
    public static String CERTIFICATEPUBPATH = "/certificate/tomcat.cer";
    //KEYSTORE
    public static String KEYSTORE_FILE      = "/certificate/clientok.p12";
    public static String PAY_URL_PREFIX     = "https://easypay.bhecard.com/agentpay/";                           // "http://10.68.203.246:18180/agentpay/";
    //支付提交地址
    public static String EASYPAY_URL        = "https://cashier.bhecard.com/portal";                              //"http://10.68.76.96:18182/portal";
    //业务类型    目前只有网上支付一种 
    public static String EASY_PAY_SERVICE   = "create_direct_pay_by_user";
    //业务类型  目前只有 1
    public static String EASY_PAYMENT_TYPE  = "1";

    //商品名称  默认的商品名称
    public static String EASY_PAY_SUBJECT   = "RechargeBusinessSubject";
    //商品描述 NOT　NULL 商品的具体描述
    public static String EASY_BODY          = "RechargeBusiness";

    //返回验证订单地址
    public static String EASY_VERIFY_URL    = "http://mapi.bhecard.com/verify/notify";                           //"http://10.68.76.96:18183/verify/notify?";

    //返回页面的url
    public static String RETURN_URL         = "http://36.33.24.109:8106/webnotify/PayInterfaceNotify.json";
    //POST 消息URL
    public static String NOTIFY_URL         = "http://36.33.24.109:8106/webnotify/PayInterfaceNotify.json";

    public static enum EasyPayVerifyResult {
        Invalid("invalid", "传入的参数无效"),
        True("true", "验证通过"),
        False("false", "验证失败");
        public String value;
        public String desc;

        public static EasyPayVerifyResult valueof(String value) {
            for (EasyPayVerifyResult result : values()) {
                if (value != null && result.value.equals(value)) {
                    return result;
                }
            }

            return null;
        }

        private EasyPayVerifyResult(String v, String desc) {
            this.value = v;
            this.desc = desc;
        }
    }

    public static enum EasyPayTradeStatus {
        WAIT_BUYER_PAY("WAIT_BUYER_PAY", "等待支付"),
        TRADE_FINISHED("TRADE_FINISHED", "支付成功"),
        TRADE_FAILURE("TRADE_FAILURE", "支付失败");
        public String value;
        public String desc;

        private EasyPayTradeStatus(String v, String d) {
            this.value = v;
            this.desc = d;
        }
    }

    public static enum EasyPaymentMethod {
        BANKPAY("bankPay", "网银支付"), //默认
        DIRECTPAY("directPay", "账户余额支付"),
        BANKDIRECT("bankDirect", "银行直连");
        public String value;
        public String desc;

        private EasyPaymentMethod(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    public static enum ReplyStatus {
        SUCCESS("succ", "成功"),
        FAIL("fail", "失败");
        public String value;
        public String desc;

        private ReplyStatus(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    public static enum CertificateType {
        ID_CARD(0, "身份证"),
        HUKOUBU(1, "户口簿"),
        HUZHAO(2, "护照"),
        JUNGUAN(3, "军官证"),
        SHIBING(4, "士兵证"),
        TAIBAO(5, "台胞证");
        public int    value;
        public String desc;

        CertificateType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    public static enum TradeAccountType {
        PERSONAL(0, "对私账户"),
        PUBLIC(1, "对公账户");
        public int    value;
        public String desc;

        TradeAccountType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    //交易代码
    public static enum QueryTransCodeType {
        PAYQUERY("payQuery", "payquery", "付款查询"),
        COLLQUERY("collQuery", "collquery", "收款查询"),
        PAYSINGLEQUERY("paySingleQuery", "paysinglequery", "单个付款查询"),
        COLLSINGLEQUERY("collSingleQuery", "collsinglequery", "多个收款查询");
        public String value;
        public String desc;
        public String surfix;

        QueryTransCodeType(String value, String sur, String desc) {
            this.value = value;
            this.desc = desc;
            this.surfix = sur;
        }
    }

    //交易代码
    public static enum TransCodeType {
        AGENTPAY("agentpay", "pay", "付款"),
        AGENTCOLL("agentcoll", "coll", "收款");
        public String value;
        public String surfix;
        public String desc;

        TransCodeType(String value, String sur, String desc) {
            this.value = value;
            this.desc = desc;
            this.surfix = sur;
        }
    }

    //批量提现提交状态
    public static enum BatchToCash {
        SUCCESS("succ", "成功"),
        FAIL("fail", "失败");
        public String state;
        public String desc;

        BatchToCash(String state, String desc) {
            this.state = state;
            this.desc = desc;
        }
    }

    //易生支付 批量支付查询返回状态，目前返回状态用该enum中的des标识，state暂未用
    public static enum BatchToCashState {
        WAIT_CONFIRM(0, "待确认"),
        WAIT_AUDIT(1, "待审核"),
        WAIT_PROCESS(2, "待处理"),
        MERCHANT_REFUSE(3, "商户拒绝"),
        EASYPAY_REFUSE(4, "易生拒绝"),
        EASYPAY_AUDIT_PASS(5, "易生审核通过"),
        PROCESSING(6, "处理中"),
        PROCESSED(7, "处理完毕");
        BatchToCashState(int state, String des) {
            this.state = state;
            this.des = des;
        }

        public int    state;
        public String des;

        public int getState() {
            return state;
        }

        public String getDes() {
            return des;
        }
    }
}
