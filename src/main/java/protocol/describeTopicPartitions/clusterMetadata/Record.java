package protocol.describeTopicPartitions.clusterMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;
import util.Util;

import java.io.FileInputStream;
import java.util.Arrays;

@Data
@AllArgsConstructor
public class Record {
    /**
     * Length is a signed variable size integer indicating the length of the record,
     * the length is calculated from the attributes field to the end of the record.
     */
    // VARINT
    int length;

    /**
     * Attributes is a 1-byte integer indicating the attributes of the record. Currently, this field is unused in the protocol.
     */
    byte attributes;

    /**
     * Timestamp Delta is a signed variable size integer indicating the difference between the timestamp of the record and the base timestamp of the record batch.
     */
    // VARINT
    int timestampDelta;

    /**
     * Offset Delta is a signed variable size integer indicating the difference between the offset of the record and the base offset of the record batch.
     */
    // VARINT
    int offsetDelta;

    /**
     * Key Length is a signed variable size integer indicating the length of the key of the record.
     */
    // VARINT
    int keyLength;

    /**
     * Key is a byte array indicating the key of the record.
     */
    byte[] key;

    /**
     * Value Length is a signed variable size integer indicating the length of the value of the record.
     */
    // VARINT
    int valueLength;

    /**
     * value is a byte array indicating the value of the record.
     * In this case, the value is the payload of the Feature level record type.
     */
    FeatureLevelRecord value;

    /**
     * Header array count is an signed variable size integer indicating the number of headers present.
     */
    // VARINT
    int headerArrayCount;

    public static DecodedResponse<Record> decode(byte[] data, long offset) throws Exception {
        long offsetAtStart=offset;

        DecodedResponse<Integer> decodedLength =Util.readSignedVarInt(data,(int)offset);
        int length=decodedLength.getContent();

        offset+=decodedLength.getBytesRead();

        byte attributes=data[(int)offset];
        offset++;

        DecodedResponse<Integer> decodedTimestampDelta=Util.readSignedVarInt(data,(int) offset);
        int timestampDelta=decodedTimestampDelta.getContent();
        offset+=decodedLength.getBytesRead();

        DecodedResponse<Integer> decodedOffsetDelta=Util.readSignedVarInt(data,(int) offset);
        int offsetDelta=decodedOffsetDelta.getContent();
        offset+=decodedOffsetDelta.getBytesRead();


        DecodedResponse<Integer> decodedKeyLength=Util.readSignedVarInt(data,(int) offset);
        int keyLength=decodedKeyLength.getContent();
        offset+=decodedKeyLength.getBytesRead();

        byte[] key= Arrays.copyOfRange(data,(int)offset,(int)offset+keyLength);
        offset+=keyLength;

        DecodedResponse<Integer> decodedValueLength=Util.readSignedVarInt(data,(int) offset);
        int valueLength=decodedValueLength.getContent();
        offset+=decodedValueLength.getBytesRead();

        DecodedResponse<FeatureLevelRecord> decodedFeatureLevelRecord=FeatureLevelRecord.decode(data,offset);
        FeatureLevelRecord featureLevelRecord=decodedFeatureLevelRecord.getContent();
        offset+=decodedFeatureLevelRecord.getBytesRead();

        DecodedResponse<Integer> decodedHeaderArrayCount=Util.readSignedVarInt(data,(int) offset);
        int headerArrayCount=decodedHeaderArrayCount.getContent();
        offset+=decodedHeaderArrayCount.getBytesRead();

        long bytesRead=offset-offsetAtStart;

        return new DecodedResponse<>(
                new Record(length,attributes,timestampDelta,offsetDelta,keyLength,key,valueLength,featureLevelRecord,headerArrayCount),
                bytesRead);
    }

}
