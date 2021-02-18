public class Main {
    public static void main(String[] args) {
        /* hard code the initial memory state to pass to the CPU
         * each instruction byte comprises a 4-bit op-code + 4-bit address
         * valid op-codes are:
         *      bin  hex dec    instruction  operand
         *      0000   0   0    LDA          address to load contents
         *      0001   1   1    ADD          address to add contents
         *      0010   2   2    SUB          address to subtract contents
         *      1110   E  14    OUT          none
         *      1111   F  15    HLT          none
         */
        byte[] program = {
                /* 00 */ (byte) 0x0E,
                /* 01 */ (byte) 0,
                /* 02 */ (byte) 0,
                /* 03 */ (byte) 0,
                /* 04 */ (byte) 0,
                /* 05 */ (byte) 0,
                /* 06 */ (byte) 0,
                /* 07 */ (byte) 0,
                /* 08 */ (byte) 0,
                /* 09 */ (byte) 0,
                /* 10 */ (byte) 0,
                /* 11 */ (byte) 0,
                /* 12 */ (byte) 0,
                /* 13 */ (byte) 0,
                /* 14 */ (byte) 0x42,
                /* 15 */ (byte) 0,
        };
        CPU cpu = new CPU(program);
        cpu.run();
    }
}
