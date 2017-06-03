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
     * 答复工人请假请求
     */
    void replyWorker(String message);
}

class Worker implements Callback {

    private Boss boss;

    public Worker(Boss boss){
        this.boss = boss;
    }

    public void leave(){

        System.out.println("请求批准请假两天");

        //同步回调
        //boss.reply(this);  //工人注册监听方法
        //System.out.println("同步阻塞等待老板的答复后,才能做别的事情");

        //异步回调
        new Thread(new Runnable() {
            @Override
            public void run() {
                boss.reply(Worker.this);
            }
        }).start();

        System.out.println("异步非阻塞,做别的事情并等待老板的回复");
    }

    @Override
    public void replyWorker(String message){
        System.out.println("老板回复: " + message);
    }

}

class Boss {

    public void reply(Callback callback){

        try {
            //老板思考2s 后回复
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.replyWorker("同意请假");

    }
}