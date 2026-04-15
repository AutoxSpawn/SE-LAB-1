.data

    A : .word 5, 4, 3, 2, 1, 10, 9, 8, 7, 6     # Define a array of 10 words
    sz_A : .word 10     # Store the size of the array as a word within memory

    min : .word 0x7FFFFFFF  # Start min at the largest possible value so that any of the first numbers will definitely be smaller 
    max : .word 0x80000000  # Start max at the smallest possible value so that any of the first numbers will definitely be larger

    # Define its strings for output
    prompt_min: .asciiz "The absolute minimum value inside the array is: "
    prompt_max: .asciiz "The absolute maximum value inside the array is: "

    new_line: .asciiz "\n"

.text
    .globl main

main:

    la s0, A    # Load the base address of our array "A" into our pointer which is s0
    lw s1, sz_A # Load the value 10 from our memory into s1

    lw s2, min  # Load the initial big value from our memory into s2
    lw s3, max  # Load the initial small value from memory into our s3

    li t0, 0    # Initialize our index counter 'i' to 0 in register to t0

loop:

    lw t1, 0(s0)    # Load the word within our current address (s0) into our t1
    bge t1, s2, skip_min    # Branch off if t1 is greater than or equal to s2 or skip the update if it is not smaller
    mv s2, t1   # If we didnt branch off, that means t1 is smaller than s2, which than we will copy t1 to s2

skip_min:

    ble t1, s3, skip_max    # Branch off if t1 is less than or equal to s3 or skip the update if its not larger
    mv s3, t1   # If we didn't branch off, that means t1 is larger than s3, which than we will copy t1 to s3

skip_max:

    addi t0, t0, 1  # Increment our index counter, which is basically i += 1
    addi s0, s0, 4  # Move the address pointer forward by (4 bytes) to the next word/index within our array
    bne t0, s1, loop    # If "i" is not equal to 10, than we will jump back to our loop and than repeat

    li a0, 4    # Setting our syscall service to 4
    la a1, prompt_min   # Load our address (min prompt) into a1

    ecall

    li a0, 1    # Setting our syscall service to 1
    mv a1, s2   # Put the final min value from s2 into a1 so we can print it

    ecall

    li a0, 4    # Set our syscall service to 4 
    la a1, new_line # print a new line character
    ecall

    li a0, 4    # Set our syscall service to 4
    la a1, prompt_max   #Load our address of our max prompt into a1

    ecall

    li a0, 1    # Set our syscall service to 1
    mv a1, s3   # Put the final max value from s3 into a1 so we can print it

    ecall

    li a0, 4
    la a1, new_line # Print another newline for a cleaner format

    ecall

    # Exit
    li a0, 10   # set our syscall service to 10 than we can end our program.
    ecall 

