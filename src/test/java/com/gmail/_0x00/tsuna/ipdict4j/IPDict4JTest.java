package com.gmail._0x00.tsuna.ipdict4j;



import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.gmail._0x00.tsuna.ipdict4j.IPDict4J.Node;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for IPDict4J.
 */
public class IPDict4JTest
{
    private IPDict4J<String> dict;
    private int SUBNETMASK_LENGTH_IS_UNDEFINED;

    public <E> void assertTheNode(Node<String> node, java.lang.String data, int subnetMaskLength, int childSubnetMaskLength, String[] indexesOfChildNodes) {
        if(indexesOfChildNodes == null) indexesOfChildNodes = new String[0];
        assertEquals(data, node.getData());
        assertEquals(subnetMaskLength, node.getSubnetMaskLength());
        assertEquals(childSubnetMaskLength, node.getChildSubnetMaskLength());

        assertEquals(indexesOfChildNodes.length, node.getRefToChildren().size());
        for(String ip : indexesOfChildNodes) {
            assertNotNull(node.getRefToChildren().get(dict.convertIPStringToBinary(ip)));
        }
    }

    @BeforeEach
    public void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        dict = new IPDict4J<>();
        SUBNETMASK_LENGTH_IS_UNDEFINED = (int)TestUtil.getStaticField(IPDict4J.class, "SUBNETMASK_LENGTH_IS_UNDEFINED");
    }

    /**
     * Test class for method convertIPStringToBinary
     */
    @Nested
    @DisplayName("convertIPStringToBinary")
    class TestConvertIPStringToBinary {

        @Test @DisplayName("should convert \"0.0.0.0\" to 0")
        void convertIPStringToBinary_8e3d5235_54ba_492a_9e40_55d5409f2445() {
            assertEquals(0, dict.convertIPStringToBinary("0.0.0.0"));
        }
        @Test @DisplayName("should convert \"255.255.255.255\" to -1")
        void convertIPStringToBinary_64cec78a_782b_4631_909b_fe99e70f1a78() {
            assertEquals(-1, dict.convertIPStringToBinary("255.255.255.255"));
        }
        @Test @DisplayName("should convert \"255.255.255.0\" to -256")
        void convertIPStringToBinary_1caf65fe_636a_4a5b_8e13_3c3a068f218f() {
            assertEquals(-256, dict.convertIPStringToBinary("255.255.255.0"));
        }
        @Test @DisplayName("should convert \"255.255.0.0\" to -65536")
        void convertIPStringToBinary_6e823b5b_3d30_485c_adb7_5329dfc58e18() {
            assertEquals(-65536, dict.convertIPStringToBinary("255.255.0.0"));
        }
        @Test @DisplayName("should convert \"255.0.0.0\" to -16777216")
        void convertIPStringToBinary_93601bcd_008e_4b9d_b10f_6768cc44321d() {
            assertEquals(-16777216, dict.convertIPStringToBinary("255.0.0.0"));
        }
        @Test @DisplayName("should convert \"192.168.1.0\" to -1062731520")
        void convertIPStringToBinary_90359485_b332_46d6_a84c_bd681dcc7613() {
            assertEquals(-1062731520, dict.convertIPStringToBinary("192.168.1.0"));
        }
        @Test @DisplayName("should convert \"192.168.1.1\" to -1062731519")
        void convertIPStringToBinary_4aa3426b_1691_4566_9060_211fdfc335c7() {
            assertEquals(-1062731519, dict.convertIPStringToBinary("192.168.1.1"));
        }
        @Test @DisplayName("should convert \"172.16.0.0\" to -1408237568")
        void convertIPStringToBinary_338e53f4_7c0d_47a0_9dc7_017c7debf898() {
            assertEquals(-1408237568, dict.convertIPStringToBinary("172.16.0.0"));
        }
        @Test @DisplayName("should convert \"10.0.0.0\" to 167772160")
        void convertIPStringToBinary_ed713a45_99e5_4cb8_8de1_d4ac0e300402() {
            assertEquals(167772160, dict.convertIPStringToBinary("10.0.0.0"));
        }
    }

    @Nested
    @DisplayName("stringifyFromBinIPv4")
    class TestStringifyFromBinIPv4 {
        @Test @DisplayName("should stringify 0 to \"0.0.0.0\"")
        void stringifyFromBinIPv4_ac098aa1_bd60_42fc_b02b_1d56e955bef1() {
            assertEquals("0.0.0.0", dict.stringifyFromBinIPv4(0));
        }
        @Test @DisplayName("should stringify -1 to \"255.255.255.255\"")
        void stringifyFromBinIPv4_ccedfc9b_0077_49cd_80f1_1dc44a318fc3() {
            assertEquals("255.255.255.255", dict.stringifyFromBinIPv4(-1));
        }
        @Test @DisplayName("should stringify -256 to \"255.255.255.0\"")
        void stringifyFromBinIPv4_d6dfedc6_bc1a_4f68_b993_2e20960ba3b8() {
            assertEquals("255.255.255.0", dict.stringifyFromBinIPv4(-256));
        }
        @Test @DisplayName("should stringify -65536 to \"255.255.0.0\"")
        void stringifyFromBinIPv4_d3f75a2a_6dc4_4e92_b0e4_72f896a1f18b() {
            assertEquals("255.255.0.0", dict.stringifyFromBinIPv4(-65536));
        }
        @Test @DisplayName("should stringify -16777216 to \"255.0.0.0\"")
        void stringifyFromBinIPv4_ed09c15c_99ea_41b7_b912_3c49f4aeaebb() {
            assertEquals("255.0.0.0", dict.stringifyFromBinIPv4(-16777216));
        }
        @Test @DisplayName("should stringify -1062731520 to \"192.168.1.0\"")
        void stringifyFromBinIPv4_5591a299_06aa_4b97_9a11_340ff306e302() {
            assertEquals("192.168.1.0", dict.stringifyFromBinIPv4(-1062731520));
        }
        @Test @DisplayName("should stringify -1408237568 to \"172.16.0.0\"")
        void stringifyFromBinIPv4_f3dbfc4c_7d41_49ac_b486_0a05ef2a69bb() {
            assertEquals("172.16.0.0", dict.stringifyFromBinIPv4(-1408237568));
        }
        @Test @DisplayName("should stringify 167772160 to \"10.0.0.0\"")
        void stringifyFromBinIPv4_ca563040_2d5e_41c1_afb5_2f35b589c560() {
            assertEquals("10.0.0.0", dict.stringifyFromBinIPv4(167772160));
        }
    }

    @Nested
    @DisplayName("hasGlueNodeOnly")
    class TestHasGlueNodeOnly {
        Node<String> n;
        Map<Integer, Node<String>> m;
        Method method;
        @BeforeEach
        void beforeEach() throws NoSuchMethodException {
            n = new Node<String>("Data of 0.0.0.0/0", 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>());
            m = new HashMap<Integer, Node<String>>();
            method = TestUtil.getMethod(IPDict4J.class, "hasGlueNodeOnly", new Class[]{Node.class});
        }

        @Test @DisplayName("should return false if only one data node is existed")
        void hasGlueNodeOnly_bf5c1d3e_7750_4b9c_9a54_9ebbbf7cda60() throws Exception {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>("Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertFalse((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return false if any node is not existed")
        void hasGlueNodeOnly_76958da8_afd0_4db9_af35_ae335c78896f() throws Exception {
            n.setRefToChildren(m);
            assertFalse((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return true if only glue node is existed")
        void hasGlueNodeOnly_47204aec_1d7b_46fb_beee_533d5800ec06() throws Exception  {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertTrue((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return false if some data nodes existed but glue node isn't")
        void hasGlueNodeOnly_8c038ec4_b696_475e_90c6_0b3afd661d88() throws Exception {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>("Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>("Data of 192.168.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertFalse((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return true if some glue nodes existed but data node isn't")
        void hasGlueNodeOnly_6e7bb0ca_a638_4eab_88ce_d4f9d47ce3fd() throws Exception {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertTrue((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return false if data node is existed in some glue nodes (pattern 1)")
        void hasGlueNodeOnly_8e3ba196_4d44_4aec_a574_32d20545fb25() throws Exception {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>("Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.3.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertFalse((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return false if data node is existed in some glue nodes (pattern 2)")
        void hasGlueNodeOnly_96199c89_df9b_489c_8108_1777a674bb33() throws Exception {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>("Data of 192.168.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.3.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertFalse((Boolean) method.invoke(dict, n));
        }
        @Test @DisplayName("should return false if data node is existed in some glue nodes (pattern 3)")
        void hasGlueNodeOnly_15227bd7_251f_48e0_99d9_e56627439fa0() throws Exception {
            m.put(dict.convertIPStringToBinary("192.168.1.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.2.0"), new Node<>(null, 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            m.put(dict.convertIPStringToBinary("192.168.3.0"), new Node<>("Data of 192.168.3.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new HashMap<>()));
            n.setRefToChildren(m);
            assertFalse((Boolean) method.invoke(dict, n));
        }
    }

    @Nested
    @DisplayName("getBinIPv4NetAddr")
    class TestGetBinIPv4NetAddr {
        Method method;
        @BeforeEach
        void beforeEach() throws Exception {
            method = TestUtil.getMethod(dict.getClass(), "getBinIPv4NetAddr", new Class[]{int.class, int.class});
        }

        @Test @DisplayName("should return 255.255.255.255(binary) if parameter 255.255.255.255(binary) and 32 were passed")
        void getBinIPv4NetAddr_29ef82f9_65c3_4489_ba42_6f33f34c25bb() throws Exception {
            assertEquals(dict.convertIPStringToBinary("255.255.255.255"),  method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 32));
        }
        @Test @DisplayName("should return 255.255.255.254(binary) if parameter 255.255.255.255(binary) and 31 were passed")
        void getBinIPv4NetAddr_8359cbe6_4605_47de_971f_fff00a96fb2b() throws Exception {
            assertEquals(dict.convertIPStringToBinary("255.255.255.254"), method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 31));
        }
        @Test @DisplayName("should return 255.255.255.128(binary) if parameter 255.255.255.255(binary) and 25 were passed")
        void getBinIPv4NetAddr_2d2f0dfc_da13_49c1_8eeb_b6e517a8f01f() throws Exception {
            assertEquals(dict.convertIPStringToBinary("255.255.255.128"), method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 25));
        }
        @Test @DisplayName("should return 255.255.255.0(binary) if parameter 255.255.255.255(binary) and 24 were passed")
        void getBinIPv4NetAddr_3fcb34f0_13c4_48f2_99ee_5bb2b0ba00d0() throws Exception {
            assertEquals(dict.convertIPStringToBinary("255.255.255.0"), method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 24));
        }
        @Test @DisplayName("should return 255.255.0.0(binary) if parameter 255.255.255.255(binary) and 16 were passed")
        void getBinIPv4NetAddr_e32f7866_c1b1_45d0_b2c4_02d2dc04bdbd() throws Exception {
            assertEquals(dict.convertIPStringToBinary("255.255.0.0"), method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 16));
        }
        @Test @DisplayName("should return 255.0.0.0(binary) if parameter 255.255.255.255(binary) and 8 were passed")
        void getBinIPv4NetAddr_46799fd5_d246_4ad6_a2df_765ca783e6de() throws Exception {
            assertEquals(dict.convertIPStringToBinary("255.0.0.0"), method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 8));
        }
        @Test @DisplayName("should return 0.0.0.0(binary) if parameter 255.255.255.255(binary) and 0 were passed")
        void getBinIPv4NetAddr_3d8f7375_a0a9_408d_a7c1_9f2da76ed510() throws Exception {
            assertEquals(dict.convertIPStringToBinary("0.0.0.0"), method.invoke(dict, dict.convertIPStringToBinary("255.255.255.255"), 0));
        }
        @Test @DisplayName("should return 192.168.1.0(binary) if parameter 192.168.1.128(binary) and 24 were passed")
        void getBinIPv4NetAddr_9492a286_b69c_47a7_97df_69b24b17b65a() throws Exception {
            assertEquals(dict.convertIPStringToBinary("192.168.1.0"), method.invoke(dict, dict.convertIPStringToBinary("192.168.1.128"), 24));
        }
        @Test @DisplayName("should return 192.168.1.128(binary) if parameter 192.168.1.128(binary) and 25 were passed")
        void getBinIPv4NetAddr_f277cd09_4c91_42fe_9a25_3f953c0e5ef9() throws Exception {
            assertEquals(dict.convertIPStringToBinary("192.168.1.128"), method.invoke(dict, dict.convertIPStringToBinary("192.168.1.128"), 25));
        }
        @Test @DisplayName("should return 192.168.0.0(binary) if parameter 192.168.1.128(binary) and 16 were passed")
        void getBinIPv4NetAddr_03b9f79d_9756_4e0e_95b0_c6d3a417b294() throws Exception {
            assertEquals(dict.convertIPStringToBinary("192.168.0.0"), method.invoke(dict, dict.convertIPStringToBinary("192.168.1.128"), 16));
        }
        @Test @DisplayName("should return 192.0.0.0(binary) if parameter 192.168.1.128(binary) and 8 were passed")
        void getBinIPv4NetAddr_d48e0188_e0c5_4ee4_b7c5_3f1baf4a1aee() throws Exception {
            assertEquals(dict.convertIPStringToBinary("192.0.0.0"), method.invoke(dict, dict.convertIPStringToBinary("192.168.1.128"), 8));
        }
    }

    @Nested
    @DisplayName("createGlueNodes")
    class TestCreateGlueNodes {
        Method method;
        @BeforeEach
        void beforeEach() throws Exception {
            method = TestUtil.getMethod(IPDict4J.class, "createGlueNodes", new Class[]{Node.class, Node.class, int.class, int.class});
        }
        @Test @DisplayName("should create a single glue node under the middle of data node")
        void createGluenodes_075a1add_3ef1_4149_a245_728c0813f9f4() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 2) 192.168.1.0/24(d)    |
                +-------------------------+
                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | New
                +-+-----------------------+
                | 192.168.0.0/16(g)       |
                +-------------------------+
                  |
                +-+-----------------------+
                | 192.168.1.0/24(d)       |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            method.invoke(dict, node, (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 16);

            node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node, null, 16, 24, new String[]{"192.168.1.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create 1 glue node over the 2 data nodes")
        void createGluenodes_227a31ae_1ff1_43d0_a377_a217091e343e() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 192.168.0.0/16(g)       |
                +-------------------------+
                  |
                +-+-----------------------+
                | 192.168.1.0/24(d)       |
                +-------------------------+
                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | New
                +-+-----------------------+
                | 192.0.0.0/8(g)          |
                +-------------------------+
                  |
                +-+-----------------------+
                | 192.168.0.0/16(g)       |
                +-------------------------+
                  |
                +-+-----------------------+
                | 192.168.1.0/24(d)       |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 16);
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 8);

            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node,null, 8, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node,null, 16, 24, new String[]{"192.168.1.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node,"Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create a single glue node under the middle of data node")
        void createGluenodes_3c7d46aa_b6e0_499d_8602_eacc4092b15a() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 192.168.0.0/16(d)       |
                +-------------------------+
                  |
                +-+-----------------------+
                | 192.168.129.0/24(d)     |
                +-------------------------+
                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 192.168.0.0/16(d)       |
                +-+-----------------------+
                  | New
                +-+-----------------------+
                | 192.168.128.0/17(g)     |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 192.168.129.0/24(d)     |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("192.168.129.0", 24, "Data of 192.168.129.0/24");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            method.invoke(
                    dict,
                    node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0")),
                    node, dict.convertIPStringToBinary("192.168.0.0"), 17);
            node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node, "Data of 192.168.0.0/16", 16, 17, new String[]{"192.168.128.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node, null, 17, 24, new String[]{"192.168.129.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.129.0"));
            assertTheNode(node, "Data of 192.168.129.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create 1 glue node over the 2 data nodes")
        void createGluenodes_9ecd5f66_085e_4be9_8a74_9c2bda18935e() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/24(d)     | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+

                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | New
                +-+-----------------------+
                | 192.168.0.0/16(g)       |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/24(d)     | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+
            */
            dict = new IPDict4J<>();
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.0.0", 24, "Data of 192.168.0.0/24");
            dict.push("192.168.128.0", 24, "Data of 192.168.128.0/24");
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"),
                    0, 16);
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node, null, 16, 24, new String[]{"192.168.128.0", "192.168.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1, "Data of 192.168.128.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create 2 glue node that has 1 child data node")
        void createGluenodes_() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/24(d)     | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+
                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/17(g)     | | 192.168.0.0/17(g)       | <- New
                +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/24(d)     | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.128.0", 24, "Data of 192.168.128.0/24");
            dict.push("192.168.0.0", 24, "Data of 192.168.0.0/24");
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 17);
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 17, new String[]{"192.168.128.0", "192.168.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1,null, 17, 24, new String[]{"192.168.128.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1,"Data of 192.168.128.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1,null, 17, 24, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1,"Data of 192.168.0.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create 2 glue node that has 2 child data node and has 1 child data node")
        void createGluenodes_25ea8b7b_0bd8_47a8_872e_ba861fa420aa() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.172.0/24(d)     | | 192.168.128.0/24(d)     | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+
                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+
                  | New                                                   | New
                +-+-----------------------+                             +-+-----------------------+
                | 192.168.128.0/17(g)     |                             | 192.168.0.0/17(g)       |
                +-------------------------+                             +-------------------------+
                  |                                                       |
                  +---------------------------+                           |
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.172.0/24(d)     | | 192.168.128.0/24(d)     | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.172.0", 24, "Data of 192.168.172.0/24");
            dict.push("192.168.128.0", 24, "Data of 192.168.128.0/24");
            dict.push("192.168.0.0", 24, "Data of 192.168.0.0/24");
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 17);
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 17, new String[]{"192.168.128.0", "192.168.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.168.172.0", "192.168.128.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.172.0"));
            assertTheNode(node2, "Data of 192.168.172.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node2, "Data of 192.168.128.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create 2 glue node that has 1 child data node and has 2 child data node")
        void createGluenodes_6f4a6031_a522_400d_a309_b90daf16c27f() throws Exception {
             /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/24(d)     | | 192.168.64.0/24(d)      | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+

                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  | New                       | New
                +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/17(g)     | | 192.168.0.0/17(g)       |
                +-------------------------+ +-+-----------------------+
                  |                           |
                  |                           +---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.128.0/24(d)     | | 192.168.64.0/24(d)      | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.128.0", 24, "Data of 192.168.128.0/24");
            dict.push("192.168.64.0", 24, "Data of 192.168.64.0/24");
            dict.push("192.168.0.0", 24, "Data of 192.168.0.0/24");
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 17);

            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 17, new String[]{"192.168.128.0", "192.168.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.168.128.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1, "Data of 192.168.128.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.168.64.0", "192.168.0.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.64.0"));
            assertTheNode(node2, "Data of 192.168.64.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, "Data of 192.168.0.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should create 2 glue node that has 2 child data node and has 2 child data node")
        void createGluenodes_29e13466_b1c9_44a6_9452_f4be5c31e4fa() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+---------------------------+
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.172.0/24(d)     | | 192.168.128.0/24(d)     | | 192.168.64.0/24(d)      | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
                > below >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  | New                                                   | New
                +-+-----------------------+                             +-+-----------------------+
                | 192.168.128.0/17(g)     |                             | 192.168.0.0/17(g)       |
                +-+-----------------------+                             +-+-----------------------+
                  |                                                       |
                  +---------------------------+                           +---------------------------+
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.172.0/24(d)     | | 192.168.128.0/24(d)     | | 192.168.64.0/24(d)      | | 192.168.0.0/24(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.172.0", 24, "Data of 192.168.172.0/24");
            dict.push("192.168.128.0", 24, "Data of 192.168.128.0/24");
            dict.push("192.168.64.0", 24, "Data of 192.168.64.0/24");
            dict.push("192.168.0.0", 24, "Data of 192.168.0.0/24");
            method.invoke(
                    dict,
                    (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null),
                    (Node<String>) TestUtil.getInstanceField(dict, "root"), 0, 17);

            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node,"Data of 0.0.0.0/0", 0, 17, new String[]{"192.168.128.0", "192.168.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.168.172.0", "192.168.128.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.172.0"));
            assertTheNode(node2, "Data of 192.168.172.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.128.0"));
            assertTheNode(node2, "Data of 192.168.128.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.168.64.0", "192.168.0.0"});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.64.0"));
            assertTheNode(node2, "Data of 192.168.64.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, "Data of 192.168.0.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
    }

    @Nested
    @DisplayName("push")
    class TestPush {
        @Test @DisplayName("should be empty node when no data has been pushed")
        void push_539f5664_cc2d_4116_b2f6_9512525ccfb9() {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-------------------------+
            */
            // Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            Node<String> node = null;
            try {
                node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            } catch(Exception e) {
                e.printStackTrace();
            }
            assertTheNode(node, null, 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push a root node 0.0.0.0/0")
        void push_78b15ca5_c107_4bdd_af16_2c134006fdd7() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push a single node 128.0.0.0/1")
        void push_972390b4_915e_425e_b95a_03ba8cda7101() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("128.0.0.0", 1, "Data of 128.0.0.0/1");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 1, new String[]{"128.0.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("128.0.0.0"));
            assertTheNode(node, "Data of 128.0.0.0/1", 1, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push 128.0.0.0/1, 0.0.0.0/1")
        void push_b25a9247_750e_48b5_9d93_d9e3a4944d4b() throws Exception {
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
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("128.0.0.0", 1, "Data of 128.0.0.0/1");
            dict.push("0.0.0.0", 1, "Data of 0.0.0.0/1");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 1, new String[]{"128.0.0.0", "0.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("128.0.0.0"));
            assertTheNode(node1, "Data of 128.0.0.0/1", 1, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node1, "Data of 0.0.0.0/1", 1, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push a node 255.255.255.255/32")
        void push_63247792_e6d7_4442_95c7_993fe9b619b9() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 1)255.255.255.255/32(d) |
                +-------------------------+
            */
            dict.push("255.255.255.255", 32, "Data of 255.255.255.255/32");
            Node<String > node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 32, new String[]{"255.255.255.255"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("255.255.255.255"));
            assertTheNode(node, "Data of 255.255.255.255/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push nodes 255.255.255.255/32, 255.255.255.254/32")
        void push_5e5dc0d9_2bb5_4f8c_a151_f7bd4fd6ac38() throws Exception {
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
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 32, new String[]{"255.255.255.255", "255.255.255.254"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("255.255.255.255"));
            assertTheNode(node1, "Data of 255.255.255.255/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("255.255.255.254"));
            assertTheNode(node1, "Data of 255.255.255.254/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push nodes 192.168.1.0/24")
        void push_07855e9e_b7a0_4b8a_a002_bae6fd7c910f() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 1)192.168.1.0/24(d)     |
                +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 24, new String[]{"192.168.1.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push node 192.168.1.0/24, 192.168.2.0/24")
        void push_12c6700c_60ba_4386_85e0_a99502e9a4b4() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 2) 192.168.2.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.168.2.0", 24, "Data of 192.168.2.0/24");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 24, new String[]{"192.168.1.0", "192.168.2.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push node 192.168.0.0/16, 192.168.1.0/24")
        void push_d03d4cb0_6599_475e_9d28_8a8ae833ef0d() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.0.0/16(d)    |
                +-------------------------+
                  |
                +-+-----------------------+
                | 2) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push node 192.168.0.0/16, 192.168.1.0/24, 192.168.2.0/24")
        void push_f392c146_65cb_4ec8_ae4f_4fb8b7ea21d5() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.0.0/16(d)    |
                +-------------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 2) 192.168.1.0/24(d)    | | 3) 192.168.2.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.168.2.0", 24, "Data of 192.168.2.0/24");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0", "192.168.2.0"});
            Node<String> node2 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node2, "Data of 192.168.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push node 192.168.0.0/16, 192.168.1.0/24, 172.16.0.0/16")
        void push_4573ecb2_0f9c_4840_b65a_148b59d14070() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.0.0/16(d)    | | 3) 172.16.0.0/16(d)     |
                +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 2) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0", "172.16.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push node 192.168.1.0/24, 192.168.0.0/16")
        void push_a98d5bbc_9293_4141_8721_cfaf506c7274() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 2) 192.168.0.0/16(d)    |
                +-------------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push node 192.168.1.0/24, 192.168.0.0/16, 192.168.2.0/24")
        void push_df72db48_faa0_4b62_9a01_e3c8e0b7877f() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 2) 192.168.0.0/16(d)    |
                +-------------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 3) 192.168.2.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("192.168.2.0", 24, "Data of 192.168.2.0/24");
            Node <String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0", "192.168.2.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node2, "Data of 192.168.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 192.168.0.0/16, 172.16.0.0/16")
        void push_4f744252_733f_4949_a4ed_5582e9140a00() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 2) 192.168.0.0/16(d)    | | 3) 172.16.0.0/16(d)     |
                +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0", "172.16.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 192.168.0.0/16, 192.0.0.0/8")
        void push_39711a8a_199e_44c5_93de_da5deb14ffec() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 3) 192.0.0.0/8(d)       |
                +-------------------------+
                  |
                +-+-----------------------+
                | 2) 192.168.0.0/16(d)    |
                +-------------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            dict.push("192.0.0.0", 8, "Data of 192.0.0.0/8");
            Node<String> node= (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, "Data of 192.0.0.0/8", 8, 16, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 192.0.0.0/8, 192.168.0.0/16")
        void push_65562f6a_d5e5_4ca4_a572_b11c9243f3ba() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 2) 192.0.0.0/8(d)       |
                +-------------------------+
                  |
                +-+-----------------------+
                | 3) 192.168.0.0/16(d)    |
                +-------------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.0.0.0", 8, "Data of 192.0.0.0/8");
            dict.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, "Data of 192.0.0.0/8", 8, 16, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/16", 16, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 172.16.0.0/16")
        void push_63a59606_1820_4e2d_8422_9fe0ce204b73() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  | Create a glue node        |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 2) 172.16.0.0/16(d)     |
                +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 1) 192.168.1.0/24(d)    |
                +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 16, new String[]{"192.168.0.0", "172.16.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 16, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }

        void assertSetType3(IPDict4J<String> dict) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 16, new String[]{"192.168.0.0", "192.169.0.0", "172.16.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node1, null, 16, 24, new String[]{"192.169.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node1, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 16, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }

        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 192.169.1.0/24, 172.16.0.0/16")
        void push_9dd17911_34a0_4f73_9286_a210671e7283() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  | Create a glue node        | Create a glue node        |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 3)172.16.0.0/16(d)      |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 2) 192.169.1.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.169.1.0", 24, "Data of 192.169.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            assertSetType3(dict);
        }
        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 172.16.0.0/16, 192.169.1.0/24")
        void push_472f3c16_5347_468a_b94e_f9a8cb1991a8() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  | Create a glue node        | Create a glue node        |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 2)172.16.0.0/16(d)      |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 3) 192.169.1.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("192.169.1.0", 24, "Data of 192.169.1.0/24");
            assertSetType3(dict);
        }

        void assertSetType5(IPDict4J<String> dict) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            assertTheNode(node, null, 0, 8, new String[]{"192.0.0.0", "172.0.0.0", "10.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"192.168.0.0", "192.169.0.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, null, 16, 24, new String[]{"192.168.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node2, "Data of 192.169.0.0/16", 16, 24, new String[]{"192.169.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node2, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }

        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 192.168.1.0/24, 172.16.0.0/16, 10.0.0.0/8, 192.169.0.0/16")
        void push_ad185d88_5ffd_4827_b8ce_6db177dce25a() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+---------------------------+
                  | Create a glue node                                    | Create a glue node        |
                +-------------------------+                             +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                             | 172.0.0.0/8(g)          | | 4)10.0.0.0/8(d)         |
                +-+-----------------------+                             +-+-----------------------+ +-+-----------------------+
                  |                                                       |
                  +---------------------------+ Create a glue node        |
                  | Create a glue node        | then update it            |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 5)192.169.0.0/16(d)     | | 3)172.16.0.0/16(d)      |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 2) 192.169.1.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            Node<String> node = (Node<String>) TestUtil.invokeInstanceMethod(dict, "getRootNode", new Class[]{}, null);
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.169.1.0", 24, "Data of 192.169.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("192.169.0.0", 16, "Data of 192.169.0.0/16");
            assertSetType5(dict);
        }

        @Test @DisplayName("should be able to push nodes 192.168.1.0/24, 172.16.0.0/16, 192.169.0.0/16, 192.168.1.0/24, 10.0.0.0/8")
        void push_ebfef8cd_643a_4b58_bf3d_a6a761f3ba3c() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+---------------------------+
                  | Create a glue node                                    | Create a glue node        |
                +-------------------------+                             +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                             | 172.0.0.0/8(g)          | | 4)10.0.0.0/8(d)         |
                +-+-----------------------+                             +-+-----------------------+ +-+-----------------------+
                  |                                                       |
                  +---------------------------+ Create a glue node        |
                  | Create a glue node        | then update it            |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 5)192.169.0.0/16(d)     | | 3)172.16.0.0/16(d)      |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 2) 192.169.1.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("192.169.0.0", 16, "Data of 192.169.0.0/16");
            dict.push("192.169.1.0", 24, "Data of 192.169.1.0/24");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            assertSetType5(dict);
        }

        @Test @DisplayName("should be able to push nodes 10.0.0.0/8, 172.16.0.0/16, 192.168.1.0/24, 192.169.0.0/16, 192.168.1.0/24")
        void push_7d52e584_f03b_4192_a81b_ed8f6a79c9bf() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+---------------------------+
                  | Create a glue node                                    | Create a glue node        |
                +-------------------------+                             +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                             | 172.0.0.0/8(g)          | | 5)10.0.0.0/8(d)         |
                +-+-----------------------+                             +-+-----------------------+ +-+-----------------------+
                  |                                                       |
                  +---------------------------+ Create a data node        |
                  | Create a glue node        | then push a data node     |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 3) 192.169.0.0/16(g)    | | 2)172.16.0.0/16(d)      |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 1) 192.168.1.0/24(d)    | | 4) 192.169.1.0/24(d)    |
                +-------------------------+ +-------------------------+
            */
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("192.169.0.0", 16, "Data of 192.169.0.0/16");
            dict.push("192.169.1.0", 24, "Data of 192.169.1.0/24");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");  /* FIXME: */
            assertSetType5(dict);
        }
        @Test @DisplayName("should remove the glue node with only one glue node under pushed node")
        void push_68d165a5_3f55_4add_838f_ea1c6231eba9() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/27(d)       | | 192.168.1.0/27(d)       |
                +-------------------------+ +-------------------------+
                // > push 192.168.2.0/26 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/26(g)       | | 192.168.1.0/26(g)       | | 192.168.2.0/26(d)       |
                +-+-----------------------+ +-+-----------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/27(d)       | | 192.168.1.0/27(d)       |
                +-------------------------+ +-------------------------+
                // > push 192.168.3.0/25 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+---------------------------+
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/25(g)       | | 192.168.1.0/25(g)       | | 192.168.2.0/25(g)       | | 192.168.3.0/25(d)       |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-------------------------+
                  |                           |                           |
                  | deleted                   | deleted                 +-+-----------------------+
                  | (192.168.0.0/26(g))       | (192.168.1.0/26(g))     | 192.168.2.0/26(d)       |
                  |                           |                         +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/27(d)       | | 192.168.1.0/27(d)       |
                +-------------------------+ +-------------------------+
                // > push 192.168.4.0/24 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+---------------------------+---------------------------+
                  |                           |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/24(g)       | | 192.168.1.0/24(g)       | | 192.168.2.0/24(g)       | | 192.168.3.0/24(g)       | | 192.168.4.0/24(d)       |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                           |                           |                           |
                  | deleted                   | deleted                   | deleted                 +-+-----------------------+
                  | (192.168.0.0/25(g))       | (192.168.1.0/25(g))       | (192.168.2.0/25(g))     | 192.168.3.0/25(d)       |
                  |                           |                           |                         +-------------------------+
                  |                           |                           |
                  |                           |                         +-+-----------------------+
                  |                           |                         | 192.168.2.0/26(d)       |
                  |                           |                         +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/27(d)       | | 192.168.1.0/27(d)       |
                +-------------------------+ +-------------------------+
            */
            dict.push("192.168.0.0", 27, "Data of 192.168.0.0/27");
            dict.push("192.168.1.0", 27, "Data of 192.168.1.0/27");
            // > push 192.168.2.0/26 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("192.168.2.0", 26, "Data of 192.168.2.0/26");
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 26, new String[]{"192.168.0.0", "192.168.1.0", "192.168.2.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 26, 27, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/27", 27, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, null, 26, 27, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/27", 27, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 192.168.3.0/25 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("192.168.3.0", 25, "Data of 192.168.3.0/25");
            root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 25, new String[]{"192.168.0.0", "192.168.1.0", "192.168.2.0", "192.168.3.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 25, 27, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/27", 27, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, null, 25, 27, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/27", 27, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, null, 25, 26, new String[]{"192.168.2.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.3.0"));
            assertTheNode(node1, "Data of 192.168.3.0/25", 25, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 192.168.4.0/24 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("192.168.4.0", 24, "Data of 192.168.4.0/24");
            root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 24, new String[]{"192.168.0.0", "192.168.1.0", "192.168.2.0", "192.168.3.0", "192.168.4.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 24, 27, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/27", 27, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, null, 24, 27, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/27", 27, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, null, 24, 26, new String[]{"192.168.2.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.3.0"));
            assertTheNode(node1, null, 24, 25, new String[]{"192.168.3.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.3.0"));
            assertTheNode(node1, "Data of 192.168.3.0/25", 25, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.4.0"));
            assertTheNode(node1, "Data of 192.168.4.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }


    }

    @Nested
    @DisplayName("rebalanceChildGlueNode")
    class TestRebalanceChildGlueNode {
        Method method = null;
        @BeforeEach
        void beforeEach() throws NoSuchMethodException {
            method = TestUtil.getMethod(IPDict4J.class, "rebalanceChildGlueNode", new Class[]{Node.class, Node.class, int.class});
        }

        @Test @DisplayName("should do nothing if the node has no child nodes")
        void rebalanceChildGlueNode_ad44aa3f_2c5d_40bd_a58a_ea4f11dc769a() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));

            method.invoke(dict, node, root, 0);
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            assertTheNode(
                    root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0")),
                    "Data of 0.0.0.0/0", 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{}
            );
        }
        @Test @DisplayName("should do nothing if tha node has child node that has subnet length 32")
        void rebalanceChildGlueNode_bd5ef6f9_37a4_45bc_b12f_a466dd9ba0d4() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.1", 32, "Data of 192.168.1.1/32");
            dict.push("192.168.1.2", 32, "Data of 192.168.1.2/32");
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            method.invoke(dict, node, root, 0);

            assertTheNode(node, "Data of 0.0.0.0/0", 0, 32, new String[]{"192.168.1.1", "192.168.1.2"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.1"));
            assertTheNode(node1, "Data of 192.168.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.2"));
            assertTheNode(node1, "Data of 192.168.1.2/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should do nothing if one of the node is data node")
        void rebalanceChildGlueNode_83de5bf2_db5f_4221_ae83_c8fc4f3abac0() throws Exception {
             /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           |
                +-------------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          | | 172.0.0.0/8(g)          | | 10.0.0.0/8(d)           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                           |
                  |                         +-+-----------------------+
                  |                         | 172.16.0.0/8(d)         |
                  |                         +-------------------------+
                  |
                +-+-----------------------+
                | 192.168.1.0/24(d)       |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            method.invoke(dict, node, root, 0);

            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0", "172.0.0.0", "10.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, null, 8, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should only delete glue node if the all glue node has subnetmask 31")
        void rebalanceChildGlueNode_ab8331ba_589d_4af0_83d3_d85d9e2f86c1() throws Exception {
             /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           | (deleted)                 |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/31(g)       | | 172.16.1.0/31(g)        | | 192.168.3.0/31(g)       |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                                                       |
                  +---------------------------+                           +---------------------------+
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.1.1/32(d)       | | 192.168.1.0/32(d)       | | 192.168.3.1/32(d)       | | 192.168.3.0/32(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
                > rebalance >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+---------------------------+
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.1.1/32(d)       | | 192.168.1.0/32(d)       | | 192.168.3.1/32(d)       | | 192.168.3.0/32(d)       |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            root.getRefToChildren().put(dict.convertIPStringToBinary("0.0.0.0"), new Node<String>("Data of 0.0.0.0/0", 0, 31));
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            node.getRefToChildren().put(dict.convertIPStringToBinary("0.0.0.0"), new Node<>("Data of 0.0.0.0/0", 0, 31, new HashMap<>()));
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            node.getRefToChildren().put(dict.convertIPStringToBinary("192.168.1.0"), new Node<String>(null, 31, 32));
            node.getRefToChildren().put(dict.convertIPStringToBinary("192.168.3.0"), new Node<String>(null, 31, 32));
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            node1.setChildSubnetMaskLength(32);
            node1.getRefToChildren().put(dict.convertIPStringToBinary("192.168.1.1"), new Node<String>("Data of 192.168.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("192.168.1.0"), new Node<String>("Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED));
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.3.0"));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("192.168.3.1"), new Node<String>("Data of 192.168.3.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("192.168.3.0"), new Node<String>("Data of 192.168.3.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED));

            method.invoke(dict, node, root, dict.convertIPStringToBinary("0.0.0.0"));

            root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));

            assertTheNode(node, "Data of 0.0.0.0/0", 0, 32, new String[]{"192.168.1.0", "192.168.1.1", "192.168.3.0", "192.168.3.1"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.1"));
            assertTheNode(node1, "Data of 192.168.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.3.0"));
            assertTheNode(node1, "Data of 192.168.3.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.3.1"));
            assertTheNode(node1, "Data of 192.168.3.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should only delete glue node and link the nodes under glue nodes with child of child nodes")
        void rebalanceChildGlueNode_bd83fc50_566b_406c_ad78_e051b04bc20b() throws Exception {
             /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | target
                +-------------------------+
                | 172.0.0.0/8(d)          |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           | deleted
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 172.16.0.0/16(g)        | | 172.17.0.0/16(g)        | | 172.18.0.0/16(d)        |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                           |
                  |                           +---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 172.16.1.0/24(d)        | | 172.17.1.0/24(d)        | | 172.17.2.0/24(d)        |
                +-+-----------------------+ +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 172.16.1.1/32(d)        |
                +-------------------------+
                > rebalance >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | target
                +-------------------------+
                | 172.0.0.0/8(d)          |
                +-+-----------------------+
                  |
                  |---------------------------+---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 172.16.1.0/24(d)        | | 172.17.1.0/24(d)        | | 172.17.2.0/24(d)        |
                +-+-----------------------+ +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 172.16.1.1/32(d)        |
                +-------------------------+
            */
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            root.getRefToChildren().put(dict.convertIPStringToBinary("0.0.0.0"), new Node<>("Data of 0.0.0.0/0", 0, 8));
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            node.setChildSubnetMaskLength(8);
            node.getRefToChildren().put(dict.convertIPStringToBinary("172.0.0.0"), new Node<>("Data of 172.0.0.0/8", 8, 16));
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.16.0.0"), new Node<>(null, 16, 24));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.17.0.0"), new Node<>(null, 16, 24));

            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            node2.getRefToChildren().put(dict.convertIPStringToBinary("172.16.1.0"), new Node<String>("Data of 172.16.1.0/24", 24, 32));
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            node2.getRefToChildren().put(dict.convertIPStringToBinary("172.16.1.1"), new Node<String>("Data of 172.16.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED));

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.0.0"));
            node2.getRefToChildren().put(dict.convertIPStringToBinary("172.17.1.0"), new Node<String>("Data of 172.17.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED));
            node2.getRefToChildren().put(dict.convertIPStringToBinary("172.17.2.0"), new Node<String>("Data of 172.17.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED));

            method.invoke(dict, node1, node, dict.convertIPStringToBinary("172.0.0.0"));
            root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));

            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"172.0.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, "Data of 172.0.0.0/8", 8, 24, new String[]{"172.16.1.0", "172.17.1.0", "172.17.2.0"});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            assertTheNode(node2, "Data of 172.16.1.0/24", 24, 32, new String[]{"172.16.1.1"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.1"));
            assertTheNode(node2, "Data of 172.16.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.1.0"));
            assertTheNode(node2, "Data of 172.17.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.1.0"));
            assertTheNode(node2, "Data of 172.17.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.2.0"));
            assertTheNode(node2, "Data of 172.17.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should only delete glue node and link the nodes under glue nodes with creating new glue nodes")
        void rebalanceChildGlueNode_d20ff552_5806_4172_bf9e_13b1568e22a0() throws Exception {
             /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | target
                +-------------------------+
                | 172.0.0.0/8(d)          |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+---------------------------+
                  |                           |                           |                           | (deleted)
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 172.16.0.0/16(g)        | | 172.17.0.0/16(g)        | | 172.18.0.0/16(g)        | | 172.19.0.0/16(d)        |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                           |                           |
                  |                           |                         +-+-----------------------+
                  |                           |                         | 172.18.1.0/24(d)        |
                  |                           |                         +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 172.16.1.0/25(d)        |   |
                +-+-----------------------+   |
                  |                           |
                  |                           +---------------------------+
                  |                           |                           |
                  |                         +-+-----------------------+ +-+-----------------------+
                  |                         | 172.17.1.0/26(d)        | | 172.17.1.64/26(d)       |
                  |                         +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 172.16.1.1/32(d)        |
                +-------------------------+
                > rebalance >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | target
                +-------------------------+
                | 172.0.0.0/8(d)          |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  | new                       | new                       |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 172.16.1.0/24(g)        | | 172.17.1.0/24(g)        | | 172.18.1.0/24(d)        |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                           |
                +-+-----------------------+   |
                | 172.16.1.0/25(d)        |   |
                +-+-----------------------+   |
                  |                           |
                  |                           +---------------------------+
                  |                           |                           |
                  |                         +-+-----------------------+ +-+-----------------------+
                  |                         | 172.17.1.0/26(d)        | | 172.17.1.64/26(d)       |
                  |                         +-------------------------+ +-------------------------+
                  |
                +-+-----------------------+
                | 172.16.1.1/32(d)        |
                +-------------------------+
            */
            Node<String> root = (Node<String>)TestUtil.getInstanceField(dict, "root");
            root.getRefToChildren().put(dict.convertIPStringToBinary("0.0.0.0"), new Node<>("Data of 0.0.0.0/0", 0, 8));
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            node.getRefToChildren().put(dict.convertIPStringToBinary("172.0.0.0"), new Node<String>("Data of 172.0.0.0/8", 8, 16));
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));

            node.getRefToChildren().put(dict.convertIPStringToBinary("172.16.0.0"), new Node<String>(null, 16, 25));
            node.getRefToChildren().put(dict.convertIPStringToBinary("172.17.0.0"), new Node<String>(null, 16, 26));
            node.getRefToChildren().put(dict.convertIPStringToBinary("172.18.0.0"), new Node<String>(null, 16, 24));

            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.16.1.0"), new Node<String>("Data of 172.16.1.0/25", 25, 32));
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.16.1.1"), new Node<String>("Data of 172.16.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED));

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.17.0.0"));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.17.1.0"), new Node<String>("Data of 172.17.1.0/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.17.1.64"), new Node<String>("Data of 172.17.1.64/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED));

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.18.0.0"));
            node1.getRefToChildren().put(dict.convertIPStringToBinary("172.18.1.0"), new Node<String>("Data of 172.18.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED));

            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            method.invoke(dict, node1, node, dict.convertIPStringToBinary("172.0.0.0"));

            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"172.0.0.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node, "Data of 172.0.0.0/8", 8, 24, new String[]{"172.16.1.0", "172.17.1.0", "172.18.1.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            assertTheNode(node1, null, 24, 25, new String[]{"172.16.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            assertTheNode(node1, "Data of 172.16.1.0/25", 25, 32, new String[]{"172.16.1.1"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.1"));
            assertTheNode(node1, "Data of 172.16.1.1/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.17.1.0"));
            assertTheNode(node1, null, 24, 26, new String[]{"172.17.1.0", "172.17.1.64"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.1.0"));
            assertTheNode(node2, "Data of 172.17.1.0/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.1.64"));
            assertTheNode(node2, "Data of 172.17.1.64/26", 26, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.18.1.0"));
            assertTheNode(node1, "Data of 172.18.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
    }

    @Nested
    @DisplayName("delete")
    class TestDelete {

        @Test @DisplayName("should delete a root data node")
        void delete_009cc596_f7b0_4fea_a129_33096672b09c() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");

            String result = dict.delete("0.0.0.0", 0);
            assertEquals("Data of 0.0.0.0/0", result);
            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single root node")
        void delete_7ddd3361_f11e_422c_80e8_378662f1019d() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 255.255.128./17(d)      |
                +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            Node<String> _root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(_root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> _node = _root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));

            dict.delete("10.0.0.0", 8);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete subnet the data that has length of subnetmask 18")
        void delete_7575d270_cedb_4c0b_9172_6349b3f4d488() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 255.255.128.0/17(d)     |
                +-+-----------------------+
                  | delete
                +-+-----------------------+
                | 255.255.192.0/18(d)     |
                +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 255.255.128.0/17(d)     |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("255.255.128.0", 17, "Data of 255.255.128.0/17");
            dict.push("255.255.192.0", 18, "Data of 255.255.192.0/18");

            String result = dict.delete("255.255.192.0", 18);
            assertEquals("Data of 255.255.192.0/18", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 17, new String[]{"255.255.128"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("255.255.128.0"));
            assertTheNode(node, "Data of 255.255.128.0/17", 17, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete middle of data that has length of subnetmask 17")
        void delete_8897fc3f_2010_48e1_b7ef_3b2f0fcf02dd() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  | delete
                +-+-----------------------+
                | 255.255.128./17(d)      |
                +-------------------------+
                  |
                +-+-----------------------+
                | 255.255.192.0/18(d)     |
                +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 255.255.192.0/18(d)     |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("255.255.128.0", 17, "Data of 255.255.128.0/17");
            dict.push("255.255.192.0", 18, "Data of 255.255.192.0/18");

            String result = dict.delete("255.255.128.0", 17);
            assertEquals("Data of 255.255.128.0/17", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 18, new String[]{"255.255.192.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("255.255.192.0"));
            assertTheNode(node, "Data of 255.255.192.0/18", 18, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single node under the glue node that has 2 node")
        void delete_688b91f2_6174_40a0_86c0_f98451553979() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          |
                +-+-----------------------+ +-------------------------+
                                              |
                                              +---------------------------+
                                              |                           | delete
                                            +-+-----------------------+ +-+-----------------------+
                                            | 172.16.0.0/16(d)        | | 172.17.0.0/16(d)        |
                                            +-------------------------+ +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          |
                +-+-----------------------+ +-------------------------+
                                              |
                                            +-+-----------------------+
                                            | 172.16.0.0/16(d)        |
                                            +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("172.0.0.0", 8, "Data of 172.0.0.0/8");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("172.17.0.0", 16, "Data of 172.17.0.0/16");
            String result = dict.delete("172.17.0.0", 16);
            assertEquals("Data of 172.17.0.0/16", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0", "172.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, "Data of 172.0.0.0/8", 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node under glue node that has 1 child node")
        void delete_f3f922f1_ada6_4532_892e_56c0995fe1d7() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          | <- glue node that has 1 child node
                +-+-----------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 10.1.0.0/16(d)          |   |
                +-+-----------------------+   |
                                              | delete
                                            +-+-----------------------+
                                            | 172.16.1.0/24(d)        |
                                            +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 10.0.0.0/8(d)           |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 10.1.0.0/16(d)          |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("10.1.0.0", 16, "Data of 10.1.0.0/16");
            dict.push("172.16.1.0", 24, "Data of 172.16.1.0/24");
            String result = dict.delete("172.16.1.0", 24);
            assertEquals("Data of 172.16.1.0/24", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, 16, new String[]{"10.1.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("10.1.0.0"));
            assertTheNode(node1, "Data of 10.1.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node under 1 data node that has 1 child node")
        void delete_b0d03343_15c3_4ed6_836f_7e3e68619e69() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          |
                +-+-----------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.1.0.0/16(d)          | | 172.16.0.0/16(d)        | <- 1 data node that has 1 child node
                +-+-----------------------+ +-------------------------+
                                              | delete
                                            +-+-----------------------+
                                            | 172.16.1.0/24(d)        |
                                            +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          |
                +-+-----------------------+ +-+-----------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.1.0.0/16(d)          | | 172.16.0.0/16(d)        |
                +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("10.1.0.0", 16, "Data of 10.1.0.0/16");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("172.16.1.0", 24, "Data of 172.16.1.0/24");
            String result = dict.delete("172.16.1.0", 24);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0", "172.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, 16, new String[]{"10.1.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("10.1.0.0"));
            assertTheNode(node1, "Data of 10.1.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node under 1 glue node that has 1 child node")
        void delete_0985d4fe_fe29_4fc3_a861_b6ab818467e6() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("10.1.0.0", 16, "Data of 10.1.0.0/16");
            dict.push("172.0.0.0", 8, "Data of 172.0.0.0/8");
            dict.push("172.17.0.0", 16, "Data of 172.17.0.0/16");
            dict.push("172.16.1.0", 24, "Data of 172.16.1.0/24");
            String result = dict.delete("172.16.1.0", 24);
            assertEquals("Data of 172.16.1.0/24", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0", "172.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, 16, new String[]{"10.1.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("10.1.0.0"));
            assertTheNode(node1, "Data of 10.1.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, "Data of 172.0.0.0/8", 8, 16, new String[]{"172.17.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.17.0.0"));
            assertTheNode(node1, "Data of 172.17.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node under 1 glue node that has 1 child node")
        void delete_fb5bde6f_3ff9_4616_adbd_e08be6917bbd() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(d)          |
                +-+-----------------------+ +-------------------------+
                  |                           | delete
                +-+-----------------------+ +-+-----------------------+
                | 10.1.0.0/16(d)          | | 172.16.0.0/16(d)        |
                +-+-----------------------+ +-------------------------+
                                              |
                                              +---------------------------+
                                              |                           |
                                            +-+-----------------------+ +-+-----------------------+
                                            | 172.16.1.0/24(d)        | | 172.16.2.0/24(d)        |
                                            +-------------------------+ +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(d)          |
                +-+-----------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 10.1.0.0/16(d)          |   |
                +-+-----------------------+   |
                                              |
                                              +---------------------------+
                                              |                           |
                                            +-+-----------------------+ +-+-----------------------+
                                            | 172.16.1.0/24(d)        | | 172.16.2.0/24(d)        |
                                            +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("10.1.0.0", 16, "Data of 10.1.0.0/16");
            dict.push("172.0.0.0", 8, "Data of 172.0.0.0/8");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("172.16.1.0", 24, "Data of 172.16.1.0/24");
            dict.push("172.16.2.0", 24, "Data of 172.16.2.0/24");
            String result = dict.delete("172.16.0.0", 16);
            assertEquals("Data of 172.16.0.0/16", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0", "172.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, 16, new String[]{"10.1.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("10.1.0.0"));
            assertTheNode(node1, "Data of 10.1.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, "Data of 172.0.0.0/8", 8, 24, new String[]{"172.16.1.0", "172.16.2.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            assertTheNode(node2, "Data of 172.16.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.2.0"));
            assertTheNode(node2, "Data of 172.16.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node under 1 glue node that has 1 child node")
        void delete_868209d5_6582_4d1d_8820_7476a8772ac2() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(d)          |
                +-+-----------------------+ +-------------------------+
                  |                           | delete
                +-+-----------------------+ +-+-----------------------+
                | 10.1.0.0/16(d)          | | 172.16.0.0/16(d)        |
                +-+-----------------------+ +-------------------------+
                                              |
                                              +---------------------------+
                                              |                           |
                                            +-+-----------------------+ +-+-----------------------+
                                            | 172.16.1.0/24(d)        | | 172.16.2.0/24(d)        |
                                            +-------------------------+ +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(d)          |
                +-+-----------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 10.1.0.0/16(d)          |   |
                +-+-----------------------+   |
                                              |
                                              +---------------------------+
                                              |                           |
                                            +-+-----------------------+ +-+-----------------------+
                                            | 172.16.1.0/24(d)        | | 172.16.2.0/24(d)        |
                                            +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("10.1.0.0", 16, "Data of 10.1.0.0/16");
            dict.push("172.0.0.0", 8, "Data of 172.0.0.0/8");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("172.16.1.0", 24, "Data of 172.16.1.0/24");
            dict.push("172.16.2.0", 24, "Data of 172.16.2.0/24");
            String result = dict.delete("172.16.0.0", 16);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0", "172.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, 16, new String[]{"10.1.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("10.1.0.0"));
            assertTheNode(node1, "Data of 10.1.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, "Data of 172.0.0.0/8", 8, 24, new String[]{"172.16.1.0", "172.16.2.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.1.0"));
            assertTheNode(node2, "Data of 172.16.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.2.0"));
            assertTheNode(node2, "Data of 172.16.2.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node with parent glue node")
        void delete_6385115d_55a0_4e4d_ac1a_e616b8e60e45() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          |
                +-+-----------------------+ +-------------------------+
                                              | delete
                                            +-+-----------------------+
                                            | 172.16.0.0/16(d)        |
                                            +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 10.0.0.0/8(d)           |
                +-+-----------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            String result = dict.delete("172.16.0.0", 16);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"10.0.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
        @Test @DisplayName("should delete a single data node with rebalancing glue node")
        void delete_9ade7c89_a5f5_4193_a80f_b4dc3b920842() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  | delete                    |
                +-+-----------------------+ +-+-----------------------+
                | 10.0.0.0/8(d)           | | 172.0.0.0/8(g)          |
                +-------------------------+ +-------------------------+
                                              |
                                            +-+-----------------------+
                                            | 172.16.0.0/16(d)        |
                                            +-------------------------+
                > delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                +-+-----------------------+
                | 172.16.0.0/16(d)        |
                +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            String result = dict.delete("10.0.0.0", 8);
            assertEquals("Data of 10.0.0.0/8", result);

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"172.16.0.0"});
            node = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
    }

    @Nested
    @DisplayName("find")
    class TestFind {
        @Test @DisplayName("should return root node if existed")
        void find_c6b6ee83_565c_4216_9728_19ab8b75351f() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            assertEquals("Data of 0.0.0.0/0", dict.find("0.0.0.0"));
        }
        @Test @DisplayName("should return undefined if no data has been registered")
        void find_5741f4e5_e448_4744_974f_c9c31eb1327b() throws Exception {
            assertEquals(null, dict.find("0.0.0.0"));
            assertEquals(null, dict.find("192.168.1.10"));
            assertEquals(null, dict.find("172.16.0.1"));
        }
        @Test @DisplayName("should return data if data has been registered in key 0.0.0.0/0")
        void find_b3956a19_6420_41bf_8852_4578327dca86() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            assertEquals("Data of 0.0.0.0/0", dict.find("0.0.0.0"));
            assertEquals("Data of 0.0.0.0/0", dict.find("10.0.0.1"));
            assertEquals("Data of 0.0.0.0/0", dict.find("172.16.0.1"));
            assertEquals("Data of 0.0.0.0/0", dict.find("192.168.1.1"));
        }
        @Test @DisplayName("should return data for 0.0.0.0/0, 192.168.1.0/24")
        void find_d9e79d2f_0fa8_400f_95f7_82d2381b600c() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");

            assertEquals("Data of 0.0.0.0/0", dict.find("0.0.0.0"));
            assertEquals("Data of 0.0.0.0/0", dict.find("10.0.0.1"));
            assertEquals("Data of 0.0.0.0/0", dict.find("172.16.0.1"));
            assertEquals("Data of 192.168.1.0/24", dict.find("192.168.1.1"));
        }
        @Test @DisplayName("should return data for 0.0.0.0/0, 10.0.0.0/8, 172.16.0.0/16, 192.168.1.0/24")
        void find_44563f98_0a3e_409b_8631_b427b28c052b() throws Exception {
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.1", 8, "Data of 10.0.0.0/8");
            dict.push("172.16.0.1", 16, "Data of 172.16.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            assertEquals("Data of 0.0.0.0/0", dict.find("0.0.0.0"));
            assertEquals("Data of 10.0.0.0/8", dict.find("10.0.0.1"));
            assertEquals("Data of 172.16.0.0/16", dict.find("172.16.0.1"));
            assertEquals("Data of 192.168.1.0/24", dict.find("192.168.1.1"));
        }
        @Test @DisplayName("should return undefined(data of 0.0.0.0/0) if data which appropriate is not found")
        void find_6cacb875_6b95_4eea_8e71_6cd3aab5b989() throws Exception {
            dict.push("10.0.0.1", 8, "Data of 10.0.0.0/8");
            dict.push("172.16.0.1", 16, "Data of 172.16.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            assertEquals(null, dict.find("0.0.0.0"));
            assertEquals(null, dict.find("11.0.0.1"));
            assertEquals(null, dict.find("172.17.0.1"));
            assertEquals(null, dict.find("192.168.2.1"));
        }
    }
    @Nested
    @DisplayName("Integrated test")
    class TestIntegrated {
        @Test @DisplayName("should pass integration test 01")
        void integrated_4d31ae37_f3c0_4dbd_aa57_4eda13c0f60c() throws Exception {
              /*
                > delete 0.0.0.0/0 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                 1) delete
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +-----------------------------------------------------------------------------------+---------------------------+
                  |                                                                                   |                           |
                +-------------------------+                                                         +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                                                         | 172.0.0.0/8(g)          | | 10.0.0.0/8(d)           |
                +-+-----------------------+                                                         +-+-----------------------+ +-------------------------+
                  |                                                                                   |
                  +---------------------------+---------------------------+                           |
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 192.170.0.0/16(d)       | | 172.16.0.0/16(d)        |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 192.168.0.0/17(d)       |   |
                +-+-----------------------+   |
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > delete 10.0.0.0/8 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +-----------------------------------------------------------------------------------+---------------------------+
                  |                                                                                   |                           | 2) delete
                +-------------------------+                                                         +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                                                         | 172.0.0.0/8(g)          | | 10.0.0.0/8(d)           |
                +-+-----------------------+                                                         +-+-----------------------+ +-+-----------------------+
                  |                                                                                   |
                  +---------------------------+---------------------------+                           |
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 192.170.0.0/16(d)       | | 172.16.0.0/16(d)        |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 192.168.0.0/17(d)       |   |
                +-+-----------------------+   |
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > delete 10.0.0.0/8 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+---------------------------+
                  |                           |                           |                           | 3) delete
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 192.170.0.0/16(d)       | | 172.16.0.0/16(d)        |
                +-+-----------------------+ +-+-----------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 192.168.0.0/17(d)       |   |
                +-+-----------------------+   |
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > delete 192.170.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           | 4) delete
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 192.170.0.0/16(d)       |
                +-+-----------------------+ +-+-----------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 192.168.0.0/17(d)       |   |
                +-+-----------------------+   |
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > push 0.0.0.0/0 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/17(d)       | | 192.169.0.0/17(g)       |
                +-+-----------------------+ +-+-----------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > push 10.0.0.0/8 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-------------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          | | 10.0.0.0/8(d)           |
                +-+-----------------------+ +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/17(d)       | | 192.169.0.0/17(g)       |
                +-+-----------------------+ +-+-----------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > push 172.16.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+---------------------------+
                  |                                                       |                           |
                +-------------------------+                             +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                             | 172.0.0.0/8(g)          | | 10.0.0.0/8(d)           |
                +-+-----------------------+                             +-+-----------------------+ +-------------------------+
                  |                                                       |
                  +---------------------------+                           |
                  |                           |                           |
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/17(d)       | | 192.169.0.0/17(g)       | | 172.16.0.0/16(d)        |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
                > push 192.170.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +-----------------------------------------------------------------------------------+---------------------------+
                  |                                                                                   |                           |
                +-------------------------+                                                         +-+-----------------------+ +-+-----------------------+
                | 192.0.0.0/8(g)          |                                                         | 172.0.0.0/8(g)          | | 10.0.0.0/8(d)           |
                +-+-----------------------+                                                         +-+-----------------------+ +-------------------------+
                  |                                                                                   |
                  +---------------------------+---------------------------+                           |
                  |                           |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.0.0/16(g)       | | 192.169.0.0/16(g)       | | 192.170.0.0/16(d)       | | 172.16.0.0/16(d)        |
                +-------------------------+ +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+   |
                | 192.168.0.0/17(d)       |   | (192.169.0.0/17(g) should be deleted)
                +-+-----------------------+   |
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(d)       | | 192.169.1.0/24(d)       |
                +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            dict.push("192.170.0.0", 16, "Data of 192.170.0.0/16");
            dict.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
            dict.push("192.169.1.0", 24, "Data of 192.169.1.0/24");
            dict.push("192.168.0.0", 17, "Data of 192.168.0.0/17");

            // > delete 0.0.0.0/0 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            assertEquals("Data of 0.0.0.0/0", dict.delete("0.0.0.0", 0));
            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 8, new String[]{"192.0.0.0", "172.0.0.0", "10.0.0.0"});
            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"192.168.0.0", "192.169.0.0", "192.170.0.0"});
            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, null, 16, 17, new String[]{"192.168.0.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            assertEquals(null, dict.find("0.0.0.0"));

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node2, null, 16, 24, new String[]{"192.169.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node2, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.170.0.0"));
            assertTheNode(node2, "Data of 192.170.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > delete 10.0.0.0/8 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            assertEquals("Data of 10.0.0.0/8", dict.delete("10.0.0.0", 8));
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 16, new String[]{"192.168.0.0", "192.169.0.0", "192.170.0.0", "172.16.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 16, 17, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24",24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node1, null, 16, 24, new String[]{"192.169.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node1, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.170.0.0"));
            assertTheNode(node1, "Data of 192.170.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > delete 10.0.0.0/8 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            assertEquals("Data of 172.16.0.0/16", dict.delete("172.16.0.0", 16));
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 16, new String[]{"192.168.0.0", "192.169.0.0", "192.170.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 16, 17, new String[]{"192.168.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node1, null, 16, 24, new String[]{"192.169.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node1, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > delete 192.170.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            assertEquals("Data of 192.170.0.0/16", dict.delete("192.170.0.0", 16));
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, null, 0, 17, new String[]{"192.168.0.0", "192.169.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.169.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node1, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 0.0.0.0/0 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 17, new String[]{"192.168.0.0", "192.169.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node1, null, 17, 24, new String[]{"192.169.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node1, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 10.0.0.0/8 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("10.0.0.0", 8, "Data of 10.0.0.0/8");
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0", "10.0.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, null, 8, 17, new String[]{"192.168.0.0", "192.169.0.0"});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node2, null, 17, 24, new String[]{"192.169.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node2, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 172.16.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0", "172.0.0.0", "10.0.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, null, 8, 17, new String[]{"192.168.0.0", "192.169.0.0"});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node2, null, 17, 24, new String[]{"192.169.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node2, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

//            // > push 192.170.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("192.170.0.0", 16, "Data of 192.170.0.0/16");
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 8, new String[]{"192.0.0.0", "172.0.0.0", "10.0.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"192.168.0.0", "192.169.0.0", "192.170.0.0"});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, null, 16, 17, new String[]{"192.168.0.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node2, "Data of 192.168.0.0/17", 17, 24, new String[]{"192.168.1.0"});
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.169.0.0"));
            assertTheNode(node2, null, 16, 24, new String[]{"192.169.1.0"});  // TODO:
            node2 = node2.getRefToChildren().get(dict.convertIPStringToBinary("192.169.1.0"));
            assertTheNode(node2, "Data of 192.169.1.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.170.0.0"));
            assertTheNode(node2, "Data of 192.170.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.0.0.0"));
            assertTheNode(node1, null, 8, 16, new String[]{"172.16.0.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("10.0.0.0"));
            assertTheNode(node1, "Data of 10.0.0.0/8", 8, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }

        @Test @DisplayName("should pass integration test 02")
        void integrated_acdb0774_10f3_449e_a43d_09b76fd9fa12() throws Exception {
            /*
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(g)       | | 192.168.2.0/24(g)       | | 172.16.8.0/24(d)        |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/32(d)       | | 192.168.2.0/32(d)       |
                +-------------------------+ +-------------------------+
                // > push 172.16.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+
                  |                                                       |
                +-+-----------------------+                             +-+-----------------------+
                | 192.168.0.0/16(g)       |                             | 172.16.0.0/16(d)        |
                +-+-----------------------+                             +-------------------------+
                  |                                                       |
                  |                                                     +-+-----------------------+
                  |                                                     | 172.16.8.0/24(d)        |
                  +---------------------------+                         +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/32(d)       | | 192.168.2.0/32(d)       |
                +-------------------------+ +-------------------------+
                // > delete 172.16.8.0/24 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +-------------------------------------------------------+
                  |                                                       |
                +-+-----------------------+                             +-+-----------------------+
                | 192.168.0.0/16(g)       |                             | 172.16.0.0/16(d)        |
                +-+-----------------------+                             +-------------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/32(d)       | | 192.168.2.0/32(d)       |
                +-------------------------+ +-------------------------+
                // > delete 172.16.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(d)            |
                +-+-----------------------+
                  |
                  +---------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/32(d)       | | 192.168.2.0/32(d)       |
                +-------------------------+ +-------------------------+
                // > push 172.16.7.0/24 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                +-------------------------+
                | 0.0.0.0/0(g)            |
                +-+-----------------------+
                  |
                  +---------------------------+---------------------------+
                  |                           |                           |
                +-+-----------------------+ +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/24(g)       | | 192.168.2.0/24(g)       | | 172.16.7.0/24(d)        |
                +-------------------------+ +-------------------------+ +-------------------------+
                  |                           |
                +-+-----------------------+ +-+-----------------------+
                | 192.168.1.0/32(d)       | | 192.168.2.0/32(d)       |
                +-------------------------+ +-------------------------+
            */
            dict.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
            dict.push("192.168.1.0", 32, "Data of 192.18.1.0/32");
            dict.push("192.168.2.0", 32, "Data of 192.18.2.0/32");
            dict.push("172.16.8.0", 24, "Data of 172.16.8.0/24");

            Node<String> root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            Node<String> node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 24, new String[]{"192.168.1.0", "192.168.2.0", "172.16.8.0"});

            Node<String> node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.8.0"));
            assertTheNode(node1, "Data of 172.16.8.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, null, 24, 32, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, null, 24, 32, new String[]{"192.168.2.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 172.16.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("172.16.0.0", 16, "Data of 172.16.0.0/16");

            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0", "172.16.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 16, 32, new String[]{"192.168.1.0", "192.168.2.0"});

            Node<String> node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node2, "Data of 192.168.2.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, 24, new String[]{"172.16.8.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("172.16.8.0"));
            assertTheNode(node1, "Data of 172.16.8.0/24", 24, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > delete 172.16.8.0/24 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            // TODO:
            assertEquals("Data of 172.16.8.0/24", dict.delete("172.16.8.0", 24));
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 16, new String[]{"192.168.0.0", "172.16.0.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.0.0"));
            assertTheNode(node1, null, 16, 32, new String[]{"192.168.1.0", "192.168.2.0"});

            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node2, "Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node2 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node2, "Data of 192.168.2.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("172.16.0.0"));
            assertTheNode(node1, "Data of 172.16.0.0/16", 16, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > delete 172.16.0.0/16 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            assertEquals("Data of 172.16.0.0/16", dict.delete("172.16.0.0", 16));
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 32, new String[]{"192.168.1.0", "192.168.2.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});

            // > push 172.16.7.0/24 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            dict.push("172.16.7.0", 24, "Data of 172.16.7.0/24");
            root = (Node<String>) TestUtil.getInstanceField(dict, "root");
            assertTheNode(root, null, SUBNETMASK_LENGTH_IS_UNDEFINED, 0, new String[]{"0.0.0.0"});
            node = root.getRefToChildren().get(dict.convertIPStringToBinary("0.0.0.0"));
            assertTheNode(node, "Data of 0.0.0.0/0", 0, 24, new String[]{"192.168.1.0", "192.168.2.0", "172.16.7.0"});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, null, 24, 32, new String[]{"192.168.1.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.1.0"));
            assertTheNode(node1, "Data of 192.168.1.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
            node1 = node.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, null, 24, 32, new String[]{"192.168.2.0"});
            node1 = node1.getRefToChildren().get(dict.convertIPStringToBinary("192.168.2.0"));
            assertTheNode(node1, "Data of 192.168.2.0/32", 32, SUBNETMASK_LENGTH_IS_UNDEFINED, new String[]{});
        }
    }
}
