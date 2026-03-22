package protocol.describeTopicPartitions.clusterMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.DecodedResponse;
import util.Util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RecordBatch{

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

    /**
     * Attributes is a 2-byte big-endian integer indicating the attributes of the record batch.
     */
    // INT16
    short attributes;

    /**
     * Last Offset Delta is a 4-byte big-endian integer indicating the difference between the last offset of this record batch and the base offset.
     */

    // INT32
    int lastOffsetDelta;

    /**
     * Base Timestamp is a 8-byte big-endian integer indicating the timestamp of the first record in this batch.
     * Unix timestamp
     */
    // INT64
    long baseTimestamp;

    /**
     * Max Timestamp is a 8-byte big-endian integer indicating the maximum timestamp of the records in this batch.
     * Unix timestamp
     */
    // INT64
    long maxTimestamp;

    /**
     * Producer ID is a 8-byte big-endian integer indicating the ID of the producer that produced the records in this batch.
     */
    // INT64
    long producerId;

    /**
     * Producer Epoch is a 2-byte big-endian integer indicating the epoch of the producer that produced the records in this batch.
     */
    // INT16
    short producerEpoch;

    /**
     * Base Sequence is a 4-byte big-endian integer indicating the sequence number of the first record in a batch.
     * It is used to ensure the correct ordering and deduplication of messages produced by a Kafka producer.
     */
    // INT32
    int baseSequence;

    /**
     * Records Length is a 4-byte big-endian integer indicating the number of records in this batch.
     */
    // INT32
    int recordsLength;


    List<Record> recordList;

    public static DecodedResponse<RecordBatch> decode(byte[] data, long offset) throws Exception {
        long offsetAtFunctionStart=offset;

        /**
         * Reading record batch
         */
        long baseOffset=Util.readINT64FromBytes(data,(int)offset);
        offset+=8;

        int batchLength=Util.readINT32FromBytes(data,(int)offset);
        offset+=4;

        int partitionLeaderEpoch=Util.readINT32FromBytes(data,(int)offset);
        offset+=4;

        byte magicByte=data[(int)offset];
        offset+=1;

        int crc=Util.readINT32FromBytes(data,(int)offset);
        offset+=4;

        short attributes=Util.readINT16FromBytes(data,(int)offset);
        offset+=2;


        int lastOffsetDelta=Util.readINT32FromBytes(data,(int) offset);
        offset+=4;

        long baseTimestamp=Util.readINT64FromBytes(data,(int) offset);
        offset+=8;

        long maxTimestamp=Util.readINT64FromBytes(data,(int) offset);
        offset+=8;

        long producerId=Util.readINT64FromBytes(data,(int) offset);
        offset+=8;

        short producerEpoch=Util.readINT16FromBytes(data,(int) offset);
        offset+=2;

        int baseSequence=Util.readINT32FromBytes(data,(int) offset);
        offset+=4;

        int recordsLength=Util.readINT32FromBytes(data,(int) offset);
        offset+=4;

        List<Record> records=new ArrayList<>();

        for(int i=0;i<recordsLength;i++){
            /**
             * Read all the record in the batch
             */
            DecodedResponse<Record> recordDecodedResponse=Record.decode(data,offset);
            offset+=recordDecodedResponse.getBytesRead();
            records.add(recordDecodedResponse.getContent());
        }

        long totalBytesRead=offset-offsetAtFunctionStart;

        return new DecodedResponse<>(
                new RecordBatch(baseOffset,batchLength,partitionLeaderEpoch,magicByte,crc,attributes,lastOffsetDelta,baseTimestamp
                        ,maxTimestamp,producerId,producerEpoch,baseSequence,recordsLength,records),
                totalBytesRead);
    }

}