package com.lzhenxing.javascaffold.javabase.grammar.serialize;

import com.google.gson.Gson;

/**
 * ClassName: SerializeC <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2018/9/23
 */
public class SerializeC {

    private SerializeA serializeA;

    private SerializeB serializeB;

    public SerializeA getSerializeA() {
        return serializeA;
    }

    public void setSerializeA(SerializeA serializeA) {
        this.serializeA = serializeA;
    }

    public SerializeB getSerializeB() {
        return serializeB;
    }

    public void setSerializeB(SerializeB serializeB) {
        this.serializeB = serializeB;
    }

    public static void main(String[] args) {
        SerializeC serializeC = new SerializeC();
        SerializeA serializeA = new SerializeA();
        SerializeB serializeB = new SerializeB();

        serializeA.setSerializeB(serializeB);

        serializeC.setSerializeA(serializeA);
        serializeC.setSerializeB(serializeB);

        System.out.println(new Gson().toJson(serializeC));

    }
}
