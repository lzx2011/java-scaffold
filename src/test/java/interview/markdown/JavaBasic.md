# Java基础
### 1.平常用到哪些集合类？ArrayList 和 LinkedList 区别？
  1. collection: ArrayList,LinkedLsit,Vector,Stack，TreeSet,HashSet,LinkedHashSet
  2. Map: HashMap,LinkedHashMap,WeakHashMap, TreeMap, HashTable, IdentityHashTable(其中key的比较是通过==而不是equals)
 current包中的集合copyonwrite之类
 ArrayList: 线程不安全，底层结构Object数组，插入和删除效率低，支持随机访问
 LinkedList:线程不安全，底层结构双向链表，插入和删除效率高，不支持随机访问

### 2.HashMap内部数据结构？jdk1.8中，对hashMap和concurrentHashMap做了哪些优化
    如何解决hash冲突的，以及如果冲突了，怎么在hash表中找到目标值?
 HashMap内部是通过一个数组实现的，只是这个数组比较特殊，数组里存储的元素是一个Entry实体(jdk 8为Node)，这个Entry实体主要包含key、value以及一个指向自身的next指针。
 HashMap 由散列表和链表组成，将存储对象hash后存储到散列表中，hash值相同的对象则添加到桶对应的链表中。put操作前会先检查存储容量是否已满（总容量*装作因子0.75），如果满了会resize两倍大小。不是线程安全的，多线程下put可能覆盖相同hash值的对象，多线程resize会形成环，导致死循环（cpu极高）。jdk8不再使用链表，而是使用红黑树解决碰撞。

###  3.HashMap 初始化操作时候的方案，ConcurrentHashMap分段锁？

###  4.有1000个数据存在 HashMap 中，实际的数量是多少，考虑负载因子和扩容
    默认初始化是16，加入没有冲突，每次resize 2倍，负载因子是0.75；
 扩容n次，1000<= 16*0.75*2^n-1   n>=8, 所以总共是16*2^8=4096

###  5.并发容器，使用场景
    ConcurrentHashMap分布式网格任务时，多线程分发任务
## 1、面向对象的特征有哪些方面？ 
    抽象，继承，封装，多态。多态性是指允许不同子类型的对象对同一消息作出不同的响应。
### 2、访问修饰符public,private,protected,以及不写（默认）时的区别？ 

### 3、String 是最基本的数据类型吗，基本类型有哪些？ 
	不是。Java中的基本数据类型只有8个：byte（1）、short（2）、int（4字节）、long（8）、float（4）、double、char（2）、boolean（1）；除了基本类型（primitive type），剩下的都是引用类型（reference type），Java 5以后引入的枚举类型也算是一种比较特殊的引用类型。
### 1、hashmap的基本原理，内部数据结构，put操作的整体流程，是否线程安全以及为什么?jdk8对hashmap做了哪些优化？ 
	HashMap内部是通过一个数组实现的，只是这个数组比较特殊，数组里存储的元素是一个Entry实体(jdk 8为Node)，这个Entry实体主要包含key、value以及一个指向自身的next指针。
    hashmap由散列表和链表组成，将存储对象hash后存储到散列表中，hash值相同的对象则添加到桶对应的链表中。put操作前会先检查存储容量是否已满（总容量*装作因子0.75），如果满了会resize两倍大小。不是线程安全的，多线程下put可能覆盖相同hash值的对象，多线程resize会形成环，导致死循环（cpu极高）。jdk8不再使用链表，而是使用红黑树解决碰撞。
### 2、String类为什么是不可变的？
    主要出于安全和性能的考虑。安全：String是几乎每个类都会使用的类，特别是作为HashMap之类的集合的key值时候，mutable的String有非常大的风险。而且一旦发生，非常难发现。因为字符串是不可变的，所以是多线程安全的，同一个字符串实例可以被多个线程共享。
    性能：只有当字符串是不可变的，字符串池才有可能实现。字符串池的实现可以在运行时节约很多heap空间，因为不同的字符串变量都指向池中的同一个字符串。
### 3、StringBuilder和StringBuffer的区别？
    Stringbuilder 继承 AbstractStringBuilder ，他的成员变量 char[] value; 而且提供了对外改变的方法；Stringbuffer也继承AbstractStringBuilder，但 Stringbuffer 提供的对外方法又封装了下，方法都用 synchronized 修饰，所以是线程安全的。
### 4、字符串常量池在哪？加号的底层原理？ 
    字符串常量池在堆区（方法区），javap -c classname 反编译class文件，加号已被java编译器优化为Stringbuilder的append操作。
### 5、反射、accessible，动态代理的原理，jdk动态代理与cglib的区别与各自的实现原理？

### 6、自动装箱和拆箱，赋值操作，在内存里面是如何实现的？ 
    自动装箱时编译器调用valueOf将原始类型值转换成对象，同时自动拆箱时，编译器通过调用类似intValue(),doubleValue()这类的方法将对象转换成原始类型值。
