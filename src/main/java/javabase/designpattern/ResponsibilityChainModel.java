package javabase.designpattern;

/**
 * 责任链模式
 * Created by gary on 2017/3/11.
 */
public class ResponsibilityChainModel {

    public static void main(String[] args){

        LeaveRequest leaveRequest = new LeaveRequest("小刘", 12, "旅游");

        CommonManager commonManager = new CommonManager("张三");
        Director director = new Director("李四");
        GeneralManager generalManager = new GeneralManager("王五");

        //指定上级关系
        commonManager.setSuperior(director);
        director.setSuperior(generalManager);

        commonManager.handleRequest(leaveRequest);
    }
}

/**
 * 请假请求类
 */
class LeaveRequest{

    private String name;
    private int leaveDays;
    private String reason;

    public LeaveRequest(String name, int leaveDays, String reason){
        this.name = name;
        this.leaveDays = leaveDays;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

/**
 * 管理者抽象类
 */
abstract class Manager{

    protected String name;
    protected Manager superior;

    public Manager(String name){
        this.name = name;

    }

    /**
     * 处理请假抽象方法
     */
    public abstract void handleRequest(LeaveRequest leaveRequest);

    public void setSuperior(Manager superior){
        this.superior = superior;
    }
}

/**
 * 经理类,能处理不超过3天的假期
 */
class CommonManager extends Manager {

    public CommonManager(String name){
        super(name);
    }

    public void handleRequest(LeaveRequest leaveRequest){

        int leaveDays = leaveRequest.getLeaveDays();
        if(leaveDays <= 3){
            System.out.println("经理" + name + "批准");
        }else{
            System.out.println("请假超过3天,经理无法处理,交由上级审批");
            if(superior != null){
                superior.handleRequest(leaveRequest);
            }
        }
    }

}

/**
 * 总监类,能处理超过三天但不超过十天的假期
 */
class Director extends Manager {

    public Director(String name){
        super(name);
    }

    public void handleRequest(LeaveRequest leaveRequest){

        int leaveDays = leaveRequest.getLeaveDays();
        if(leaveDays > 3 && leaveDays <= 10){
            System.out.println("总监批准");
        }else{
            System.out.println("请假超过10天,总监无法处理,交由上级审批");
            if(superior != null){
                superior.handleRequest(leaveRequest);
            }
        }
    }

}

/**
 * 总经理类,能处理超过10天以上的假期
 */
class GeneralManager extends Manager {

    public GeneralManager(String name){
        super(name);
    }

    public void handleRequest(LeaveRequest leaveRequest){

        int leaveDays = leaveRequest.getLeaveDays();
        if(leaveDays > 10){
            System.out.println("总经理批准");
        }else{
            if(superior != null){
                superior.handleRequest(leaveRequest);
            }
        }
    }

}

