/*****************************************************
 * 项目名称         T68 Tag Reader
 * 包名称           com.mobilercn.util
 * 文件名称         JJHexHelper.java
 * 编写者           wu.s.w
 *                 www.mobilercn.com
 * 编写日期          2011-12-22
 * Version         1.0
 * 
 * 十六进制和字符串换转
 * 
 *****************************************************/
package com.mobilercn.util;


import java.io.ByteArrayOutputStream;


public class JJHexHelper {
	
	private static final byte[] ENCODE_TABLE = {
			(byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6', (byte)'7',
            (byte)'8', (byte)'9', (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f'
    };
	
	private static final byte[] DECODE_TABLE = new byte[128];
	
	static{
        for (int i = 0; i < ENCODE_TABLE.length; i++)
        {
        	DECODE_TABLE[ENCODE_TABLE[i]] = (byte)i;
        }
        
        DECODE_TABLE['A'] = DECODE_TABLE['a'];
        DECODE_TABLE['B'] = DECODE_TABLE['b'];
        DECODE_TABLE['C'] = DECODE_TABLE['c'];
        DECODE_TABLE['D'] = DECODE_TABLE['d'];
        DECODE_TABLE['E'] = DECODE_TABLE['e'];
        DECODE_TABLE['F'] = DECODE_TABLE['f'];
	}

	public static String encode(byte[] data){
		String result = null;
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int cnt = data.length;
			for(int i = 0; i < cnt; i ++){
	            int    v = data[i] & 0xff;
	            bos.write(ENCODE_TABLE[(v >>> 4)]);
	            bos.write(ENCODE_TABLE[v & 0xf]);
			}
			result = new String(bos.toByteArray());
			bos.close();
			bos = null;
		}
		catch(Exception e){}
		
		return result;
	}
	
	public static String encode(byte[] data, int start, int len){
		String result = null;
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for(int i = start; i < (start + len); i ++){
	            int    v = data[i] & 0xff;
	            bos.write(ENCODE_TABLE[(v >>> 4)]);
	            bos.write(ENCODE_TABLE[v & 0xf]);
			}
			result = new String(bos.toByteArray());
			bos.close();
			bos = null;
		}
		catch(Exception e){}
		
		return result;
	}
	
    private static boolean ignore(char c)
    {
        return (c == '\n' || c =='\r' || c == '\t' || c == ' ');
    }

    public static byte[] decode(String data)
    {
    	byte[] result = null;
    	
    	try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();   		
            byte    b1, b2;
            
            int     end = data.length();
            
            while (end > 0)
            {
                if (!ignore(data.charAt(end - 1)))
                {
                    break;
                }       
                end--;
            }
            
            int i = 0;
            while (i < end)
            {
                while (i < end && ignore(data.charAt(i)))
                {
                    i++;
                }
                
                b1 = DECODE_TABLE[data.charAt(i++)];
                
                while (i < end && ignore(data.charAt(i)))
                {
                    i++;
                }
                
                b2 = DECODE_TABLE[data.charAt(i++)];
                bos.write((b1 << 4) | b2);               
            }
            
            result = bos.toByteArray();
            bos.close();
            bos = null;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
}
