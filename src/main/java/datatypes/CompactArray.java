package datatypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;
import protocol.apiversions.ApiKey;
import protocol.describeTopicPartitions.TopicRequest;
import protocol.describeTopicPartitions.TopicResponse;
import util.Util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
        if(arrayList!=null){
            if(className== ApiKey.class){

                for(T item:arrayList){
                    outputStream.write(((ApiKey)item).encode());
                }
            }
            else if(className== TopicResponse.class){
                for(T item:arrayList){
                    outputStream.write(((TopicResponse)item).encode());
                }
            }
            else{
                throw new Exception("Class not implemented for encoding in compact array");
            }
        }

        return outputStream.toByteArray();
    }

    public static<T> DecodedResponse<CompactArray<T>> decode(byte[] data, int offset,Class<T> type) throws Exception {
        /**
         * Compact array stars with Little Endian unsigned int length+1,
         * so to get actual length, need to do -1
         */

        int offsetBeforeReading=offset;

        DecodedResponse<Integer> decodedCompactArrayLength=Util.decodeVarInt(data,offset);
        offset+= (int) decodedCompactArrayLength.getBytesRead();

        int compactArrayLength=decodedCompactArrayLength.getContent()-1;

        /**
         * Decode every item in the array
         */
        List<T> topicRequestList=new ArrayList<>();

        for(int i=0;i<compactArrayLength;i++){
            if(type==TopicRequest.class){
                DecodedResponse<TopicRequest> decodedTopicRequest=TopicRequest.decode(data,offset);

                offset+= (int) decodedTopicRequest.getBytesRead();
                topicRequestList.add((T) decodedTopicRequest.getContent());
            }
            else{
                throw new Exception("decode method in CompactArray not implemented for this class");
            }
        }

        int bytesRead=offset-offsetBeforeReading;

        CompactArray<T> compactArray=new CompactArray<>(topicRequestList,null);

        return new DecodedResponse<>(compactArray,bytesRead);
    }

}
