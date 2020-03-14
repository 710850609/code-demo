# redis 集群搭建
目标： 搭建 3主3从的集群
### 1 软件所在目录
```bash
cd ~/software/redis-cluster
```

### 2 下载redis并编译
```bash
wget http://download.redis.io/releases/redis-5.0.5.tar.gz
tar -xzvf redis-5.0.5.tar.gz
cd redis-5.0.5
make
```
### 3 复制常用redis工具
```bash
cp ~/software/redis-cluster/redis-5.0.5/redis-cli ~/software/redis-cluster/
cp ~/software/redis-cluster/redis-5.0.5/redis-server ~/software/redis-cluster/
```

### 4 复制默认配置文件，并修改成对应实例配置
复制默认配置文件，变成多个实例运行配置，每个配置带上对应的端口，redis-{端口}.conf
这里使用7001-7006端口
```bash
cp ~/software/redis-cluster/redis-5.0.5/redis.conf ~/software/redis-cluster/redis-7001.conf
cp ~/software/redis-cluster/redis-5.0.5/redis.conf ~/software/redis-cluster/redis-7002.conf
cp ~/software/redis-cluster/redis-5.0.5/redis.conf ~/software/redis-cluster/redis-7003.conf
cp ~/software/redis-cluster/redis-5.0.5/redis.conf ~/software/redis-cluster/redis-7004.conf
cp ~/software/redis-cluster/redis-5.0.5/redis.conf ~/software/redis-cluster/redis-7005.conf
cp ~/software/redis-cluster/redis-5.0.5/redis.conf ~/software/redis-cluster/redis-7006.conf
```
配置参数修改，每个配置需要修改如下配置
```bash
bind {ip}  # 注释掉这行，放行所有ip请求
protected-mode no ## 保护模式设置关闭
port {端口}  # 设置实例运行端口
daemonize yes  # 设置允许后台运行 
pidfile /var/run/redis_{端口}.pid  # 设置pid保存文件，不同实例带上对应端口
cluster-config-file nodes-{端口}.conf  # 节点配置，不同节点带上对应端口
```
### 5 创建实例启动/关闭脚本
start.sh
```bash
# 启动所有节点服务 
cd /root/software/redis-cluster/

nohup ./redis-server ./redis-7001.conf &

nohup ./redis-server ./redis-7002.conf &

nohup ./redis-server ./redis-7003.conf &
```
stop.sh
```bash
# 停止所有redis进程
ps -ef | grep redis | awk '{print $2}' | xargs kill -9
```
脚本授权
```bash
sudo chmod +x st*
```
### 6 生成redis-cluster
启动所有redis实例
```bash
./start.sh
```
关联生成集群（如果需要外网访问，命令中的ip必须是外网ip。如果在阿里云等第三方服务器上，注意网络安全策略开通所有 （1000+实例端口）端口，如有7001端口的实例，必须开通17001端口）
```bash
./redis-cli --cluster create {ip}:7001 {ip}:7002 {ip}:7003 {ip}:7004 {ip}:7005 {ip}:7006 --cluster-replicas 1:1
```

### 软件目录结构
~/software/redis-cluster <br/>
|-- redis-5.0.5.tar.gz <br/>
|-- redis-5.0.5 <br/>
|-- redis-7001.conf <br/>
|-- redis-7002.conf <br/>
|-- redis-7003.conf <br/>
|-- redis-7004.conf <br/>
|-- redis-7005.conf <br/>
|-- redis-7006.conf <br/>
|-- redis-cli <br/>
|-- redis-server <br/>
|-- start.sh <br/>
|-- stop.sh <br/>