package protocol.describeTopicPartitions.clusterMetadata;

import java.io.FileInputStream;

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
    int key;

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

    Record decode(FileInputStream fileInputStream,long offset) throws Exception {
        throw new Exception("Function not implemented");
    }

}
