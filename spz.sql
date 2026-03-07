# 删除已有数据库
drop database if exists spz;
# 创建数据库
create database spz;
# 设置字符集
alter database spz character set 'UTF8';

# 切换数据库
use spz;

# 删除已有的表
drop table if exists address;
drop table if exists message_user;
drop table if exists entrust;
drop table if exists scrap_trade_detail;
drop table if exists scrap_trade;
drop table if exists scrap;
drop table if exists second_hand;
drop table if exists trends;
drop table if exists relationship;
drop table if exists user;
drop table if exists scrap_type;
drop table if exists message_trade;
drop table if exists message_scrap_trade;
drop table if exists second_hand_item;
drop table if exists second_hand_trade;
drop table if exists second_hand_trade_user;
drop table if exists tag_group;
drop table if exists tag;
drop table if exists tag_tag_group;
drop table if exists second_hand_item_image;
drop table if exists second_hand_item_tag;
drop table if exists user_item_browse;
drop table if exists user_item_collect;
drop table if exists user_setting;


# 重新建表

-- 用户信息表
create table user
(
    id           int primary key auto_increment comment '用户id',
    username     varchar(20)           comment '用户名',
    nickname     varchar(20) default 'momo' comment '昵称',
    open_id      varchar(100) unique comment '微信openid 唯一', # 微信openid 目前唯一 后面可能限制只有3个 唯一可以提高查询速度
    phone        varchar(12)  comment '电话号码',
    password     varchar(32) not null default '123456' comment '密码',
    gender       tinyint unsigned default 0 comment '性别 , 0 男, 1 女',
    image        varchar(300) default '/images/mine/cat.jpeg' comment '头像',
    head_image   varchar(300)         ,
    introduction varchar(300) comment '自我介绍',
    address      varchar(300) comment '简要地址',
    school       varchar(30) comment '校区',
    update_time  datetime comment '更新时间',
    create_time  datetime comment '创建时间'
) comment '用户信息表';

-- 回收品类型表
create table scrap_type
(
    id          int primary key auto_increment comment '回收品标识',
    name        varchar(30) not null comment '回收品类型名称',
    image       varchar(300) comment '回收品类型图片',
    price       int default 0 comment '回收品类型上传量统计 服务前端，亦可记录',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间'
) comment '回收品类型表';

-- 回收品表
create table scrap
(
    id            int primary key auto_increment comment '回收品id',
    name          varchar(40) not null comment '回收品名称',
    price         int comment '回收品价格',
    image         varchar(300) comment '物品图片',
    scrap_type_id int comment '回收品类型标识',
    type          int default 0 comment '描述类型 0计量描述 1度量单位 2其他描述',
    count         int default 0 comment '用于统计，服务于前端',
    size          varchar(20) comment '1 度量单位 度量单位或类型 kg/斤/g',
    other         varchar(20) comment '2其他描述 其他详细或限制描述',
    update_time   datetime comment '更新时间',
    create_time   datetime comment '创建时间',
    foreign key (scrap_type_id) references scrap_type (id)
) comment '回收品表';

-- 回收品交易表
create table scrap_trade
(
    id          int primary key auto_increment comment '回收品交易表唯一标识',
    number      varchar(50) not null comment '订单编号',
    user_id     int         not null comment '用户id',
    consignee   varchar(20) comment '交易员名称',
    status      int         not null default 0 comment '回收品交易状态 0: 上传 1: 接单 2: 确认 3: 完成 4: 取消',
    address     varchar(300) comment '用户地址',
    phone       varchar(16) comment '用户手机号',
    predict_price       int comment '交易总金额',
    real_price         int comment '回收真实价格',
    image       varchar(300) comment '图片描述',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间',
    foreign key (user_id) references user (id) -- 联系用户表的id
) comment '回收品交易表';

-- 回收品交易表明细
create table scrap_trade_detail
(
    id             int primary key auto_increment comment '回收品交易表明细唯一标识',
    user_id        int not null comment '用户id',
    scrap_id       int not null comment '回收品id',
    scrap_trade_id int not null comment '回收品交易表id',
    status         int not null default 0 comment '回收品交易状态 0: 上传 1: 接单 2: 确认 3: 完成 4: 取消',
    count          int comment '单品记录数,统计量',
    price          int comment '总价',
    update_time    datetime comment '更新时间',
    create_time    datetime comment '创建时间',
    foreign key (user_id) references user (id),-- 联系用户表的id
    foreign key (scrap_id) references scrap (id), -- 联系回收品表的id
    foreign key (scrap_trade_id) references scrap_trade (id) -- 联系回收品交易表的id
) comment '回收品交易表明细';

-- 用户关系表
create table relationship
(
    id int primary key auto_increment comment '单条关系唯一标识',
    userId1 int          not null comment '用户id1',
    userId2 int          not null comment '用户id2',
    status int default 1 comment '用户间状态 0:取消通信权利 1:发送好友申请 2:可以相互通信 3:收到好友申请' ,
    greet varchar(50) default '你好' comment '好友验证信息',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间',
    foreign key (userId1) references user (id),
    foreign key (userId2) references user (id)
)comment '用户关系表';

-- 通讯表
create table message_user
(
    id          int primary key auto_increment comment '通讯id',
    sender_id     int          not null comment '发送者id',
    receiver_id int          not null comment '接收者id',
    message     varchar(300) not null comment '内容',
    mes_status    int     default 1    not null comment '消息状态',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间',
    foreign key (sender_id) references user (id),
    foreign key (receiver_id) references user (id)
) comment '通讯表';

-- 用户地址表
create table address
(
    user_id     int comment '用户id',
    address_ip  varchar(50) not null comment '用户ip地址',
    access int default 0 comment '访问次数',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间',
    foreign key (user_id) references user (id) -- 联系用户表id
) comment '用户地址表';

-- 委托表
create table entrust
(
    id          int primary key auto_increment comment '委托id',
    user_id     int          not null comment '用户id',
    info        varchar(200) not null comment '委托的需求（文字）',
    image       varchar(300) comment '委托的图片',
    price       int unsigned not null comment '委托的价格',
    status      varchar(8)   not null default '待接单' comment '委托的状态',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间',
    foreign key (user_id) references user (id) -- 联系用户表id
) comment '委托表';

-- 二手物品表
create table second_hand_item
(
    id int primary key auto_increment comment '物品编号',
    uuid varchar(50) comment '物品id',
    name varchar(30) not null comment '二手物品名称', -- 非空
    image varchar(100) not null comment '二手物品图片',
    status int comment '二手物品状态 1:待内容审核 2:发布中 3:下架 4待价格审核',
    price int comment '二手物品价格',
    information varchar(250) not null comment '二手物品描述', -- 非空
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',
    user_id int not null comment '用户id',
    foreign key (user_id) references user (id)-- 联系用户表的id
) comment '二手物品表';

-- 二手物品审核不通过原因表
create table second_hand_item_reject
(
    id int primary key auto_increment comment '审核表编号',
    item_id int comment '二手物品id',
    manager_id int comment '管理员id',
    message varchar(200) not null comment '二手物品描述',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',
    foreign key (item_id) references second_hand_item (id)-- 联系用户表的id
) comment '二手物品审核不通过原因表';

