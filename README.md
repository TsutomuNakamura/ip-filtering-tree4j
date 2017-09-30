# ip-filtering-tree4j
ip-filtering-tree4j is high performance on-memory database with key of ip address written in Java.
Due to benefits of applying tree algorithm, there are little lag even if a lots of record were registered in the db as long as the permit the space of memory. 

It is an another version of the [ip-filtering-tree](https://github.com/TsutomuNakamura/ip-filtering-tree).

# Getting started
This program requires Java 8 or greater, so please install it your environment first.

## Installing
TODO: 

## Usage
The basic usage of ip-filtering-tree4j is to call push, find and delete functions after import this module. Examples are like below.

```
IPFilteringTree4J<String> db = new IPFilteringTree4J<>();
```

* push
```
// push(<ipaddr>, <subnet mask length>, <data>);
db.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
db.push("192.168.2.0", 24, "Data of 192.168.2.0/24");
db.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
db.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
```

* find
```
db.find("192.168.1.0");    // -> Data of 192.168.1.0/24
db.find("192.168.2.100");    // -> Data of 192.168.2.0/24
db.find("192.168.101.32");    // -> Data of 192.168.0.0/16
db.find("10.1.0.23");    // -> Data of 0.0.0.0/0
```

* delete
```
db.delete("192.168.1.0", 24);    // -> Data of 192.168.1.0/24
db.find("192.168.1.0");  // -> data of 192.168.0.0/16
db.delete("192.168.0.0", 16);    // -> Data of 192.168.0.0/16
db.find("192.168.1.0");  // -> Data of 0.0.0.0/0
```
