package protocol.describeTopicPartitions.clusterMetadata;

import datatypes.CompactString;
import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;
import util.Util;

import java.io.FileInputStream;

@Data
@AllArgsConstructor
public class FeatureLevelRecord {
    /**
     * Frame Version is a 1-byte integer indicating the version of the format of the record.
     */
    // INT8
    byte frameVersion;

    /**
     * Type is a 1-byte integer indicating the type of the record.
     */
    // INT8
    byte type;

    /**
     * Version is a 1-byte integer indicating the version of the feature level record.
     */
    // INT8
    byte version;

    /**
     * Name length is a unsigned variable size integer indicating the length of the name.
     * But, as name is a compact string, the length of the name is always length - 1.
     */
    // UNSIGNED_VARINT
    int nameLength;

    /**
     * Name is a compact string
     */
    CompactString name;

    /**
     * Feature Level is a 2-byte big-endian integer indicating the level of the feature.
     */
    // INT16
    short featureLevel;


    /**
     * Tagged fields
     */
    // VARINT
    int taggedFields;

    public static DecodedResponse<FeatureLevelRecord> decode(byte[] data, long offset) throws Exception {
        long offsetBeforeStart=offset;

        byte frameVersion=data[(int)offset];
        offset++;
        byte type=data[(int)offset];
        offset++;
        byte version=data[(int)offset];
        offset++;

        DecodedResponse<CompactString> decodedName =CompactString.decode(data,(int)offset);

        int nameLength=decodedName.getContent().getValue().length()+1;

        offset+=decodedName.getBytesRead();

        short featureLevel=Util.readINT16FromBytes(data,(int)offset);

        offset+=2;

        DecodedResponse<Integer> decodedTaggedFields=Util.readSignedVarInt(data,(int)offset);
        offset+=decodedTaggedFields.getBytesRead();

        int taggedFields=decodedTaggedFields.getContent();

        long bytesRead=offset-offsetBeforeStart;

        return new DecodedResponse<>(
                new FeatureLevelRecord(frameVersion,type,version,nameLength,decodedName.getContent(),featureLevel,taggedFields),
                bytesRead);
    }

}