### 7、接口和抽象类的区别？
    接口中没有方法的实现（jdk8有个default默认接口实现），抽象类中可以有抽象方法，也可以有具体的实现，也可以没有抽象方法；子类只能继承一个抽象类，但可以实现多个接口；抽象类可以有构造器，接口不行。接口里定义的变量只能是公共的静态的常量，抽象类中的变量是普通变量。
### 8、concurrenthashmap的原理，内部数据结构，如何提高并发性，jdk8中做了哪些优化？	
	jdk 1.6版: ConcurrenHashMap可以说是HashMap的升级版，ConcurrentHashMap是线程安全的，但是与Hashtablea相比，实现线程安全的方式不同。Hashtable是通过对hash表结构进行锁定，是阻塞式的，当一个线程占有这个锁时，其他线程必须阻塞等待其释放锁。ConcurrentHashMap是采用分离锁的方式，它并没有对整个hash表进行锁定，而是局部锁定，也就是说当一个线程占有这个局部锁时，不影响其他线程对hash表其他地方的访问。
具体实现:ConcurrentHashMap内部有一个Segment<K,V>数组,该Segment对象可以充当锁。Segment对象内部有一个HashEntry<K,V>数组，于是每个Segment可以守护若干个桶(HashEntry),每个桶又有可能是一个HashEntry连接起来的链表，存储发生碰撞的元素。
每个ConcurrentHashMap在默认并发级下会创建包含16个Segment对象的数组，每个数组有若干个桶，当我们进行put方法时，通过hash方法对key进行计算，得到hash值，找到对应的segment，然后对该segment进行加锁，然后调用segment的put方法进行存储操作，此时其他线程就不能访问当前的segment，但可以访问其他的segment对象，不会发生阻塞等待。
jdk 1.8版 在jdk 8中，ConcurrentHashMap不再使用Segment分离锁，而是采用一种乐观锁CAS算法来实现同步问题，但其底层还是“数组+链表->红黑树”的实现。
    currentHashMap和hashmap底层结构类似，但currentHashMap是线程安全的，因为它使用segment（继承可重入锁）分段锁，分段存储数据，每段数据都有一个分段锁，提高了并发性。jdk8不在使用拉链法，使用红黑树替代链表结构,使用乐观锁cas算法。
### 9、hashset的原理？
    对于HashSet而言，它是基于HashMap实现的，底层采用HashMap来保存元素，相关HashSet的操作，基本上都是直接调用底层HashMap的相关方法来完成。hashmap的key是hashset的实际值，value存的是个虚拟值。add（）的实现return map.put(e, PRESENT)==null;该方法如果添加的是在 HashSet 中不存在的，则返回 true；如果添加的元素已经存在，返回 false。其原因在于我们之前提到的关于 HashMap 的 put 方法。该方法在添加 key 不重复的键值对的时候，会返回 null。
### 10、synchronized，static修饰类和方法有什么区别？
    synchronized（ClassName.class）作用的对象是这个类的所有对象;修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；static修饰类一般作为其他类的静态内部类，修饰方法是静态方法，是类的所有对象所共有，可以直接用类名调用。
### 11、HashMap的rehash的过程，指针碰撞问题？
    rehash过程：rehash的判断条件实际上就是当前的数据量是否达到了当前容量乘以负载因子，如果达到了，只要再新加数据就会触发rehash。如果当前容量已经达到了最大用量 ，则不扩充，直接更新threshold的值为int的最大值MAX_VALUE = 0x7fffffff;（2的31次幂减一），否则就扩充容量为当前容量的两倍。。指针碰撞问题：jdk8以前：我们可以看出Entry是一个单向链表，这也是我们为什么说HashMap是通过拉链法解决hash冲突的。jdk8后：不再使用链表，改用了红黑树，提高了查找效率。
### 12、 Java IO的一些内容，包括NIO，BIO，AIO等？

### 14、Java创建对象有哪几种?
	大概有四种—new、工厂模式、反射和克隆。
###  15、JDK和JRE的区别是什么？
	JDK: java开发工具包,包含了JRE、编译器和其它工具（如：javaDOc、java调试器)
    JRE: java运行环境,包含java虚拟机和java程序所需的核心类库。
### 16、fail-fast与fail-safe有什么区别？
	Iterator的fail-fast属性与当前的集合共同起作用，因此它不会受到集合中任何改动的影响。Java.util包中的所有集合类都被设计为fail->fast的，而java.util.concurrent中的集合类都为fail-safe的。当检测到正在遍历的集合的结构被改变时，Fail-fast迭代器抛出ConcurrentModificationException，而fail-safe迭代器从不抛出ConcurrentModificationException。
### 17、LinkedHashMap的实现原理?
	LinkedHashMap也是基于HashMap实现的，不同的是它定义了一个Entry header，这个header不是放在Table里，它是额外独立出来的。LinkedHashMap通过继承hashMap中的Entry,并添加两个属性Entry before,after,和header结合起来组成一个双向链表，来实现按插入顺序或访问顺序排序。LinkedHashMap定义了排序模式accessOrder，该属性为boolean型变量，对于访问顺序，为true；对于插入顺序，则为false。一般情况下，不必指定排序模式，其迭代顺序即为默认为插入顺序。
