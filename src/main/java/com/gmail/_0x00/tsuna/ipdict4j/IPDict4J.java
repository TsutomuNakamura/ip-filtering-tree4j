package com.gmail._0x00.tsuna.ipdict4j;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class IPDict4J <T>
{
    private Node root;

    private final String IPV4_DELEMITOR = "\\.";

    private static final Pattern IPV4_REGEX
            = Pattern.compile("^(([01]?\\\\d\\\\d?|2[0-4]\\\\d|25[0-5])\\\\.){3}([01]?\\\\d\\\\d?|2[0-4]\\\\d|25[0-5])$");

    public static final int SUBNETMASK_LENGTH_IS_UNDEFINED = -1;

    public IPDict4J() {
        root = new Node<T>();
        root.setData(null);
        root.setSubnetMaskLength(-1);
        root.setChildSubnetMaskLength(0);
        Map<Integer, T> map = new HashMap<Integer, T>();
        root.setRefToChildren(map);
        root.addRefToChildren(0, null);
    }

    public void push(String ipaddr, int subnetMaskLength, T data) throws Exception {
        if(!IPV4_REGEX.matcher(ipaddr).matches())
            throw new Exception("String of IPv4 " + ipaddr + " is invalid");
        if(data == null)
            throw new Exception("TODO: Cannot push null as data");

    }

    /**
     * Convert String IPv4 format to binary of 32bit integer.
     * @param ip String of IPv4 address
     * @return IPv4 address converted 32bit int.
     */
    public int convertIPStringToBinary(String ip) {
        int binIPv4 = 0;
        String[] ipv4Str = ip.split(IPV4_DELEMITOR);

        for(int i = 0; i < ipv4Str.length; ++i) {
            binIPv4 += (Integer.parseInt(ipv4Str[i]) << ((3 - i) * 8));
        }

        return binIPv4;
    }

    /**
     * Check whether the node has glue node only or not.
     * @param node the node to check
     * @return result of this method
     * @since 1.8+
     */
    public boolean hasGlueNodeOnly(Node<T> node) {
        Map<Integer, Node<T>> m = node.getRefToChildren();
        if(m == null || m.isEmpty()) return false;

        return !m.entrySet().stream().anyMatch(e -> e.getValue().getData() != null);
    }

    /**
     * Class Node is the node of the ipdict tree.
     * There are 2 types of the node, one is a data node that has data entity and the other is a glue node that has no data(null).
     * The purpose of existing glue node is to search the node under the node that has subnet mask length shorter than the data node to search.
     *
     * @param <T> type of data
     */
    static class Node <T>{
        private T data;
        private int subnetMaskLength;
        private int childSubnetMaskLength;
        private Map<Integer, Node<T>> refToChildren;

        /**
         * Constructs an empty node.
         */
        protected Node() {
            this.refToChildren = new HashMap<>();
        }

        /**
         * Constructs the node with values.
         * @param data the data indexed by IP address
         * @param subnetMaskLength subnetmask length in current network address
         * @param childSubnetMaskLength subnetmask length in network address under this node
         * @param refToChild referenct to child node
         */
        protected Node(T data, int subnetMaskLength, int childSubnetMaskLength, Map<Integer, Node<T>> refToChild) {
            this.refToChildren          = new HashMap<>();
            this.data                   = data;
            this.subnetMaskLength       = subnetMaskLength;
            this.childSubnetMaskLength  = childSubnetMaskLength;
            this.refToChildren          = refToChild;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public int getSubnetMaskLength() {
            return subnetMaskLength;
        }

        public void setSubnetMaskLength(int subnetMaskLength) {
            this.subnetMaskLength = subnetMaskLength;
        }

        public int getChildSubnetMaskLength() {
            return childSubnetMaskLength;
        }

        public void setChildSubnetMaskLength(int childSubnetMaskLength) {
            this.childSubnetMaskLength = childSubnetMaskLength;
        }

        public Map<Integer, Node<T>> getRefToChildren() {
            return refToChildren;
        }

        public void setRefToChildren(Map<Integer, Node<T>> refToChildren) {
            this.refToChildren = refToChildren;
        }

        public void addRefToChildren(int binaryAddress, Node childNode) {
            if(this.refToChildren == null) this.refToChildren = new HashMap<>();
            this.refToChildren.put(binaryAddress, childNode);
        }
    }
}
