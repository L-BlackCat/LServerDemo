import org.example.netty.group_chat.engine.utils.KDateUtil;

public class TestMain {
    public static void main(String[] args) {
//        long timestamp = System.currentTimeMillis(); // 获取当前时间戳
//        int machineId = 123; // 设置机械编号
//        int sequenceId = 456; // 设置序列编号
//        int region = 0; // 设置区域
//
//        // 将时间戳转换为long类型
//        long packedTimestamp = timestamp;
//        // 将机械编号和序列编号打包成一个long类型
//        long packedId = (machineId & 0x3FF) << 42 | (sequenceId & 0x3FF) << 12 | (region & 0x3) << 63;
//
//        // 将打包后的信息合并到一起
//        long uuid = ((packedTimestamp >> 26) << 48) | packedId & 0xFFFFFFFFFFFFL;
//
//        System.out.println(Long.toBinaryString(uuid));
//        System.out.println(uuid);


        long now = KDateUtil.Instance.now();
        /**
         * zoneId用来占据4bits的值
         *
         * now用来占据42bits的值
         *
         * autoUid.incrementAndGet获得一个22bits的值
         */
        long uuid =  ((now & 0x1FFFFF) << 12) | (1 & 0xFFF);
        System.out.println(uuid);
        System.out.println(Long.toBinaryString(uuid));
    }
}
