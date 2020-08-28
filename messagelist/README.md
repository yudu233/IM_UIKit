# 消息列表



聊天消息列表，可展示多类型消息类型，支持自定义扩展

### 添加依赖

```
test
```

### 使用方式

#### 1. 布局文件UI展示

```xml
    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/swipeRefreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#10000000"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```



#### 2.构造Adapter

```java
MsgAdapter msgAdapter = new MsgAdapter(data, container.activity);
msgAdapter.setUpFetchEnable(true);
mRecyclerView.setAdapter(msgAdapter);
```



#### 3. 注册消息ViewHolder

`MsgViewHolderFactory` 内部默认添加了文本/图片/视频消息类型。

外部添加其他类型消息Holder：

```java
/**
* 注册消息ViewHolder
*
* @param messageType 消息类型
* @param viewHolder  消息ViewHolder
*/
MsgViewHolderFactory.register(MessageType.location, MsgViewHolderLocation.class);
```



#### 4.构造实体类

通过实现IMessage、IUser接口，构造消息体和用户信息实体类。



#### 5.消息数据管理

##### 新增消息：

```java
//添加单条消息
msgAdapter.addMessage(MESSAGE message);

//添加多条消息
msgAdapter.addMessages(List<MESSAGE> messages);
```

##### 删除消息：

```java
//根据消息删除单条消息
msgAdapter.deleteMessage(MESSAGE message);

//根据消息id删除单条消息
msgAdapter.deleteMsgById(String msgId);

//根据消息体删除多条消息
msgAdapter.deleteMessages(List<MESSAGE> messages);

//根据消息id删除多条消息
msgAdapter.deleteMsgById(String[] msgIds);
```

##### 更新消息：

```java
msgAdapter.updateMessage(int position, MESSAGE message);
```

##### 清空消息：

```java
msgAdapter.clearMessages();
```