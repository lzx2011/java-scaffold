package com.lzhenxing.javascaffold.javabase.designpattern;

/**
 * ClassName: CallbackPattern <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/31
 */
public class CallbackPattern {

    public static void main(String[] args){

        Boss boss = new Boss();
        Worker worker = new Worker(boss);
        worker.leave();
    }
}

interface Callback {
    /**
     * 等老板批准后答复老板
     */
    void replyBoss();
}

class Worker implements Callback {

    private Boss boss;

    public Worker(Boss boss){
        this.boss = boss;
    }

    public void leave(){
        System.out.println("请求批准请假两天");
        boss.reply(this);
    }


    public void replyBoss(){
        System.out.println("好的,谢谢老板");
    }

}

class Boss {

    public void reply(Callback callback){

        try {
            //老板思考3s 后回复
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("同意请假");
        callback.replyBoss();

    }
}