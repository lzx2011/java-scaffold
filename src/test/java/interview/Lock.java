package interview;

/**
 * ClassName: Lock <br/>
 * Function: 可以写下锁的文章<br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class Lock {
/**
 1.synchronized 和 ReentranLock 的区别？
    https://blog.csdn.net/zxd8080666/article/details/83214089
 2.乐观锁和悲观锁了解吗？JDK中涉及到乐观锁和悲观锁的内容？
    所谓乐观锁就是，每次不加锁而是假设没有冲突而去完成某项操作，如果因为冲突失败就重试，直到成功为止。
    假定会发生并发冲突，屏蔽一切可能违反数据完整性的操作。
    java中的CAS是乐观锁，synchronized是悲观锁
 3.分布式锁的实现你知道的有哪些？具体详细谈一种实现方式
    zookeeper、redis
    具体实现：zookeeper
    1.建立一个节点，假如名为：lock 。节点类型为持久节点（PERSISTENT）
    2.每当进程需要访问共享资源时，会调用分布式锁的lock()或tryLock()方法获得锁，这个时候会在第一步创建的lock节点下建立相应的顺序子节点，节点类型为临时顺序节点（EPHEMERAL_SEQUENTIAL），称为thisPath. 当thisPath在所有子节点中最小时, 说明该进程获得了锁. 进程获得锁之后, 就可以访问共享资源了. 访问完成后, 需要将thisPath删除. 锁由新的最小的子节点获得.
    有了清晰的思路之后, 还需要补充一些细节. 进程如何知道thisPath是所有子节点中最小的呢? 可以在创建的时候, 通过getChildren方法获取子节点列表, 然后在列表中找到排名比thisPath前1位的节点, 称为waitPath, 然后在waitPath上注册监听, 当waitPath被删除后, 进程获得通知, 此时说明该进程获得了锁.
 4.什么时候多线程会发生死锁（死锁条件），写一个例子吧
    java 死锁产生的四个必要条件：
    1、互斥使用，即当资源被一个线程使用(占有)时，别的线程不能使用
    2、不可抢占，资源请求者不能强制从资源占有者手中夺取资源，资源只能由资源占有者主动释放。
    3、请求和保持，即当资源请求者在请求其他的资源的同时保持对原有资源的占有。
    4、循环等待，即存在一个等待队列：P1占有P2的资源，P2占有P3的资源，P3占有P1的资源。这样就形成了一个等待环路。
    当上述四个条件都成立的时候，便形成死锁。当然，死锁的情况下如果打破上述任何一个条件，便可让死锁消失。
    https://blog.csdn.net/revitalizing/article/details/73694941

 5.锁是如何升级的
    synchronized 锁升级原理：在锁对象的对象头里面有一个 threadid 字段，在第一次访问的时候 threadid 为空，jvm 让其持有偏向锁，并将 threadid 设置为其线程 id，再次进入的时候会先判断 threadid 是否与其线程 id 一致，如果一致则可以直接使用此对象，如果不一致，则升级偏向锁为轻量级锁，通过自旋循环一定次数来获取锁，执行一定次数之后，如果还没有正常获取到要使用的对象，此时就会把锁从轻量级升级为重量级锁，此过程就构成了 synchronized 锁的升级。
    锁的升级的目的：锁升级是为了减低了锁带来的性能消耗。在 Java 6 之后优化 synchronized 的实现方式，使用了偏向锁升级为轻量级锁再升级到重量级锁的方式，从而减低了锁带来的性能消耗。
    https://blog.csdn.net/syilt/article/details/90576464
    所谓锁的升级、降级，就是 JVM 优化 synchronized 运行的机制，当 JVM 检测到不同的竞争状况时，会自动切换到适合的锁实现，这种切换就是锁的升级、降级。
    https://cloud.tencent.com/developer/article/1414969
 6.锁的类型
    提供了三种不同的 Monitor 实现，也就是常说的三种不同的锁：偏斜锁（Biased Locking）、轻量级锁和重量级锁，大大进了其性能。







 */
}
