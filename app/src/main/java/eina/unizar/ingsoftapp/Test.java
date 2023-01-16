package eina.unizar.ingsoftapp;

import android.util.Log;

public class Test {
    private String DEBUG = "Testing";

    public int assertTrue(boolean success) {
        if (success) { return 0; }

        Log.d(DEBUG,"Se esperaba True");
        return 1;
    }

    public int assertFalse(Boolean success)  {
        if (success) {
            Log.d(DEBUG,"Se esperaba False");
            return 1;
        }

        return 0;
    }

    public int assertEquals(long i, long rowId) {
        if (i != rowId) {
            Log.d(DEBUG,"Se esperaba que fuesen iguales");
            return 1;
        }

        return 0;
    }
}