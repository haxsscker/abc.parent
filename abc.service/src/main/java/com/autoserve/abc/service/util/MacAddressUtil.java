package com.autoserve.abc.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-29,15:22
 */
public class MacAddressUtil {
    private static final Logger logger = LoggerFactory.getLogger(MacAddressUtil.class);

    public static String getMacAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
            byte[] mac = networkInterface.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            return sb.toString();
        } catch (UnknownHostException uhe) {
            logger.warn("无法获取服务器mac地址");
            return "";
        } catch (SocketException se) {
            logger.warn("无法获取服务器mac地址");
            return "";
        }
    }
}