-- 二手交易表
create table second_hand_trade
(
    id int primary key not null auto_increment comment '订单id',
    number      varchar(50) not null comment '订单编号',
    item_image varchar(100) comment '物品图片',
    item_price int not null comment '物品价格',
    item_information varchar(100) not null comment '物品信息',
    place varchar(50) not null comment '交易地点',
    approach varchar(20) not null comment '交易方式',
    trade_time datetime comment '交易时间',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '二手交易表';

-- 二手交易表与用户表的关联表
create table second_hand_trade_user
(
    id int primary key not null auto_increment comment 'id',
    second_hand_trade_id int not null comment '二手交易订单id',
    second_hand_trade_status int not null comment '二手交易订单状态 1:创建 2:取消 3:完成 4:删除 5:卖家已发货',
    buyer_id int not null comment '买家id',
    buyer_status int not null comment '买家状态 1:创建 2:取消 3:完成 4:删除',
    seller_id int not null comment '卖家id',
    seller_status int not null comment '卖家状态 1:创建 2:取消 3:完成 4:删除',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',
    foreign key (second_hand_trade_id) references second_hand_trade (id),
    foreign key (buyer_id) references user (id),
    foreign key (seller_id) references user (id)
) comment '二手交易表与用户表的关联表';


-- 动态表
create table trends
(
    id          int primary key auto_increment comment '动态表id',
    user_id     int          not null comment '用户id',
    image       varchar(300) comment '图片',
    info        varchar(200) not null comment '需求',
    update_time datetime comment '更新时间',
    create_time datetime comment '发布时间',
    foreign key (id) references user (id) -- 联系用户表id
);

-- 管理员表
create table manager
(
    id          int primary key auto_increment comment '管理员唯一标识',
    name        varchar(64) not null comment '名字',
    username    varchar(64) not null unique comment '唯一用户名',
    password    varchar(64) not null default '123456' comment '密码',
    phone       varchar(16) comment '手机号',
    authority   int default 1 comment '权限等级 1:超级管理员 2:二手物品内容审核 3:二手物品价格审核 4:回收员 5:客服 6:好友申请审核 7:接单员',
    update_time datetime comment '更新时间',
    create_time datetime comment '创建时间'
) comment '管理员表';

-- 交易时间信息表
create table message_trade
(
    id int primary key auto_increment comment '交易信息ID',
    name varchar(100) default 'momo' comment '交易信息名',
    message varchar(500) not null comment '信息',
    trade_time_start datetime comment '交易开始时间',
    trade_time_finish datetime comment '交易结束时间',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '交易时间信息表';

-- 交易时间信息与用户id的关联表
create table message_scrap_trade
(
    id int primary key auto_increment comment '关联ID',
    message_trade_id int not null comment ' 交易信息ID',
    scrap_trade_id int not null comment '交易ID',
    status int default 0 comment '查看的状态'
) comment '交易时间信息与用户id的关联表';

-- 标签分组表
create table `tag_group`
(
    id          int primary key auto_increment comment '分组ID',
    `name`      varchar(20) default 'momo' comment '分组名',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '标签分组表';

-- 标签表
create table `tag`
(
    id          int primary key auto_increment comment '标签ID',
    `name`      varchar(40) unique not null comment '标签名',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '标签表';

-- 标签与分组的关联表
create table `tag_tag_group`
(
    id int primary key auto_increment comment '关联ID',
    tag_id int not null comment '标签ID',
    tag_group_id int not null comment '标签分组ID',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '标签与分组的关联表';

-- 图片与二手物品关联表
create table `second_hand_item_image`
(
    id int primary key auto_increment comment '关联ID',
    second_hand_item_id int not null comment '二手物品ID',
    image varchar(100) not null comment '图片',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '图片与二手物品关联表';

-- 二手物品与标签的关联表
create table `second_hand_item_tag`
(
    id int primary key auto_increment comment '关联ID',
    second_hand_item_id int not null comment '二手物品ID',
    tag_tag_group_id int not null comment '标签ID',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '二手物品与标签的关联表';

-- 用户与物品浏览关联表
create table `user_item_browse`
(
    id int primary key auto_increment comment '关联ID',
#     type int not null default 1 comment '类型 1:二手 2:委托 3:点赞', #拓展
    user_id int not null comment '用户ID',
    item_id int not null comment '物品ID',
    count   int not null default 1 comment '浏览次数',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '用户与物品浏览关联表';

-- 用户与物品收藏关联表
create table `user_item_collect`
(
    id int primary key auto_increment comment '关联ID',
#     type int not null default 1 comment '类型 1:二手 2:委托 3:点赞', #拓展
    user_id int not null comment '用户ID',
    item_id int not null comment '物品ID',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '用户与物品收藏关联表';

-- 用户的设置表
create table `user_setting`
(
    user_id int primary key auto_increment comment '用户ID 主键',
    remind TINYINT(1) default 1 comment '是否开启消息提醒 0:关闭 1:开启',
    message TINYINT(1) default 1 comment '是否开启消息推送 0:关闭 1:开启',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '用户的设置表';

-- 投诉表
create table `complaint`
(
    id int primary key auto_increment comment '投诉表id',
    complainant int comment '投诉人id',
    respondent int comment '被投诉人id',
    order_id int comment '投诉的二手订单id',
    status int default 0 comment '状态 1:等待客服反馈商家 2:等待商家反馈客服 3:等待客服反馈用户 4:投诉完成', -- 状态 0:未处理 1:已跟踪 2:已与投诉人沟通 3:已反馈被投诉人 4:已反馈投诉人 5:已处理
    data text comment 'json格式内容',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
) comment '投诉表';

/* 用户数据 */
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('评委', '金枪鱼', '212648dd-f050-49bf-a0bb-71688035be7a', '17301321081', '123456', '0', '1.png', '1.png', '灯蛾', '南苑2栋', '清远校区', '2022-09-12 07:04:03', '2022-02-15 21:12:43');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('评委1', '折耳猫', '9388994a-33a9-4719-bbd1-a9677f4ac391', '17505881306', '123456', '0', '1.png', '1.png', '鸰', '南苑2栋', '清远校区', '2022-11-22 13:02:06', '2022-03-25 01:57:09');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('评委2', '萨摩', '7d204bc5-3798-4bcd-91ee-36f885e5a853', '17362262486', '123456', '0', '1.png', '1.png', '角甲', '南苑2栋', '广州校区', '2022-10-01 17:52:52', '2022-06-10 14:59:07');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('评委3', '野驴', 'c9863f8b-58cd-4e5a-87fa-912580d16d9a', '14796423846', '123456', '0', '1.png', '1.png', '黄占', '南苑2栋', '清远校区', '2022-06-05 07:04:39', '2022-11-07 13:18:02');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('评委4', '印随', '04d90352-4bdc-4ed8-9962-c56a6c9bcc97', '15310538236', '123456', '1', '1.png', '1.png', '杂骨', '南苑2栋', '肇庆校区', '2022-07-08 19:04:19', '2022-07-05 00:08:15');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('工作人员', '扇贝', '7d204bc5-3798-4bcd-91ee-36f885e5a852', '14576995159', '123456', '0', '1.png', '1.png', '夜行性动物', '南苑2栋', '清远校区', '2022-12-22 12:22:46', '2022-12-06 06:48:54');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('叶俊驰', '狮子狗', '0949f235-35ad-4ca8-8392-b118bdd8976e', '13739855560', '123456', '1', '1.png', '1.png', '小鸥', '南苑2栋', '肇庆校区', '2022-10-17 11:47:17', '2022-06-05 18:09:45');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('卢熠彤', '毛霉', '212648dd-f050-49bf-a0bb-71688035be7d', '13147586377', '123456', '0', '1.png', '1.png', '母狗', '南苑2栋', '广州校区', '2022-03-10 01:47:36', '2022-09-16 00:08:14');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('田子默', '人工鱼礁', 'f2b3d1e5-59b6-4a03-a9b6-a0d4bbc42d48', '17756293989', '123456', '0', '1.png', '1.png', '阴道毛滴虫', '南苑2栋', '肇庆校区', '2022-01-19 01:08:34', '2022-09-09 10:30:20');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('程志泽', '五步蛇', 'b2739ce2-bafd-406b-bc1c-16744326a5a5', '17795477400', '123456', '1', '1.png', '1.png', '小鹿犬', '南苑2栋', '广州校区', '2022-11-03 12:10:10', '2022-10-28 17:36:22');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('萧博涛', '萨摩', '212648dd-f050-49bf-a0bb-71688035be7c', '17194201817', '123456', '0', '1.png', '1.png', '灰鹅', '南苑2栋', '清远校区', '2022-12-19 22:38:17', '2022-08-01 23:14:16');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('余烨伟', '猎豹', '212648dd-f050-49bf-a0bb-71688035be7f', '17226317234', '123456', '0', '1.png', '1.png', '黄占', '南苑2栋', '肇庆校区', '2022-03-28 17:38:20', '2022-03-12 15:19:52');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('林语堂', '鱼胆', '04d90352-4bdc-4ed8-9962-c56a6c9bcc98', '15794461147', '123456', '0', '1.png', '1.png', '肉驴', '南苑2栋', '广州校区', '2022-01-15 11:18:14', '2022-10-09 21:34:40');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('苏博超', '动物克隆', 'f2b3d1e5-59b6-4a03-a9b6-a0d4bbc44d48', '18199580612', '123456', '1', '1.png', '1.png', '星虫', '南苑2栋', '广州校区', '2022-06-03 23:12:18', '2022-03-27 12:16:30');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('蔡伟泽', '鹮', '0949f235-35ad-4ca8-8392-b118bdd8976a', '15941024170', '123456', '0', '1.png', '1.png', '越前龙', '南苑2栋', '清远校区', '2022-03-01 16:22:45', '2022-08-16 22:58:17');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('贺修洁', '犬科动物', '9388994a-33a9-4719-bbd1-a9677f7ac391', '15722733040', '123456', '0', '1.png', '1.png', '杜泊羊', '南苑2栋', '肇庆校区', '2022-11-08 17:44:19', '2022-03-27 14:31:41');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('马擎宇', '狮子狗', '7d204bc5-3798-4bcd-91ee-36f885e50853', '15024658835', '123456', '1', '1.png', '1.png', '野猫', '南苑2栋', '广州校区', '2022-12-07 09:55:12', '2022-07-26 09:53:23');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('沈俊驰', '莴笋叶', '0949f235-35ad-4ca8-8392-b118bdd8a76e', '17573784381', '123456', '0', '1.png', '1.png', '昆虫及', '南苑2栋', '肇庆校区', '2022-01-16 21:02:06', '2022-02-28 18:27:08');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('万鹏涛', '中猴', '04d90352-4bdc-4ed8-9962-c56a6c9bcc07', '14572338865', '123456', '1', '1.png', '1.png', '画眉鸟', '南苑2栋', '肇庆校区', '2022-04-09 03:36:34', '2022-08-14 19:59:08');
insert into spz.`user` (`username`, `nickname`, `open_id`, `phone`, `password`, `gender`, `image`, `head_image`, `introduction`, `address`, `school`, `update_time`, `create_time`) values ('顾绍齐', '鹡鸰', 'f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d08', '14777657986', '123456', '0', '1.png', '1.png', '野生大熊猫', '南苑2栋', '清远校区', '2022-06-09 22:30:34', '2022-07-24 04:44:08');

# 回收品类型
insert into `scrap_type` (`id`, `name`, `image`, `create_time`, `update_time`)
values (1, '塑料', '/images/recycle/static.png', '2022-11-13 09:26:22', '2022-10-14 00:48:24');
insert into `scrap_type` (`id`, `name`, `image`, `create_time`, `update_time`)
values (2, '纸张', '/images/recycle/paper.png', '2022-11-05 03:47:41', '2022-03-31 13:12:03');
insert into `scrap_type` (`id`, `name`, `image`, `create_time`, `update_time`)
values (3, '布料', '/images/recycle/cloth.png', '2022-05-22 07:32:45', '2022-04-25 04:04:04');
insert into `scrap_type` (`id`, `name`, `image`, `create_time`, `update_time`)
values (4, '金属', '/images/recycle/metal.png', '2022-02-21 20:49:26', '2022-04-30 02:51:21');

# 用户地址
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (1, '1', '2022-05-21 03:18:29', '2022-11-09 11:17:50');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (2, '2', '2022-01-09 19:40:20', '2022-06-16 01:56:19');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (3, '3', '2022-07-10 17:22:50', '2022-04-30 14:53:42');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (4, '4', '2022-05-07 17:41:09', '2022-08-18 10:15:07');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (5, '5', '2022-06-02 23:15:07', '2022-01-21 00:03:58');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (6, '6', '2022-04-26 15:12:23', '2022-07-08 17:28:02');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (7, '7', '2022-10-08 06:23:07', '2022-04-23 11:48:10');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (8, '8', '2022-08-08 14:00:01', '2022-07-04 05:23:50');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (9, '9', '2022-07-20 13:00:22', '2022-07-11 11:53:00');
insert into `address` (`user_id`, `address_ip`, `create_time`, `update_time`)
values (10, '10', '2022-02-22 06:01:26', '2022-02-04 23:34:50');

# 回收品单体数据
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (1, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-04-29 09:27:05', '2022-06-20 02:06:29');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (2, '塑料桶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-06-06 21:44:44', '2022-06-26 23:24:11');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (3, '塑料玩具', 150, '1.png', '3', 1, 0, '个', '按个算', '2022-08-11 16:32:58', '2022-07-09 07:45:56');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (4, '旧衣服', 150, '1.png', '3', 1, 0, '个', '按个算', '2022-07-03 18:59:59', '2022-01-19 06:09:25');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (5, '被子', 150, '1.png', '3', 1, 0, '个', '按个算', '2022-03-22 21:24:05', '2022-08-03 10:07:33');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (6, '金属盘', 150, '1.png', '4', 1, 0, '个', '按个算', '2022-05-17 00:45:06', '2022-07-06 21:49:20');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (7, '金属碗', 150, '1.png', '4', 1, 0, '个', '按个算', '2022-02-07 18:10:54', '2022-06-15 09:57:53');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (8, '书', 150, '1.png', '2', 1, 0, '个', '按个算', '2022-07-25 03:36:30', '2022-07-09 10:12:26');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (9, '废纸', 150, '1.png', '2', 1, 0, '个', '按个算', '2022-09-21 13:28:20', '2022-12-20 05:50:45');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (10, '纸皮', 150, '1.png', '2', 1, 0, '个', '按个算', '2022-07-14 06:19:08', '2022-03-26 08:58:44');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (11, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-11-18 23:17:52', '2022-12-04 04:38:33');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (12, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-10-17 22:49:43', '2022-04-13 20:18:26');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (13, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-03-05 09:23:23', '2022-11-07 22:48:25');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (14, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-08-06 04:35:01', '2022-06-29 07:38:29');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (15, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-05-07 09:33:59', '2022-10-30 05:47:14');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (16, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-07-30 06:48:13', '2022-06-08 05:45:48');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (17, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-09-07 11:49:41', '2022-11-21 13:29:38');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (18, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-01-06 19:36:13', '2022-06-26 09:23:05');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (19, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-11-13 10:22:53', '2022-09-17 10:13:04');
insert into `scrap` (`id`, `name`, `price`, `image`, `scrap_type_id`, `type`, `count`, `size`, `other`, `create_time`,
                     `update_time`)
values (20, '塑料瓶', 150, '1.png', '1', 1, 0, '个', '按个算', '2022-03-21 16:06:45', '2022-12-08 12:41:39');

# 给表打补丁
update scrap
set image = '/images/recycle/static.png'
where scrap_type_id = 1;

# 插入管理员数据
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (1, '管理员', 'admin', '123456', '13490238731', now(), now());
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (2, '总设计师', 'last', '123456', '18809673726', now(), now());
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (3, '项目设计师', '清水', '123456', '13209976041', now(), now());
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (4, 'UI加前端', 'bumorak', '123456', '13709876252', now(), now());
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (5, '后端', 'yuhun123', '123456', '19802452671', now(), now());
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (6, '项目管理', 'yuying', '123456', '13232851413', now(), now());
# insert into manager(id, name, username, password, phone, update_time, create_time)
# VALUES (7, '前端', '卢氢', '123456', '13232851411', now(), now());

# 插入管理员数据
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (1, '管理员', 'admin', '123456', '13490238731', now(), now());
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (2, '总设计师', '杨坤钿', '123456', '13431969531', now(), now());
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (3, '项目设计师', '陈金麟', '123456', '13209976041', now(), now());
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (4, 'UI加前端', '洪俊麒', '123456', '13709876252', now(), now());
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (5, '后端', '李俊宏', '123456', '19802452671', now(), now());
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (6, '项目管理', '吴钰滢', '123456', '13232851413', now(), now());
insert into manager(id, name, username, password, phone, update_time, create_time)
VALUES (7, '前端', '卢佳仪', '123456', '13232851411', now(), now());
insert into manager(id, name, username, password, phone, authority, update_time, create_time)
VALUES (8, '内容审核', '内容审核', '123456', '13232851411', 2, now(), now());
insert into manager(id, name, username, password, phone, authority, update_time, create_time)
VALUES (9, '价格审核', '价格审核', '123456', '13232851411', 3, now(), now());
insert into manager(id, name, username, password, phone, authority, update_time, create_time)
VALUES (10, '回收员', '回收员', '123456', '13232851411', 4, now(), now());
insert into manager(id, name, username, password, phone, authority, update_time, create_time)
VALUES (11, '客服', '客服', '123456', '13232851411', 5, now(), now());
insert into manager(id, name, username, password, phone, authority, update_time, create_time)
VALUES (12, '好友申请审核', '好友申请审核', '123456', '13232851411', 6, now(), now());
insert into manager(id, name, username, password, phone, authority, update_time, create_time)
VALUES (13, '接单员', '接单员', '123456', '13232851411', 7, now(), now());

# 回收品交易表
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (1, 'f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d48', 6, '南苑2栋', '17891152547', 5, '2022-02-25 18:20:24', '2022-11-04 01:07:44', '龙明');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (2, 'b2739ce2-bafd-406b-bc1c-16744326a5c5', 2, '南苑2栋', '17334998981', 7, '2022-04-12 14:52:14', '2022-06-20 01:35:06', '谢锦程');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (3, '7d204bc5-3798-4bcd-91ee-36f885e5a853', 3, '南苑2栋', '15505287783', 25, '2022-06-30 04:37:48', '2022-10-10 09:22:03', '程晋鹏');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (4, '212648dd-f050-49bf-a0bb-71688035be7a', 1, '南苑2栋', '15317247203', 81, '2022-09-29 16:03:51', '2022-04-08 21:33:25', '范文轩');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (5, '9388994a-33a9-4719-bbd1-a9677f4ac391', 8, '南苑2栋', '15949161915', 10, '2022-07-23 00:28:50', '2022-09-10 20:41:28', '谭瑾瑜');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (6, '04d90352-4bdc-4ed8-9962-c56a6c9bcc97', 4, '南苑2栋', '15772579531', 82, '2022-10-10 01:21:41', '2022-04-20 15:02:03', '夏哲瀚');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (7, '212648dd-f050-49bf-a0bb-71688035be7a', 2, '南苑2栋', '15860627397', 25, '2022-05-16 11:36:43', '2022-06-26 15:52:55', '武明');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (8, 'e655d637-ed11-458c-a4a7-0fe394440138', 10, '南苑2栋', '17735545459', 7, '2022-05-25 07:56:59', '2022-08-12 08:33:17', '贾思源');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (9, 'b2739ce2-bafd-406b-bc1c-16744326a5c5', 8, '南苑2栋', '15559629509', 14, '2022-05-29 04:01:38', '2022-12-12 05:02:30', '丁懿轩');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (10, 'b2739ce2-bafd-406b-bc1c-16744326a5c5', 8, '南苑2栋', '17585850485', 23, '2022-02-19 12:29:13', '2022-11-05 00:14:03', '贺君浩');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (11, 'e655d637-ed11-458c-a4a7-0fe394440138', 1, '南苑2栋', '17308112843', 47, '2022-05-22 17:47:02', '2022-05-16 00:26:17', '龙智宸');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (12, '7d204bc5-3798-4bcd-91ee-36f885e5a853', 2, '南苑2栋', '15272170180', 18, '2022-09-01 01:47:47', '2022-05-23 22:16:33', '孟明');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (13, '0949f235-35ad-4ca8-8392-b118bdd8976e', 6, '南苑2栋', '17835589576', 93, '2022-06-30 09:04:54', '2022-01-29 06:44:20', '顾文博');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (14, 'e655d637-ed11-458c-a4a7-0fe394440138', 2, '南苑2栋', '15332345253', 84, '2022-03-15 13:26:06', '2022-07-06 07:36:34', '严语堂');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`) values (15, 'b2739ce2-bafd-406b-bc1c-16744326a5c5', 9, '南苑2栋', '17628207517', 14, '2022-07-04 00:18:17', '2022-10-26 12:14:41', '黎越彬');
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`, status) values (16, 'f655d637-ed11-458c-a4a7-0fe394440138', 1, '南苑2栋', '17308112843', 47, '2022-05-22 17:47:02', '2022-05-16 00:26:17', '龙智宸', 1);
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`, status) values (17, '8d204bc5-3798-4bcd-91ee-36f885e5a853', 2, '南苑2栋', '15272170180', 18, '2022-09-01 01:47:47', '2022-05-23 22:16:33', '孟明', 2);
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`, status) values (18, '1949f235-35ad-4ca8-8392-b118bdd8976e', 6, '南苑2栋', '17835589576', 93, '2022-06-30 09:04:54', '2022-01-29 06:44:20', '顾文博', 3);
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`, status) values (19, 'f655d637-ed11-458c-a4a7-0fe394440138', 2, '南苑2栋', '15332345253', 84, '2022-03-15 13:26:06', '2022-07-06 07:36:34', '严语堂', 4);
insert into `scrap_trade` (`id`, `number`, `user_id`, `address`, `phone`, `predict_price`, `update_time`, `create_time`, `consignee`, status) values (20, 'd2739ce2-bafd-406b-bc1c-16744326a5c5', 9, '南苑2栋', '17628207517', 14, '2022-07-04 00:18:17', '2022-10-26 12:14:41', '黎越彬', 3);

# 回收品记录表明细
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (1, 5, 10, 1, 6, 28, '2022-04-11 16:44:10', '2022-04-12 10:35:54');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (2, 3, 7, 1, 2, 69, '2022-06-01 22:36:21', '2022-10-04 02:14:09');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (3, 1, 3, 1, 7, 16, '2022-08-11 17:52:31', '2022-12-17 00:23:21');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (4, 3, 9, 1, 8, 21, '2022-01-14 09:12:15', '2022-08-23 18:09:33');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (5, 8, 6, 1, 10, 86, '2022-08-05 21:55:16', '2022-06-10 18:49:36');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (6, 3, 3, 1, 10, 94, '2022-11-20 23:42:23', '2022-01-08 17:06:29');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (7, 1, 8, 1, 9, 56, '2022-02-13 03:12:23', '2022-10-31 21:17:21');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (8, 9, 6, 1, 10, 36, '2022-10-22 14:12:15', '2022-06-24 05:09:46');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (9, 3, 1, 1, 4, 52, '2022-06-26 08:13:16', '2022-01-29 11:07:45');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (10, 6, 1, 1, 6, 72, '2022-06-30 20:14:40', '2022-05-08 07:52:44');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (11, 5, 1, 1, 8, 37, '2022-01-17 22:41:02', '2022-01-11 15:26:17');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (12, 5, 2, 1, 1, 91, '2022-02-05 05:33:22', '2022-07-21 12:19:48');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (13, 1, 9, 1, 1, 70, '2022-10-07 23:40:15', '2022-09-08 23:39:18');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (14, 8, 9, 1, 4, 24, '2022-07-11 03:04:47', '2022-01-07 09:32:35');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (15, 5, 10, 1, 7, 16, '2022-05-02 06:00:14', '2022-07-27 22:05:28');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (16, 8, 10, 1, 4, 54, '2022-09-27 17:46:53', '2022-10-11 20:47:04');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (17, 2, 9, 1, 2, 66, '2022-12-05 04:21:45', '2022-10-17 09:54:01');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (18, 8, 8, 1, 5, 67, '2022-01-10 22:11:28', '2022-01-31 09:29:09');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (19, 7, 4, 1, 5, 86, '2022-06-17 18:48:56', '2022-08-25 11:05:45');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (20, 5, 8, 1, 2, 29, '2022-05-29 02:12:59', '2022-07-21 14:43:13');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (21, 1, 5, 1, 4, 84, '2022-12-18 05:50:52', '2022-09-28 03:40:47');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (22, 9, 5, 1, 2, 5, '2022-03-08 15:22:29', '2022-03-08 17:38:38');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (23, 1, 4, 1, 4, 10, '2022-12-11 06:36:00', '2022-09-06 23:28:20');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (24, 6, 10, 1, 4, 87, '2022-05-30 06:02:26', '2022-06-18 15:41:19');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (25, 6, 1, 1, 4, 66, '2022-09-10 06:23:40', '2022-09-30 19:59:18');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (26, 9, 8, 1, 3, 74, '2022-03-07 03:46:45', '2022-05-18 10:37:36');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (27, 3, 7, 1, 9, 35, '2022-09-02 18:06:01', '2022-08-01 15:34:57');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (28, 6, 9, 1, 3, 85, '2022-01-05 17:48:40', '2022-09-13 14:35:00');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (29, 6, 4, 1, 8, 17, '2022-07-01 22:28:49', '2022-11-07 02:51:25');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (30, 3, 10, 1, 7, 94, '2022-08-10 09:50:41', '2022-05-22 23:52:09');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (31, 7, 9, 1, 4, 98, '2022-09-30 17:58:44', '2022-12-28 16:36:16');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (32, 7, 3, 1, 1, 85, '2022-06-09 01:00:27', '2022-04-23 05:19:25');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (33, 6, 4, 1, 7, 39, '2022-03-02 23:52:44', '2022-07-30 01:50:48');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (34, 8, 1, 1, 2, 17, '2022-11-14 08:51:13', '2022-11-25 14:26:36');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (35, 1, 2, 1, 4, 32, '2022-01-12 17:28:16', '2022-11-21 23:35:26');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (36, 10, 8, 1, 2, 56, '2022-06-01 02:12:46', '2022-07-27 12:23:12');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (37, 5, 7, 1, 2, 16, '2022-07-18 10:27:00', '2022-05-05 10:06:10');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (38, 8, 9, 1, 3, 35, '2022-07-30 13:09:02', '2022-10-17 11:41:54');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (39, 8, 5, 1, 3, 72, '2022-08-01 00:13:00', '2022-08-29 21:52:23');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (40, 4, 7, 1, 1, 28, '2022-12-09 01:29:41', '2022-05-07 23:13:43');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (41, 5, 10, 1, 3, 11, '2022-05-09 02:09:25', '2022-01-20 21:24:52');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (42, 2, 1, 1, 5, 60, '2022-06-02 08:18:13', '2022-08-12 17:14:30');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (43, 9, 8, 1, 2, 55, '2022-12-05 17:41:14', '2022-06-21 08:40:14');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (44, 6, 8, 1, 4, 7, '2022-03-28 18:57:35', '2022-09-26 08:52:03');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (45, 3, 7, 1, 8, 97, '2022-08-09 02:16:17', '2022-04-15 17:29:11');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (46, 6, 9, 1, 7, 96, '2022-05-19 14:13:07', '2022-11-13 20:05:50');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (47, 7, 5, 1, 5, 33, '2022-12-21 06:06:33', '2022-01-26 00:12:47');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (48, 6, 1, 1, 4, 70, '2022-09-09 19:16:13', '2022-11-26 01:17:14');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (49, 5, 6, 1, 4, 18, '2022-12-28 07:04:00', '2022-10-24 19:23:24');
insert into `scrap_trade_detail` (`id`, `user_id`, `scrap_id`, `scrap_trade_id`, `count`, `price`, `update_time`,
                                  `create_time`)
values (50, 2, 3, 1, 1, 46, '2022-01-19 06:56:14', '2022-01-20 11:20:41');

# 用户关系
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (1, 2, 3, 2, '2022-10-09 15:45:40', '2022-10-04 00:15:34');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (2, 3, 2, 2, '2022-05-05 10:29:37', '2022-02-13 18:20:42');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (3, 1, 7, 2, '2022-06-28 12:16:58', '2022-03-05 08:31:15');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (4, 7, 1, 2, '2022-08-13 20:35:47', '2022-08-29 05:53:51');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (5, 8, 1, 0, '2022-05-12 12:11:42', '2022-02-10 10:01:27');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (6, 1, 8, 0, '2022-03-10 02:51:29', '2022-11-30 12:24:11');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (7, 2, 4, 1, '2022-10-12 01:30:44', '2022-06-23 00:39:56');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (8, 4, 2, 3, '2022-01-14 17:09:45', '2022-06-24 12:25:55');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (9, 2, 5, 3, '2022-04-30 15:34:24', '2022-11-07 11:22:02');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (10, 5, 2, 1, '2022-01-01 18:53:48', '2022-07-03 12:44:01');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (11, 1, 2, 1,'2022-12-29 10:52:16', '2022-03-21 05:32:15');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (12, 2, 1, 3,'2022-11-28 17:37:03', '2022-07-17 05:51:43');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (13, 1, 3, 3,'2022-02-16 00:21:34', '2022-12-02 00:54:18');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (14, 3, 1, 1,'2022-01-29 01:47:00', '2022-11-10 22:53:25');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (15, 1, 4, 1,'2022-04-02 21:09:36', '2022-03-20 23:00:17');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (16, 4, 1, 3,'2022-02-27 21:51:52', '2022-09-15 23:38:48');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (17, 1, 5, 3,'2022-09-19 23:42:59', '2022-07-06 03:04:12');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (18, 5, 1, 1,'2022-02-13 02:42:35', '2022-06-05 13:26:00');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (19, 1, 10, 2, '2022-08-12 12:18:21', '2022-12-15 02:26:51');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (20, 10, 1, 2, '2022-11-14 09:26:18', '2022-03-05 14:16:46');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (21, 1, 6, 2,'2022-09-19 23:42:59', '2022-07-06 03:04:12');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (22, 6, 1, 2,'2022-02-13 02:42:35', '2022-06-05 13:26:00');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (23, 10, 2, 4, '2022-08-12 12:18:21', '2022-12-15 02:26:51');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (24, 2, 10, 5, '2022-11-14 09:26:18', '2022-03-05 14:16:46');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (25, 11, 12, 5, '2022-11-14 09:26:18', '2022-03-05 14:16:46');
insert into `relationship` (`id`, `userId1`, `userId2`,  status, `update_time`, `create_time`) values (26, 12, 11, 4, '2022-11-14 09:26:18', '2022-03-05 14:16:46');

# 通讯表数据
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (1, 9, 6, '你好', '2022-05-25 08:50:44', '2022-07-02 05:19:03');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (2, 4, 3, '你好', '2022-01-01 00:28:31', '2022-03-16 20:14:21');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (3, 3, 2, '你好', '2022-10-17 14:28:59', '2022-03-06 14:06:44');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (4, 5, 1, '你好', '2022-05-07 05:43:48', '2022-12-30 21:48:31');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (5, 10, 3, '你好', '2022-03-08 16:10:20', '2022-03-03 06:28:18');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (6, 7, 10, '你好', '2022-10-07 18:02:19', '2022-06-09 17:12:05');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (7, 1, 5, '你好', '2022-12-16 18:21:09', '2022-03-05 11:07:17');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (8, 2, 5, '你好', '2022-02-10 04:03:52', '2022-03-09 15:50:58');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (9, 2, 1, '你好', '2022-03-17 14:47:44', '2022-05-10 02:25:16');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (10, 1, 5, '你好', '2022-09-10 03:28:39', '2022-06-28 13:04:04');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (11, 4, 5, '你好', '2022-06-11 13:01:03', '2022-06-09 13:51:14');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (12, 6, 10, '你好', '2022-03-26 06:32:57', '2022-06-16 04:44:46');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (13, 3, 3, '你好', '2022-06-16 14:45:59', '2022-01-01 17:05:09');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (14, 2, 3, '你好', '2022-02-04 01:25:11', '2022-10-06 01:01:49');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (15, 7, 9, '你好', '2022-05-28 04:35:20', '2022-04-11 02:24:55');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (16, 8, 5, '你好', '2022-07-28 14:23:04', '2022-01-16 22:47:05');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (17, 7, 1, '你好', '2022-10-02 16:30:34', '2022-01-10 03:40:16');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (18, 3, 3, '你好', '2022-01-14 07:12:07', '2022-01-17 08:32:24');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (19, 1, 9, '你好', '2022-02-03 13:38:32', '2022-02-27 19:54:44');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (20, 7, 4, '你好', '2022-12-04 09:28:39', '2022-08-06 15:44:37');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (21, 3, 4, '你好', '2022-03-15 16:37:08', '2022-12-08 10:18:37');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (22, 2, 8, '你好', '2022-12-21 00:00:59', '2022-05-29 17:52:21');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (23, 1, 6, '你好', '2022-01-15 19:07:04', '2022-05-17 12:37:16');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (24, 9, 4, '你好', '2022-01-22 20:00:04', '2022-04-10 12:06:58');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (25, 7, 10, '你好', '2022-08-15 23:19:45', '2022-12-29 19:23:02');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (26, 9, 7, '你好', '2022-03-16 11:35:01', '2022-08-30 02:21:53');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (27, 4, 2, '你好', '2022-02-11 23:31:01', '2022-09-22 07:59:55');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (28, 7, 10, '你好', '2022-01-05 05:46:28', '2022-07-29 07:23:15');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (29, 4, 10, '你好', '2022-09-03 01:07:57', '2022-01-23 18:06:39');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (30, 10, 1, '你好，很高兴见到你，我是拾品站的开发人员', '2022-07-04 01:06:45', '2022-11-16 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (31, 1, 10, '你好，幸会', '2022-08-15 23:19:45', '2022-11-17 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (32, 10, 1, '你好，我们拾品站专注于创新产品，期待与你合作。', '2022-03-16 11:35:01', '2022-11-18 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (33, 1, 10, '听起来很不错，我很期待看看你们的产品。', '2022-02-11 23:31:01', '2022-11-19 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (34, 10, 1, '我们的产品设计独特，功能强大，相信你会喜欢。', '2022-01-05 05:46:28', '2022-11-20 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (35, 1, 10, '非常好，我希望能尽快了解更多关于你们产品的信息。', '2022-09-03 01:07:57', '2022-11-21 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (36, 10, 1, '没问题，我会尽快安排，期待我们的进一步交流。', '2022-09-03 01:07:57', '2022-11-22 15:02:59');
insert into message_user (`id`, sender_id, `receiver_id`, message, `update_time`, `create_time`) values (37, 10, 1, '^_^', '2022-09-03 01:07:57', '2022-11-23 15:02:59');

# 交易信息表
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('大街小巷', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-12-15 12:45:36', '2022-04-30 17:28:52');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('力所能及', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-07-21 02:56:34', '2022-05-25 16:26:07');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('任重道远', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-05-16 13:12:31', '2022-10-22 02:59:51');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('继往开来', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-03-09 07:16:24', '2022-02-04 00:55:10');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('足不出户', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-10-12 13:39:57', '2022-09-07 18:19:52');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('一举一动', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-11-12 06:29:09', '2022-05-25 11:14:43');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('挨家挨户', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-04-24 01:18:58', '2022-12-01 02:14:31');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('艰苦奋斗', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-02-21 13:25:10', '2022-06-29 08:00:36');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('津津乐道', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-07-25 09:11:59', '2022-11-30 13:02:48');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('名不虚传', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-06-14 15:51:03', '2022-07-22 00:53:58');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('滥用职权', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-04-24 02:19:37', '2022-06-24 14:17:20');
insert into `message_trade` (`name`, `message`, `trade_time_start`, `trade_time_finish`, `create_time`, `update_time`) values ('此起彼伏', '如果对此时间没疑问，您可以在历史记录中点击确认', '2024-04-02 14:00:00', '2024-04-02 16:00:00', '2022-12-16 23:22:50', '2022-04-27 07:53:24');

# 交易信息与交易关联表
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (7, 6);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (1, 9);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (6, 2);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (5, 2);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (3, 4);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (9, 8);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (8, 6);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (4, 1);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (4, 4);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (5, 3);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (10, 5);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (5, 10);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (1, 5);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (9, 5);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (2, 10);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (9, 9);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (1, 2);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (3, 2);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (8, 6);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (3, 5);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (6, 5);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (4, 3);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (8, 8);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (6, 1);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (2, 5);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (2, 2);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (7, 7);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (8, 8);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (8, 10);
insert into `message_scrap_trade` (`message_trade_id`, `scrap_trade_id`) values (3, 6);

# 二手物品表
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 26, '二手物品八成新',  '花海', '2022-09-27 19:08:34', '2022-07-20 13:22:40', 1);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 28, '二手物品八成新',  '坏女孩', '2022-09-13 20:14:11', '2022-04-14 08:10:35', 3);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 8,  '二手物品八成新', '漠河舞厅', '2022-12-23 06:19:53', '2022-07-25 11:55:16', 2);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 64, '二手物品八成新',  '童话', '2022-06-21 15:01:46', '2022-05-23 21:58:07', 3);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 93, '二手物品八成新',  '答案', '2022-01-24 05:24:44', '2022-05-08 14:58:40', 2);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 55, '二手物品八成新',  '晴天', '2022-01-07 16:36:36', '2022-02-12 21:00:48', 9);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 16, '二手物品八成新',  '反方向的钟', '2022-02-20 21:54:13', '2022-01-08 06:48:21', 8);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 71, '二手物品八成新',  '演员', '2022-11-08 03:33:47', '2022-07-13 00:12:19', 3);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 83, '二手物品八成新',  '手写的从前', '2022-07-18 11:54:05', '2022-03-08 03:50:56', 1);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 13, '二手物品八成新',  '手写的从前', '2022-07-12 22:22:29', '2022-07-04 19:21:48', 7);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 48, '二手物品八成新',  '青花瓷', '2022-03-17 01:30:01', '2022-04-04 10:06:10', 4);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 68, '二手物品八成新',  '像我这样的人', '2022-01-06 23:15:48', '2022-10-24 13:55:22', 8);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 2, 85, '二手物品八成新',  '漠河舞厅', '2022-06-28 16:53:42', '2022-05-01 03:43:59', 6);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 1, 67, '二手物品八成新',  '无问', '2022-02-09 22:20:00', '2022-03-05 10:50:00', 4);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 1, 48, '二手物品八成新',  '反方向的钟', '2022-01-08 10:18:47', '2022-12-12 03:57:55', 1);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 1, 59, '二手物品八成新',  '烟花易冷', '2022-10-29 08:03:17', '2022-09-10 03:46:41', 1);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 1, 8,  '二手物品八成新', '童话', '2022-07-02 22:02:10', '2022-07-20 03:34:09', 8);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 3, 11, '二手物品八成新',  '本草纲目', '2022-01-04 14:22:16', '2022-08-12 09:23:04', 1);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 3, 43, '二手物品八成新',  '晴天', '2022-07-16 04:46:52', '2022-03-25 07:38:12', 3);
insert into spz.`second_hand_item` (`image`, `status`, `price`, `information`, `name`, `create_time`, `update_time`, `user_id`) values ('1.png', 3, 57, '二手物品八成新',  '盛夏', '2022-09-10 06:15:09', '2022-07-17 15:58:42', 1);

# 二手交易表
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('c9863f8b-58cd-4e5a-87fa-912580d16d9a', '1.png', 64, '像我这样的人', '西宁市', '线下交易', '2022-06-19 22:34:36', '2022-03-25 14:35:32', '2022-02-09 14:50:05');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d48', '1.png', 75, '花海', '张家界市', '线下交易', '2022-03-18 07:43:48', '2022-07-13 02:44:31', '2022-03-20 02:11:19');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('9388994a-33a9-4719-bbd1-a9677f4ac391', '1.png', 76, '手写的从前', '华蓥市', '线下交易', '2022-12-11 18:23:45', '2022-10-07 15:14:02', '2022-07-17 22:16:15');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('c9863f8b-58cd-4e5a-87fa-912580d16d9a', '1.png', 93, '勇气', '临沧市', '线下交易', '2022-11-25 08:13:29', '2022-08-29 14:13:09', '2022-06-22 21:36:41');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('8506b114-8117-4fcd-975e-12919cad6155', '1.png', 84, '漠河舞厅', '宁国市', '线下交易', '2022-06-15 09:07:25', '2022-11-28 12:16:53', '2022-03-09 10:26:41');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('e655d637-ed11-458c-a4a7-0fe394440138', '1.png', 25, '青花瓷', '邢台市', '线下交易', '2022-04-24 09:36:52', '2022-04-06 22:10:23', '2022-07-17 18:55:43');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('c9863f8b-58cd-4e5a-87fa-912580d16d9a', '1.png', 76, '模特', '鹤岗市', '线下交易', '2022-07-04 09:04:19', '2022-08-04 06:22:04', '2022-06-08 23:04:25');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('c9863f8b-58cd-4e5a-87fa-912580d16d9a', '1.png', 6, '烟花易冷', '同江市', '线下交易', '2022-12-04 23:32:22', '2022-08-26 08:29:46', '2022-06-28 01:59:25');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d48', '1.png', 33, '盛夏', '兰州市', '线下交易', '2022-09-14 02:26:42', '2022-11-22 08:47:40', '2022-09-14 18:06:46');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('0949f235-35ad-4ca8-8392-b118bdd8976e', '1.png', 65, '盛夏', '福清市', '线下交易', '2022-11-16 10:11:02', '2022-04-24 09:23:21', '2022-01-20 13:27:10');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('8506b114-8117-4fcd-975e-12919cad6155', '1.png', 10, '烟花易冷', '台北市', '线下交易', '2022-04-07 02:18:33', '2022-11-20 01:29:33', '2022-12-26 09:44:01');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('0949f235-35ad-4ca8-8392-b118bdd8976e', '1.png', 14, '坏女孩', '安国市', '线下交易', '2022-06-26 22:33:31', '2022-04-05 16:42:13', '2022-11-08 16:43:24');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('0949f235-35ad-4ca8-8392-b118bdd8976e', '1.png', 58, '勇气', '青岛市', '线下交易', '2022-04-26 12:47:27', '2022-01-02 23:08:30', '2022-12-07 14:20:37');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('9388994a-33a9-4719-bbd1-a9677f4ac391', '1.png', 65, '模特', '呼伦贝尔市', '线下交易', '2022-12-20 14:04:00', '2022-01-07 04:28:54', '2022-05-07 18:08:09');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d48', '1.png', 61, '模特', '宁安市', '线下交易', '2022-02-01 21:57:48', '2022-11-04 19:46:21', '2022-02-06 00:22:18');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('7d204bc5-3798-4bcd-91ee-36f885e5a853', '1.png', 11, '坏女孩', '七台河市', '线下交易', '2022-04-17 06:39:40', '2022-06-25 15:50:05', '2022-04-11 18:15:16');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('8506b114-8117-4fcd-975e-12919cad6155', '1.png', 37, '遇见', '襄阳市', '线下交易', '2022-08-22 02:21:48', '2022-04-04 15:06:21', '2022-09-26 21:25:10');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('212648dd-f050-49bf-a0bb-71688035be7a', '1.png', 66, '勇气', '海口市', '线下交易', '2022-11-26 15:19:04', '2022-09-30 20:15:03', '2022-06-29 21:50:58');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d48', '1.png', 82, '发如雪', '鞍山市', '线下交易', '2022-07-20 20:28:49', '2022-04-03 03:55:46', '2022-04-21 07:35:46');
insert into spz.`second_hand_trade` (`number`, `item_image`, `item_price`, `item_information`, `place`, `approach`, `trade_time`, `create_time`, `update_time`) values ('f2b3d1e5-59b6-4a03-a9b6-a0d4bbc41d48', '1.png', 92, '火红的萨日朗', '定州市', '线下交易', '2022-03-14 07:13:08', '2022-12-14 10:43:14', '2022-10-11 03:52:56');

# 二手交易关系表
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (1, 1, 1, 1, 2, 1, '2022-02-26 11:52:11', '2022-09-11 08:48:38');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (2, 2, 1, 2, 2, 2, '2022-05-05 13:04:57', '2022-09-05 07:15:17');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (3, 3, 1, 3, 3, 3, '2022-05-25 03:06:29', '2022-05-12 09:55:46');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (4, 4, 1, 4, 4, 4, '2022-11-11 20:21:08', '2022-02-10 00:15:07');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (5, 2, 1, 1, 2, 2, '2022-06-26 15:02:00', '2022-08-24 08:55:07');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (6, 2, 1, 2, 2, 1, '2022-06-16 14:29:54', '2022-04-20 01:39:45');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (7, 3, 1, 4, 2, 3, '2022-02-01 10:16:17', '2022-04-28 12:56:28');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (8, 3, 2, 1, 2, 4, '2022-10-16 23:54:28', '2022-09-20 06:05:00');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (9, 3, 2, 3, 1, 3, '2022-01-01 20:22:15', '2022-12-07 16:18:23');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (10, 1, 3, 1, 1, 1, '2022-02-20 19:29:00', '2022-11-30 20:54:52');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (11, 2, 3, 2, 1, 2, '2022-04-20 02:35:52', '2022-06-23 00:51:18');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (12, 4, 3, 4, 1, 4, '2022-06-30 13:43:32', '2022-06-16 14:35:24');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (13, 2, 3, 2, 1, 1, '2022-03-10 07:35:51', '2022-10-25 12:11:12');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (14, 2, 8, 1, 1, 2, '2022-02-05 21:22:55', '2022-09-21 19:46:48');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (15, 3, 2, 3, 1, 4, '2022-05-20 14:03:53', '2022-11-07 12:32:31');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (16, 3, 10, 4, 1, 3, '2022-02-13 13:14:55', '2022-02-26 21:50:34');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (17, 4, 2, 4, 1, 4, '2022-04-28 15:00:41', '2022-10-03 08:48:08');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (18, 1, 6, 1, 5, 1, '2022-09-04 05:52:14', '2022-06-20 22:19:59');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (19, 1, 6, 7, 6, 1, '2022-03-17 20:27:56', '2022-11-14 08:50:12');
insert into spz.`second_hand_trade_user` (`second_hand_trade_id`, `second_hand_trade_status`, `buyer_id`, `buyer_status`, `seller_id`, `seller_status`, `create_time`, `update_time`) values (20, 1, 4, 1, 1, 1, '2022-10-11 09:41:08', '2022-10-27 07:10:14');


-- 标签分组表
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('新旧', '2022-01-04 03:59:22', '2022-06-06 15:18:13');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('大小', '2022-02-21 06:46:49', '2022-05-07 10:23:10');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('尺寸', '2022-10-29 16:42:53', '2022-10-19 01:34:23');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('价值', '2022-02-25 19:27:00', '2022-01-19 01:31:58');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('价格', '2022-03-28 07:16:29', '2022-09-01 12:28:12');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('颜色', '2022-10-05 10:26:36', '2022-01-12 23:17:53');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('风格', '2022-06-18 22:39:27', '2022-12-04 23:57:53');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('类型', '2022-06-18 22:39:27', '2022-12-04 23:57:53');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('音乐', '2022-06-18 22:39:27', '2022-12-04 23:57:53');
insert into spz.`tag_group` (`name`, `create_time`, `update_time`) values ('业务', '2022-06-18 22:39:27', '2022-12-04 23:57:53');

-- 标签表
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('融为一体', '2022-04-05 07:25:26', '2022-12-20 11:58:35');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('举足轻重', '2022-12-01 10:07:55', '2022-07-14 13:04:46');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('首当其冲', '2022-07-01 03:59:30', '2022-07-03 14:04:33');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('名不虚传', '2022-03-23 11:20:12', '2022-10-03 05:44:36');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('奋发有为', '2022-03-21 03:58:43', '2022-01-04 13:34:40');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('理直气壮', '2022-08-10 04:01:31', '2022-11-30 06:26:16');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('紧锣密鼓', '2022-04-29 20:37:03', '2022-03-13 14:40:24');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('供不应求', '2022-05-09 23:44:44', '2022-05-27 00:10:11');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('三位一体', '2022-02-04 14:30:10', '2022-02-24 03:59:48');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('弄虚作假', '2022-06-10 21:32:40', '2022-06-19 09:28:29');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('统筹兼顾', '2022-11-26 20:45:58', '2022-07-16 21:00:15');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('引人注目', '2022-04-18 21:44:04', '2022-06-05 06:22:19');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('循序渐进', '2022-01-14 11:02:41', '2022-11-11 19:51:16');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('万紫千红', '2022-12-10 05:45:56', '2022-12-19 19:41:49');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('随时随地', '2022-08-17 05:19:58', '2022-10-13 18:46:02');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('家喻户晓', '2022-12-18 02:45:38', '2022-02-10 03:06:55');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('大吃一惊', '2022-01-24 17:20:26', '2022-04-01 08:45:33');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('不知不觉', '2022-04-19 09:36:34', '2022-05-22 14:07:58');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('风口浪尖', '2022-08-07 22:58:50', '2022-03-17 02:28:34');
insert into spz.`tag` (`name`, `create_time`, `update_time`) values ('前所未有', '2022-09-19 18:33:41', '2022-02-22 02:12:22');

-- 标签与分组的关联表
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 1, '2022-05-20 06:21:50', '2022-03-22 17:40:47');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 2, '2022-09-16 21:56:55', '2022-07-30 06:10:16');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 3, '2022-07-02 09:45:17', '2022-11-02 01:34:38');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 4, '2022-06-21 07:51:57', '2022-04-23 21:29:53');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 5, '2022-04-06 05:04:43', '2022-09-19 04:39:41');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 6, '2022-09-29 12:52:27', '2022-08-02 06:48:15');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 7, '2022-03-30 03:53:08', '2022-06-23 14:02:02');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 8, '2022-02-21 00:45:24', '2022-02-05 23:10:13');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 9, '2022-04-15 04:49:43', '2022-10-16 02:32:25');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (1, 10, '2022-04-25 19:40:43', '2022-09-06 21:41:46');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (2, 1, '2022-05-11 15:10:59', '2022-07-20 14:45:39');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (2, 2, '2022-12-18 16:02:33', '2022-05-07 03:40:39');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (3, 3, '2022-04-18 13:32:28', '2022-07-12 07:04:29');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (6, 4, '2022-08-14 10:50:46', '2022-07-31 07:12:11');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (2, 5, '2022-03-04 04:22:15', '2022-08-12 03:55:51');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (7, 6, '2022-10-31 11:50:08', '2022-02-18 21:45:47');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (4, 7, '2022-03-06 05:55:28', '2022-10-09 18:19:54');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (4, 8, '2022-03-09 04:49:47', '2022-05-20 05:09:00');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (2, 9, '2022-05-20 21:46:46', '2022-04-06 18:22:51');
insert into spz.`tag_tag_group` (`tag_id`, `tag_group_id`, `create_time`, `update_time`) values (6, 3, '2022-01-07 18:55:05', '2022-06-08 08:27:34');

-- 图片与二手物品关联表
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (1, '1.jpg', '2022-04-25 18:37:17', '2022-09-03 15:18:03');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (1, '1.jpg', '2022-05-04 20:32:23', '2022-11-22 21:14:57');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (2, '1.jpg', '2022-08-13 18:20:31', '2022-07-23 20:55:56');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (3, '1.jpg', '2022-07-17 18:15:44', '2022-06-25 11:12:03');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (6, '1.jpg', '2022-12-19 10:18:39', '2022-02-07 11:58:09');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (4, '1.jpg', '2022-05-20 20:57:30', '2022-05-27 03:56:41');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (10, '1.jpg', '2022-07-16 09:31:48', '2022-12-17 04:19:00');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (9, '1.jpg', '2022-03-21 21:56:01', '2022-05-29 16:34:53');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (6, '1.jpg', '2022-06-13 13:39:09', '2022-06-04 11:23:19');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (5, '1.jpg', '2022-07-12 04:56:05', '2022-08-28 15:53:03');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (9, '1.jpg', '2022-07-21 00:06:08', '2022-08-02 13:59:55');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (1, '1.jpg', '2022-02-12 10:05:41', '2022-08-12 09:36:46');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (3, '1.jpg', '2022-12-29 03:22:16', '2022-11-10 12:25:13');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (8, '1.jpg', '2022-11-23 03:17:59', '2022-02-23 17:58:31');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (5, '1.jpg', '2022-03-18 20:49:21', '2022-11-13 04:26:10');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (8, '1.jpg', '2022-05-07 08:51:16', '2022-09-16 10:55:57');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (7, '1.jpg', '2022-10-07 10:08:18', '2022-08-08 22:13:52');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (5, '1.jpg', '2022-04-21 01:30:41', '2022-03-23 12:27:30');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (9, '1.jpg', '2022-06-29 08:49:06', '2022-01-30 06:45:17');
insert into spz.`second_hand_item_image` (`second_hand_item_id`, `image`, `create_time`, `update_time`) values (3, '1.jpg', '2022-01-30 09:28:54', '2022-09-14 20:11:24');

-- 二手物品与标签的关联表
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (1, 1, '2022-06-26 15:54:40', '2022-08-14 18:02:15');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (1, 2, '2022-08-01 13:40:59', '2022-11-11 12:58:30');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (2, 1, '2022-02-12 06:03:50', '2022-01-20 21:16:53');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (1, 3, '2022-04-20 08:35:03', '2022-07-11 08:23:08');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (2, 10, '2022-04-13 21:13:57', '2022-07-21 00:09:11');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (1, 4, '2022-08-12 04:42:53', '2022-12-23 03:00:46');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (3, 1, '2022-04-22 10:16:07', '2022-04-07 05:04:33');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (4, 1, '2022-09-22 03:19:04', '2022-12-26 18:12:45');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (5, 5, '2022-11-05 09:03:23', '2022-10-26 05:58:22');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (2, 10, '2022-07-24 16:44:07', '2022-10-18 03:39:58');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (10, 6, '2022-02-25 11:06:40', '2022-10-24 06:48:08');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (4, 2, '2022-01-08 18:17:03', '2022-07-10 17:06:09');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (2, 2, '2022-01-23 09:15:45', '2022-07-20 23:57:32');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (5, 9, '2022-03-26 09:31:25', '2022-02-28 08:25:11');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (6, 3, '2022-07-17 18:26:34', '2022-05-04 21:12:05');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (1, 8, '2022-11-26 16:46:32', '2022-07-22 05:51:18');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (8, 1, '2022-07-01 14:16:09', '2022-08-29 03:59:10');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (7, 9, '2022-06-04 23:43:13', '2022-05-03 13:29:41');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (1, 3, '2022-12-08 03:35:06', '2022-02-21 02:50:42');
insert into spz.`second_hand_item_tag` (`second_hand_item_id`, `tag_tag_group_id`, `create_time`, `update_time`) values (6, 1, '2022-07-31 05:31:35', '2022-02-16 07:50:52');

-- 用户与物品浏览关联表
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 8, '2022-06-28 17:32:35', '2022-11-21 08:31:47');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 2, '2022-09-25 15:48:16', '2022-07-07 14:27:54');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 1, '2022-02-09 19:28:15', '2022-07-25 13:21:40');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 1, '2022-02-28 19:06:22', '2022-01-27 11:41:31');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (3, 1, '2022-05-09 21:33:40', '2022-10-17 11:24:11');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 2, '2022-11-19 22:37:48', '2022-02-23 07:45:09');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (4, 3, '2022-04-03 02:00:14', '2022-06-28 03:01:16');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 6, '2022-01-22 23:51:59', '2022-06-16 20:02:08');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (7, 1, '2022-01-16 02:47:06', '2022-04-27 22:27:57');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (10, 1, '2022-05-11 00:38:22', '2022-10-04 17:07:37');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (3, 6, '2022-08-21 03:38:39', '2022-09-13 21:14:24');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (7, 7, '2022-04-25 07:04:31', '2022-07-25 13:09:39');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 10, '2022-07-21 03:51:59', '2022-02-12 05:40:40');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (3, 4, '2022-03-25 19:51:59', '2022-01-07 13:28:01');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (7, 5, '2022-08-13 13:13:07', '2022-11-20 15:57:32');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (10, 4, '2022-11-02 19:50:23', '2022-02-02 16:06:18');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (10, 3, '2022-04-08 23:15:08', '2022-12-16 17:20:15');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 7, '2022-08-21 09:25:42', '2022-06-03 22:03:03');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 4, '2022-12-18 07:53:48', '2022-01-02 19:04:15');
insert into spz.`user_item_browse` (`user_id`, `item_id`, `create_time`, `update_time`) values (6, 6, '2022-07-01 10:13:58', '2022-07-06 04:36:09');

-- 用户与物品收藏关联表
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 10, '2022-03-22 16:03:00', '2022-07-23 18:14:25');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 7, '2022-10-29 06:12:43', '2022-12-20 10:09:02');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 1, '2022-12-25 18:12:46', '2022-04-19 19:59:06');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 2, '2022-08-14 09:31:06', '2022-08-28 10:24:57');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 6, '2022-03-29 01:51:09', '2022-03-23 06:31:22');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 3, '2022-07-08 18:27:08', '2022-04-19 18:16:19');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 1, '2022-01-17 01:44:34', '2022-07-17 10:40:09');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 3, '2022-01-25 12:40:01', '2022-09-20 17:17:30');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (6, 1, '2022-01-11 00:10:00', '2022-03-19 08:55:39');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (4, 1, '2022-12-27 01:07:42', '2022-09-20 09:13:09');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (6, 2, '2022-12-27 09:16:24', '2022-06-12 19:34:40');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (9, 6, '2022-01-07 11:52:22', '2022-03-18 22:08:45');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (10, 4, '2022-01-17 08:45:25', '2022-10-03 22:42:25');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (10, 7, '2022-12-12 11:02:35', '2022-02-14 15:52:24');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (6, 9, '2022-07-19 00:47:12', '2022-05-10 15:58:27');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (1, 5, '2022-06-18 07:23:26', '2022-02-17 21:11:36');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (6, 6, '2022-05-04 04:57:06', '2022-03-03 08:31:43');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (9, 2, '2022-08-02 07:26:33', '2022-03-27 22:22:10');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (2, 9, '2022-12-22 20:05:14', '2022-12-31 02:07:20');
insert into spz.`user_item_collect` (`user_id`, `item_id`, `create_time`, `update_time`) values (3, 3, '2022-11-10 00:52:48', '2022-02-05 04:28:15');

-- 用户的设置表
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (1, '1', '1', '2022-02-25 07:44:21', '2022-11-26 14:39:42');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (2, '1', '1', '2022-09-08 08:09:55', '2022-05-03 23:39:15');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (3, '1', '1', '2022-10-27 14:55:37', '2022-11-10 08:57:21');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (4, '1', '1', '2022-02-06 06:37:02', '2022-08-23 23:10:23');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (5, '1', '1', '2022-01-15 07:10:22', '2022-04-07 19:35:39');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (6, '1', '1', '2022-06-10 00:41:51', '2022-11-04 15:30:31');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (7, '1', '1', '2022-12-11 07:50:57', '2022-01-07 06:43:39');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (8, '1', '1', '2022-04-05 08:42:03', '2022-03-17 06:44:21');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (9, '1', '1', '2022-03-23 14:05:45', '2022-09-04 08:58:23');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (10, '1', '1', '2022-08-23 14:46:26', '2022-11-28 14:13:22');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (11, '1', '1', '2022-05-30 04:34:24', '2022-05-10 00:54:48');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (12, '1', '1', '2022-04-29 13:04:07', '2022-11-26 10:35:25');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (13, '1', '1', '2022-04-13 22:44:20', '2022-09-10 00:19:24');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (14, '1', '1', '2022-03-10 09:09:18', '2022-05-06 17:57:26');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (15, '1', '1', '2022-06-12 01:41:57', '2022-07-19 16:25:44');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (16, '1', '1', '2022-04-04 12:52:44', '2022-04-18 17:33:19');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (17, '1', '1', '2022-05-13 10:52:07', '2022-05-07 08:58:48');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (18, '1', '1', '2022-11-30 05:21:22', '2022-12-21 20:38:19');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (19, '1', '1', '2022-02-18 04:22:13', '2022-02-25 20:07:43');
insert into spz.`user_setting` (`user_id`, `remind`, `message`, `create_time`, `update_time`) values (20, '1', '1', '2022-08-16 04:48:05', '2022-02-20 15:00:05');

-- 二手物品与标签的关联表
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (1, 1, '审核不通过', '2022-11-21 23:21:53', '2022-11-07 20:23:57');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (2, 2, '审核不通过', '2022-11-09 18:21:52', '2022-01-31 20:40:05');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (3, 3, '审核不通过', '2022-01-17 21:25:14', '2022-01-11 18:38:59');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (4, 4, '审核不通过', '2022-08-14 10:11:50', '2022-11-03 17:35:45');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (5, 5, '审核不通过', '2022-07-30 23:01:51', '2022-11-14 12:09:47');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (6, 6, '审核不通过', '2022-03-15 00:35:16', '2022-09-22 20:00:39');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (7, 7, '审核不通过', '2022-09-10 04:24:53', '2022-05-17 07:40:49');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (8, 8, '审核不通过', '2022-01-22 20:39:01', '2022-10-26 23:30:31');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (9, 9, '审核不通过', '2022-05-07 19:45:12', '2022-02-28 04:01:29');
insert into spz.`second_hand_item_reject` (`item_id`, `manager_id`, `message`, `create_time`, `update_time`) values (10, 10, '审核不通过', '2022-07-09 22:15:09', '2022-05-02 11:51:52');

-- 投诉表
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 7, 8, 2, 0, '{"message":{"message1":"产品质量低下"}}', '2022-07-02 12:54:12', '2022-04-27 04:08:38');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 3, 1, 7, 1, '{"message":{"message1":"产品质量低下"}}', '2022-12-11 10:29:44', '2022-03-07 14:52:17');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 10, 9, 9, 0, '{"message":{"message1":"产品质量低下"}}', '2022-02-21 19:33:43', '2022-07-09 17:37:13');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 7, 1, 6, 0, '{"message":{"message1":"产品质量低下"}}', '2022-12-05 18:56:37', '2022-05-11 05:08:43');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 4, 4, 1, 0, '{"message":{"message1":"产品质量低下"}}', '2022-04-21 19:01:34', '2022-03-29 14:06:09');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 7, 9, 5, 0, '{"message":{"message1":"产品质量低下"}}', '2022-11-03 21:52:46', '2022-04-26 20:24:54');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 8, 9, 1, 1, '{"message":{"message1":"产品质量低下"}}', '2022-11-29 22:35:47', '2022-01-29 12:00:29');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 9, 1, 1, 1, '{"message":{"message1":"产品质量低下"}}', '2022-11-24 15:40:05', '2022-03-28 19:16:31');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 8, 7, 9, 1, '{"message":{"message1":"产品质量低下"}}', '2022-05-22 21:37:29', '2022-07-11 23:42:49');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 2, 2, 7, 1, '{"message":{"message1":"产品质量低下"}}', '2022-03-20 02:54:01', '2022-04-17 17:46:23');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 4, 1, 8, 1, '{"message":{"message1":"产品质量低下"}}', '2022-11-03 13:23:08', '2022-03-27 08:57:08');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 6, 3, 10, 0, '{"message":{"message1":"产品质量低下"}}', '2022-07-18 07:27:19', '2022-09-15 07:11:27');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 5, 2, 3, 0, '{"message":{"message1":"产品质量低下"}}', '2022-09-08 03:06:01', '2022-07-28 07:07:12');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 5, 10, 3, 0, '{"message":{"message1":"产品质量低下"}}', '2022-09-07 12:46:12', '2022-06-27 17:46:08');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 5, 9, 2, 0, '{"message":{"message1":"产品质量低下"}}', '2022-02-03 17:33:06', '2022-05-31 13:53:48');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 4, 1, 4, 1, '{"message":{"message1":"产品质量低下"}}', '2022-08-16 05:20:46', '2022-09-03 06:44:02');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 8, 9, 4, 1, '{"message":{"message1":"产品质量低下"}}', '2022-11-28 09:58:33', '2022-03-14 15:18:05');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 10, 9, 10, 1, '{"message":{"message1":"产品质量低下"}}', '2022-01-05 22:47:53', '2022-02-11 13:01:06');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 4, 7, 9, 0, '{"message":{"message1":"产品质量低下"}}', '2022-09-05 04:14:20', '2022-11-05 10:40:37');
insert into spz.`complaint` (`complainant`, `respondent`, `order_id`, `status`, `data`, `create_time`, `update_time`) values ( 3, 1, 6, 1, '{"message":{"message1":"产品质量低下"}}', '2022-06-05 17:46:15', '2022-09-21 01:46:10');
