package com.autoserve.abc.service.biz.impl.exportpdf;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.service.biz.entity.AgreementMessage;
import com.autoserve.abc.service.biz.entity.ContractBean;
import com.autoserve.abc.service.biz.enums.ContractObject;
import com.autoserve.abc.service.biz.intf.exportpdf.ExportPdf;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.NumberToCN;

@Service
public class InvestExportPdfImpl implements ExportPdf {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 隐藏企业名字
     * 
     * @param str
     * @return
     */
    private String hideCompanyName(String str) {
        if (StringUtils.isBlank(str) || str.length() <= 2) {
            return str;
        }
        int len = str.length();
        String result = str.substring(0, 3) + "******";
        result += str.substring(len * 2 / 3, len);
        return result;
    }
    
    /**
     * 隐藏用户姓名
     * 
     * @param str
     * @return
     */
    private String hideUserRealName(String str) {
        if (StringUtils.isBlank(str) || str.length() <= 1) {
            return str;
        }

        String result = str.substring(0, 1) + "**";
        return result;
    }

    @Override
    public BaseResult investBorrowMoney(List<InvestInformationDO> dataset, AgreementMessage agreementMessage,
                                        OutputStream out) {
        InvestExportPdf ex = new InvestExportPdf();
        
        if ("0".equals(agreementMessage.getLoanContractType()))
        {
        	String str = "甲方（债权人）：";
        	String str1 = "具体名单详见合同签字处";
        	if(ContractObject.INVESTOR.getType()==agreementMessage.getContractObject()){
        		str1 = dataset.get(0).getRealName();
        	}
            String str2 = "乙方（债务人）：";
            String str3 = agreementMessage.getFName();
            String str4 = "法定代表人/授权代理人：";
            String str5 = this.hideUserRealName(agreementMessage.getFAgentName());
            String str6 = "法定代表人/授权代理人注册用户名：";
            String str7 = agreementMessage.getFAgentUserName();
            String str8 = new String();
            String str9 = new String();
            if (agreementMessage.isCompany()) {
                str3 = this.hideCompanyName(str3);
                str8 = "企业营业执照号：";

                String lic = agreementMessage.getCcBusinessLicense();
                if (!StringUtil.isBlank(lic) && lic.length() > 6) {
                    str9 = lic.substring(0, 6) + "********" + lic.substring(lic.length() - 4, lic.length());
                } else {
                    logger.warn("未获取到企业营业执照号");
                }

            } else {
            	str3 = this.hideUserRealName(str3);
                str8 = "法定代表人/授权代理人身份证号码：";
                str9 = agreementMessage.getFAgentIdCard().substring(0, 6)
                        + "********"
                        + agreementMessage.getFAgentIdCard().substring(agreementMessage.getFAgentIdCard().length() - 4,
                                agreementMessage.getFAgentIdCard().length());
            }

            String str10 = "丙方：";
            String str11 = agreementMessage.getSName();
            String str12 = "网站：";
            String str13 = "www.xh99d.com";
            String str14 = "住所：";
            String str15 = agreementMessage.getSAddress();
            List<Map<String, PdfParagraph>> list = new ArrayList<Map<String, PdfParagraph>>();
            Map<String, PdfParagraph> params = null;
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str));
            params.put("value", new PdfParagraph(str1, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str2));
            params.put("value", new PdfParagraph(str3, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str4));
            params.put("value", new PdfParagraph(str5, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str6));
            params.put("value", new PdfParagraph(str7, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str8));
            params.put("value", new PdfParagraph(str9, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str10));
            params.put("value", new PdfParagraph(str11, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str12));
            params.put("value", new PdfParagraph(str13, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str14));
            params.put("value", new PdfParagraph(str15, 10));
            list.add(params);
            List<String> stringList = new ArrayList<String>();
            String clauseStr = "鉴于：";
            stringList.add(clauseStr);
            String clauseStr1 = "1、丙方是域名为www.xh99d.com（以下简称“丙方”)的网平台的运营管理人，为甲方和乙方提供融资撮合、管理等服务。";
            stringList.add(clauseStr1);
            String clauseStr2 = "2、甲方、乙方自愿注册成为平台用户，在通过网站达成借款交易时，自愿遵守丙方网站上公布的各项相关业务规则。";
            stringList.add(clauseStr2);
            String clauseStr3 = "鉴此，根据中华人民共和国相关法律法规，合同各方本着自愿、诚实信用的原则，经协商一致，达成本合同，并保证共同遵守执行。";
            stringList.add(clauseStr3);
            String clauseStr4 = "注：本合同中托管机构是指丙方委托的银行资金托管机构 ；账户是指甲方、乙方在银行资金托管机构开立的账户）。";
            stringList.add(clauseStr4);
            String titleStr1 = "t第一条 借款用途";
            stringList.add(titleStr1);
            String clauseStr5 = "本合同项下借款的用途为" + agreementMessage.getLoanUse() + "。乙方不得将借款挪作他用，甲方有权监督款项的使用。";
            stringList.add(clauseStr5);
            String titleStr2 = "t第二条 借款金额和期限";
            stringList.add(titleStr2);
            String clauseStr6 = "2.1 本合同项下借款金额为人民币 " + agreementMessage.getLoanMoney() + "（大写："
                    + NumberToCN.number2CNMontrayUnit(new BigDecimal(agreementMessage.getLoanMoney())) + "）（大小写不一致时，以大写为准）。";
            stringList.add(clauseStr6);
            String clauseStr7 = "2.2 本合同项下的借款期限为" + agreementMessage.getContractTerm() + "天，自"
                    + agreementMessage.getContractStartDate() + "至" + agreementMessage.getContractEndDate() + "止。";
            stringList.add(clauseStr7);
            String clauseStr8 = "2.3 实际借款金额及合同生效日与本合同约定不一致的，以丙方平台上生成的电子合同为准。因乙方提前还款的，以提前还款日确定借款期限届满日期。丙方平台上生成的其他电子合同是本合同的组成部分，与本合同具有同等法律效力。";
            stringList.add(clauseStr8);
            String titleStr3 = "t第三条 借款利率及利息的计付 ";
            stringList.add(titleStr3);
            String clauseStr9 = "3.1 本合同项下年利率为" + agreementMessage.getAnnualInterest() + "%的固定利率，在借款期限内利率保持不变。";
            stringList.add(clauseStr9);
            String clauseStr10 = "3.2 本合同项下的借款自合同生效日开始计息，按月结息，结息日为每月" + agreementMessage.getSettlementDate()
                    + "日（节假日可顺延），借款到期，利随本清。利率的折算：日利率=月利率÷30=年利率÷360。";
            stringList.add(clauseStr10);
            String clauseStr11 = "3.3 本合同项下逾期还款的罚息利率在原利率基础上加收" + agreementMessage.getPunitiveInterest()
                    + "%确定，挪用借款的罚息利率按在原借款利率基础上加收" + agreementMessage.getPunitiveInterest() + "%确定，既逾期又挪用的罚息利率择其重确定，不能并处。";
            stringList.add(clauseStr11);
            String titleStr4 = "t第四条 投资和还款";
            stringList.add(titleStr4);
            String clauseStr12 = "4.1 乙方或者授权代理人提取借款必须满足下列前提条件，否则甲方有权拒绝向乙方投资任何款项：";
            stringList.add(clauseStr12);
            String clauseStr13 = "（1）已按甲方要求提供相应担保且已经办理完毕相关担保手续；";
            stringList.add(clauseStr13);
            String clauseStr14 = "（2）提供的借款用途证明材料与本合同约定用途相符；";
            stringList.add(clauseStr14);
            String clauseStr15 = "（3）已经成为丙方平台的注册会员；";
            stringList.add(clauseStr15);
            String clauseStr16 = "（4）已经在托管机构开立账户；";
            stringList.add(clauseStr16);
            String clauseStr17 = "乙方或授权代理人账户详情：户名：" + agreementMessage.getFName() + "，账号："
                    + agreementMessage.getFAgentUserName() + " 。";
            stringList.add(clauseStr17);
            String clauseStr18 = "（5）提交甲方和丙方要求的其他材料。";
            stringList.add(clauseStr18);
            String clauseStr19 = "（6）提交的材料符合甲方和丙方的要求。";
            stringList.add(clauseStr19);
            String clauseStr20 = "4.2 甲方通过丙方平台投标,投标完成后托管机构冻结甲方账户中相应的投标资金，本合同成立。在项目满标时或投标时间截止时，托管机构将冻结资金直接划转至乙方账户，即视为甲方已向乙方进行了投资，本合同生效。";
            stringList.add(clauseStr20);
            String clauseStr21 = " 4.3 乙方应按本合同约定按时足额偿还借款本金、利息和其他应付款项。乙方应在还款日和每一结息日前，在账户中足额存入当期应该偿付利息、本金和其他应付款项。借款到期，乙方按时将本金、利息和其他应付款项通过丙方平台足额转入到甲方的账户，甲方确认收到后，乙方方为完成清偿义务。";
            stringList.add(clauseStr21);
            String clauseStr22 = "4.4 乙方可通过丙方平台办理提前还款。乙方提前还款时，在本合同项下不存在任何到期应付而未付的款项，包括但不限于借款本金、利息、罚息、服务费、违约金和其他费用。";
            stringList.add(clauseStr22);
            String clauseStr23 = "4.5 提前还款的利息计算的期间仍按合同原约定计算，已收取的服务费不再退还。";
            stringList.add(clauseStr23);
            String titleStr5 = "t第五条 担保（仅适用有担保的情况）";
            stringList.add(titleStr5);
            String clauseStr24 = "5.1 除信用借款外，乙方应为其在本合同项下义务的履行提供甲方认可的合法有效的担保。担保合同另行签订。";
            stringList.add(clauseStr24);
            String clauseStr25 = "5.2 本合同项下担保物发生受损、贬值、产权纠纷、被查封或扣押，或抵押人擅自处理抵押物，或保证担保的保证人财务状况发生不利变化或发生其他不利于甲方债权的变化，乙方应及时通知甲方，并另行提供甲方认可的其他担保。";
            stringList.add(clauseStr25);
            String clauseStr26 = "5.3 本合同项下借款以应收账款提供质押担保的，在本合同有效期内，出现下列情形之一，甲方有权宣布借款提前到期，要求乙方立即偿还部分或全部借款本息，或追加甲方认可的合法、有效、足额的担保：";
            stringList.add(clauseStr26);
            String clauseStr27 = "（1）应收账款出质人对付款方的应收账款坏账率连续2个月上升；";
            stringList.add(clauseStr27);
            String clauseStr28 = "（2）应收账款出质人对付款方已到期未收回的应收账款占对该付款方应收账款余额的5%以上；";
            stringList.add(clauseStr28);
            String clauseStr29 = "（3）应收账款出质人与付款方或其他第三方产生贸易纠纷（包括但不限于质量、技术、服务方面的纠纷）或债务纠纷，导致应收账款可能无法到期按时偿付的。";
            stringList.add(clauseStr29);
            String titleStr6 = "t第六条 服务费的收取";
            stringList.add(titleStr6);
            String clauseStr30 = "6.1 丙方为本合同借款所提供的服务向甲方、乙方收取服务费用。向甲方收取的服务费以网站公布的收费标准、方式和时间为准；对乙方收取服务费以《服务费支付协议》为准。";
            stringList.add(clauseStr30);
            String clauseStr31 = "6.2 甲方、乙方未按合同约定履行义务，丙方有权收取甲方_______违约金，乙方_______违约金。";
            stringList.add(clauseStr31);
            String titleStr7 = "t第七条 债权转让";
            stringList.add(titleStr7);
            String clauseStr32 = "7.1 甲方有权将其在本合同项下的债权部分或全部转让给第三方，甲方的转让行为无须获得乙方和担保人同意。甲方权利的转让应当通知乙方，通知以站内信或者邮件等形式作出。";
            stringList.add(clauseStr32);
            String clauseStr33 = " 7.2 甲方通过丙方平台进行债权转让并签订《债权转让合同》，有关债权转让的规定以《债权转让合同》为准。";
            stringList.add(clauseStr33);
            String clauseStr34 = "7.3 未经甲方书面同意，乙方不得转让其在本合同项下的任何权利和义务。";
            stringList.add(clauseStr34);
            String titleStr8 = "t第八条 陈述和保证";
            stringList.add(titleStr8);
            String clauseStr35 = "乙方向甲方做出以下陈述和保证，该陈述和保证在本合同有效期内始终有效：";
            stringList.add(clauseStr35);
            String clauseStr36 = "8.1 依法具备借款人主体资格，具有签订和履行本合同的资格和能力。";
            stringList.add(clauseStr36);
            String clauseStr37 = "8.2 签订本合同已获得所有必需的授权或批准，签订和履行本合同不违反本公司章程和相关法律法规的规定，与应承担的其他合同项下的义务均无抵触。";
            stringList.add(clauseStr37);
            String clauseStr38 = "8.3 应付的其他债务已按期偿付，无恶意拖欠甲方本息行为。";
            stringList.add(clauseStr38);
            String clauseStr39 = "8.4 有健全的组织机构和财务管理制度，在最近一年的生产经营过程中未发生重大违规违纪行为，现任高级管理人员无任何重大不良记录。";
            stringList.add(clauseStr39);
            String clauseStr40 = "8.5 提供给甲方和丙方的所有文件和资料都是真实、准确、完整和有效的，不存在虚假记载、重大遗漏或误导性陈述。";
            stringList.add(clauseStr40);
            String clauseStr41 = "8.6 提供给甲方和丙方的财务会计报告乃依据中国会计准则编制，真实、公正、完整地反映了乙方的经营状况和负债情况，并且自最新的财务会计报告截至日以来，乙方的财务状况未发生任何重大不利变化。";
            stringList.add(clauseStr41);
            String clauseStr42 = "8.7 未向甲方和丙方隐瞒其所涉及的诉讼、仲裁或索赔事件。";
            stringList.add(clauseStr42);
            String clauseStr43 = "8.8 若乙方为自然人，则其同时陈述和保证如下：";
            stringList.add(clauseStr43);
            String clauseStr44 = "（1）具有完全民事权利能力和完全民事行为能力；";
            stringList.add(clauseStr44);
            String clauseStr45 = "（2）有合法的收入来源和充足的偿债能力；";
            stringList.add(clauseStr45);
            String clauseStr46 = "（3）无恶意拖欠银行贷款本息、信用卡恶意透支等行为；";
            stringList.add(clauseStr46);
            String clauseStr47 = "（4）无赌博、吸毒等不良行为或犯罪记录；";
            stringList.add(clauseStr47);
            String clauseStr48 = "（5）向甲方提供担保已征得配偶同意。";
            stringList.add(clauseStr48);
            String titleStr9 = "t第九条 乙方承诺";
            stringList.add(titleStr9);
            String clauseStr49 = "乙方向甲方做出以下承诺，该承诺在本合同有效期内始终有效：";
            stringList.add(clauseStr49);
            String clauseStr50 = "9.1 按照本合同约定的期限和用途使用借款，所借款项不得用于相关法律法规禁止或限制的用途。";
            stringList.add(clauseStr50);
            String clauseStr51 = "9.2 按照本合同的约定清偿借款本金、利息和其他应付款项。";
            stringList.add(clauseStr51);
            String clauseStr52 = "9.3 接受并积极配合甲方以账户分析、凭证检查、现场调查等方式对包括用途在内的借款资金使用情况的检查和监督，按照甲方要求定期汇总报告借款资金使用情况。";
            stringList.add(clauseStr52);
            String clauseStr53 = "9.4 接受甲方信贷检查，按照甲方要求提供真实、准确、完整的资产负债表、损益表等财务会计资料和反映乙方偿债能力的其他资料，积极协助并配合甲方对其生产经营和财务情况的调查、了解和监督。";
            stringList.add(clauseStr53);
            String clauseStr54 = "9.5 在还清本合同项下借款本息和其他应付款项前，不以任何形式分配股息和红利。";
            stringList.add(clauseStr54);
            String clauseStr55 = "9.6 进行合并、分立、减资、股权变动、重大资产和债权转让、重大对外投资、实质性增加债务融资以及其他可能对甲方权益造成不利影响的行动时，事先征得甲方书面同意或就甲方债权的实现作出令甲方满意的安排方可进行。";
            stringList.add(clauseStr55);
            String clauseStr56 = "9.7 发生下列情形之一，及时通知甲方：";
            stringList.add(clauseStr56);
            String clauseStr57 = "（1）公司章程、经营范围、注册资本、法定代表人变更；";
            stringList.add(clauseStr57);
            String clauseStr58 = "（2）歇业、解散、清算、停业整顿、被吊销营业执照、被撤销或申请（被申请）破产；";
            stringList.add(clauseStr58);
            String clauseStr59 = "（3）涉及或可能涉及重大经济纠纷、诉讼、仲裁，或财产被依法查封、扣押或监管；";
            stringList.add(clauseStr59);
            String clauseStr60 = "（4）股东、董事和现任高级管理人员涉嫌重大案件或经济纠纷；";
            stringList.add(clauseStr60);
            String clauseStr61 = "（5）乙方为自然人的，住所、工作单位、联系方式等发生变更；";
            stringList.add(clauseStr61);
            String clauseStr62 = "9.8 及时、全面、准确地向甲方、丙方披露关联方关系及关联交易。";
            stringList.add(clauseStr62);
            String clauseStr63 = "9.9 对甲方和丙方寄出或以其他方式送达的各类通知及时签收。如因乙方提供的地址不准确、送达地址变更未及时告知合同相对人、乙方或者指定代收人拒绝签收等原因，导致通知或相关法律文书未能被乙方实际接收的，文书退回之日视为送达之日。";
            stringList.add(clauseStr63);
            String clauseStr64 = "9.10 不以降低偿债能力的方式处置自有资产；向第三方提供担保不损害甲方的权益。";
            stringList.add(clauseStr64);
            String clauseStr65 = "9.11 如本合同项下借款系以信用方式发放，完整、真实、准确地定期向甲方、丙方报送对外担保情况。对外提供担保可能影响其在本合同项下义务的履行的，须经甲方、丙方书面同意。";
            stringList.add(clauseStr65);
            String clauseStr66 = "9.12 承担甲方和丙方为实现本合同项下债权而产生的费用，包括但不限于律师费、评估费、拍卖费等。";
            stringList.add(clauseStr66);
            String clauseStr67 = "9.13 本合同项下债务的清偿顺序优先于乙方对其股东的债务，并且与乙方对其他债权人的同类债务至少处于平等地位。";
            stringList.add(clauseStr67);
            String clauseStr68 = "9.14 加强环境和社会风险管理，并就此接受甲方的监督检查。如甲方要求，向甲方提交环境和社会风险报告。";
            stringList.add(clauseStr68);
            String titleStr10 = "t第十条  甲方的权利与义务";
            stringList.add(titleStr10);
            String clauseStr69 = "10.1  甲方声明：";
            stringList.add(clauseStr69);
            String clauseStr70 = "（1）甲方的投标行为是其自主行为，符合相关法律法规及公司章程等内部制度有关规定；";
            stringList.add(clauseStr70);
            String clauseStr71 = "（2）甲方资金来源合法，并为甲方自主支配；";
            stringList.add(clauseStr71);
            String clauseStr72 = "（3）甲方身份合法；";
            stringList.add(clauseStr72);
            String clauseStr73 = "（4）一切投资风险由甲方承担。";
            stringList.add(clauseStr73);
            String clauseStr74 = " 10.2 甲方应在平台注册账户；甲方在贷款（理财）时，授权丙方委托托管机构将资金划转至乙方的账户。";
            stringList.add(clauseStr74);
            String clauseStr75 = " 10.3 甲方有权监督乙方按约定用途使用贷款。";
            stringList.add(clauseStr75);
            String clauseStr76 = " 10.4 甲方在乙方不按期偿还本付息的情况下，有权要求平台通过托管机构从乙方账户直接扣收，乙方承诺对此行为不提出任何异议。";
            stringList.add(clauseStr76);
            String clauseStr77 = "10.5 甲方有权直接向乙方催收借款本息或通过法律手段提起诉讼。";
            stringList.add(clauseStr77);
            String clauseStr78 = "10.6 甲方有权将本合同项下的所有权利委托予第三方。";
            stringList.add(clauseStr78);
            String titleStr11 = "t第十一条   丙方的权利与义务";
            stringList.add(titleStr11);
            String clauseStr79 = "11.1 丙方权利：";
            stringList.add(clauseStr79);
            String clauseStr80 = "（1）丙方有权根据乙方提供的各项信息及丙方独立获得的信息对乙方的信用进行评估，自主决定是否将乙方的借款需求向甲方推荐。";
            stringList.add(clauseStr80);
            String clauseStr81 = "（2）丙方有权代甲方审核乙方的借款申请手续，并代甲方将借款划转至通过审核的乙方账户。";
            stringList.add(clauseStr81);
            String clauseStr82 = "（3）丙方有权根据本合同向甲乙方收取服务费用，并从其或托管机构代管、代收或代付的款项中直接扣收。";
            stringList.add(clauseStr82);
            String clauseStr83 = "11.2 丙方义务：";
            stringList.add(clauseStr83);
            String clauseStr84 = "（1）丙方应根据甲方要求，及时通知并督促托管机构将乙方还本付息资金划至甲方账户。";
            stringList.add(clauseStr84);
            String clauseStr85 = "（2）项目满标后或投标截止时间已到，丙方通知托管机构将投标资金从甲方账户划转至乙方账户。";
            stringList.add(clauseStr85);
            String clauseStr86 = "（3）借款到期，乙方未按时归还的，丙方应及时通知甲方。 ";
            stringList.add(clauseStr86);
            String clauseStr87 = "因电信网络服务终止、黑客攻击等客观因素出现，导致合同内容延迟履行或不能履行的，丙方免于责任。 ";
            stringList.add(clauseStr87);
            String titleStr12 = "t第十二条 违约 ";
            stringList.add(titleStr12);
            String clauseStr88 = "12.1 发生下列情形之一的，视为乙方违约：";
            stringList.add(clauseStr88);
            String clauseStr89 = "（1）乙方未按照约定偿还本合同项下借款本息及其他应付款项，或未履行本合同项下任何其他义务，或违背在本合同项下的陈述、保证或承诺的；";
            stringList.add(clauseStr89);
            String clauseStr90 = "（2）本合同项下担保发生了不利于甲方债权的变化，乙方未另行提供甲方认可的其他担保的；";
            stringList.add(clauseStr90);
            String clauseStr91 = "（3）乙方任何其他债务在到期（包括被宣布提前到期）后未能清偿，或者不履行或违反在其他协议项下的义务，已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr91);
            String clauseStr92 = "（4）乙方的盈利能力、偿债能力、营运能力和现金流量等财务指标突破约定标准，或发生恶化已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr92);
            String clauseStr93 = "（5）乙方股权结构、生产经营、对外投资等发生重大不利变化，已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr93);
            String clauseStr94 = "（6）乙方涉及或可能涉及重大经济纠纷、诉讼、仲裁，或资产被查封、扣押或被强行执行，或被司法机关或行政机关依法立案查处或依法采取处罚措施，或因违反国家有关规定或政策被媒体曝光，已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr94);
            String clauseStr95 = "（7）乙方主要投资者个人、关键管理人员异常变动、失踪或被司法机关依法调查或限制人身自由，已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr95);
            String clauseStr96 = "（8）乙方利用与关联方之间的虚假合同，利用无实际交易背景的交易套取甲方资金或授信，或通过关联交易有意逃废甲方债权的；";
            stringList.add(clauseStr96);
            String clauseStr97 = "（9）乙方已经或可能歇业、解散、清算、停业整顿、被吊销营业执照、被撤销或申请（被申请）破产；";
            stringList.add(clauseStr97);
            String clauseStr98 = "（10）乙方因违反食品安全、安全生产、环境保护及其他环境和社会风险管理相关法律法规、监管规定或行业标准而造成责任事故、重大环境和社会风险事件，已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr98);
            String clauseStr99 = "（11）如本合同项下借款系以信用方式发放，乙方的信用等级、盈利水平、资产负债率、经营活动现金净流量等指标不符合甲方信用贷款条件的；或乙方未经甲方书面同意，以其有效经营资产向他人设定抵/质押担保或对外提供保证担保，已经或可能影响到其在本合同项下义务的履行；";
            stringList.add(clauseStr99);
            String clauseStr100 = "（12）若乙方为自然人，乙方因被羁押、刑事拘留、劳动教养等被限制人身自由，对其偿债能力产生不利影响；";
            stringList.add(clauseStr100);
            String clauseStr101 = "(13) 若乙方为自然人，乙方死亡或被宣告死亡、失踪或被宣告失踪，或者成为限制民事行为能力人或丧失民事行为能力，而无继承人、受遗赠人、监护人或财产代管人，或其继承人、受遗赠人、监护人或财产代管人拒绝代乙方履行本合同项下义务的；";
            stringList.add(clauseStr101);
            String clauseStr102 = "（14）可能导致甲方在本合同项下债权的实现受到不利影响的其他情形。";
            stringList.add(clauseStr102);
            String clauseStr103 = "12.2 乙方违约，甲方或丙方有权采取下列一项或多项措施";
            stringList.add(clauseStr103);
            String clauseStr104 = "（1）要求乙方限期纠正违约行为；";
            stringList.add(clauseStr104);
//            String clauseStr105 = "（2）停止依据本合同和甲方与乙方之间的其他合同向乙方发放借款和其他融资款项，部分或全部取消乙方未提取借款和其他融资款项；";
//            stringList.add(clauseStr105);
            String clauseStr106 = "（2）宣布本合同下未偿还的借款到期，立即收回未偿还款项；";
            stringList.add(clauseStr106);
            String clauseStr107 = "（3）要求乙方赔偿因其违约给甲方和丙方造成的损失；";
            stringList.add(clauseStr107);
            String clauseStr108 = "（4）法律法规规定、本合同约定或甲方认为必要的其他措施。";
            stringList.add(clauseStr108);
            String clauseStr109 = "12.3 借款到期（含被宣布立即到期）乙方未按约偿还的，甲方有权自逾期之日起按本合同约定的逾期罚息利率计收罚息。 ";
            stringList.add(clauseStr109);
            String clauseStr110 = "12.4 乙方未按本合同约定用途使用借款的，甲方有权自借款被挪用之日起，对挪用部分按本合同约定的挪用借款罚息利率计收罚息。";
            stringList.add(clauseStr110);
            String clauseStr111 = "12.5 乙方未按期偿还借款本金、利息（包括罚息）或其他应付款项的，甲方或丙方有权通过媒体进行公告催收。";
            stringList.add(clauseStr111);
            String clauseStr112 = "12.6 乙方的关联方与乙方之间的控制或被控制关系发生变化，或乙方的关联方发生上述第12.1条中除第（1）、（2）两项之外的其他情形，已经或可能影响到乙方在本合同项下义务的履行的，甲方有权采取本合同约定的各项措施。";
            stringList.add(clauseStr112);
            String clauseStr105 = "12.7 丙方保留将借款人违约失信的相关信息向监管机构、合法的第三方征信机构及媒体等机构披露的权利。";
            stringList.add(clauseStr105);
            String tilte13 = "t第十三条  保密条款";
            stringList.add(tilte13);
            String clauseStr113 = "13.1 甲乙丙三方（下称三方）应对其在履约过程中获悉的任何有关对方的非公开信息和资料保密，未经对方事先书面同意，不得用于本合同约定之外的目的，不得向其他方披露或以其他方式公开，但相关法律法规另有规定或者监管部门另有要求的除外。";
            stringList.add(clauseStr113);
            String clauseStr114 = "13.2 三方一致同意，上述保密义务同样适用于三方为履行本合同而各自委托的专业机构和人员。";
            stringList.add(clauseStr114);
            String titleStr14 = "t第十四条  争议的解决";
            stringList.add(titleStr14);
            String clauseStr115 = "本合同的订立、效力、解释、履行及争议的解决均适用中华人民共和国法律。凡由本合同引起的或与本合同有关的一切争议和纠纷，甲乙双方当事人应协商解决；协商不成，可向新华久久贷所在地法院通过诉讼方式解决。";
            stringList.add(clauseStr115);
            String titleStr15 = "t第十五条 合同的变更和解除";
            stringList.add(titleStr15);
            String clauseStr116 = "15.1 对本合同的任何变更应由各方协商一致并以书面形式作出。变更条款或协议构成本合同的一部分，与本合同具有同等法律效力。";
            stringList.add(clauseStr116);
            String clauseStr117 = "15.2 本合同的解除，不影响有关争议解决条款的效力。";
            stringList.add(clauseStr117);
            String titleStr16 = "t第十六条：其他";
            stringList.add(titleStr16);
            String clauseStr118 = "17.1 税务缴纳：甲方在交易过程中产生的相关税费，由甲方自行向主管机关申报、缴纳。";
            stringList.add(clauseStr118);
            String clauseStr119 = "17.2 各方自行保管其在本网站及托管机构注册的账户信息和密码。若有泄露，产生的一切后果自行承担；并及时书面通知丙方。";
            stringList.add(clauseStr119);
            String clauseStr120 = "17.3 甲方有权依据有关法律法规的规定或金融监管机构的要求，将与本合同有关的信息和乙方其他相关信息提供给中国人民银行征信系统和其他依法设立的信用信息数据库，供具有适当资格的机构或个人查询和使用。甲方也有权为本合同订立和履行之目的，通过中国人民银行征信系统和其他依法设立的信用信息数据库查询乙方的相关信息。";
            stringList.add(clauseStr120);
            String clauseStr121 = "17.4本合同所述之“关联方”、“关联方关系”、“关联方交易”、“主要投资者个人”、“关键管理人员”等词语与财政部颁布的《企业会计准则第36号——关联方披露》（财会[2006]3号）以及其后对该准则的修订中的相同词语有相同含义。";
            stringList.add(clauseStr121);
            String clauseStr122 = "17.5 本合同所述之环境和社会风险指乙方及其重要关联方在建设、生产、经营活动中可能给环境和社会带来的危害及相关风险，包括与耗能、污染、土地、健康、安全、移民安置、生态保护、气候变化等有关的环境与社会问题。";
            stringList.add(clauseStr122);
            String clauseStr123 = "17.6 乙方根据其业务规则制作保留的关于本合同项下借款的单据和凭证，构成证明双方债权债务关系的有效证据，对乙方具有约束力。";
            stringList.add(clauseStr123);
            String titleStr18 = "t第十八条 双方约定的其他事项";
            stringList.add(titleStr18);
            String clauseStr124 = "18.1________________________________________________________";
            stringList.add(clauseStr124);
            String clauseStr125 = "18.2________________________________________________________";
            stringList.add(clauseStr125);
            String clauseStr126 = "18.3________________________________________________________";
            stringList.add(clauseStr126);

