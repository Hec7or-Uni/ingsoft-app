package eina.unizar.ingsoftapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HabitacionesReservasUnitTest {
    private static final String MIX_TAG     = "HabitacionesReservas";
    private static final String CLEAN_TAG   = "Clean";

    private HabitacionesReservasDbAdapter   mixDBHelper;

    // ----- Habitaciones y Reservas -----

    @Test
    public void creation_isCorrect() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"2");
        assertTrue(-1 != rowId);
        Log.d(MIX_TAG,"HabitacionReserva creada con exito");
    }

    @Test
    public void update_isCorrect() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,"6");
        assertTrue(success);
        Log.d(MIX_TAG,"HabitacionReserva actualizada con exito");
    }

    @Test
    public void delete_isCorrect() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(1,1);
        assertTrue(success);
        Log.d(MIX_TAG,"HabitacionReserva borrada con exito");
    }

    // -----------------------------------------------

    @Test
    public void creation_isIncorrect_1() {
        long rowId = mixDBHelper.createHabitacionReserva(-1,1,"2");
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: idHabitacion invalido");
    }

    @Test
    public void creation_isIncorrect_2() {
        long rowId = mixDBHelper.createHabitacionReserva(1,-1,"2");
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: idReserva invalido");
    }

    @Test
    public void creation_isIncorrect_3() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,null);
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: ocupacion nula");
    }

    @Test
    public void creation_isIncorrect_4() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"0");
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: ocupacion menor que 1");
    }

    // -----------------------------------------------

    @Test
    public void update_isIncorrect_1() {
        Boolean success = mixDBHelper.updateHabitacionReserva(-1,1,"6");
        assertFalse(success);
        Log.d(MIX_TAG,"check: idHabitacion invalido");
    }

    @Test
    public void update_isIncorrect_2() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,-1,"6");
        assertFalse(success);
        Log.d(MIX_TAG,"check: idReserva invalido");
    }

    @Test
    public void update_isIncorrect_3() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,null);
        assertFalse(success);
        Log.d(MIX_TAG,"check: ocupacion nula");
    }

    @Test
    public void update_isIncorrect_4() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,"0");
        assertFalse(success);
        Log.d(MIX_TAG,"check: ocupacion menor que 1");
    }

    // -----------------------------------------------

    @Test
    public void delete_isIncorrect_1() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(-1,1);
        assertFalse(success);
        Log.d(MIX_TAG,"check: idhabitacion es negativo");
    }

    @Test
    public void delete_isIncorrect_2() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(1,-1);
        assertFalse(success);
        Log.d(MIX_TAG,"check: idreserva es negativo");
    }
}