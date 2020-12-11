/**
 * Solve the problem of moving the number of disks specified
 * by the first parameter from the stack specified by the
 * second parameter to the stack specified by the third
 * parameter. The stack specified by the fourth parameter
 * is available for use as a spare. Stacks are specified by
 * number: 0, 1, or 2.
 */
public class Chapter09TowerOfHanoiExample {
    public static void main(String[] args) {
        towersOfHanoi(4, 0, 1, 2);
    }
    static void towersOfHanoi(int disks, int from, int to, int spare) {
        if (disks == 1) {
            // There is only one disk to be moved. Just move it.
            System.out.printf("Move disk 1 from stack %d to stack %d%n",
                    from, to);
        }
        else {
            // Move all but one disk to the spare stack, then
            // move the bottom disk, then put all the other
            // disks on top of it.
            towersOfHanoi(disks-1, from, spare, to);
            System.out.printf("Move disk %d from stack %d to stack %d%n",
                    disks, from, to);
            towersOfHanoi(disks-1, spare, to, from);
        }
    }
}
