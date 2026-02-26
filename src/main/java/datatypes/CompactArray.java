package datatypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import protocol.apiversions.ApiKey;
import util.Util;

import java.io.ByteArrayOutputStream;
import java.util.List;

@AllArgsConstructor
@Data
public class CompactArray<T> {
    List<T> arrayList;
    Class<T> className;

    public byte[] encode() throws Exception {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        /**
         * Write VarInt length of compact array first
         *
         * Adding 1 because thats how its supposed to be
         */
        outputStream.write(Util.convertIntegerToVarIntBytes(arrayList.size()+1));

        /**
         * Write each and every element in list to bytes
         */
        if(className== ApiKey.class){
            for(T item:arrayList){
                outputStream.write(((ApiKey)item).encode());
            }
        }
        else{
            throw  new Exception("Class not implemented for encoding in compact array");
        }
        return outputStream.toByteArray();
    }
}
