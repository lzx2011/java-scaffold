package interview;

/**
 * ClassName: Database <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class Mysql {
/**
 1.事务的ACID，其中把事务的隔离性详细解释一遍
 事务是由一组SQL语句组成的逻辑处理单元，事务具有以下4个属性，通常简称为事务的ACID属性。ACID是Atomic（原子性）
 Consistency（一致性）Isolation（隔离性）Durability（持久性）
 Atomic（原子性）：指整个数据库事务是不可分割的工作单位。只有使据库中所有的操作执行成功，才算整个事务成功；事务中任何一个SQL语句执行失败，那么已经执行成功的SQL语句也必须撤销，数据库状态应该退回到执行事务前的状态。
 Consistency（一致性）：指数据库事务不能破坏关系数据的完整性以及业务逻辑上的一致性。例如对银行转帐事务，不管事务成功还是失败，应该保证事务结束后ACCOUNTS表中Tom和Jack的存款总额为2000元。
 Isolation（隔离性）：指的是在并发环境中，当不同的事务同时操纵相同的数据时，每个事务都有各自的完整数据空间。
 Durability（持久性）：指的是只要事务成功结束，它对数据库所做的更新就必须永久保存下来。即使发生系统崩溃，重新启动数据库系统后，数据库还能恢复到事务成功结束时的状态。

 2.脏读、幻影读、不可重复读
    脏读：一个事务读取到另一事务未提交的更新新据。当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有
 提交到数据库中，这时，另外一个事务也访问这个数据，然后使用了这个数据。因为这个数据是还没有提交的数据， 那么另
 外一个事务读到的这个数据是脏数据，依据脏数据所做的操作也可能是不正确的。
    不可重复读：在同一事务中，多次读取同一数据返回的结果有所不同。换句话说就是，后续读取可以读到另一事务已提交的
 更新数据。相反，“可重复读”在同一事务中多次读取数据时，能够保证所读数据一样，也就是，后续读取不能读到另一事务
 已提交的更新数据。
    幻读：事务T1执行一次查询，然后事务T2新插入一行记录，这行记录恰好可以满足T1所使用的查询的条件。然后T1又使用相同
 的查询再次对表进行检索，但是此时却看到了事务T2刚才插入的新行。这个新行就称为“幻像”，因为对T1来说这一行就像突然
 出现的一样。
 3.MySQL行锁是否会有死锁的情况？
    会，举例子
 4.mysql的索引,使用B+树索引的好处,相比起其他数据结构的优势
    局部性原理与磁盘预读

 局部性原理：当一个数据被用到时，其附近的数据也通常会马上被使用
 程序运行期间所需要的数据通常比较集中。

 由于磁盘的存取速度与内存之间鸿沟,为了提高效率,要尽量减少磁盘I/O.磁盘往往不是严格按需读取，而是每次都会预读,磁盘读取完需要的数据,会顺序向后读一定长度的数据放入内存。

 由于磁盘顺序读取的效率很高(不需要寻道时间，只需很少的旋转时间)，因此对于具有局部性的程序来说，预读可以提高I/O效率.预读的长度一般为页(page)的整倍数。

 1. B+树更适合外部存储,由于内节点无 data 域,一个结点可以存储更多的内结点,每个节点能索引的范围更大更精确,也意味着 B+树单次磁盘IO的信息量大于B-树,I/O效率更高。
 2. Mysql是一种关系型数据库，区间访问是常见的一种情况，B+树叶节点增加的链指针,加强了区间访问性，可使用在范围区间查询等，而B-树每个节点 key 和 data 在一起，则无法区间查找。


 5.mysql性能查看以及如何优化
    https://www.cnblogs.com/jpfss/p/9155199.html

 6.MySQL的主从复制怎么做的，答日志，具体原理是什么，有什么优缺点（每个日志的作用）。
    MySQL——主从复制
 7.然后问了一个近期的项目，问了mycat，怎么分库分表，你们数据库怎么设计的，为什么这么设计，分表的依据，怎么优化（tddl的原理了）













 */
}