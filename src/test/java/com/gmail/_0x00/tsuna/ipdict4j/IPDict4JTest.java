package com.gmail._0x00.tsuna.ipdict4j;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.gmail._0x00.tsuna.ipdict4j.IPDict4J.Node;
/**
 * Unit test for IPDict4J.
 */
public class IPDict4JTest
{
    @Test
    public void testconvertIPStringToBinary() {
        IPDict4J<String> i4j = new IPDict4J<String>();
        assertEquals(0, i4j.convertIPStringToBinary("0.0.0.0"));
        assertEquals(-1, i4j.convertIPStringToBinary("255.255.255.255"));
        assertEquals(-256, i4j.convertIPStringToBinary("255.255.255.0"));
        assertEquals(-65536, i4j.convertIPStringToBinary("255.255.0.0"));
        assertEquals(-16777216, i4j.convertIPStringToBinary("255.0.0.0"));
        assertEquals(-1062731520, i4j.convertIPStringToBinary("192.168.1.0"));
        assertEquals(-1062731519, i4j.convertIPStringToBinary("192.168.1.1"));
        assertEquals(-1408237568, i4j.convertIPStringToBinary("172.16.0.0"));
        assertEquals(167772160, i4j.convertIPStringToBinary("10.0.0.0"));
    }

    @Test
    public void testHasGlueNodeOnly() {
        IPDict4J<String> dict = new IPDict4J<>();

        // Test it should return false if only one data node is existed.
        Node<String> n = new Node<>();
        Map<Integer, Node<String>> m = new HashMap<>();

        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>("Data of 192.168.1.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertFalse(dict.hasGlueNodeOnly(n));

        // Test it should return false if any node is not existed.
        n = new Node<>();
        m = new HashMap<>();
        n.setRefToChildren(m);
        assertFalse(dict.hasGlueNodeOnly(n));

        // Test it should return true if only glue node is existed.
        n = new Node<>();
        m = new HashMap<>();
        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertTrue(dict.hasGlueNodeOnly(n));

        // Test it should return false if some data nodes existed but glue node isn't.
        n = new Node<>();
        m = new HashMap<>();
        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>("Data of 192.168.1.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>("Data of 192.168.2.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertFalse(dict.hasGlueNodeOnly(n));

        // Test it should return true if some glue nodes existed but data node isn't.
        n = new Node<>();
        m = new HashMap<>();
        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertTrue(dict.hasGlueNodeOnly(n));

        // Test it should return false if data node is existed in some glue nodes.
        n = new Node<>();
        m = new HashMap<>();
        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>("Data of 192.168.1.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.3.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertFalse(dict.hasGlueNodeOnly(n));

        n = new Node<>();
        m = new HashMap<>();
        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>("Data of 192.168.2.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.3.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertFalse(dict.hasGlueNodeOnly(n));

        n = new Node<>();
        m = new HashMap<>();
        m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>(null, 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        m.put(dict.convertIPStringToBinary("192.168.3.0"), new Node<>("Data of 192.168.3.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
        n.setRefToChildren(m);
        assertFalse(dict.hasGlueNodeOnly(n));
    }

    @Test
    public void getBinIPv4NetAddr() {
        IPDict4J<String> dict = new IPDict4J<>();
        assertEquals(dict.convertIPStringToBinary("255.255.255.255"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 32));
        assertEquals(dict.convertIPStringToBinary("255.255.255.254"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 31));
        assertEquals(dict.convertIPStringToBinary("255.255.255.128"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 25));
        assertEquals(dict.convertIPStringToBinary("255.255.255.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 24));
        assertEquals(dict.convertIPStringToBinary("255.255.0.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 16));
        assertEquals(dict.convertIPStringToBinary("255.0.0.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 8));
        assertEquals(dict.convertIPStringToBinary("0.0.0.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("255.255.255.255"), 0));
        assertEquals(dict.convertIPStringToBinary("192.168.1.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("192.168.1.128"), 24));
        assertEquals(dict.convertIPStringToBinary("192.168.1.128"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("192.168.1.128"), 25));
        assertEquals(dict.convertIPStringToBinary("192.168.0.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("192.168.1.128"), 16));
        assertEquals(dict.convertIPStringToBinary("192.0.0.0"), dict.getBinIPv4NetAddr(dict.convertIPStringToBinary("192.168.1.128"), 8));
    }

    @Test
    public void createGlueNodes() throws NoSuchFieldException, IllegalAccessException {
        IPDict4J<String> dict = new IPDict4J<>();
        Node<String> rootNode = dict.getRootNode();
        rootNode.setChildSubnetMaskLength(24);
    }

    @Test
    public void push() {
        // getVariableOfInstance();
        return;
    }
}