            String clauseStr127 = "甲方：（盖章）";
            String clauseStr128 = "乙方：（盖章）";
            String clauseStr129 = "法定代表人/授权代理人：";
            String clauseStr130 = "丙方：（盖章）";
            List<Map<String, PdfParagraph>> downList = new ArrayList<Map<String, PdfParagraph>>();
            Map<String, PdfParagraph> downParams = null;
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr127));
            if(ContractObject.INVESTOR.getType()==agreementMessage.getContractObject()){
        		downParams.put("value", new PdfParagraph(dataset.get(0).getRealName(), 10));
        	}else{
        		downParams.put("value", new PdfParagraph("           详 见 附 件 《 甲 方 名 单 》     ", 10));
        	}
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr128));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr129));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr130));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr129));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            ex.exportPdf(agreementMessage.getAgreementNo(), list, stringList, dataset, downList,
                    agreementMessage.getContractStartDate(), out, agreementMessage.getLoanContractType());
        }
        // 尚未确定具体内容，先做区分
        else
        {
        	String str = "甲方（受让人）：";
            String str1 = "具体名单详见合同签字处";
            String str2 = "乙方（出让人）：";
            String str3 = agreementMessage.getFName();
            String str4 = "法定代表人/授权代理人：";
            String str5 = this.hideUserRealName(agreementMessage.getFAgentName());
            String str6 = "法定代表人/授权代理人注册用户名：";
            String str7 = agreementMessage.getFAgentUserName();
            String str8 = new String();
            String str9 = new String();
            if (agreementMessage.isCompany()) {
                str3 = this.hideCompanyName(str3);
                str8 = "企业营业执照号：";

                String lic = agreementMessage.getCcBusinessLicense();
                if (!StringUtil.isBlank(lic) && lic.length() > 6) {
                    str9 = lic.substring(0, 6) + "********" + lic.substring(lic.length() - 4, lic.length());
                } else {
                    logger.warn("未获取到企业营业执照号");
                }

            } else {
            	str3 = this.hideUserRealName(str3);
                str8 = "法定代表人/授权代理人身份证号码：";
                str9 = agreementMessage.getFAgentIdCard().substring(0, 6)
                        + "********"
                        + agreementMessage.getFAgentIdCard().substring(agreementMessage.getFAgentIdCard().length() - 4,
                                agreementMessage.getFAgentIdCard().length());
            }

            String str10 = "丙方：";
            String str11 = agreementMessage.getSName();
            String str12 = "网站：";
            String str13 = "www.xh99d.com";
            String str14 = "住所：";
            String str15 = agreementMessage.getSAddress();
            List<Map<String, PdfParagraph>> list = new ArrayList<Map<String, PdfParagraph>>();
            Map<String, PdfParagraph> params = null;
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str));
            params.put("value", new PdfParagraph(str1, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str2));
            params.put("value", new PdfParagraph(str3, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str4));
            params.put("value", new PdfParagraph(str5, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str6));
            params.put("value", new PdfParagraph(str7, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str8));
            params.put("value", new PdfParagraph(str9, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str10));
            params.put("value", new PdfParagraph(str11, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str12));
            params.put("value", new PdfParagraph(str13, 10));
            list.add(params);
            params = new HashMap<String, PdfParagraph>();
            params.put("name", new PdfParagraph(str14));
            params.put("value", new PdfParagraph(str15, 10));
            list.add(params);
            List<String> stringList = new ArrayList<String>();
            String clauseStr = "鉴于：";
            stringList.add(clauseStr);
            String clauseStr1 = "1、丙方是域名为www.xh99d.com（以下简称“丙方”)的网平台的运营管理人，为甲方和乙方提供融资撮合、管理等服务。";
            stringList.add(clauseStr1);
            String clauseStr2 = "2、甲方、乙方自愿注册成为平台用户，在通过网站达成债权转让交易时，自愿遵守丙方网站上公布的各项相关业务规则。";
            stringList.add(clauseStr2);
            String clauseStr3 = "鉴此，根据中华人民共和国相关法律法规，合同各方本着自愿、诚实信用的原则，经协商一致，达成本合同，并保证共同遵守执行。";
            stringList.add(clauseStr3);
            String clauseStr4 = "注：本合同中托管机构是指丙方委托的银行资金托管机构 ；账户是指甲方、乙方在银行资金托管机构开立的账户）。";
            stringList.add(clauseStr4);
            String titleStr1 = "t第一条 原债权借款用途";
            stringList.add(titleStr1);
            String clauseStr5 = "本合同项下原债权的借款用途为" + agreementMessage.getLoanUse() + "。乙方不得将借款挪作他用，甲方有权监督款项的使用。";
            stringList.add(clauseStr5);
            String titleStr2 = "t第二条 债权金额和期限";
            stringList.add(titleStr2);
            String clauseStr6 = "2.1 本合同项下转让债权金额为人民币 " + agreementMessage.getLoanMoney() + "（大写："
                    + NumberToCN.number2CNMontrayUnit(new BigDecimal(agreementMessage.getLoanMoney())) + "）（大小写不一致时，以大写为准）。";
            stringList.add(clauseStr6);
            String clauseStr7 = "2.2 本合同项下的转让债权期限为" + agreementMessage.getContractTerm() + "天，自"
                    + agreementMessage.getContractStartDate() + "至" + agreementMessage.getContractEndDate() + "止。";
            stringList.add(clauseStr7);
            String clauseStr8 = "2.3 实际转让债权金额及合同生效日与本合同约定不一致的，以丙方平台上生成的电子合同为准。因乙方提前回购的，以提前回购日确定转让债权期限届满日期。丙方平台上生成的其他相关电子合同是本合同的组成部分，与本合同具有同等法律效力。";
            stringList.add(clauseStr8);
            String titleStr3 = "t第三条 转让债权利率及利息的计付 ";
            stringList.add(titleStr3);
            String clauseStr9 = "3.1 本合同项下年利率为" + agreementMessage.getAnnualInterest() + "%的固定利率，在转让债权期限内利率保持不变。";
            stringList.add(clauseStr9);
            String clauseStr10 = "3.2 本合同项下的转让债权自合同生效日开始计息，按月结息，结息日为每月" + agreementMessage.getSettlementDate()
                    + "日（节假日可顺延），债权到期，利随本清。利率的折算：日利率=月利率÷30=年利率÷360。";
            stringList.add(clauseStr10);
            String clauseStr11 = "3.3 本合同项下逾期回购的罚息利率在原利率基础上加收" + agreementMessage.getPunitiveInterest()
                    + "%确定，挪用转让债权的罚息利率按在原转让利率基础上加收" + agreementMessage.getPunitiveInterest() + "%确定，既逾期又挪用的罚息利率择其重确定，不能并处。";
            stringList.add(clauseStr11);
            String titleStr4 = "t第四条 投资和回购";
            stringList.add(titleStr4);
            String clauseStr12 = "4.1 乙方或者授权代理人转让债权必须满足下列前提条件，否则甲方有权拒绝向乙方投资任何款项：";
            stringList.add(clauseStr12);
            String clauseStr13 = "（1）已按甲方要求提供相应担保且已经办理完毕相关担保手续；";
            stringList.add(clauseStr13);
            String clauseStr14 = "（2）提供的原债权借款用途证明材料与本合同约定用途相符；";
            stringList.add(clauseStr14);
            String clauseStr15 = "（3）已经成为丙方平台的注册会员；";
            stringList.add(clauseStr15);
            String clauseStr16 = "（4）已经在托管机构开立账户；";
            stringList.add(clauseStr16);
            String clauseStr17 = "乙方或授权代理人账户详情：户名：" + agreementMessage.getFName() + "，账号："
                    + agreementMessage.getFAgentUserName() + " 。";
            stringList.add(clauseStr17);
            String clauseStr18 = "（5）提交甲方和丙方要求的其他材料。";
            stringList.add(clauseStr18);
            String clauseStr19 = "（6）提交的材料符合甲方和丙方的要求。";
            stringList.add(clauseStr19);
            String clauseStr20 = "4.2 甲方通过丙方平台投标,投标完成后托管机构冻结甲方账户中相应的投标资金，本合同成立。在项目满标时或投标时间截止时，托管机构将冻结资金直接划转至乙方账户，即视为甲方已向乙方进行了投资，本合同生效。";
            stringList.add(clauseStr20);
            String clauseStr21 = " 4.3 乙方应按本合同约定按时足额回购转让债权本金、利息和其他应付款项。乙方应在回购日和每一结息日前，在账户中足额存入当期应该偿付利息、本金和其他应付款项。转让债权到期，乙方按时将本金、利息和其他应付款项通过丙方平台足额转入到甲方的账户，甲方确认收到后，乙方方为完成清偿义务。";
            stringList.add(clauseStr21);
            String clauseStr22 = "4.4 乙方可通过丙方平台办理提前回购。乙方提前回购时，在本合同项下不存在任何到期应付而未付的款项，包括但不限于转让债权本金、利息、罚息、服务费、违约金和其他费用。";
            stringList.add(clauseStr22);
            String clauseStr23 = "4.5 提前回购的利息计算按实际天数计算，已收取的服务费不再退还。";
            stringList.add(clauseStr23);
            String titleStr5 = "t第五条 担保（仅适用有担保的情况）";
            stringList.add(titleStr5);
            String clauseStr24 = "5.1 除信用债权外，乙方应为其在本合同项下义务的履行提供甲方认可的合法有效的担保。担保合同另行签订。";
            stringList.add(clauseStr24);
            String clauseStr25 = "5.2 本合同项下担保物发生受损、贬值、产权纠纷、被查封或扣押，或抵押人擅自处理抵押物，或保证担保的保证人财务状况发生不利变化或发生其他不利于甲方债权的变化，乙方应及时通知甲方，并另行提供甲方认可的其他担保。";
            stringList.add(clauseStr25);
