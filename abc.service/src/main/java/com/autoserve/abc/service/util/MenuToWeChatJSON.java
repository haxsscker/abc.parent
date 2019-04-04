package com.autoserve.abc.service.util;

import java.util.List;

import com.autoserve.abc.service.biz.entity.WeChatMenuItem;

/**
 * 类MenuToWeChatJSON.java的实现描述：将微信菜单更新到公共号
 * 
 * @author WangMing 2015年6月25日 下午4:11:42
 */
public class MenuToWeChatJSON {

    /**
     * 将查询出的菜单拼接成符合格式的数据
     * 
     * @param list
     * @return
     */
    public static String menuItemToJSON(List<WeChatMenuItem> list) {

        String appid = SystemGetPropeties.getBossString("AppID");
        String SafeUrl = SystemGetPropeties.getBossString("SafeUrl");
        String wechatUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
                + "&redirect_uri=http://" + SafeUrl + "/wechat&response_type=code&scope=snsapi_base&state=";
        String url = "http://" + SafeUrl;
        StringBuffer jsonData = new StringBuffer();
        jsonData.append("{\"button\":[");
        for (int i = 0; i < list.size(); i++) {

            if (i == (list.size() - 1)) {

                if (list.get(i).getChildren().size() > 0) {
                    jsonData.append("{");
                    jsonData.append("\"name\":\"" + list.get(i).getMenuName() + "\",");
                    jsonData.append("\"sub_button\":[");
                    //遍历子菜单
                    for (int j = 0; j < list.get(i).getChildren().size(); j++) {
                        if (j == list.get(i).getChildren().size() - 1) {
                            jsonData.append("{");
                            jsonData.append("\"type\":\"view\",");
                            jsonData.append("\"name\":\"" + list.get(i).getChildren().get(j).getMenuName() + "\",");
                            //非授权网页
                            if (list.get(i).getChildren().get(j).getMenuSafe() == 0) {
                                jsonData.append("\"url\":\"" + url + list.get(i).getChildren().get(j).getMenuUrl()
                                        + "\"");
                            } else {//授权网页
                                jsonData.append("\"url\":\"" + wechatUrl
                                        + list.get(i).getChildren().get(j).getMenuKey() + "#wechat_redirect\"");
                            }
                            jsonData.append("}");
                        } else {
                            jsonData.append("{");
                            jsonData.append("\"type\":\"view\",");
                            jsonData.append("\"name\":\"" + list.get(i).getChildren().get(j).getMenuName() + "\",");
                            //非授权网页
                            if (list.get(i).getChildren().get(j).getMenuSafe() == 0) {
                                jsonData.append("\"url\":\"" + url + list.get(i).getChildren().get(j).getMenuUrl()
                                        + "\"");
                            } else {
                                //授权网页 
                                jsonData.append("\"url\":\"" + wechatUrl
                                        + list.get(i).getChildren().get(j).getMenuKey() + "#wechat_redirect\"");
                            }
                            jsonData.append("},");
                        }
                    }
                    jsonData.append("]");
                    jsonData.append("}");

                } else {
                    jsonData.append("{");
                    jsonData.append("\"type\":\"view\",");
                    jsonData.append("\"name\":\"" + list.get(i).getMenuName() + "\",");
                    if (list.get(i).getMenuSafe() == 0) {
                        //非网页授权
                        jsonData.append("\"url\":\"" + url + list.get(i).getMenuUrl() + "\"");
                    } else {
                        //网页授权
                        jsonData.append("\"url\":\"" + wechatUrl + list.get(i).getMenuKey() + "#wechat_redirect\"");
                    }
                    jsonData.append("}");
                }

            } else {

                if (list.get(i).getChildren().size() > 0) {
                    jsonData.append("{");
                    jsonData.append("\"name\":\"" + list.get(i).getMenuName() + "\",");
                    jsonData.append("\"sub_button\":[");
                    //遍历子菜单
                    for (int j = 0; j < list.get(i).getChildren().size(); j++) {
                        if (j == list.get(i).getChildren().size() - 1) {
                            jsonData.append("{");
                            jsonData.append("\"type\":\"view\",");
                            jsonData.append("\"name\":\"" + list.get(i).getChildren().get(j).getMenuName() + "\",");
                            //非网页授权
                            if (list.get(i).getChildren().get(j).getMenuSafe() == 0) {
                                jsonData.append("\"url\":\"" + url + list.get(i).getChildren().get(j).getMenuUrl()
                                        + "\"");
                            } else {//网页授权
                                jsonData.append("\"url\":\"" + wechatUrl
                                        + list.get(i).getChildren().get(j).getMenuKey() + "#wechat_redirect\"");
                            }
                            jsonData.append("}");
                        } else {
                            jsonData.append("{");
                            jsonData.append("\"type\":\"view\",");
                            jsonData.append("\"name\":\"" + list.get(i).getChildren().get(j).getMenuName() + "\",");
                            //非网页授权
                            if (list.get(i).getChildren().get(j).getMenuSafe() == 0) {
                                jsonData.append("\"url\":\"" + url + list.get(i).getChildren().get(j).getMenuUrl()
                                        + "\"");
                            } else {//网页授权
                                jsonData.append("\"url\":\"" + wechatUrl
                                        + list.get(i).getChildren().get(j).getMenuKey() + "#wechat_redirect\"");
                            }

                            jsonData.append("},");
                        }
                    }
                    jsonData.append("]");
                    jsonData.append("},");

                } else {
                    jsonData.append("{");
                    jsonData.append("\"type\":\"view\",");
                    jsonData.append("\"name\":\"" + list.get(i).getMenuName() + "\",");
                    //非网页授权
                    if (list.get(i).getMenuSafe() == 0) {
                        jsonData.append("\"url\":\"" + url + list.get(i).getMenuUrl() + "\"");
                    } else {//网页授权
                        jsonData.append("\"url\":\"" + wechatUrl + list.get(i).getMenuKey() + "#wechat_redirect\"");
                    }
                    jsonData.append("},");
                }

            }

        }

        jsonData.append("]}");

        return jsonData.toString();
    }
}
