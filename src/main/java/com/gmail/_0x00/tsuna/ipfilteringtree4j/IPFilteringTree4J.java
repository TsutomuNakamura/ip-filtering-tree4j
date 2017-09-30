package com.gmail._0x00.tsuna.ipfilteringtree4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * IPFilteringTree4j is a key value on memory database mapped by IPv4 address.
 * It can push various types of values by using IPv4 address and its subnet mask length.
 * And it search the data with Longest prefix match(also called Maximum prefix length match) rules by using tree algorithms.
 * Due to the algorithm, if you want to push data as a default, you can push data with key 0.0.0.0/0.<br><br>
 *
 * Quick examples ofousages are like below.<br><br>
 *
 * <h2>instantiate:</h2>
 * <pre>
 * {@code
 * IPFilteringTree4J db = new IPFilteringTree4J();
 * }
 * </pre>
 *
 * <h2>push:</h2>
 * <pre>
 * {@code
 * db.push("");
 * // push(<ipaddr>, <subnet mask length>, <data>);
 * db.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
 * db.push("192.168.2.0", 24, "Data of 192.168.2.0/24");
 * db.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
 * db.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
 * }
 * </pre>
 *
 * <h2>find:</h2>
 * <pre>
 * {@code
 * db.find("192.168.1.0");    // -> Data of 192.168.1.0/24
 * db.find("192.168.2.100");    // -> Data of 192.168.2.0/24
 * db.find("192.168.101.32");    // -> Data of 192.168.0.0/16
 * db.find("10.1.0.23");    // -> Data of 0.0.0.0/0
 * }
 * </pre>
 *
 * <h2>delete:</h2>
 * <pre>
 * {@code
 * db.delete("192.168.1.0", 24);    // -> Data of 192.168.1.0/24
 * db.find("192.168.1.0");  // -> data of 192.168.0.0/16
 * db.delete("192.168.0.0", 16);    // -> Data of 192.168.0.0/16
 * db.find("192.168.1.0");  // -> Data of 0.0.0.0/0
 * }
 * </pre>
 *
 * @param <T> the type of valued mapped by IPv4 address
 * @author Tsutomu Nakamura
 * @since 1.8+
 */
public class IPFilteringTree4J <T>
{
    /** Delemiter of IPv4 */
    private static final String IPV4_DELEMITOR = "\\.";

    /** Constant which means subnetmask length is unregistered */
    private static final int SUBNETMASK_LENGTH_IS_UNDEFINED = -1;

    /** Root node of the tree */
    private Node<T> root = new Node<>(null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new HashMap<>());

    /** List of subnet mask length of IPv4 */
    private static int[] masks;

    static {
        masks = new int[32 + 1];
        int mask = 0xffffffff;
        for(int i = 32; i >= 0; --i) {
            masks[i] = mask;
            mask <<= 1;
        }
    };

