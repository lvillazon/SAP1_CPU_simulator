public class CPU {
    // emulates the SAP-1 CPU
    // 8-bit data bus
    // 4-bit memory addresses - max 16 bytes
    // 2, 8-bit registers with an ALU
    // 16 control flags
    // 5 instructions:
    //  LDA, STA, ADD, SUB, HLT

    private byte[] RAM;  // 16 bytes of addressable RAM
    private byte MAR; // Memory Address Register
    private byte IR;  // Instruction Register
    private byte aRegister;   // A-register, aka accumulator
    private byte PC;  // program counter

    // control flags
    private boolean PC_ENABLE;
    private boolean PC_OUT;
    private boolean HALT_FLAG;

    public CPU(byte[] program) {
        // the RAM is initialised to a 16-byte array of zeros
        // the input program is then copied over, a byte at a time
        // this ensures that the CPU always has exactly 16-bytes of RAM
        // regardless of what is passed to it
        RAM = new byte[16];
        for (int i=0; i<16; i++) {
            RAM[i] = (byte) 0;
        }
        int i = 0;
        while (i<16 && i<program.length) {
            RAM[i] = program[i];
            i++;
        }
        // all other registers are initialised to 0
        aRegister = 0;
        PC = 0;
        HALT_FLAG = false;

    }

    public void run() {
        // execute the program until a halt instruction is reached
        while (!HALT_FLAG) {
            fetch_instruction();
            execute_instruction();
            display_current_state();
        }
    }

    public void display_current_state() {
        // dump all registers and flags to the console
        System.out.println("PC:  " + Byte.toString(PC));
        System.out.println("A:   " + Byte.toString(aRegister));

        // display RAM contents
        System.out.print("RAM: ");
        for(int i=0; i<16; i++) {
            System.out.print(RAM[i] + " ");
        }
        System.out.println("\n----------------------------");
    }

    private void fetch_instruction() {
        // copy PC to MAR
        MAR = PC;
        // copy contents of RAM pointed to by MAR to IR
        IR = RAM[MAR];
        // increment PC
        incrementPC();
    }

    private void incrementPC() {
        // PC counts from 0 to 15 and then wraps
        PC = (byte)((PC + 1) % 16);
    }

    private void execute_instruction() {
        // decode instruction in IR
        byte op_code = (byte) (IR & 0b11110000); // top 4 bits
        byte operand = (byte) (IR & 0b00001111); // bottom 4 bits
        // do whatever it says
        System.out.println("opcode:" + Byte.toString(op_code));
        System.out.println("operand:" + Byte.toString(operand));
        if (op_code == 0b0000) {  // LDA
            // copy contents of address in the operand to A-register
            aRegister = RAM[operand];
        } else {
            System.out.println("Unrecognised op-code:" + Byte.toString(op_code));
        }
    }
}
