package protocol.describeTopicPartitions.clusterMetadata;

import datatypes.CompactString;

import java.io.FileInputStream;

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

    FeatureLevelRecord decode(FileInputStream fileInputStream,long offset) throws Exception {
        throw new Exception("Function not implemented");
    }

}
