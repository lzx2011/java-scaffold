package interview;

/**
 * ClassName: JavaBasic <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class JavaBasic {
/**
 1.平常用到哪些集合类？ArrayList 和 LinkedList 区别？
    1）collection: ArrayList,LinkedLsit,Vector,Stack，TreeSet,HashSet,LinkedHashSet
    2) Map: HashMap,LinkedHashMap,WeakHashMap, TreeMap, HashTable, IdentityHashTable(其中key的比较是通过==而不是equals)
 current包中的集合copyonwrite之类
 ArrayList: 线程不安全，底层结构Object数组，插入和删除效率低，支持随机访问
 LinkedList:线程不安全，底层结构双向链表，插入和删除效率高，不支持随机访问

 2.HashMap内部数据结构？jdk1.8中，对hashMap和concurrentHashMap做了哪些优化
    如何解决hash冲突的，以及如果冲突了，怎么在hash表中找到目标值?
 HashMap内部是通过一个数组实现的，只是这个数组比较特殊，数组里存储的元素是一个Entry实体(jdk 8为Node)，这个Entry实体主要包含key、value以及一个指向自身的next指针。
 HashMap 由散列表和链表组成，将存储对象hash后存储到散列表中，hash值相同的对象则添加到桶对应的链表中。put操作前会先检查存储容量是否已满（总容量*装作因子0.75），如果满了会resize两倍大小。不是线程安全的，多线程下put可能覆盖相同hash值的对象，多线程resize会形成环，导致死循环（cpu极高）。jdk8不再使用链表，而是使用红黑树解决碰撞。

 3.HashMap 初始化操作时候的方案，ConcurrentHashMap分段锁？

 4.有1000个数据存在 HashMap 中，实际的数量是多少，考虑负载因子和扩容
    默认初始化是16，加入没有冲突，每次resize 2倍，负载因子是0.75；
 扩容n次，1000<= 16*0.75*2^n-1   n>=8, 所以总共是16*2^8=4096

 5.并发容器，使用场景
    ConcurrentHashMap分布式网格任务时，多线程分发任务

 3.Java GC机制？GC Roots有哪些？
    GC（Garbage Collection)当程序员创建对象时，GC就开始监控这个对象的地址、大小以及使用情况。通常，GC采用有向图的方式记录和管理堆（heap）中的所有对象。通过这种方式确定哪些对象是"可达的"，哪些对象是"不可达的".当GC确定一些对象为"不可达"时，GC就有责任回收这些内存空间。
 新生代（Eden：survivor1：survivor2=8：1：1），老年代（new：old=1：2），永久代
 可达性分析法：一个对象在没有任何强引用指向他或该对象通过根节点不可达时需要被垃圾回收器回收；
 引用计数法：引用计数法有一个缺陷就是无法解决循环引用问题。
    一个对象可以属于多个root，GC root有几下种：
 Class - 由系统类加载器(system class loader)加载的对象，这些类是不能够被回收的，他们可以以静态字段的方式保存持有其它对象。我们需要注意的一点就是，通过用户自定义的类加载器加载的类，除非相应的java.lang.Class实例以其它的某种（或多种）方式成为roots，否则它们并不是roots，.
 Thread - 活着的线程
 Stack Local - Java方法的local变量或参数
 JNI Local - JNI方法的local变量或参数
 JNI Global - 全局JNI引用
 Monitor Used - 用于同步的监控对象
 Held by JVM - 用于JVM特殊目的由GC保留的对象，但实际上这个与JVM的实现是有关的。可能已知的一些类型是：系统类加载器、一些JVM知道的重要的异常类、一些用于处理异常的预分配对象以及一些自定义的类加载器等。然而，JVM并没有为这些对象提供其它的信息，因此需要去确定哪些是属于"JVM持有"的了。
 https://www.cnblogs.com/w-wfy/p/6415768.html
 */

/**
 1. java8 和 9有什么新特性


 */
}
