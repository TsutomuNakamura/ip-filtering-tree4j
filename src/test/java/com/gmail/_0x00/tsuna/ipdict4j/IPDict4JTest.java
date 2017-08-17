package com.gmail._0x00.tsuna.ipdict4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Unit test for IPDict4J.
 */
public class IPDict4JTest
{
    @Test
    public void hoge()
    {
        assertTrue( true );
    }

    @Test
    public void convertIPv4StringToBinary() {
        IPDict4J<String> i4j = new IPDict4J<String>();
        assertEquals(0, i4j.convertIPv4StringToBinary("0.0.0.0"));
        assertEquals(-1, i4j.convertIPv4StringToBinary("255.255.255.255"));
        assertEquals(-256, i4j.convertIPv4StringToBinary("255.255.255.0"));
        assertEquals(-65536, i4j.convertIPv4StringToBinary("255.255.0.0"));
        assertEquals(-16777216, i4j.convertIPv4StringToBinary("255.0.0.0"));
        assertEquals(-1062731520, i4j.convertIPv4StringToBinary("192.168.1.0"));
        assertEquals(-1062731519, i4j.convertIPv4StringToBinary("192.168.1.1"));
        assertEquals(-1408237568, i4j.convertIPv4StringToBinary("172.16.0.0"));
        assertEquals(167772160, i4j.convertIPv4StringToBinary("10.0.0.0"));
    }
}
