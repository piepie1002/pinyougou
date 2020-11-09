package com.pie.paradise.utils;

/**
 * snowflake算法
 * @author LIN
 * @since JDK 1.8
 */
public class SnowFlake {
    /**
     * 起始的时间戳
     */
    private static final long START_STMP = 1480166465666L;
    /**
     * 每一位占用的位数
     */
    private static final long SEQUENCE_BIT = 12; //序列号占用的位数
    private static final long MACHINE_BIT = 5;   //机器标识占用的位数
    private static final long DATACENTER_BIT = 5;//数据中心占用的位数
    /**
     * 每一部分的最大值
     */
    //1^1 = 0   1^0=1  0^0 =0
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);//31
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);//4095
    private static final long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);//31
    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳
    
    public SnowFlake(long datacenterId,long machineId){
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0){
            throw new IllegalArgumentException("数据中心的Id不能大于最大数据中心值或小于0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0){
            throw new IllegalArgumentException("机器数量不能大于最大机器数量或小于0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }
    
    public synchronized long nextId(){
        //获取当前时间(毫秒)
        long currStmp = getNewstmp();
        if (currStmp < lastStmp){
            throw new RuntimeException("时间倒退,拒绝生成ID");
        }
        //都在同一毫秒
        if (currStmp == lastStmp){
            //相同毫秒内,序列号自增,& 这个操作的意思是同一个位上，都是1，结果才是1，否则就是0
            //和(sequence+1)效果是一样的，不同的是，一旦超过了4095，sequence会从新变成0。
            sequence = (sequence+1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L){
                currStmp = getNextMill();
            }
        }else {
            //不同毫秒内,序列号设为0
            sequence = 0L;
        }
        //上一个时间戳就等于刚刚执行的这个时间戳了
        lastStmp = currStmp;
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分    
    }

    private long getNextMill() {
        long mill = getNewstmp();
        //如果小于或等于上一个时间戳,继续获取当前时间戳 
        while (mill<=lastStmp){
            mill = getNewstmp();
        }
        return mill;
    }

    //返回当前时间(毫秒)方法
    private long getNewstmp() {
        return System.currentTimeMillis();
    }
    //测试
    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        for (int i = 0; i < (1 << 5); i++) {
            long id = snowFlake.nextId();
            System.out.println(id);
        }
    }
}