### 18、强引用，弱引用，软引用和虚引用？
	ThreadLocalMap中的key存储的是ThreadLocal实例的弱引用，弱引用还常用在缓存中。当内存不足gc时，就会回收弱引用
### 19、Java中如何实现序列化，有什么意义？ 
	序列化就是一种用来处理对象流的机制，所谓对象流也就是将对象的内容进行流化。可以对流化后的对象进行读写操作，也可将流化后的对象传输于网络之间。序列化是为了解决对象流读写操作时可能引发的问题（如果不进行序列化可能会存在数据乱序的问题）。 
要实现序列化，需要让一个类实现Serializable接口，该接口是一个标识性接口，标注该类对象是可被序列化的，然后使用一个输出流来构造一个对象输出流并通过writeObject(Object)方法就可以将实现对象写出（即保存其状态）；如果需要反序列化则可以用一个输入流建立对象输入流，然后通过readObject方法从流中读取对象。序列化除了能够实现对象的持久化之外，还能够用于对象的深度克隆
### 20、Java中有几种类型的流？ 
	字节流和字符流。字节流继承于InputStream、OutputStream，字符流继承于Reader、Writer。在java.io 包中还有许多其他的流，主要是为了提高性能和使用方便。关于Java的I/O需要注意的有两点：一是两种对称性（输入和输出的对称性，字节和字符的对称性）；二是两种设计模式（适配器模式和装潢模式）。另外Java中的流不同于C#的是它只有一个维度一个方向。
### 21、Statement和PreparedStatement有什么区别？哪个性能更好？ 
	与Statement相比，①PreparedStatement接口代表预编译的语句，它主要的优势在于可以减少SQL的编译错误并增加SQL的安全性（减少SQL注射攻击的可能性）；②PreparedStatement中的SQL语句是可以带参数的，避免了用字符串连接拼接SQL语句的麻烦和不安全；③当批量处理SQL或频繁执行相同的查询时，PreparedStatement有明显的性能上的优势，由于数据库可以将编译优化后的SQL语句缓存起来，下次执行相同结构的语句时就会很快（不用再次编译和生成执行计划）。
### 22、JDBC中如何进行事务处理？ 
	Connection提供了事务处理的方法，通过调用setAutoCommit(false)可以设置手动提交事务；当事务完成后用commit()显式提交事务；如果在事务处理过程中发生异常则通过rollback()进行事务回滚。除此之外，从JDBC 3.0中还引入了Savepoint（保存点）的概念，允许通过代码设置保存点并让事务回滚到指定的保存点。 
### 23、Java中是如何支持正则表达式操作的？ 
	Java中的String类提供了支持正则表达式操作的方法，包括：matches()、replaceAll()、replaceFirst()、split()。此外，Java中可以用Pattern类表示正则表达式对象，它提供了丰富的API进行各种正则表达式操作，请参考下面面试题的代码。
### 24、获得一个类的类对象有哪些方式？ 
	- 方法1：类型.class，例如：String.class 
	- 方法2：对象.getClass()，例如："hello".getClass() 
	- 方法3：Class.forName()，例如：Class.forName("java.lang.String")
### 25、如何通过反射创建对象？ 
 	- 方法1：通过类对象调用newInstance()方法，例如：String.class.newInstance() 
	- 方法2：通过类对象的getConstructor()或getDeclaredConstructor()方法获得构造器（Constructor）对象并调用其newInstance()方法创建对象，例如：String.class.getConstructor(String.class).newInstance("Hello");
### 26、快速失败机制？
	Fail-Fast机制:在使用迭代器的过程中有其他线程修改了map，那么将抛出ConcurrentModificationException，这就是所谓fail-fast机制。这一机制在源码中的实现是通过modCount域，modCount顾名思义就是修改次数，对HashMap内容的修改都将增加这个值，那么在迭代器初始化过程中会将这个值赋给迭代器的expectedModCount。在迭代过程中，判断modCount跟expectedModCount是否相等，如果不相等就表示已经有其他线程修改了Map.
### 27、接口与回调；回调的原理；写一个回调demo；
### 28、泛型原理，举例说明；解析与分派；
### 简述happen-before规则 ；
### JUC和Object ； Monitor机制区别是什么 ； 简述AQS原理 ；
### 简述DCL失效原因，解决方法 ；
### 简述nio原理 ；
### 简述字节码文件组成 ；
### 如何实现一个ThreadLocal ；
### 说说你了解的一个线程安全队列 ；
### Atomic包的实现原理是什么 ；
### CAS又是怎么保证原子性的 ；
### 死锁的四个必要条件；
### 常见编码方式；utf-8编码中的中文占几个字节；int型几个字节；
### MVC MVP MVVM; 常见的设计模式；写出观察者模式的代码；
