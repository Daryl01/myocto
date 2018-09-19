package com.youxi912.yule912.util;

public class SecurityIrreversible {
    //RFC1321中定义的标准4*4矩阵的常量定义。
    private static final int S11 = 7, S12 = 13, S13 = 17, S14 = 23;
    private static final int S21 = 5, S22 = 21, S23 = 17, S24 = 24;
    private static final int S31 = 4, S32 = 11, S33 = 16, S34 = 23;
    private static final int S41 = 9, S42 = 14, S43 = 11, S44 = 29;
    //按RFC1321标准定义不可变byte型数组PADDING
    //private const byte[] PADDING= new byte[64];
    private byte[] PADDING = {-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    // MD5计算过程中的3组核心数据，采用数组形式存放
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32[] state = new UInt32[4];
    private int[] state = new int[4]; // 计算状态(分别对应a b c d)
    private byte[] buffer = new byte[64]; // 分配64个字节私有缓冲区
    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32[] count = new UInt32[2];
    private int[] count = new int[2]; // 位个数
    // 最新一次计算结果的16进制ASCII字符串表示，代表了16个字符串形式的MD5值
    public String resultStr;
    // 最新一次计算结果的2进制数组表示，一共16个字节，代表了128bit形式的MD5值
    public byte[] digest = new byte[16];

    // MD5_Encoding类提供的主要的接口函数getMD5ofStr，用来进行数据加密变换。调用其可对任意字符串进行加密运算，并以字符串形式返回加密结果。
    public final String getMD5ofStr(String message) {
        md5Init(); // 初始化
        char[] c = message.toCharArray();
        byte[] b = new byte[c.length];
        for (int i = 0; i < c.length; i++) {
            b[i] = (byte) c[i];
        }
        md5Update(b, message.length()); // 调用MD5的主计算过程
        md5Final(); // 输出结果到digest数组中
        for (int i = 0; i < 16; i++) {
            resultStr += byteToHEX(digest[i]); // 将digest数组中的每个byte型数据转为16进制形式的字符串
        }
        return resultStr;
    }

    // 标准的构造函数，调用md5Init函数进行初始化工作
    public SecurityIrreversible() {
        md5Init();
        return;
    }

    // md5初始化函数.初始化核心变量.
    private void md5Init() {
        state[0] = 0x67452301; // 定义state为RFC1321中定义的标准幻数
        state[1] = (int) 0xefcdab89; // 定义state为RFC1321中定义的标准幻数
        state[2] = (int) 0x98badcfe; // 定义state为RFC1321中定义的标准幻数
        state[3] = 0x10325476; // 定义state为RFC1321中定义的标准幻数
        count[0] = count[1] = 0; // 初始化为0
        resultStr = ""; // 初始化resultStr字符串为空
        for (int i = 0; i < 16; i++) {
            digest[i] = 0; //初始化digest数组元素为0
        }
        return;
    }

    //定义F G H I 为4个基数 ，即为4个基本的MD5函数,进行简单的位运算
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 F(UInt32 x, UInt32 y, UInt32 z)
    private int F(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 G(UInt32 x, UInt32 y, UInt32 z)
    private int G(int x, int y, int z) {
        return (x & z) | (y & (~z));
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 H(UInt32 x, UInt32 y, UInt32 z)
    private int H(int x, int y, int z) {
        return x ^ y ^ z;
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 I(UInt32 x, UInt32 y, UInt32 z)
    private int I(int x, int y, int z) {
        return y ^ (x | (~z));
    }

    // FF,GG,HH和II调用F,G,H,I函数进行进一步变换
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 FF(UInt32 a, UInt32 b, UInt32 c, UInt32 d, UInt32 x, int s, UInt32 ac)
    private int FF(int a, int b, int c, int d, int x, int s, int ac) {
        a += F(b, c, d) + x + ac;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
        a = a << s | a >>> (32 - s); //这里UInt32型数据右移时使用无符号右移运算符>>>
        a += b;
        return a;
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 GG(UInt32 a, UInt32 b, UInt32 c, UInt32 d, UInt32 x, int s, UInt32 ac)
    private int GG(int a, int b, int c, int d, int x, int s, int ac) {
        a += G(b, c, d) + x + ac;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
        a = a << s | a >>> (32 - s); //这里UInt32型数据右移时使用无符号右移运算符>>>
        a += b;
        return a;
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 HH(UInt32 a, UInt32 b, UInt32 c, UInt32 d, UInt32 x, int s, UInt32 ac)
    private int HH(int a, int b, int c, int d, int x, int s, int ac) {
        a += H(b, c, d) + x + ac;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
        a = a << s | a >>> (32 - s); //这里UInt32型数据右移时使用无符号右移运算符>>>
        a += b;
        return a;
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt32 II(UInt32 a, UInt32 b, UInt32 c, UInt32 d, UInt32 x, int s, UInt32 ac)
    private int II(int a, int b, int c, int d, int x, int s, int ac) {
        a += I(b, c, d) + x + ac;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
        a = a << s | a >>> (32 - s); //这里UInt32型数据右移时使用无符号右移运算符>>>
        a += b;
        return a;
    }

    // MD5的主计算过程，input是需要变换的二进制字节串，inputlen是长度
    private void md5Update(byte[] input, int inputLen) {
        int i = 0, index, partLen;
        byte[] block = new byte[64]; // 分配64个字节缓冲区
        //根据count计算index值。这里UInt32型数据右移时使用无符号右移运算符>>>
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
        index = (int) (count[0] >>> 3) & 0x3F;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((count[0] += ((UInt32)inputLen << 3)) < ((UInt32)inputLen << 3))
        if ((count[0] += ((int) inputLen << 3)) < ((int) inputLen << 3)) {
            count[1]++;
        }
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: count[1] += ((UInt32)inputLen >> 29);
        count[1] += ((int) inputLen >>> 29); //这里int型数据右移时使用无符号右移运算符>>>
        partLen = 64 - index; //计算partLen值
        if (inputLen >= partLen) {
            md5Memcpy(buffer, input, index, 0, partLen);
            md5Transform(buffer);
            for (i = partLen; i + 63 < inputLen; i += 64) {
                md5Memcpy(block, input, 0, i, 64);
                md5Transform(block);
            }
            index = 0;
        } else {
            i = 0;
        }
        md5Memcpy(buffer, input, index, i, inputLen - i);
    }

    // 整理和填写输出结果，结果放到数组digest中。
    private void md5Final() {
        byte[] bits = new byte[8];
        int index, padLen;
        Encode(bits, count, 8);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
        index = (int) (count[0] >>> 3) & 0x3f; //这里UInt32型数据右移时使用无符号右移运算符>>>
        padLen = (index < 56) ? (56 - index) : (120 - index);
        md5Update(PADDING, padLen);
        md5Update(bits, 8);
        Encode(digest, state, 16);
    }

    // byte数组的块拷贝函数，将input数组中的起始位置为inpos，长度len的数据拷贝到output数组起始位置outpos处。
    private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len) {
        int i;
        for (i = 0; i < len; i++) {
            output[outpos + i] = input[inpos + i];
        }
    }

    // MD5核心变换计算程序，由md5Update函数调用，block是分块的原始字节数组
    private void md5Transform(byte[] block) {
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: UInt32 a = state[0], b = state[1], c = state[2], d = state[3];
        int a = state[0], b = state[1], c = state[2], d = state[3];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: UInt32[] x = new UInt32[16];
        int[] x = new int[16];
        Decode(x, block, 64);
        // 进行4级级联运算
        // 第1级
        a = FF(a, b, c, d, x[0], S11, 0xd76aa478); // 1
        d = FF(d, a, b, c, x[1], S12, 0xe8c7b756); // 2
        c = FF(c, d, a, b, x[2], S13, 0x242070db); // 3
        b = FF(b, c, d, a, x[3], S14, 0xc1bdceee); // 4
        a = FF(a, b, c, d, x[4], S11, 0xf57c0faf); // 5
        d = FF(d, a, b, c, x[5], S12, 0x4787c62a); // 6
        c = FF(c, d, a, b, x[6], S13, 0xa8304613); // 7
        b = FF(b, c, d, a, x[7], S14, 0xfd469501); // 8
        a = FF(a, b, c, d, x[8], S11, 0x698098d8); // 9
        d = FF(d, a, b, c, x[9], S12, 0x8b44f7af); // 10
        c = FF(c, d, a, b, x[10], S13, 0xffff5bb1); // 11
        b = FF(b, c, d, a, x[11], S14, 0x895cd7be); // 12
        a = FF(a, b, c, d, x[12], S11, 0x6b901122); // 13
        d = FF(d, a, b, c, x[13], S12, 0xfd987193); // 14
        c = FF(c, d, a, b, x[14], S13, 0xa679438e); // 15
        b = FF(b, c, d, a, x[15], S14, 0x49b40821); // 16
        // 第2级
        a = GG(a, b, c, d, x[1], S21, 0xf61e2562); // 17
        d = GG(d, a, b, c, x[6], S22, 0xc040b340); // 18
        c = GG(c, d, a, b, x[11], S23, 0x265e5a51); // 19
        b = GG(b, c, d, a, x[0], S24, 0xe9b6c7aa); // 20
        a = GG(a, b, c, d, x[5], S21, 0xd62f105d); // 21
        d = GG(d, a, b, c, x[10], S22, 0x2441453); // 22
        c = GG(c, d, a, b, x[15], S23, 0xd8a1e681); // 23
        b = GG(b, c, d, a, x[4], S24, 0xe7d3fbc8); // 24
        a = GG(a, b, c, d, x[9], S21, 0x21e1cde6); // 25
        d = GG(d, a, b, c, x[14], S22, 0xc33707d6); // 26
        c = GG(c, d, a, b, x[3], S23, 0xf4d50d87); // 27
        b = GG(b, c, d, a, x[8], S24, 0x455a14ed); // 28
        a = GG(a, b, c, d, x[13], S21, 0xa9e3e905); // 29
        d = GG(d, a, b, c, x[2], S22, 0xfcefa3f8); // 30
        c = GG(c, d, a, b, x[7], S23, 0x676f02d9); // 31
        b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8a); // 32
        // 第3级
        a = HH(a, b, c, d, x[5], S31, 0xfffa3942); // 33
        d = HH(d, a, b, c, x[8], S32, 0x8771f681); // 34
        c = HH(c, d, a, b, x[11], S33, 0x6d9d6122); // 35
        b = HH(b, c, d, a, x[14], S34, 0xfde5380c); // 36
        a = HH(a, b, c, d, x[1], S31, 0xa4beea44); // 37
        d = HH(d, a, b, c, x[4], S32, 0x4bdecfa9); // 38
        c = HH(c, d, a, b, x[7], S33, 0xf6bb4b60); // 39
        b = HH(b, c, d, a, x[10], S34, 0xbebfbc70); // 40
        a = HH(a, b, c, d, x[13], S31, 0x289b7ec6); // 41
        d = HH(d, a, b, c, x[0], S32, 0xeaa127fa); // 42
        c = HH(c, d, a, b, x[3], S33, 0xd4ef3085); // 43
        b = HH(b, c, d, a, x[6], S34, 0x4881d05); // 44
        a = HH(a, b, c, d, x[9], S31, 0xd9d4d039); // 45
        d = HH(d, a, b, c, x[12], S32, 0xe6db99e5); // 46
        c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8); // 47
        b = HH(b, c, d, a, x[2], S34, 0xc4ac5665); // 48
        // 第4级
        a = II(a, b, c, d, x[0], S41, 0xf4292244); // 49
        d = II(d, a, b, c, x[7], S42, 0x432aff97); // 50
        c = II(c, d, a, b, x[14], S43, 0xab9423a7); // 51
        b = II(b, c, d, a, x[5], S44, 0xfc93a039); // 52
        a = II(a, b, c, d, x[12], S41, 0x655b59c3); // 53
        d = II(d, a, b, c, x[3], S42, 0x8f0ccc92); // 54
        c = II(c, d, a, b, x[10], S43, 0xffeff47d); // 55
        b = II(b, c, d, a, x[1], S44, 0x85845dd1); // 56
        a = II(a, b, c, d, x[8], S41, 0x6fa87e4f); // 57
        d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0); // 58
        c = II(c, d, a, b, x[6], S43, 0xa3014314); // 59
        b = II(b, c, d, a, x[13], S44, 0x4e0811a1); // 60
        a = II(a, b, c, d, x[4], S41, 0xf7537e82); // 61
        d = II(d, a, b, c, x[11], S42, 0xbd3af235); // 62
        c = II(c, d, a, b, x[2], S43, 0x2ad7d2bb); // 63
        b = II(b, c, d, a, x[9], S44, 0xeb86d391); // 64
        //分别累加到state[0],state[1],state[2],state[3]
        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;
    }

    // 把byte型数据转换为无符号UInt32型数据
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static UInt32 byteToul(sbyte b)
    private static int byteToul(byte b) {
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (UInt32)(b > 0 ? b : (b & 0x7F + 128));
        return (int) (b > 0 ? b : (b & 0x7F + 128));
    }

    // 把byte类型的数据转换成十六进制ASCII字符表示
    private static String byteToHEX(byte i) {
        char[] DigitStr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] ou = new char[2];
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
        ou[0] = DigitStr[(i >> 4) & 0x0F]; //取高4位
        ou[1] = DigitStr[i & 0x0F]; //取低4位
        String s = new String(ou);
        return s;
    }

    // 将UInt32型数组按顺序拆成byte型数组,长度为len
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void Encode(sbyte[] output, UInt32[] input, int len)
    private void Encode(byte[] output, int[] input, int len) {
        int i, j;
        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[j] = (byte) (input[i] & 0xffL);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
            output[j + 1] = (byte) ((input[i] >>> 8) & 0xffL);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
            output[j + 2] = (byte) ((input[i] >>> 16) & 0xffL);
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
            output[j + 3] = (byte) ((input[i] >>> 24) & 0xffL);
        }
    }

    // 将byte型数组按顺序合成UInt32型数组，长度为len
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void Decode(UInt32[] output, sbyte[] input, int len)
    private void Decode(int[] output, byte[] input, int len) {
        int i, j;
        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[i] = byteToul(input[j]) | (byteToul(input[j + 1]) << 8) | (byteToul(input[j + 2]) << 16) | (byteToul(input[j + 3]) << 24);
        }
        return;
    }
}