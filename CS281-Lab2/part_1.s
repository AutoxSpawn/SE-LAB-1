.data

    A : .word 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    sum : .word 0
    sz_A : .word 10

    prompt: .asciiz "The sum of the array is: "
    new_line: .asciiz "\n"

.text
    .globl main     # assembly directive that makes the symbol main
                    # global and this is where execution starts

main:

    la t0, A    # We are basically getting the starting address of the array (t0 = &A)
    la t1, sum  # We are basically getting the address of the located directly after A

    sub s1, t1, t0  # We are calculating the total bytes

    srli s1, s1, 2  # Here we are basically doing fast division, we are shifting right by 2 which is basically dividing it by 4

    la s0, A    # Setting up our pointer for the loop that we are going to do
    li t0, 0    # i will be the index (t0 = 0)
    li t2, 0    # the sum will be stored here (t2 = 0)

sum_loop:

    lw t1, 0(s0) # Grab the current number from our array with s0 being our pointer. 0 in front of s0 means we are skipping 0 bytes before getting that number
    add t2, t2, t1 # Add that number into our running total in "t2".
    addi t0, t0, 1 # Keeping track of how many we have hit, basically a "i += 1".
    addi s0, s0, 4 # Jump 4 bytes ahead so we are able to reach the next integer.
    bne t0, s1, sum_loop # Checking the loop to see if our counter is at the end "s1", if it's not at the end we will go back to the top.

#now save the total in sum variable which is in t2
    la t0, sum
    sw t2, 0(t0)

#print the results
    #print the prompt

    li a0, 4    # 4 is syscall for print_str
    la a1, prompt
    ecall

    # Print the sum value
    la t1, sum
    lw t1, 0(t1)
    li a0, 1    # 1 is syscall for print_int
    mv a1, t1
    ecall

    #print the newline
    li a0, 4    # 4 is syscall for print_str
    la a1, new_line
    ecall

    #now exit
    li a0, 10 # Exit code for ecall
    ecall