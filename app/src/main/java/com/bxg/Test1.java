package com.bxg;

public class Test1 {
    public void a(){
        int a[][]={{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
        int b[][]=new int[4][3];
        int i,j;
        for(i=0;i<3;i++){
            for(j=0;j<4;j++){
                System.out.print(i+" "+j);
                System.out.print(3-j+" "+i);
                b[i][j]=a[3-j][i];
                System.out.print(b[i][j]+"   ");
            }
        }

    }


}
