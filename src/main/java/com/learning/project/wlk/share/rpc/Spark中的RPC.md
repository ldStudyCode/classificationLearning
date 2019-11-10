# Spark中的RPC

## Spark为什么需要RPC
Spark作为一个开源的分布式内存计算引擎，"分布式"三个字就注定了要使用rpc或类似的机制来统一消息通讯。

因为分布式意味着有多台机器来提供统一的服务，而统一的服务在逻辑高层是不会考虑网络通讯、数据交互的细节的，
需要将这些琐碎的细节在底层封装起来。一个十分适合的方式就是用rpc机制进行封装（当然也有其他的方式，如使用RESTFul）。
通过使用RPC机制，Spark集群内部的各个节点，无需考虑与其他节点的网络交互细节、无需考虑其他节点是否在同一个主机上，只用
通过RPC环境进行数据、消息传递即可，高效、便捷。


## Spark中哪里有RPC的身影
主要的看两个类：管理集群资源调度的Master、负责具体任务计算的Worker、

### 网络底层：spark-network-common

主要是通过netty，建立起底层的网络通讯
encoder、decoder、channelHandler等
重点是channelHandler，是和RPC机制建立联系的关键点




## Spark的rpc封装

RpcEnv：

