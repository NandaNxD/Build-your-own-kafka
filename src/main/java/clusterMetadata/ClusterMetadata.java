package clusterMetadata;

public class ClusterMetadata{

    /**
     * Base Offset is a 8-byte big-endian integer indicating the offset of the first record in this batch.
     */
    // INT64 big endian
    long baseOffset;

    /***
     *  Batch Length is a 4-byte big-endian integer indicating the length of the entire record batch in bytes.
     *  This value excludes the Base Offset (8 bytes) and the Batch Length (4 bytes) itself, but includes all other bytes in the record batch.
     */
    // INT32 big endian
    int batchLength;

    /**
     * Partition Leader Epoch is a 4-byte big-endian integer indicating the epoch of the leader for this partition.
     * It is a monotonically increasing number that is incremented by 1 whenever the partition leader changes.
     * This value is used to detect out of order writes.
     */
    // INT32
    int partitionLeaderEpoch;

    /**
     * Magic Byte is a 1-byte integer indicating the version of the record batch format.
     * This value is used to evolve the record batch format in a backward-compatible way.
     */
    // INT8
    byte magicByte;

    /**
     * CRC is a 4-byte big-endian integer indicating the CRC32-C checksum of the record batch.
     * The CRC is computed over the data following the CRC field to the end of the record batch.
     * The CRC32-C (Castagnoli) polynomial is used for the computation.
     */
    // INT32
    int crc;



}