package com.czy;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by Administrator on 2019/6/28 0028.
 */
public class XStreamTest {
    public static void main(String[] args) {
        XStream stream = new XStream(new DomDriver());

        stream.fromXML("");
        stream.toXML(null);
    }
}
