CREATE TABLE IF NOT EXISTS cbd_ex4.books (
    book_id int,
    num_pages int,
    author_id int,
    date timestamp,
    title text,
    categories list<text>,
    PRIMARY KEY (book_id, num_pages)
) WITH ID = 4d9a1380-6194-11ec-b612-f7cd8f5d4dc9
    AND CLUSTERING ORDER BY (num_pages DESC)
    AND additional_write_policy = '99p'
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND cdc = false
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '16', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND default_time_to_live = 0
    AND extensions = {}
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair = 'BLOCKING'
    AND speculative_retry = '99p';
CREATE INDEX IF NOT EXISTS num_pages_idx ON cbd_ex4.books (num_pages);
