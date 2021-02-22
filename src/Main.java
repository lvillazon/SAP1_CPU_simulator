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
        int[] program = {
                /* 00 */ 0x0E, // LDA 14
                /* 01 */ 0x1D, // ADD 13
                /* 02 */ 0x2C, // SUB 12
                /* 03 */ 0xE0, // OUT
                /* 04 */ 0xF0, // HLT
                /* 05 */ 0,
                /* 06 */ 0,
                /* 07 */ 0,
                /* 08 */ 0,
                /* 09 */ 0,
                /* 10 */ 0,
                /* 11 */ 0,
                /* 12 */ 0x01,
                /* 13 */ 0x28,
                /* 14 */ 0x42,
                /* 15 */ 0,
        };
        CPU cpu = new CPU(program);
        cpu.run();
    }
}