//            String clauseStr26 = "5.3 本合同项下借款以应收账款提供质押担保的，在本合同有效期内，出现下列情形之一，甲方有权宣布借款提前到期，要求乙方立即偿还部分或全部借款本息，或追加甲方认可的合法、有效、足额的担保：";
//            stringList.add(clauseStr26);
//            String clauseStr27 = "（1）应收账款出质人对付款方的应收账款坏账率连续2个月上升；";
//            stringList.add(clauseStr27);
//            String clauseStr28 = "（2）应收账款出质人对付款方已到期未收回的应收账款占对该付款方应收账款余额的5%以上；";
//            stringList.add(clauseStr28);
//            String clauseStr29 = "（3）应收账款出质人与付款方或其他第三方产生贸易纠纷（包括但不限于质量、技术、服务方面的纠纷）或债务纠纷，导致应收账款可能无法到期按时偿付的。";
//            stringList.add(clauseStr29);
            String titleStr6 = "t第六条 服务费的收取";
            stringList.add(titleStr6);
            String clauseStr30 = "6.1 丙方为本合同债权转让所提供的服务向甲方、乙方收取服务费用。向甲方收取的服务费以网站公布的收费标准、方式和时间为准；对乙方收取服务费以《服务费支付协议》为准。";
            stringList.add(clauseStr30);
            String clauseStr31 = "6.2 甲方、乙方未按合同约定履行义务，丙方有权收取甲方_______违约金，乙方_______违约金。";
            stringList.add(clauseStr31);
            String titleStr7 = "t第七条 债权再转让";
            stringList.add(titleStr7);
            String clauseStr32 = "7.1 甲方有权将其在本合同项下的债权部分或全部再转让给第三方，甲方的再转让行为无须获得乙方和担保人同意。甲方权利的再转让应当通知乙方，通知以站内信或者邮件等形式作出。";
            stringList.add(clauseStr32);
            String clauseStr33 = " 7.2 甲方通过丙方平台进行债权转让并签订《债权转让合同》，有关债权转让的规定以《债权转让合同》为准。";
            stringList.add(clauseStr33);
            String clauseStr34 = "7.3 未经甲方书面同意，乙方不得转让其在本合同项下的任何权利和义务。";
            stringList.add(clauseStr34);
            String titleStr8 = "t第八条 陈述和保证";
            stringList.add(titleStr8);
            String clauseStr35 = "乙方向甲方做出以下陈述和保证，该陈述和保证在本合同有效期内始终有效：";
            stringList.add(clauseStr35);
            String clauseStr36 = "8.1 依法具备债权人主体资格，具有签订和履行本合同的资格和能力。";
            stringList.add(clauseStr36);
            String clauseStr37 = "8.2 签订本合同已获得所有必需的授权或批准，签订和履行本合同不违反本公司章程和相关法律法规的规定，与应承担的其他合同项下的义务均无抵触。";
            stringList.add(clauseStr37);
            String clauseStr38 = "8.3 应付的其他债务已按期偿付，无恶意拖欠甲方本息行为。";
            stringList.add(clauseStr38);
            String clauseStr39 = "8.4 有健全的组织机构和财务管理制度，在最近一年的生产经营过程中未发生重大违规违纪行为，现任高级管理人员无任何重大不良记录。";
            stringList.add(clauseStr39);
            String clauseStr40 = "8.5 提供给甲方和丙方的所有文件和资料都是真实、准确、完整和有效的，不存在虚假记载、重大遗漏或误导性陈述。";
            stringList.add(clauseStr40);
            String clauseStr41 = "8.6 提供给甲方和丙方的财务会计报告乃依据中国会计准则编制，真实、公正、完整地反映了乙方的经营状况和负债情况，并且自最新的财务会计报告截至日以来，乙方的财务状况未发生任何重大不利变化。";
            stringList.add(clauseStr41);
            String clauseStr42 = "8.7 未向甲方和丙方隐瞒其所涉及的诉讼、仲裁或索赔事件。";
            stringList.add(clauseStr42);
            String clauseStr43 = "8.8 若乙方为自然人，则其同时陈述和保证如下：";
            stringList.add(clauseStr43);
            String clauseStr44 = "（1）具有完全民事权利能力和完全民事行为能力；";
            stringList.add(clauseStr44);
            String clauseStr45 = "（2）有合法的收入来源和充足的偿债能力；";
            stringList.add(clauseStr45);
            String clauseStr46 = "（3）无恶意拖欠银行贷款本息、信用卡恶意透支等行为；";
            stringList.add(clauseStr46);
            String clauseStr47 = "（4）无赌博、吸毒等不良行为或犯罪记录；";
            stringList.add(clauseStr47);
            String clauseStr48 = "（5）向甲方提供担保已征得配偶同意。";
            stringList.add(clauseStr48);
            String titleStr9 = "t第九条 乙方承诺";
            stringList.add(titleStr9);
            String clauseStr49 = "乙方向甲方做出以下承诺，该承诺在本合同有效期内始终有效：";
            stringList.add(clauseStr49);
            String clauseStr50 = "9.1 按照本合同约定的期限和用途使用资金，所转让债权款项不得用于相关法律法规禁止或限制的用途。";
            stringList.add(clauseStr50);
            String clauseStr51 = "9.2 按照本合同的约定清偿债权本金、利息和其他应付款项。";
            stringList.add(clauseStr51);
            String clauseStr52 = "9.3 标的债权转让后，乙方不得与债务人对债权文件的任何内容做出可能对标的债权产生不利影响的变更、终止、放弃等，亦不得采取任何方式转移或变相转移债权文件项下的义务。";
            stringList.add(clauseStr52);
            String clauseStr53 = "9.4 乙方应将标的债权受益权转让相关事宜告知债务人。";
            stringList.add(clauseStr53);
