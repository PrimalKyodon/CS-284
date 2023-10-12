/*
 * Name: Aaren Patel
 * Date: February 7, 2023
 * Pledge: I pledge my honor that I hava abided by the Stevens Honor System
 * Class: CS - 284; Homework 1
 */
import java.io.*;

public class BinaryNumber{
    private int length;
    private int[] data;
        
    public BinaryNumber(int length){
        /*Creates a BinaryNumber object of size length with length 0s*/
        if (length == 0)
            throw new IllegalArgumentException("A BinaryNumber can't have a length of 0");
        if (length < 0)
            throw new ArrayIndexOutOfBoundsException("Index is less than 0");
        this.length = length;
        data = new int[length];
        for(int i = 0; i < length; i++)
            data[i] = 0;
    }

    public BinaryNumber(String binNum){
        /*Creates a BinaryNumber object with binNum*/
        length = binNum.length();
        data = new int[length];
        for(int i = 0; i < length; i++){
            int digit = Character.getNumericValue(binNum.charAt(i));
            if (digit == 0 || digit == 1)
                data[i] = digit;
            else
                throw new IllegalArgumentException("A BinaryNumber must contain only 1s or 0s");
        }
    }

    public int getLength(){
        /*returns the length of the binary number*/
        return length;
    }

    public int[] getInnerArray(){
        /*returns the binary number in an array*/
        return data;
    }
    
    public int getDigit(int index){
        /*Returns the digit in the binary number at the input index*/
        if (index < 0 || index >= data.length)
            throw new ArrayIndexOutOfBoundsException("Index not within the range of BinaryNumber");
        return data[index];
    }

    public int toDecimal(){
        /*Converts the binary number to base10*/
        int sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += data[i] * Math.pow(2, i);
        return sum;
    }

    public void bitShift(int direction, int amount){
        /*Performs the bitshift operations*/
        if (!(direction == -1 || direction == 1))
            throw new IllegalArgumentException("Direction must be -1 for left or 1 for right.");
        if (amount < 0)
            throw new IllegalArgumentException("Bit Shift amount must be greater than or equal to 0");
        int[] arr;
        if (direction == -1){
            length += amount;
            arr = new int[length];
            for (int i = 0;  i < length-amount; i++)
                arr[i] = data[i];
            for (int i = length-amount; i < length; i++)
                arr[i] = 0;
        }
        else{
            if (amount >= length){
                length = 1;
                arr = new int[]{0};
            }
            else{
                length -= amount;
                arr = new int[length];
                for (int i = 0; i < length; i++)
                    arr[i] = data[i];
            }
        }
        data = arr;
    }
    

    public static int[] bwor(BinaryNumber bn1, BinaryNumber bn2){
        /*Performs the bitwise or operation*/
        if (bn1.getLength() != bn2.getLength())
            throw new IllegalArgumentException("In order to do bitwise or of the two BinaryNumber objects they must be of same length");
        int[] arr = new int[bn1.getLength()];
        for (int i = 0; i < bn1.getLength(); i++){
            if (bn1.getDigit(i) == 1 || bn2.getDigit(i) == 1)
                arr[i] = 1;
            else
                arr[i] = 0;
        }
        return arr;
    }

    public static int[] bwand(BinaryNumber bn1, BinaryNumber bn2){
        /*Performs the bitwise and operation*/
        if (bn1.getLength() != bn2.getLength())
            throw new IllegalArgumentException("In order to do bitwise and of the two BinaryNumber objects they must be of same length");
        int[] arr = new int[bn1.getLength()];
        for (int i = 0; i < bn1.getLength(); i++){
            if (bn1.getDigit(i) == 1 && bn2.getDigit(i) == 1)
                arr[i] = 1;
            else
                arr[i] = 0;
        }
        return arr;
    }

    @Override
    public String toString(){
        /*Overrides the Object toString and returns the binary number*/
        String binNum = "";
        for (int i = 0; i < length; i++)
            binNum += String.valueOf(data[i]);
        return binNum;
    }
    
    public int[] prepend(int x){
        /*Adds 0s to the beginning of an array*/
        int[] arr = new int[length+x];
        for (int i = 0; i < x; i++)
            arr[i] = 0;
        for (int i = x; i < x+length; i++)
            arr[i] = data[i-x];
        return arr;
    }

    public void add(BinaryNumber bn){
        /*adds two binary numbers*/
        int bnl = bn.getLength();
        int[] binNum1;
        int[] binNum2;
        if (bnl < length){
            binNum1 = bn.prepend(length-bnl);
            binNum2 = data;
        }
        else if (bnl < length){
            binNum1 = bn.getInnerArray();
            binNum2 = this.prepend(bnl-length);
        }
        else{
            binNum1 = bn.getInnerArray();
            binNum2 = data;
        }
        int[] arr = new int[binNum1.length];
        int sum = 0;
        int carry = 0;
        bnl = binNum1.length;
        for(int i = bnl-1; i >= 0; i--){
            sum = carry + binNum1[i] + binNum2[i];
            carry = 0;
            if (sum > 1){
                carry = 1;
                sum -= 2;
            }
            arr[i] = sum;
            sum = 0;
        }
        if (carry == 1){
            int[] arr2 = new int[arr.length + 1];
            for(int i = 0; i < arr.length; i++)
                arr2[i+1] = arr[i];
            arr2[0] = 1;
            arr = arr2;
        }
        data = arr;
        length = arr.length;
    }
}
