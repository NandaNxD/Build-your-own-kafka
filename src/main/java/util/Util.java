package util;

import models.DecodedResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

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

    public static DecodedResponse<Integer> readUnsignedVarInt(byte[] data, int offset){
        boolean end=false;
        int result=0;

        int bytesRead=0;

        while(!end){
            result |=(data[offset]&127)<<7*bytesRead;
            if((data[offset]&128)==0){
                end=true;
            }
            offset++;
            bytesRead++;
        }
        return new DecodedResponse<>(result,bytesRead);
    }

    public static DecodedResponse<Integer> readSignedVarInt(byte[] data, int offset){
        boolean end=false;
        int result=0;

        int bytesRead=0;

        while(!end){
            result |=(data[offset]&127)<<7*bytesRead;
            if((data[offset]&128)==0){
                end=true;
            }
            offset++;
            bytesRead++;
        }
        return new DecodedResponse<>((((result&1)>0)?-result/2:result/2),bytesRead);
    }

    public static short readINT16FromBytes(byte[] data, int offset){
        return ByteBuffer.wrap(Arrays.copyOfRange(data,offset,offset+2)).getShort();
    }

    public static int readINT32FromBytes(byte[] data, int offset){
        return ByteBuffer.wrap(Arrays.copyOfRange(data,offset,offset+4)).getInt();
    }

    public static long readINT64FromBytes(byte[] data, int offset){
        return ByteBuffer.wrap(Arrays.copyOfRange(data,offset,offset+8)).getLong();
    }



}


/**
 *
 int[] outputData={0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 79, 0, 0, 0, 1, 2, 176, 105, 69, 124, 0, 0, 0, 0, 0, 0, 0, 0, 1, 145, 224, 90, 248, 24, 0, 0, 1, 145, 224, 90, 248, 24, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 0, 0, 1, 58, 0, 0, 0, 1, 46, 1, 12, 0, 17, 109, 101, 116, 97, 100, 97, 116, 97, 46, 118, 101, 114, 115, 105, 111, 110, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 228, 0, 0, 0, 1, 2, 36, 219, 18, 221, 0, 0, 0, 0, 0, 2, 0, 0, 1, 145, 224, 91, 45, 21, 0, 0, 1, 145, 224, 91, 45, 21, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 0, 0, 3, 60, 0, 0, 0, 1, 48, 1, 2, 0, 4, 115, 97, 122, 0, 0, 0, 0, 0, 0, 64, 0, 128, 0, 0, 0, 0, 0, 0, 145, 0, 0, 144, 1, 0, 0, 2, 1, 130, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 0, 128, 0, 0, 0, 0, 0, 0, 145, 2, 0, 0, 0, 1, 2, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 16, 0, 0, 0, 0, 0, 64, 0, 128, 0, 0, 0, 0, 0, 0, 1, 0, 0, 144, 1, 0, 0, 4, 1, 130, 1, 1, 3, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 64, 0, 128, 0, 0, 0, 0, 0, 0, 145, 2, 0, 0, 0, 1, 2, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 16, 0, 0, 0, 0, 0, 64, 0, 128, 0, 0, 0, 0, 0, 0, 1, 0, 0};
 FileOutputStream fileOutputStream=new FileOutputStream(new File("__cluster_metadata.log"));

 for(int i=0;i<outputData.length;i++){
 fileOutputStream.write(outputData[i]);
 }

 fileOutputStream.flush();
 */