//            String clauseStr54 = "9.5 在还清本合同项下借款本息和其他应付款项前，不以任何形式分配股息和红利。";
//            stringList.add(clauseStr54);
//            String clauseStr55 = "9.6 进行合并、分立、减资、股权变动、重大资产和债权转让、重大对外投资、实质性增加债务融资以及其他可能对甲方权益造成不利影响的行动时，事先征得甲方书面同意或就甲方债权的实现作出令甲方满意的安排方可进行。";
//            stringList.add(clauseStr55);
//            String clauseStr56 = "9.7 发生下列情形之一，及时通知甲方：";
//            stringList.add(clauseStr56);
//            String clauseStr57 = "（1）公司章程、经营范围、注册资本、法定代表人变更；";
//            stringList.add(clauseStr57);
//            String clauseStr58 = "（2）歇业、解散、清算、停业整顿、被吊销营业执照、被撤销或申请（被申请）破产；";
//            stringList.add(clauseStr58);
//            String clauseStr59 = "（3）涉及或可能涉及重大经济纠纷、诉讼、仲裁，或财产被依法查封、扣押或监管；";
//            stringList.add(clauseStr59);
//            String clauseStr60 = "（4）股东、董事和现任高级管理人员涉嫌重大案件或经济纠纷；";
//            stringList.add(clauseStr60);
//            String clauseStr61 = "（5）乙方为自然人的，住所、工作单位、联系方式等发生变更；";
//            stringList.add(clauseStr61);
//            String clauseStr62 = "9.8 及时、全面、准确地向甲方、丙方披露关联方关系及关联交易。";
//            stringList.add(clauseStr62);
//            String clauseStr63 = "9.9 对甲方和丙方寄出或以其他方式送达的各类通知及时签收。如因乙方提供的地址不准确、送达地址变更未及时告知合同相对人、乙方或者指定代收人拒绝签收等原因，导致通知或相关法律文书未能被乙方实际接收的，文书退回之日视为送达之日。";
//            stringList.add(clauseStr63);
//            String clauseStr64 = "9.10 不以降低偿债能力的方式处置自有资产；向第三方提供担保不损害甲方的权益。";
//            stringList.add(clauseStr64);
//            String clauseStr65 = "9.11 如本合同项下借款系以信用方式发放，完整、真实、准确地定期向甲方、丙方报送对外担保情况。对外提供担保可能影响其在本合同项下义务的履行的，须经甲方、丙方书面同意。";
//            stringList.add(clauseStr65);
//            String clauseStr66 = "9.12 承担甲方和丙方为实现本合同项下债权而产生的费用，包括但不限于律师费、评估费、拍卖费等。";
//            stringList.add(clauseStr66);
//            String clauseStr67 = "9.13 本合同项下债务的清偿顺序优先于乙方对其股东的债务，并且与乙方对其他债权人的同类债务至少处于平等地位。";
//            stringList.add(clauseStr67);
//            String clauseStr68 = "9.14 加强环境和社会风险管理，并就此接受甲方的监督检查。如甲方要求，向甲方提交环境和社会风险报告。";
//            stringList.add(clauseStr68);
            String titleStr10 = "t第十条  甲方的权利与义务";
            stringList.add(titleStr10);
            String clauseStr69 = "10.1  甲方声明：";
            stringList.add(clauseStr69);
            String clauseStr70 = "（1）甲方的投标行为是其自主行为，符合相关法律法规及公司章程等内部制度有关规定；";
            stringList.add(clauseStr70);
            String clauseStr71 = "（2）甲方资金来源合法，并为甲方自主支配；";
            stringList.add(clauseStr71);
            String clauseStr72 = "（3）甲方身份合法；";
            stringList.add(clauseStr72);
            String clauseStr73 = "（4）一切投资风险由甲方承担。";
            stringList.add(clauseStr73);
            String clauseStr74 = " 10.2 甲方应在平台注册账户；甲方在投资（理财）时，授权丙方委托托管机构将资金划转至乙方的账户。";
            stringList.add(clauseStr74);
            String clauseStr75 = " 10.3 甲方有权监督乙方按约定用途使用贷款。";
            stringList.add(clauseStr75);
            String clauseStr76 = " 10.4 甲方在乙方不按期偿支付本息的情况下，有权要求平台通过托管机构从乙方账户直接扣收，乙方承诺对此行为不提出任何异议。";
            stringList.add(clauseStr76);
            String clauseStr77 = "10.5 甲方有权直接向乙方催收债权本息或通过法律手段提起诉讼。";
            stringList.add(clauseStr77);
            String clauseStr78 = "10.6 甲方有权将本合同项下的所有权利委托予第三方。";
            stringList.add(clauseStr78);
            String titleStr11 = "t第十一条   丙方的权利与义务";
            stringList.add(titleStr11);
            String clauseStr79 = "11.1 丙方权利：";
            stringList.add(clauseStr79);
            String clauseStr80 = "（1）丙方有权根据乙方提供的各项信息及丙方独立获得的信息对乙方的信用进行评估，自主决定是否将乙方的债权转让需求向甲方推荐。";
            stringList.add(clauseStr80);
            String clauseStr81 = "（2）丙方有权代甲方审核乙方的转让债权申请手续，并代甲方将款项划转至通过审核的乙方账户。";
            stringList.add(clauseStr81);
            String clauseStr82 = "（3）丙方有权根据本合同向甲乙方收取服务费用，并从其或托管机构代管、代收或代付的款项中直接扣收。";
            stringList.add(clauseStr82);
            String clauseStr83 = "11.2 丙方义务：";
            stringList.add(clauseStr83);
            String clauseStr84 = "（1）丙方应根据甲方要求，及时通知并督促托管机构将乙方回购本息资金划至甲方账户。";
            stringList.add(clauseStr84);
            String clauseStr85 = "（2）项目满标后或投标截止时间已到，丙方通知托管机构将投标资金从甲方账户划转至乙方账户。";
            stringList.add(clauseStr85);
            String clauseStr86 = "（3）债权到期，乙方未按时回购的，丙方应及时通知甲方。 ";
            stringList.add(clauseStr86);
            String clauseStr87 = "因电信网络服务终止、黑客攻击等客观因素出现，导致合同内容延迟履行或不能履行的，丙方免于责任。 ";
            stringList.add(clauseStr87);
            String titleStr12 = "t第十二条 违约 ";
            stringList.add(titleStr12);
            String clauseStr88 = "12.1 发生下列情形之一的，视为乙方违约：";
            stringList.add(clauseStr88);
            String clauseStr89 = "（1）乙方未按照约定回购本合同项下债权本息及其他应付款项，或未履行本合同项下任何其他义务，或违背在本合同项下的陈述、保证或承诺的；";
            stringList.add(clauseStr89);
            String clauseStr90 = "（2）本合同项下担保发生了不利于甲方债权的变化，乙方未另行提供甲方认可的其他担保的；";
            stringList.add(clauseStr90);
            String clauseStr91 = "（3）乙方任何其他债务在到期（包括被宣布提前到期）后未能回购，或者不履行或违反在其他协议项下的义务，已经或可能影响到其在本合同项下义务的履行的；";
            stringList.add(clauseStr91);
