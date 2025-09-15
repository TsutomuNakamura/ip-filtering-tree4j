# ip-filtering-tree4j
ip-filtering-tree4j is high performance on-memory database with key of ip address written in Java.
Due to benefits of applying tree algorithm, there are little lag even if a lots of record were registered in the db as long as the permit the space of memory. 

It is an another version of the [ip-filtering-tree](https://github.com/TsutomuNakamura/ip-filtering-tree).

# Getting started
This program requires Java 8 or greater and mvn, so please install it your environment first.

## Building in your local
If you can not download it from maven centrarl repository, you can build it with clone this repository and run mvn command.

```
$ git clone https://github.com/TsutomuNakamura/ip-filtering-tree4j.git
$ cd ip-filtering-tree4j
$ export JAVA_HOME=$(readlink -f $(which java))
$ mvn package
-> *.jar file will be created in ./target/ip-filtering-tree4j-x.x.x.jar
```

## Use maven central repository
* ~/.m2/settings.xml
```
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>your-sonatype-username</username>
      <password>your-sonatype-password</password>
    </server>
  </servers>
</settings>
```

```
$ mvn clean deploy
```

## Usage
Basic usage of ip-filtering-tree4j is to call push, find and delete functions after import this module. Examples are like below.

```Java
IPFilteringTree4J<String> db = new IPFilteringTree4J<>();
```

* push
```Java
// push(<ipaddr>, <subnet mask length>, <data>);
db.push("192.168.1.0", 24, "Data of 192.168.1.0/24");
db.push("192.168.2.0", 24, "Data of 192.168.2.0/24");
db.push("192.168.0.0", 16, "Data of 192.168.0.0/16");
db.push("0.0.0.0", 0, "Data of 0.0.0.0/0");
```

* find
```Java
db.find("192.168.1.0");    // -> Data of 192.168.1.0/24
db.find("192.168.2.100");    // -> Data of 192.168.2.0/24
db.find("192.168.101.32");    // -> Data of 192.168.0.0/16
db.find("10.1.0.23");    // -> Data of 0.0.0.0/0
```

* delete
```Java
db.delete("192.168.1.0", 24);    // -> Data of 192.168.1.0/24
db.find("192.168.1.0");  // -> data of 192.168.0.0/16
db.delete("192.168.0.0", 16);    // -> Data of 192.168.0.0/16
db.find("192.168.1.0");  // -> Data of 0.0.0.0/0
```

# Illustration of processing
It is an another version of the [ip-filtering-tree](https://github.com/TsutomuNakamura/ip-filtering-tree), so please see [here](https://github.com/TsutomuNakamura/ip-filtering-tree#illustration-of-processing).

## TODO
* Apply IPv6 features
* Apply cache features
* Implement iterable functions

## License
This software is released under the MIT License, see LICENSE.txt.
