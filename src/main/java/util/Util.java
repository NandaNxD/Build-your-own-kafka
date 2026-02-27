package util;

import models.DecodedResponse;

import java.io.ByteArrayOutputStream;

public class Util {

    public static byte[] encodeNullableINT8(int value){
        return new byte[]{(byte) (value)};
    }
    public static byte[] encodeINT16(int value){
        return encodeInteger(2,value);
    }

    public static byte[] encodeINT32(int value){
        return encodeInteger(4,value);
    }

    public static byte[] encodeInteger(int numberOfBytes,int value){
        byte[] result=new byte[numberOfBytes];
        for(int i=0;i<numberOfBytes;i++){
            result[numberOfBytes-i-1]= (byte)((value)>>(i*8) & 0xFF);
        }
        return result;
    }

    public static byte[] convertIntegerToVarIntBytes(int value){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        while(value>=1){
            int encodedValue=(value&127);
            value=value>>>7;

            if(value>0){
                encodedValue |= 128;
            }

            outputStream.write(encodedValue);
        }

        return outputStream.toByteArray();
    }

    public static DecodedResponse<Integer> decodeVarInt(byte[] data, int offset){
        boolean end=false;
        int result=0;

        int bytesRead=0;

        while(!end){
            result |=(data[offset]&127)<<8*bytesRead;
            if((data[offset]&128)==0){
                end=true;
            }
            offset++;
            bytesRead++;
        }
        return new DecodedResponse<>(result,bytesRead);
    }
}