//            String clauseStr92 = "（4）乙方的盈利能力、偿债能力、营运能力和现金流量等财务指标突破约定标准，或发生恶化已经或可能影响到其在本合同项下义务的履行的；";
//            stringList.add(clauseStr92);
//            String clauseStr93 = "（5）乙方股权结构、生产经营、对外投资等发生重大不利变化，已经或可能影响到其在本合同项下义务的履行的；";
//            stringList.add(clauseStr93);
//            String clauseStr94 = "（6）乙方涉及或可能涉及重大经济纠纷、诉讼、仲裁，或资产被查封、扣押或被强行执行，或被司法机关或行政机关依法立案查处或依法采取处罚措施，或因违反国家有关规定或政策被媒体曝光，已经或可能影响到其在本合同项下义务的履行的；";
//            stringList.add(clauseStr94);
//            String clauseStr95 = "（7）乙方主要投资者个人、关键管理人员异常变动、失踪或被司法机关依法调查或限制人身自由，已经或可能影响到其在本合同项下义务的履行的；";
//            stringList.add(clauseStr95);
//            String clauseStr96 = "（8）乙方利用与关联方之间的虚假合同，利用无实际交易背景的交易套取甲方资金或授信，或通过关联交易有意逃废甲方债权的；";
//            stringList.add(clauseStr96);
//            String clauseStr97 = "（9）乙方已经或可能歇业、解散、清算、停业整顿、被吊销营业执照、被撤销或申请（被申请）破产；";
//            stringList.add(clauseStr97);
//            String clauseStr98 = "（10）乙方因违反食品安全、安全生产、环境保护及其他环境和社会风险管理相关法律法规、监管规定或行业标准而造成责任事故、重大环境和社会风险事件，已经或可能影响到其在本合同项下义务的履行的；";
//            stringList.add(clauseStr98);
//            String clauseStr99 = "（11）如本合同项下借款系以信用方式发放，乙方的信用等级、盈利水平、资产负债率、经营活动现金净流量等指标不符合甲方信用贷款条件的；或乙方未经甲方书面同意，以其有效经营资产向他人设定抵/质押担保或对外提供保证担保，已经或可能影响到其在本合同项下义务的履行；";
//            stringList.add(clauseStr99);
//            String clauseStr100 = "（12）若乙方为自然人，乙方因被羁押、刑事拘留、劳动教养等被限制人身自由，对其偿债能力产生不利影响；";
//            stringList.add(clauseStr100);
//            String clauseStr101 = "(13) 若乙方为自然人，乙方死亡或被宣告死亡、失踪或被宣告失踪，或者成为限制民事行为能力人或丧失民事行为能力，而无继承人、受遗赠人、监护人或财产代管人，或其继承人、受遗赠人、监护人或财产代管人拒绝代乙方履行本合同项下义务的；";
//            stringList.add(clauseStr101);
//            String clauseStr102 = "（14）可能导致甲方在本合同项下债权的实现受到不利影响的其他情形。";
//            stringList.add(clauseStr102);
            String clauseStr103 = "12.2 乙方违约，甲方或丙方有权采取下列一项或多项措施";
            stringList.add(clauseStr103);
            String clauseStr104 = "（1）要求乙方限期纠正违约行为；";
            stringList.add(clauseStr104);
