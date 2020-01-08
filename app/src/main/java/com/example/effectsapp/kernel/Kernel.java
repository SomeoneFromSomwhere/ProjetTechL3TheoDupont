package com.example.effectsapp;

public class Kernel {

    private int[][] matrice;
    private int sumKernel;

    Kernel(int[] values, int size){
        this.sumKernel = 0;
        this.matrice = new int[size][size];
        for(int y=0; y<size; y++){
            for(int x=0; x<size; x++){
                int value = values[size*y + x];
                this.matrice[x][y] = value;
                this.sumKernel = sumKernel + value;
            }
        }
    }

    public int[][] getMatrice() {
        return matrice;
    }

    public int getSumKernel() {
        return sumKernel;
    }

    public void setSumKernel(int sumKernel) {
        this.sumKernel = sumKernel;
    }

    public void setMatrice(int[][] matrice) {
        this.matrice = matrice;
    }

}