    /** Constructs a new empty ip-filtering-tree. */
    public IPFilteringTree4J() {
        Map<Integer, Node<T>> rootMap = root.getRefToChildren();
        rootMap.put(0, new Node<>(null, 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
    }

    /**
     * Push a data indexed by IPv4 address in the tree databaase
     * @param ip IPv4 address string for index
     * @param subnetMaskLength Length of network address for IPv4 address
     * @param data Data
     * @return instance of IPFilteringTree4J
     * @throws IllegalArgumentException if data was null or wrong IPv4 address format
     */
    public IPFilteringTree4J push(String ip, int subnetMaskLength, T data) throws IllegalArgumentException {

        if(data == null)
            throw new IllegalArgumentException("Cannot push null as data (" + ip + "/" + subnetMaskLength + ", null)");

        return pushDataToIPv4Tree(
                root,
                root.getRefToChildren().get(0),
                convertIPStringToBinary(ip),
                subnetMaskLength,
                data);
    }

    private Node<T> getRootNode() {
        return root.getRefToChildren().get(0);
    }

    private IPFilteringTree4J<T> pushDataToIPv4Tree(Node<T> node, Node<T> pNode, int ipv4, int subnetMaskLength, T data) throws IllegalArgumentException {
        Node<T> currentNode     = node;
        Node<T> parentNode      = pNode;
        int lastNetworkAddress  = 0;
        int networkAddress = getBinIPv4NetAddr(ipv4, subnetMaskLength);

        while(true) {
            int subnetLengthOfCurrentNode = currentNode.getSubnetMaskLength();
            if(subnetLengthOfCurrentNode == subnetMaskLength) {
                // The data may have been existed
                if(currentNode.getData() != null) {
                    throw new IllegalArgumentException(
                            "The data you are going to push is already registered with same IP index (" + stringifyFromBinIPv4(ipv4) + "/" + subnetMaskLength + ").");
                }
                // Override the data in this glue node
                parentNode.getRefToChildren().put(
                        networkAddress,
                        new Node<>(data, subnetMaskLength, currentNode.getChildSubnetMaskLength(), currentNode.getRefToChildren()));

                for(Map.Entry<Integer, Node<T>> e: parentNode.getRefToChildren().entrySet()) {
                    if(hasGlueNodeOnly(e.getValue())) {
                        rebalanceChildGlueNode(e.getValue(), parentNode, e.getKey());
                    }
                }

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
                    // Create glue node then check the glue node's network address
                    // If data node was already existed, createGlueNodes does nothing then continues then throws error.
                    createGlueNodes(currentNode, parentNode, lastNetworkAddress, subnetMaskLength);
                    currentNode = parentNode.getRefToChildren().get(lastNetworkAddress);
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
                    /* continue; */
                } else {  /* currentNode.getChildSubnetMaskLength() == subnetMaskLength */
                    if(currentNode.getRefToChildren().get(ipv4) == null) {
                        currentNode.getRefToChildren().put(
                                ipv4,
                                new Node<>(null, subnetMaskLength, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
                    }
                    parentNode = currentNode;
                    currentNode = currentNode.getRefToChildren().get(ipv4);
                    /* continue; */
                }
            } else {
                // unreachable
                throw new Error("Oops... Push instruction has gone something wrong (" + stringifyFromBinIPv4(ipv4) + "/" + subnetMaskLength + ").");
            }
        }

        return this;
    }

    /**
     * Convert String IPv4 format to binary of 32bit integer.
     * @param ip String of IPv4 address
     * @return IPv4 address converted 32bit int.
     */
    public int convertIPStringToBinary(String ip) {
        int binIPv4 = 0;
        int octet;
        String[] ipv4Str = ip.split(IPV4_DELEMITOR);

        if(ipv4Str.length != 4)
            throw new IllegalArgumentException("Format of IPv4 address \"" + ip + "\" is illegal");

        try {
            for(int i = 0; i < ipv4Str.length; ++i) {
                 octet = Integer.parseInt(ipv4Str[i]);
                 if(octet > 255 || octet < 0)
                     throw new IllegalArgumentException("Format of IPv4 address \"" + ip + "\" is illegal");

                 binIPv4 += (octet << ((3 - i) * 8));
            }
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Format of IPv4 address \"" + ip + "\" is illegal");
        }

        return binIPv4;
    }

    /**
     * Stringify binary IPv4 address.
     * @param ip binary IP address
     * @return result
     */
    public String stringifyFromBinIPv4(int ip) {
        int copyIp = ip;
        int mask = 255;
        String result = "";;

        for(int i = 0; i < 4; ++i) {
            result = (copyIp & mask) + result;
            if(i < 3) {
                result = "." + result;
                copyIp >>>= 8;
            }
        }

        return result;
    }

    /**
     * Find the data from ip address
     * @param ip the ip address to find the data
     * @return data or null.
     */
    public T find(String ip) {
        int keyIp = convertIPStringToBinary(ip);
        int netAddress;
        Node<T> currentNode = root.getRefToChildren().get(0);
        Node<T> nextNode;
        T result = currentNode.getData();

        while(currentNode.getChildSubnetMaskLength() != SUBNETMASK_LENGTH_IS_UNDEFINED) {
            netAddress = getBinIPv4NetAddr(keyIp, currentNode.getChildSubnetMaskLength());
            if((nextNode = currentNode.getRefToChildren().get(netAddress)) != null) {
                if(nextNode.getData() != null) {
                    result = nextNode.getData();
                }
                currentNode = nextNode;
            } else {
                return result;
            }
        }

        return result;
    }

    /**
     * Remove a data in the tree indexed by IPv4 network address.
     * @param ip IPv4 address string for index.
     * @param maskLen Length of network address for IPv4 address.
     * @return data that has deleted.
     */
    public T delete(String ip, int maskLen) {
        return deleteDataFromTheTree(getRootNode(), root, convertIPStringToBinary(ip), maskLen);
    }

    /**
     * Remove a data in the tree indexed by IPv4 network address.
     * @param node target node that may have the node that will be deleted
     * @param parentNode parent node of target node
     * @param ip ip address to the node
     * @param maskLen subnet mask length to the node that will be deleted
     * @return data that has deleted
     */
    private T deleteDataFromTheTree(Node<T> node, Node<T> parentNode, final int ip, final int maskLen) {
        Node<T> currentNode = node;
        // Node<T> ppNode      = root;
        int maskLenOfCurrentNode = node.getSubnetMaskLength();
        T result;
        Stack<Bucket<T>> stack = new Stack<>();
        stack.push(new Bucket<>(parentNode, 0));

        while(true) {
            if(currentNode.getSubnetMaskLength() == maskLen) {
                result = currentNode.getData();
                if(result == null) return null;  /* target was not found */

                if(currentNode.getSubnetMaskLength() == 0) {
                    // Delete root node as glue node
                    Node<T> newNode = new Node<>(
                            null, currentNode.getSubnetMaskLength(), currentNode.getChildSubnetMaskLength(), currentNode.getRefToChildren());
                    stack.pop().getNode().getRefToChildren().put(0, newNode);  /* put("0.0.0.0", newNode) */

                    return result;
                }

                // If currentNode is not root one
                Bucket<T> parentNodeBucket;
                Node<T> pNode;
                int netAddrToPNodeChild;
                while(!stack.empty()) {
                    parentNodeBucket    = stack.pop();
                    pNode               = parentNodeBucket.getNode();
                    netAddrToPNodeChild = parentNodeBucket.getIpv4ToNode();

                    if((pNode.getSubnetMaskLength() != 0) && (pNode.getData() == null && pNode.getRefToChildren().size() == 1)) {
                        // The parent glue node will be deleted with target node
                        continue;
                    }

                    pNode.getRefToChildren().remove(netAddrToPNodeChild);
                    if(pNode.getRefToChildren().size() == 0) {
                        // FIXME:
                        pNode.setChildSubnetMaskLength(SUBNETMASK_LENGTH_IS_UNDEFINED);
                        if(currentNode.getChildSubnetMaskLength() != SUBNETMASK_LENGTH_IS_UNDEFINED) {
                            pNode.setRefToChildren(currentNode.getRefToChildren());
                            pNode.setChildSubnetMaskLength(currentNode.getChildSubnetMaskLength());
                        }
                    } else {
                        // if(stack.size() == 0) throw new Exception("Something wrong");

                        Bucket<T> b = stack.pop();
                        Node<T> ppNode = b.getNode();  /* Parent node of parent node */
                        int keyToChildOfPpnode = b.getIpv4ToNode();
                        rebalanceChildGlueNode(pNode, ppNode, keyToChildOfPpnode);
                    }
                    break;
                }
                return result;
            }
            int childNetAddr = getBinIPv4NetAddr(ip, currentNode.getChildSubnetMaskLength());
            Node<T> nextNode = currentNode.getRefToChildren().get(childNetAddr);
            if(nextNode != null) {
                stack.push(new Bucket<>(currentNode, childNetAddr));
                currentNode = nextNode;
            } else {
                return null;  /* Target to delete was not found */
            }
        }
    }

    /**
     * Check whether the node has glue node only or not.
     * @param node the node to check
     * @return result of this method
     * @since 1.8+
     */
    private boolean hasGlueNodeOnly(Node<T> node) {
        Map<Integer, Node<T>> m = node.getRefToChildren();
        if(m == null || m.isEmpty()) return false;

        return !m.entrySet().stream().anyMatch(e -> e.getValue().getData() != null);
    }

    private void rebalanceChildGlueNode(Node<T> node, Node<T> parentNode, int keyToCurrentNode) {
        if(node.getChildSubnetMaskLength() == 32 ||
                node.getChildSubnetMaskLength() == IPFilteringTree4J.SUBNETMASK_LENGTH_IS_UNDEFINED ||
                !hasGlueNodeOnly(node)) {
            return;
        }

        Map<Integer, Node<T>> oldChildNodes = node.getRefToChildren();
        int len                         = IPFilteringTree4J.SUBNETMASK_LENGTH_IS_UNDEFINED;
        int minimum                     = 32;
        Map<Integer, Object> variance   = new HashMap<>();
        boolean doCreate                = false;

        for(Map.Entry<Integer, Node<T>> e : oldChildNodes.entrySet()) {
            len = e.getValue().getChildSubnetMaskLength();
            if(len == IPFilteringTree4J.SUBNETMASK_LENGTH_IS_UNDEFINED) continue;
            variance.put(len, null);
            if(minimum > len) minimum = len;

            if(node.getSubnetMaskLength() >= (len - 2)) {
                doCreate = true;
                break;
            }
        }

        if(variance.size() > 1) doCreate = true;

        Node<T> newNode = new Node<>(node.getData(), node.getSubnetMaskLength(), minimum, new HashMap<>());

        for(Map.Entry<Integer, Node<T>> e : oldChildNodes.entrySet()) {
            if(doCreate && e.getValue().getChildSubnetMaskLength() > minimum) {
                createGlueNodes(e.getValue(), node, e.getKey(), minimum);
            }
            for(Map.Entry<Integer, Node<T>> e2 : e.getValue().getRefToChildren().entrySet()) {
                newNode.getRefToChildren().put(e2.getKey(), e.getValue().getRefToChildren().get(e2.getKey()));
            }
        }

        parentNode.getRefToChildren().put(keyToCurrentNode, newNode);
    }

    /**
     * Get network address from binary IPv4 address
     * @param ipv4 the IPv4 binary address
     * @param subnetMaskLength the length of subnetmask
     * @return network address
     */
    private int getBinIPv4NetAddr(int ipv4, int subnetMaskLength) {
        return (masks[subnetMaskLength] & ipv4);
    }

    /**
     * Create glue node with at specific length
     * @param currentNode node that the glue nodes will be created
     * @param parentNode parent node of currentNode
     * @param netAddrToCurrent net address to current node from parent node
     * @param subnetMaskLength subnet mask length of the glue node that will be created under currentNode
     */
    private void createGlueNodes(Node<T> currentNode, Node<T> parentNode, int netAddrToCurrent, int subnetMaskLength) {
        Map<Integer, Node<T>> childNodes        = currentNode.getRefToChildren();
        Map<Integer, Node<T>> rootOfGlueNodes   = new HashMap<>();

        for(Map.Entry<Integer, Node<T>> e : childNodes.entrySet()) {
            int netAddress = getBinIPv4NetAddr(e.getKey(), subnetMaskLength);

            if(rootOfGlueNodes.get(netAddress) == null) {
                rootOfGlueNodes.put(
                        netAddress,
                        new Node<>(null, subnetMaskLength, currentNode.getChildSubnetMaskLength(), new HashMap<>()));
            }
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
         * @param data
         * @param subnetMaskLength
         * @param childSubnetMaskLength
         */
        protected Node(T data, int subnetMaskLength, int childSubnetMaskLength) {
            this.data = data;
            this.subnetMaskLength = subnetMaskLength;
            this.childSubnetMaskLength = childSubnetMaskLength;
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
    }

    static class Bucket <T> {
        /** Node */
        private Node<T> node;
        /** IPv4 address to the node */
        private int ipv4ToNode;

        public Bucket(Node<T> node, int ipv4ToNode) {
            this.node = node;
            this.ipv4ToNode = ipv4ToNode;
        }

        public Node<T> getNode() {
            return node;
        }

        public int getIpv4ToNode() {
            return ipv4ToNode;
        }

        public void setNode(Node<T> node) {
            this.node = node;
        }

        public void setIpv4ToNode(int ipv4ToNode) {
            this.ipv4ToNode = ipv4ToNode;
        }
    }
}