//            String clauseStr105 = "（2）停止依据本合同和甲方与乙方之间的其他合同向乙方发放借款和其他融资款项，部分或全部取消乙方未提取转让债权和其他融资款项；";
//            stringList.add(clauseStr105);
            String clauseStr106 = "（2）宣布本合同下未偿还的转让债权到期，立即收回未偿还款项；";
            stringList.add(clauseStr106);
            String clauseStr107 = "（3）要求乙方赔偿因其违约给甲方和丙方造成的损失；";
            stringList.add(clauseStr107);
            String clauseStr108 = "（4）法律法规规定、本合同约定或甲方认为必要的其他措施。";
            stringList.add(clauseStr108);
            String clauseStr109 = "12.3 债权到期（含被宣布立即到期）乙方未按约偿还的，甲方有权自逾期之日起按本合同约定的逾期罚息利率计收罚息。 ";
            stringList.add(clauseStr109);
            String clauseStr110 = "12.4 乙方未按本合同约定用途使用借款的，甲方有权自借款被挪用之日起，对挪用部分按本合同约定的挪用借款罚息利率计收罚息。";
            stringList.add(clauseStr110);
            String clauseStr111 = "12.5 乙方未按期回购债权本金、利息（包括罚息）或其他应付款项的，甲方或丙方有权通过媒体进行公告催收。";
            stringList.add(clauseStr111);
            String clauseStr112 = "12.6 乙方的关联方与乙方之间的控制或被控制关系发生变化，或乙方的关联方发生上述第12.1条中除第（1）、（2）两项之外的其他情形，已经或可能影响到乙方在本合同项下义务的履行的，甲方有权采取本合同约定的各项措施。";
            stringList.add(clauseStr112);
            String clauseStr105 = "12.7 丙方保留将借款人违约失信的相关信息向监管机构、合法的第三方征信机构及媒体等机构披露的权利。";
            stringList.add(clauseStr105);
            String tilte13 = "t第十三条  保密条款";
            stringList.add(tilte13);
            String clauseStr113 = "13.1 甲乙丙三方（下称三方）应对其在履约过程中获悉的任何有关对方的非公开信息和资料保密，未经对方事先书面同意，不得用于本合同约定之外的目的，不得向其他方披露或以其他方式公开，但相关法律法规另有规定或者监管部门另有要求的除外。";
            stringList.add(clauseStr113);
            String clauseStr114 = "13.2 三方一致同意，上述保密义务同样适用于三方为履行本合同而各自委托的专业机构和人员。";
            stringList.add(clauseStr114);
            String titleStr14 = "t第十四条  争议的解决";
            stringList.add(titleStr14);
            String clauseStr115 = "本合同的订立、效力、解释、履行及争议的解决均适用中华人民共和国法律。凡由本合同引起的或与本合同有关的一切争议和纠纷，甲乙双方当事人应协商解决；协商不成，可向新华久久贷所在地法院通过诉讼方式解决。";
            stringList.add(clauseStr115);
            String titleStr15 = "t第十五条 合同的变更和解除";
            stringList.add(titleStr15);
            String clauseStr116 = "15.1 对本合同的任何变更应由各方协商一致并以书面形式作出。变更条款或协议构成本合同的一部分，与本合同具有同等法律效力。";
            stringList.add(clauseStr116);
            String clauseStr117 = "15.2 本合同的解除，不影响有关争议解决条款的效力。";
            stringList.add(clauseStr117);
            String titleStr16 = "t第十六条：其他";
            stringList.add(titleStr16);
            String clauseStr118 = "17.1 税务缴纳：甲方在交易过程中产生的相关税费，由甲方自行向主管机关申报、缴纳。";
            stringList.add(clauseStr118);
            String clauseStr119 = "17.2 各方自行保管其在本网站及托管机构注册的账户信息和密码。若有泄露，产生的一切后果自行承担；并及时书面通知丙方。";
            stringList.add(clauseStr119);
            String clauseStr120 = "17.3 甲方有权依据有关法律法规的规定或金融监管机构的要求，将与本合同有关的信息和乙方其他相关信息提供给中国人民银行征信系统和其他依法设立的信用信息数据库，供具有适当资格的机构或个人查询和使用。甲方也有权为本合同订立和履行之目的，通过中国人民银行征信系统和其他依法设立的信用信息数据库查询乙方的相关信息。";
            stringList.add(clauseStr120);
            String clauseStr121 = "17.4本合同所述之“关联方”、“关联方关系”、“关联方交易”、“主要投资者个人”、“关键管理人员”等词语与财政部颁布的《企业会计准则第36号——关联方披露》（财会[2006]3号）以及其后对该准则的修订中的相同词语有相同含义。";
            stringList.add(clauseStr121);
            String clauseStr122 = "17.5 本合同所述之环境和社会风险指乙方及其重要关联方在建设、生产、经营活动中可能给环境和社会带来的危害及相关风险，包括与耗能、污染、土地、健康、安全、移民安置、生态保护、气候变化等有关的环境与社会问题。";
            stringList.add(clauseStr122);
            String clauseStr123 = "17.6 乙方根据其业务规则制作保留的关于本合同项下借款的单据和凭证，构成证明双方债权债务关系的有效证据，对乙方具有约束力。";
            stringList.add(clauseStr123);
            String titleStr18 = "t第十八条 双方约定的其他事项";
            stringList.add(titleStr18);
            String clauseStr124 = "18.1________________________________________________________";
            stringList.add(clauseStr124);
            String clauseStr125 = "18.2________________________________________________________";
            stringList.add(clauseStr125);
            String clauseStr126 = "18.3________________________________________________________";
            stringList.add(clauseStr126);

            String clauseStr127 = "甲方：";
            String clauseStr128 = "乙方：（盖章）";
            String clauseStr129 = "法定代表人/授权代理人：";
            String clauseStr130 = "丙方：（盖章）";
            List<Map<String, PdfParagraph>> downList = new ArrayList<Map<String, PdfParagraph>>();
            Map<String, PdfParagraph> downParams = null;
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr127));
            downParams.put("value", new PdfParagraph("           详 见 附 件 《 甲 方 名 单 》     ", 10));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr128));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr129));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr130));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            downParams = new HashMap<String, PdfParagraph>();
            downParams.put("name", new PdfParagraph(clauseStr129));
            downParams.put("value", new PdfParagraph("______________________________________________"));
            downList.add(downParams);
            ex.exportPdf(agreementMessage.getAgreementNo(), list, stringList, dataset, downList,
                    agreementMessage.getContractStartDate(), out, agreementMessage.getLoanContractType());
        }
        
        return null;
    }

    @Override
    public BaseResult exportObligatoryRight(List<InvestInformationDO> dataset, ContractBean contractBean,
                                            OutputStream out) {
    	 String topTableStr6 = "甲方（出让人）：";
         String topTableStr7 = this.hideUserRealName(contractBean.getPartyBName());
         String topTableStr8 = "新华久久贷注册用户名：";
         String topTableStr9 = contractBean.getPartyBUserName();
         String topTableStr10 = "身份证号码：";
         String topTableStr11 = contractBean.getPartyBCardNo().substring(0, 6)
                 + "********"
                 + contractBean.getPartyBCardNo().substring(contractBean.getPartyBCardNo().length() - 4,
                         contractBean.getPartyBCardNo().length());
        String topTableStr = "乙方（受让人）：";
        String topTableStr1 = "  详 见 附 件 《 乙 方 名 单 》 ";
        if(ContractObject.ASSIGNEE.getType()==contractBean.getContractObject()){
        	topTableStr1 = dataset.get(0).getRealName();
        }
        String topTableStr12 = "丙方（下称居间方）：";
        String topTableStr13 = contractBean.getPartyCName();
        String topTableStr14 = "网站：";
        String topTableStr15 = contractBean.getPartyCUserName();
        String topTableStr16 = "住所：";
        String topTableStr17 = contractBean.getPartyCCardNo();
        List<Map<String, PdfParagraph>> topList = new ArrayList<Map<String, PdfParagraph>>();
        Map<String, PdfParagraph> params = null;
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr6));
        params.put("value", new PdfParagraph(topTableStr7, 10));
        topList.add(params);
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr8));
        params.put("value", new PdfParagraph(topTableStr9, 10));
        topList.add(params);
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr10));
        params.put("value", new PdfParagraph(topTableStr11, 10));
        topList.add(params);
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr));
        params.put("value", new PdfParagraph(topTableStr1, 10));
        topList.add(params);      
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr12));
        params.put("value", new PdfParagraph(topTableStr13, 10));
        topList.add(params);
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr14));
        params.put("value", new PdfParagraph(topTableStr15, 10));
        topList.add(params);
        params = new HashMap<String, PdfParagraph>();
        params.put("name", new PdfParagraph(topTableStr16));
        params.put("value", new PdfParagraph(topTableStr17, 10));
        topList.add(params);
        List<String> stringList = new ArrayList<String>();
        String clauseStr1 = "鉴于：";
        stringList.add(clauseStr1);
        String clauseStr2 = "1、丙方是一家依法成立并有效存续的金融信息服务有限责任公司，拥有投融资网络服务平台（域名为www.xh99d.com）；并能有效提供融资居间撮合、管理等服务。";
        stringList.add(clauseStr2);
        String clauseStr3 = "2、甲方、乙方均为具有完全民事权利能力和完全民事行为能力的自然人或法人，且自愿注册成为丙方网站用户。";
        stringList.add(clauseStr3);
        String clauseStr4 = "3、甲方、乙方通过丙方达成债权转让意向；自愿遵守本合同、丙方用户服务合同以及丙方通过网站公布的各项相关业务规则。";
        stringList.add(clauseStr4);
        String clauseStr5 = "鉴此，根据中华人民共和国相关法律法规，遵循平等自愿、公平、诚实信用的原则，经三方协商，就甲方向乙方转让所拥有的债权相关事宜达成一致，签订本债权转让合同。";
        stringList.add(clauseStr5);
        String clauseStr6 = "（注：本合同中托管机构是指丙方委托的银行资金托管机构；账户是指甲方、乙方在银行资金托管机构开立的账户。）";
        stringList.add(clauseStr6);
        String title1 = "t第一条 转让债权的范围";
        stringList.add(title1);
        String str1 = "甲方转让给乙方的债权为：编号为" + contractBean.getOriginContractNo() + " 合同项下的本金、利息及相应的权利。";
        stringList.add(str1);
        String title2 = "t第二条 转让债权的账面价值、转让价款、期限";
        stringList.add(title2);
