package interview;

/**
 * ClassName: ArchitectureDesign <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class Architecture {
/**
 1.高并发的应用场景，技术需要涉及到哪些？怎样来架构设计？

 2.着高并发的问题，谈到了秒杀等的技术应用：kafka、redis、mycat等

 3.分布式了解哪些东西？

 4.cap了解么，分别指什么，base呢，强一致性和弱一致性有什么方法来做，2pc了解么，说一下大概过程。

 5.负载均衡怎么做的呢，为什么这么做，了解过集群雪崩么。

 6.这样一个题目，一个节点要和客户连接建立心跳检测，大概有百万数量的连接，并且会定期发送心跳包，要写一个update方法和check方法，update方法更新心跳状态，check删除超时节点，怎么做?
 刚开始做了个hash发现check要轮询太慢了，然后用计时器和开线程检测也不行，最后说了个LRU，他说OK的。




  */
}
