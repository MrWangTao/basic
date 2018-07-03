# redis
## 初识 redis
+ 何时使用redis？

  如果程序对性能的要求不高，又或者因为费用原因而没有办法将大量数据存储到内存里面，
  那么用户可以选择使用关系型数据库或者其他非关系型数据库；
  在实际中，应该根据自己的需求来决定是否使用Redis，并考虑是将Redis用作主存储还是
  辅助存储，以及如何通过复制、持久化和事务等手段保证数据的完整性
  
+ 一些数据库和缓存服务器的特性与功能
  
  名称|类型|数据存储选项|查询类型|附加功能
  ----|----|----|----|----
  Redis|使用内存存储（in-memory）的非关系数据库|`字符串`、`列表`、`集合`、`散列表`、`有序集合`|每种数据类型都有自己的专属命令，另外还有批量操作（bulk operation）和不完全（partial）的事务支持|发布与订阅，主从复制（master/slave replication），持久化，脚本（stored procedure）
  memcached|使用内存存储的键值缓存|键值之间的映射|创建命令、读取命令、更新命令、删除命令以及其他几个命令|为提升性能而设的多线程服务器
  MySql|关系数据库|每个数据库可包含多个表，每个表可以包含多个行；可以处理多个标的视图（view）；支持空间（spatial）和第三方扩展|SELECT、INSERT、UPDATE、DELETE、函数、存储过程|支持ACID性质（需要使用InnoDB），主从复制和主主复制（master/master replication）
  PostgreSQL|关系数据库|每个数据库可以包含多个表，每个表可以包含多个行；可以处理多个表的视图；支持空间和第三方扩展；支持可定制类型|SELECT、INSERT、UPDATE、DELETE、内置函数、自定义的存储过程|支持ACID性质，主从复制，由第三方支持的多主复制（multi-master replication）
  MongoDB|使用硬盘存储（on-disk）的非关系文档存储|么个数据库可以包含多个表，每个表可以包含多个无schema（schema-kess）的BSON文档|创建命令、读取命令、更新命令、删除命令、条件查询命令等|支持map-reduce操作，主从复制，分片，空间搜索（spatial index）
  
+ 当服务器被关闭时，服务器存储的数据将何去何从？
  
  > Redis拥有两种不同形式的持久化方法，他们都可以用小而紧凑的格式将存储在内存中的数据写入硬盘
  + 时间点转储（point-in-time dump），转储操作既可以在"指定时间段内有指定数量的写操作执行"这一条件被满足时执行，
  又可以通过调用两条转储到硬盘（dump-to-disk）命令中任何一条来执行
  + 持久化方法将所有修改了数据库的命令都写入一个值追加（append-only）文件里面，用户可以根据数据的重要程度，将只追加
  写入设置为从不同步（sync）、每秒同步一次或者每写入一次命令就同步一次。
  
  
## redis 简单操作
+ String
 
  命令|行为|案例
  ----|----|----
  GET|获取存储在给定键中的值|GET <key>
  SET|设置存储在给定键中的值|SET <key> <value>
  DEL|删除存储在给定键中的值`这个命令可以用于所有类型`|DEL <key>
  INCR|自增|INCR <key>
  DECR|自减|DECR <key>
  
+ list (linked-list)
  
  命令|行为|案例
  ----|----|----
  RPUSH/LPUSH|从右或左向列表中存放数据|RPUSH/LPUSH <key> <value1> <value2> <...>
  LRANGE|获取列表在给定范围上的所有值|LRANGE <key> 0 2, LRANGE <key> 0 -1 `-1：表示结束位置`
  LINDEX|获取列表在给定位置上的值|LINDEX <key> 2
  LPOP/RPOP|从列表的左端或者右端弹出一个值，并返回被弹出的值
  
+ set

  命令|行为|案例
  ----|----|----
  SADD|将给定元素添加到集合|SADD <key> <value1> <value2> <...>
  SMEMBERS|返回集合包含的所有元素|SMEMBERS <key>
  SISMEMBER|检查给定元素是否存在在集合中|SISMEMBERS <key> <value>
  SREM|如果给定的元素存在于集合中，那么移除这个元素|SREM <key> <value>
  SINTER|在给定的set中，跟主set的交集|SINTER <key> <set1> <set2> <...>
  SUNION|在给定的set中, 跟主set的并集|SUNION <key> <set1> <set2> <...>
  SDIFF|在给定的set中，跟主set的差集，也就是说主set中的数据没有在别的set中存在的集合|SDIFF <key> <set1> <set2> <...>
  
+ hash

  命令|行为|案例
  ----|----|----
  HSET|在散列里面关联起给定的键值对|HSET <hash-key> <key> <value>
  HGET|获取指定散列键的值|HGET <hash-key> <key>
  HGETALL|获取散列包含的所有键值对|HGETALL <hash-key>
  HDEL|如果给定的键存在在散列里面，那么移除这个键|HDEL <hash-key> <key>
  
+ zset
    
  命令|行为|案例
  ----|----|----
  ZADD|将一个带有给定分值的成员添加到有序集合里面|ZADD <key> <score> <value>
  ZRANGE|根据元素在有序排列中所处的位置，从有序集合里面获取多个元素|ZRANGE <key> 0 -1 withscores
  ZRANGEBYSCORE|获取有序集合在给定分值范围内的所有元素|ZRANGEBYSCORE <key> 0 800 withscores `在0-800分之间查询`
  ZREM|如果给定成员存在于有序集合，那么移除这个成员|ZREM <key> <value>