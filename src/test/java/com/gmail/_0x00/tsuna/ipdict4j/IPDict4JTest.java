package com.gmail._0x00.tsuna.ipdict4j;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.gmail._0x00.tsuna.ipdict4j.IPDict4J.Node;
import static org.junit.Assert.*;

/**
 * Unit test for IPDict4J.
 */
public class IPDict4JTest
{
    private IPDict4J<String> dict;

    public <E> void assertTheNode(Node<E> node, E data, int subnetMaskLength, int childSubnetMaskLength, String[] indexesOfChildNodes) {
        if(indexesOfChildNodes == null) indexesOfChildNodes = new String[0];
        assertEquals(data, node.getData());
        assertEquals(subnetMaskLength, node.getSubnetMaskLength());
        assertEquals(childSubnetMaskLength, node.getChildSubnetMaskLength());

        assertEquals(node.getRefToChildren().size(), indexesOfChildNodes.length);
        for(String ip : indexesOfChildNodes) {
            assertNotNull(node.getRefToChildren().get(dict.convertIPStringToBinary(ip)));
        }
    }

    @Before
    public void beforeEach() {
        dict = new IPDict4J<>();
    }

    @Test
    public void testconvertIPStringToBinary() {
        assertEquals(0, dict.convertIPStringToBinary("0.0.0.0"));
        assertEquals(-1, dict.convertIPStringToBinary("255.255.255.255"));
        assertEquals(-256, dict.convertIPStringToBinary("255.255.255.0"));
        assertEquals(-65536, dict.convertIPStringToBinary("255.255.0.0"));
        assertEquals(-16777216, dict.convertIPStringToBinary("255.0.0.0"));
        assertEquals(-1062731520, dict.convertIPStringToBinary("192.168.1.0"));
        assertEquals(-1062731519, dict.convertIPStringToBinary("192.168.1.1"));
        assertEquals(-1408237568, dict.convertIPStringToBinary("172.16.0.0"));
        assertEquals(167772160, dict.convertIPStringToBinary("10.0.0.0"));
    }

    @Test
    public void testHasGlueNodeOnly() {
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
        Node<String> rootNode = dict.getRootNode();
        rootNode.setChildSubnetMaskLength(24);
    }

    @Test
    public void push() throws Exception {
        // It should be empty node when no data has been pushed
        /*  (Structure of tree)
            +-------------------------+
            | 0.0.0.0/0(g)            |
            +-------------------------+
        */
        Node<String> rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, null, 0, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        // It should be able to push a root node 0.0.0.0/0
        /*
            +-------------------------+
            | 0.0.0.0/0(d)            |
            +-------------------------+
        */
        dict = new IPDict4J<>();
        dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
        rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, "Data of 0.0.0.0/0", 0, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        // It should be able to push a single node 128.0.0.0/1
        /*
            +-------------------------+
            | 0.0.0.0/0(g)            |
            +-+-----------------------+
              |
            +-+-----------------------+
            | 1)128.0.0.0/1(d)        |
            +-------------------------+
        */
        dict = new IPDict4J<>();
        dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
        dict.push("128.0.0.0", 1, "Data of 128.0.0.0/1");
        rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, "Data of 0.0.0.0/0", 0, 1, new String[]{"128.0.0.0"});
        Node<String> node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("128.0.0.0"));
        assertTheNode(node, "Data of 128.0.0.0/1", 1, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        /*
            +-------------------------+
            | 0.0.0.0/0(g)            |
            +-+-----------------------+
              |
              +---------------------------+
              |                           |
            +-+-----------------------+ +-+-----------------------+
            | 1)128.0.0.0/1(d)        | | 2)0.0.0.0/1(d)          |
            +-------------------------+ +-------------------------+
        */
        dict = new IPDict4J<>();
        dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
        dict.push("128.0.0.0", 1, "Data of 128.0.0.0/1");
        dict.push("0.0.0.0", 1, "Data of 0.0.0.0/1");
        rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, "Data of 0.0.0.0/0", 0, 1, new String[]{"128.0.0.0", "0.0.0.0"});
        node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("128.0.0.0"));
        assertTheNode(node, "Data of 128.0.0.0/1", 1, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
        assertTheNode(node, "Data of 0.0.0.0/1", 1, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        // It should be able to push a node 255.255.255.255/32
        /*
            +-------------------------+
            | 0.0.0.0/0(g)            |
            +-+-----------------------+
              |
            +-+-----------------------+
            | 1)255.255.255.255/32(d) |
            +-------------------------+
        */
        dict = new IPDict4J<>();
        dict.push("255.255.255.255", 32, "Data of 255.255.255.255/32");
        rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, null, 0, 32, new String[]{"255.255.255.255"});
        node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("255.255.255.255"));
        assertTheNode(node, "Data of 255.255.255.255/32", 32, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        // It should be able to push nodes 255.255.255.255/32, 255.255.255.254/32
        /*
            +-------------------------+
            | 0.0.0.0/0(g)            |
            +-+-----------------------+
              |
              +---------------------------+
              |                           |
            +-+-----------------------+ +-+-----------------------+
            | 1)255.255.255.255/32(d) | | 2)255.255.255.254/32(d) |
            +-------------------------+ +-------------------------+
        */
        dict = new IPDict4J<>();
        dict.push("255.255.255.255", 32, "Data of 255.255.255.255/32");
        dict.push("255.255.255.254", 32, "Data of 255.255.255.254/32");
        rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, null, 0, 32, new String[]{"255.255.255.255", "255.255.255.254"});
        node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("255.255.255.255"));
        assertTheNode(node, "Data of 255.255.255.255/32", 32, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("255.255.255.254"));
        assertTheNode(node, "Data of 255.255.255.254/32", 32, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        /*
            +-------------------------+
            | 0.0.0.0/0(g)            |
            +-+-----------------------+
              |
            +-+-----------------------+
            | 1)192.168.1.0/24(d)     |
            +-------------------------+
        */
        dict = new IPDict4J<>();
        dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
        rootNode = (Node<String>)TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
        assertTheNode(rootNode, null, 0, 24, new String[]{"192.168.1.0"});
        node = rootNode.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
        assertTheNode(node, "Data of 192.168.1.0/24", 24, IPDict4J.SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

        // TODO:
    }
}
