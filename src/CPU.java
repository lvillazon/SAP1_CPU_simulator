public class CPU {
    // emulates the SAP-1 CPU
    // 8-bit data bus
    // 4-bit memory addresses - max 16 bytes
    // 2, 8-bit registers with an ALU
    // 16 control flags
    // 5 instructions:
    //  LDA, STA, ADD, SUB, HLT

    private int[] RAM;  // 16 bytes of addressable RAM
    private int MAR; // Memory Address Register
    private int IR;  // Instruction Register
    private int aRegister;   // A-register, aka accumulator
    private int bRegister;   // B-register
    private int outputRegister;
    private int PC;  // program counter

    // control flags
    private boolean HALT_FLAG;

    public CPU(int[] program) {
        // the RAM is initialised to a 16-byte array of zeros
        // the input program is then copied over, a byte at a time
        // this ensures that the CPU always has exactly 16-bytes of RAM
        // regardless of what is passed to it
        RAM = new int[16];
        for (int i=0; i<16; i++) {
            RAM[i] = (int) 0;
        }
        int i = 0;
        while (i<16 && i<program.length) {
            RAM[i] = program[i];
            i++;
        }
        // all other registers are initialised to 0
        aRegister = 0;
        bRegister = 0;
        outputRegister = 0;
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
        System.out.println("PC:  " + Integer.toHexString(PC));
        System.out.println("MAR: " + Integer.toHexString(MAR));
        System.out.println("IR:  " + Integer.toHexString(IR));
        System.out.println("A:   " + Integer.toHexString(aRegister));
        System.out.println("B:   " + Integer.toHexString(bRegister));
        System.out.println("OUT: " + Integer.toHexString(outputRegister));

        // display RAM contents
        System.out.print("RAM: ");
        for(int i=0; i<16; i++) {
            System.out.print(Integer.toHexString(RAM[i]) + " ");
        }
        System.out.println("\n----------------------------");
    }

    public void display_output() {
        // display output register on the console
        System.out.println(outputRegister);
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
        PC = (PC + 1) % 16;
    }

    private void execute_instruction() {
        // decode instruction in IR
        int op_code = (IR & 0b11110000) >> 4; // top 4 bits of the lower byte
        int operand = IR & 0b00001111; // bottom 4 bits
        // do whatever it says
        System.out.println("op-code:" + op_code);
        System.out.println("operand:" + operand);
        switch (op_code) {
            case 0b0000:  // LDA
                // copy contents of address in the operand to A-register
                aRegister = RAM[operand];
                break;
            case 0b0001:  // ADD
                // copy contents of address in the operand to B-register
                // add this to the A-register and store result in A-register
                bRegister = RAM[operand];
                aRegister = aRegister + bRegister;
                break;
            case 0b0010:  // SUB
                // copy contents of address in the operand to B-register
                // subtract this from the A-register and store result in A-register
                bRegister = RAM[operand];
                aRegister = aRegister - bRegister;
                break;
            case 0b1110:  // OUT
                outputRegister = aRegister;
                break;
            case 0b1111:  // HLT
                HALT_FLAG = true;
                break;
            default:
                System.out.println("Unrecognised op-code:" + op_code);
        }
    }
}
