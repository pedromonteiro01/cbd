CREATE TABLE IF NOT EXISTS cbd_ex4.books (
    author_id int,
    title text,
    num_pages int,
    book_id int,
    date timestamp,
    categories list<text>,
    PRIMARY KEY (author_id, title, num_pages)
) WITH ID = da6dc830-6192-11ec-b612-f7cd8f5d4dc9
    AND CLUSTERING ORDER BY (title ASC, num_pages ASC)
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
