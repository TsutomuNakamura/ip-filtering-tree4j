package com.gmail._0x00.tsuna.ipdict4j;

import java.util.HashMap;
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
            throw new IllegalFormatException("String of IPv4 " + ipaddr + " is invalid");
        if(data == null)
            throw new IllegalFormatException("TODO: Cannot push null as data");
    }

    /**
     * Convert String IPv4 format to binary of 32bit integer.
     * @param ip String of IPv4
     * @return binary IPv4
     */
    public int convertIPv4StringToBinary(String ip) {
        int binIPv4 = 0;
        String[] ipv4Str = ip.split(IPV4_DELEMITOR);

        for(int i = 0; i < ipv4Str.length; ++i) {
            binIPv4 += (Integer.parseInt(ipv4Str[i]) << ((3 - i) * 8));
        }

        return binIPv4;
    }

    class Node <T>{
        private T data;
        private int subnetMaskLength;
        private int childSubnetMaskLength;
        Map<Integer, T> refToChildren;
        protected Node() {}

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

        public Map<Integer, T> getRefToChildren() {
            return refToChildren;
        }

        public void setRefToChildren(Map<Integer, T> refToChildren) {
            this.refToChildren = refToChildren;
        }

        public void addRefToChildren(int address, Node childNode) {

        }
    }
}