//      String str2 = "2.1 截至" + contractBean.getContractStartDate() + "，本合同项下的债权账面价值为本金"
//      + contractBean.getContractPrincipal() + "(大写："
//      +NumberToCN.number2CNMontrayUnit(new BigDecimal(contractBean.getContractPrincipal())) + "），利息"
//      + contractBean.getContractInterest() + "（大写："
//      +NumberToCN.number2CNMontrayUnit(new BigDecimal(contractBean.getContractInterest())) + " ） ";
        String str2 = "2.1 截至" + contractBean.getContractStartDate() + "，本合同项下的债权账面价值为本金"
                + contractBean.getContractPrincipal() + "(大写："
                + NumberToCN.number2CNMontrayUnit(new BigDecimal(contractBean.getContractPrincipal())) + "），利息"
                + "以甲方与债务人签订的原合同约定标准计算（如存在提前或逾期还款的，利息计算至债务人实际支付之日）。";
        stringList.add(str2);
        String str3 = "2.2 本合同项下甲方转让给乙方的债权转让价款为人民币" + contractBean.getContractMoney() + "（大写："
                + NumberToCN.number2CNMontrayUnit(new BigDecimal(contractBean.getContractMoney())) + "）";
        stringList.add(str3);
        String str4 = "2.3 本合同项下转让债权的期限为" + contractBean.getContractTerm() + "天（即距债务到期届满剩余的时间），如债务人提前或逾期还款的，期限计算至债务人实际履行义务之日。";
        stringList.add(str4);
        String str5 = "2.4 实际借款金额及合同生效日与本合同约定不一致的，以丙方平台上生成的电子合同为准。因乙方提前还款的，以提前还款日确定借款期限届满日期。丙方平台上生成的电子合同是合同的组成部分，与本合同具有同等法律效力。";
        stringList.add(str5);
        String title3 = "t第三条 转让债权利率及计付";
        stringList.add(title3);
        String str6 = "3.1 本合同项下的债权转让的年利率为" + contractBean.getAnnualInterest() + "%的固定利率，在债权期限内利率保持不变。";
        stringList.add(str6);
        String str7 = "3.2 本合同项下的借款自合同生效日开始计息，按月结息，结息日为" + contractBean.getSettlementDate()
                + "日，借款到期，利随本清。利率的折算：日利率=月利率÷30=年利率÷360。";
        stringList.add(str7);
        String str8 = "3.3 本合同项下逾期还款的罚息利率按在原借款合同约定的利率基础上加收" + contractBean.getPunitiveInterest()
                + "%确定，挪用借款的罚息利率按在原借款合同约定的利率基础上加收" + contractBean.getPunitiveInterest() + "%确定。既逾期又挪用的罚息利率择其重而确定，不能并处。";
        stringList.add(str8);
        String title4 = "t第四条 转让债权";
        stringList.add(title4);
        String str9 = "4.1 甲方通过平台进行债权转让，乙方通过平台受让债权。乙方投标完成后托管机构冻结乙方账户中相应的投标资金，本合同成立。在项目满标时托管机构将冻结资金直接划转至甲方账户，债权转让成功，本合同生效。";
        stringList.add(str9);
        String str10 = "4.2 乙方无需征得债务人同意，可将本合同项下的权利转让给第三方。乙方权利的转让应当通知债务人，乙方可委托甲方代为通知。";
        stringList.add(str10);
        String str11 = "4.3 在债权存续期间，乙方再次转让后的利息、罚息按照第三条的规定计算。";
        stringList.add(str11);
		String str98 = "4.4 利息将在结息日直接支付给当日实际债权持有人。";
        stringList.add(str98);
        String title5 = "t第五条 还款途径";
        stringList.add(title5);
        String str12 = "债权转让后，甲、乙双方同意，还本付息途径为：由原债务人（指《借款合同》中债务人）支付给第一任债权人（指《借款合同》中债权人）直接通过平台支付给乙方。";
        stringList.add(str12);
        String title6 = "t第六条 服务费";
        stringList.add(title6);
        String str13 = "6.1 丙方为本合同债权转让所提供的服务向甲方收取服务费用。向甲方收取的服务费以网站公布的收费标准、方式和时间为准。";
        stringList.add(str13);
        String str14 = "6.2 若乙方受让的债权发生原债务人无法清偿到期债务的情况，乙方不得要求丙方退还其所收服务费。";
        stringList.add(str14);
        String title7 = "t第七条 甲方的权利与义务";
        stringList.add(title7);
        String str15 = "7.1 甲方声明：";
        stringList.add(str15);
        String str16 = "（1）甲方是转让债权的合法权利人；";
        stringList.add(str16);
        String str17 = "（2）甲方的债权转让是其真实意思表示，符合相关法律法规及公司章程等内部制度有关规定；";
        stringList.add(str17);
        String str18 = "（3）甲方债权没有瑕疵，并为甲方自主支配。";
        stringList.add(str18);
        String str19 = "7.2 债权转让后，甲方不得与原债务人对债权文件的内容做任何变更，亦不得转移或变相转移债权文件项下的义务。";
        stringList.add(str19);
        String title8 = "t第八条 乙方的权利与义务";
        stringList.add(title8);
        String str20 = "8.1 乙方了解丙方平台公布债权转让项目后，拥有自主决定是否受让债权的权利。";
        stringList.add(str20);
        String str21 = "8.2 乙方享有按本合同约定取得受让债权带来的利息收益。";
        stringList.add(str21);
        String str22 = "8.3 乙方需按照约定及时、足额向丙方支付服务费。";
        stringList.add(str22);
        String str23 = "8.4 乙方变更账户信息、通讯地址、电话等相关重要信息，应最迟在信息更改后次日通过平台修改信息，因乙方未及时修改信息而导致损失的，由乙方自行承担责任。";
        stringList.add(str23);
        String str24 = "8.5 乙方声明：";
        stringList.add(str24);
        String str25 = "（1）乙方资金来源合法，并为乙方自主支配；";
        stringList.add(str25);
        String str26 = "（2）乙方均为具有完全民事权利能力和完全民事行为能力的自然人或法人；";
        stringList.add(str26);
        String str27 = "（3）一切投资风险由乙方承担。";
        stringList.add(str27);
        String str28 = "8.6 乙方为法人的，签订本合同已根据本公司章程规定的程序和权限获得所有必需的授权或批准，不违反法律法规及其他相关规定。";
        stringList.add(str28);
        String str29 = "8.7 在债权受让时，授权丙方委托托管机构将资金划转至甲方账户。";
        stringList.add(str29);
        String str30 = "8.8 乙方在原债务人未按照约定偿还本息时，有权要求担保方履行担保责任。";
        stringList.add(str30);
        String title9 = "t第九条 丙方的权利与义务";
        stringList.add(title9);
        String str31 = "9.1 甲方或乙方点击同意网站平台公示的相关规则按钮时，即视为已阅读并完全接受丙方公示的相关规则。";
        stringList.add(str31);
        String str32 = "9.2 丙方有权审核甲方的债权转让申请手续。";
        stringList.add(str32);
        String str33 = "9.3 丙方为甲乙双方提供相关服务，有权受委托采取包括但不限于通过站内信、邮件、电话、短信、公函等形式进行通知，公告，催收。";
        stringList.add(str33);
        String str34 = "9.4 由于电信网络服务终止、黑客攻击等客观因素出现，导致合同内容延迟履行或不能履行的，丙方免于责任。 ";
        stringList.add(str34);
        String title10 = "t第十条 违约责任";
        stringList.add(title10);
        String str35 = "任何一方违反本合同的约定，均应承担违约责任，并赔偿守约方因此遭受的损失（包括但不限于诉讼费、律师费等）。 ";
        stringList.add(str35);
        String title11 = "t第十一条 合同的变更、解除";
        stringList.add(title11);
        String str36 = "11.1 对本合同的任何变更应由各方协商一致并以书面形式作出。变更条款或协议构成本合同的一部分，与本合同具有同等法律效力。";
        stringList.add(str36);
        String str37 = "11.2 本合同的解除，不影响有关争议解决条款的效力。";
        stringList.add(str37);
        String title12 = "t第十二条 保密";
        stringList.add(title12);
        String str38 = "12.1 甲乙丙三方（下称三方）应对其在履约过程中获悉的任何有关对方的非公开信息和资料保密，未经对方事先书面同意，不得用于本合同约定之外的目的，不得向其他方披露或以其他方式以公开，但相关法律法规另有规定或者监管部门另有要求的除外。";
        stringList.add(str38);
        String str39 = "12.2 三方一致同意，上述保密义务同样适用于三方为履行本合同而各自委托的专业机构和人员。";
        stringList.add(str39);
        String title13 = "t第十三条 法律适用和争议解决";
        stringList.add(title13);
        String str40 = "本合同的订立、效力、解释、履行及争议的解决均适用中华人民共和国法律。凡由本合同引起的或与本合同有关的一切争议和纠纷，甲乙双方当事人应协商解决；协商不成，可向新华久久贷所在地法院通过诉讼方式解决。";
        stringList.add(str40);
        String title14 = "t第十四条 其他";
        stringList.add(title14);
        String str41 = "14.1 本合同所涉名词含义，除在上下文中另有定义外，以丙方公布的相关定义含义为准。";
        stringList.add(str41);
        String str42 = "14.2 税务缴纳：甲乙双方在交易过程中产生的相关税费，由双方自行向其主管机关申报、缴纳。";
        stringList.add(str42);
        String str43 = "14.3 乙方未行使或部分行使或延迟行使本合同项下的任何权利，不构成对该权利或其他权利的放弃和变更，也不影响其进一步行使该权利或其他权利。";
        stringList.add(str43);
        String str44 = "14.4 本合同任何条款的无效或不可执行，不影响其他条款的有效性和可执行性，也不影响整个合同的效力。";
        stringList.add(str44);
        String str45 = "14.5 甲乙双方确认，自行保管其在本网站注册的账户信息及密码，在托管机构开立的账户。若有泄露，由此所产生的一切后果自行承担并及时书面通知丙方。";
        stringList.add(str45);
        String title15 = "t第十五条 双方约定的其他事项";
        stringList.add(title15);
        String str46 = "15.1_______________________________________";
        stringList.add(str46);
        String str47 = "15.2_______________________________________";
        stringList.add(str47);
        String str48 = "15.3_______________________________________";
        stringList.add(str48);
        CreditorExportPdf creditorExportPdf = new CreditorExportPdf();
        creditorExportPdf.exportPdf(contractBean.getContractNo(), stringList, topList, dataset, out);
        return null;
    }
}
