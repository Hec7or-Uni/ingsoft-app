package eina.unizar.ingsoftapp;

public class Test {

    public void assertTrue(boolean success) throws Exception {
        if (success == false) throw new Exception("Se esperaba True");
    }

    public void assertFalse(Boolean success) throws Exception {
        if (success == true) throw new Exception("Se esperaba False");
    }

    public void assertEquals(int i, long rowId) throws Exception {
        if (i != rowId) throw new Exception("Se esperaba que fuesen iguales");
    }
}