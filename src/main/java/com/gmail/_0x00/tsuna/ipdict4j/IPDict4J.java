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
    private static final String IPV4_DELEMITOR = "\\.";

    private static final Pattern IPV4_REGEX
            = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    public static final int SUBNETMASK_LENGTH_IS_UNDEFINED = -1;

    private Node<T> root = new Node<>(null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new HashMap<>());

    private int[] masks = new int[32 + 1];

    public IPDict4J() {
        Map<Integer, Node<T>> rootMap = root.getRefToChildren();
        rootMap.put(0, new Node<>(null, 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));

        int mask = 0xffffffff;
        for(int i = 32; i >= 0; --i) {
            masks[i] = mask;
            mask <<= 1;
        }
    }


    public void push(String ipAddress, int subnetMaskLength, T data) throws Exception {
        if(!IPV4_REGEX.matcher(ipAddress).matches())
            throw new Exception("String of IPv4 " + ipAddress + " is invalid");
        if(data == null)
            throw new Exception("TODO: Cannot push null as data");

        pushDataToIPv4Tree(
                root,
                root.getRefToChildren().get(0),
                convertIPStringToBinary(ipAddress),
                subnetMaskLength,
                data);
    }

    public Node<T> getRootNode() {
        return root.getRefToChildren().get(0);
    }

    private void pushDataToIPv4Tree(Node<T> node, Node<T> pNode, int ipv4, int subnetMaskLength, T data) throws Exception {
        Node<T> currentNode     = node;
        Node<T> parentNode      = pNode;
        int lastNetworkAddress  = 0;
        int networkAddress = getBinIPv4NetAddr(ipv4, subnetMaskLength);

        while(true) {
            int subnetLengthOfCurrentNode = currentNode.getSubnetMaskLength();
            if(subnetLengthOfCurrentNode == subnetMaskLength) {
                // The data may have been existed
                if(currentNode.getData() != null) {
                    // TODO: appropriate exception
                    throw new Exception("The data you are going to is already registerd at index ...");
                }
                // Override the data in this glue node
                parentNode.getRefToChildren().put(
                        networkAddress,
                        new Node<>(data, subnetMaskLength, currentNode.getChildSubnetMaskLength(), currentNode.getRefToChildren()));
                break;
            } else if(subnetLengthOfCurrentNode < subnetMaskLength) {

                if(currentNode.getRefToChildren().isEmpty()) {
                    Node<T> newNode = new Node<>(
                            currentNode.getData(),
                            currentNode.getSubnetMaskLength(),
                            subnetMaskLength,
                            currentNode.getRefToChildren());

                    newNode.getRefToChildren().put(
                            networkAddress,
                            new Node<>(data, subnetMaskLength, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));

                    parentNode.getRefToChildren().put(lastNetworkAddress, newNode);

                    break;
                }

                if(currentNode.getChildSubnetMaskLength() > subnetMaskLength) {
                    // TODO:
                    // Create glue node then check the glue node's network address
                    createGlueNodes(currentNode, parentNode, lastNetworkAddress, subnetMaskLength);
                    currentNode.getRefToChildren().put(
                            networkAddress, new Node<>(data, subnetMaskLength, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
                    break;
                } else if(currentNode.getChildSubnetMaskLength() < subnetMaskLength) {
                    // continue then new node will be appended
                    int childNetworkAddress = getBinIPv4NetAddr(ipv4, currentNode.getChildSubnetMaskLength());

                    if(currentNode.getRefToChildren().get(childNetworkAddress) == null) {
                        currentNode.getRefToChildren().put(
                                childNetworkAddress,
                                new Node<>(null, currentNode.getChildSubnetMaskLength(), SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
                    }
                    parentNode          = currentNode;
                    currentNode         = currentNode.getRefToChildren().get(childNetworkAddress);
                    lastNetworkAddress  = getBinIPv4NetAddr(ipv4, currentNode.getSubnetMaskLength());
                    continue;
                } else {
                    if(currentNode.getRefToChildren().get(ipv4) == null) {
                        currentNode.getRefToChildren().put(
                                ipv4,
                                new Node<>(null, subnetMaskLength, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
                    }
                    parentNode = currentNode;
                    currentNode = currentNode.getRefToChildren().get(ipv4);
                    continue;
                }
            } else {
                // unreachable
                throw new Exception("Push instruction has gone wrong exception.");
            }
        }
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
     * Get network address from binary IPv4 address
     * @param ipv4 the IPv4 binary address
     * @param subnetMaskLength the length of subnetmask
     * @return network address
     */
    public int getBinIPv4NetAddr(int ipv4, int subnetMaskLength) {
        return (masks[subnetMaskLength] & ipv4);
    }

    public void createGlueNodes(Node<T> currentNode, Node<T> parentNode, int netAddrToCurrent, int subnetMaskLength) {
        Map<Integer, Node<T>> childNodes        = currentNode.getRefToChildren();
        Map<Integer, Node<T>> rootOfGlueNodes   = new HashMap<>();

        for(Map.Entry<Integer, Node<T>> e : childNodes.entrySet()) {
            int netAddress = getBinIPv4NetAddr(e.getKey(), subnetMaskLength);

            //if(node.getNodeToRefToChildren(netAddress) == null) {
            if(currentNode.getRefToChildren().get(netAddress) == null) {
                rootOfGlueNodes.put(
                        netAddress,
                        new Node<>(null, subnetMaskLength, currentNode.getChildSubnetMaskLength(), new HashMap<>()));
            }
            Node<T> glueNode = rootOfGlueNodes.get(netAddress);
            rootOfGlueNodes.get(netAddress).getRefToChildren().put( e.getKey(), childNodes.get(e.getKey()) );
        }

        Node<T> newCurrentNode
                = new Node<>(currentNode.getData(), currentNode.getSubnetMaskLength(), subnetMaskLength, rootOfGlueNodes);
        parentNode.getRefToChildren().put(netAddrToCurrent, newCurrentNode);
    }

    /**
     * Class Node is the node of the ipdict tree.
     * There are 2 types of the node, one is a data node that has data entity and the other is a glue node that has no data(null).
     * The purpose of existing glue node is to search the node under the node that has subnet mask length shorter than the data node to search.
     *
     * @param <T> type of data
     */
    static class Node <T> {
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
         *
         * @param data                  the data indexed by IP address
         * @param subnetMaskLength      subnetmask length in current network address
         * @param childSubnetMaskLength subnetmask length in network address under this node
         * @param refToChild            referenct to child node
         */
        protected Node(T data, int subnetMaskLength, int childSubnetMaskLength, Map<Integer, Node<T>> refToChild) {
            this.refToChildren = new HashMap<>();
            this.data = data;
            this.subnetMaskLength = subnetMaskLength;
            this.childSubnetMaskLength = childSubnetMaskLength;
            this.refToChildren = refToChild;
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

        //public void putNodeToRefToChildren(int binaryAddress, Node<T> childNode) {
        //    this.refToChildren.put(binaryAddress, childNode);
        //}

        //public Node<T> getNodeToRefToChildren(int binaryAddress) {
        //    return this.refToChildren.get(binaryAddress);
        //}
    }
}
