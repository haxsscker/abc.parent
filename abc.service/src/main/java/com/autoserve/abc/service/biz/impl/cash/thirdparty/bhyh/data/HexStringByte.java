package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.data;

public class HexStringByte {
	public static String stringToHex(String bin) {
		char[] digital = "0123456789ABCDEF".toCharArray();
		StringBuffer sb = new StringBuffer("");
		byte[] bs = bin.getBytes();

		for (int i = 0; i < bs.length; ++i) {
			int bit = (bs[i] & 240) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 15;
			sb.append(digital[bit]);
		}

		return sb.toString();
	}

	public static String hexToString(String hex) {
		String digital = "0123456789ABCDEF";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];

		for (int i = 0; i < bytes.length; ++i) {
			int temp = digital.indexOf(hex2char[2 * i]) * 16;
			temp += digital.indexOf(hex2char[2 * i + 1]);
			bytes[i] = (byte) (temp & 255);
		}

		return new String(bytes);
	}

	public static String byteToHex(byte[] b) {
		String hs = "";
		String tmp = "";

		for (int n = 0; n < b.length; ++n) {
			tmp = Integer.toHexString(b[n] & 255);
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}

		tmp = null;
		return hs.toUpperCase();
	}

	public static String byteToHex2(byte[] b) {
		String hs = "";
		String tmp = "";

		for (int n = 0; n < b.length; ++n) {
			tmp = Integer.toHexString(b[n] & 255);
			if (tmp.length() == 1) {
				hs = hs + "0x0" + tmp;
			} else {
				hs = hs + "0x" + tmp;
			}
		}

		tmp = null;
		return hs;
	}

	public static byte[] hexToByte(byte[] b) {
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("���볤�Ȳ���ż��");
		} else {
			byte[] b2 = new byte[b.length / 2];

			for (int n = 0; n < b.length; n += 2) {
				String item = new String(b, n, 2);
				b2[n / 2] = (byte) Integer.parseInt(item, 16);
			}

			b = (byte[]) null;
			return b2;
		}
	}
	  
    /**
     * 把16进制字符串转换成字节数组  
     * @param hexStr
     * @return
     */
    public static byte[] HexString2Bytes(String hexStr) {  
       byte[] b = new byte[hexStr.length() / 2];  
       for (int i = 0; i < b.length; i++)   
         b[i]=(byte) Integer.parseInt(hexStr.substring(2*i,2*i+2),16);  
      return b;  
    }  
    
        
     /**
      * 把字节数组转换成16进制字符串  
      * @param byteArray
      * @return
      */
     public static  String bytesToHexString(byte[] byteArray){  
       StringBuffer hexStr = new StringBuffer(byteArray.length*2);  
       for (int i = 0; i < byteArray.length; i++) {  
           String sTemp= Integer.toHexString(0xFF& byteArray[i]);  
           int j=0;  
           while(j<2-sTemp.length())  
               {sTemp="0"+sTemp;j++;}  
           hexStr.append(sTemp.toUpperCase());  
         }  
        return hexStr.toString();  
      }  
}
