export DISPLAY=localhost:0.0
java -Djava.awt.headless=true -Xmx800m -cp Data/lib/netty-3.10.5.Final/jar/netty-3.10.5.Final.jar:bin:deps/poi.jar:deps/mysql.jar:deps/mina.jar:deps/slf4j.jar:deps/slf4j-nop.jar:deps/jython.jar:log4j-1.2.15.jar: server.Server
