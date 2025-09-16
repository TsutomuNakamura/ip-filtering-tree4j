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
$ readlink -f $(which java)
/usr/lib/jvm/java-21-openjdk/bin/java
$ export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
$ mvn package
-> *.jar file will be created in ./target/ip-filtering-tree4j-x.x.x.jar
```

## Use maven central repository
You should create your GOG key first if you do not have it yet.
Then upload it to a public key server like `keyserver.ubuntu.com`.

* Generate your GPG key
```
$ gpg --gen-key
> # Follow the instructions
$ gpg --list-keys
$ gpg --list-secret-keys
[keyboxd]
---------
sec   ed25519 YYYY-MM-DD [SC] [expires: YYYY-MM-DD]
      01234567890ABCDEF1234567890ABCDEF1234567
uid           [ultimate] Name of the key <your-email@example.com>
ssb   cv25519 YYYY-MM-DD [E] [expires: YYYY-MM-DD]

$ gpg --keyserver keyserver.ubuntu.com --send-keys 01234567890ABCDEF1234567890ABCDEF1234567
```

Next, create your user token from [https://central.sonatype.com/](https://central.sonatype.com/).
Then set your sonatype username, password and GPG keys in `~/.m2/settings.xml` file like below.

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

  <profiles>
    <profile>
      <id>gpg</id>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <gpg.passphrase>your-gpg-passphrase</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
  
  <activeProfiles>
    <activeProfile>gpg</activeProfile>
  </activeProfiles>
</settings>
```

Then deploy this module to maven central repository with `mvn deploy`.

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
