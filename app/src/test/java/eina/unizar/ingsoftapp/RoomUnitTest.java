package eina.unizar.ingsoftapp;

import org.junit.Test;
import android.util.Log;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RoomUnitTest {
    private static final String ROOM_TAG    = "Habitacion";
    private static final String CLEAN_TAG   = "Clean";

    private RoomsDbAdapter roomsDBHelper;
    // ----- Habitaciones ----------------------------

    @Test
    public void creation_isCorrect() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","25");
        Log.d(ROOM_TAG, "id");
        assertTrue(-1 != rowId);
        Log.d(ROOM_TAG,"Habitacion creada con exito");
    }

    @Test
    public void update_isCorrect() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion actualizada con exito");
    }

    @Test
    public void delete_isCorrect() {
        Boolean success = roomsDBHelper.deleteHabitacion(1);
        assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion borrada con exito");
    }

    // -----------------------------------------------

    @Test
    public void creation_isIncorrect_1() {
        long rowId = roomsDBHelper.createHabitacion(null,"2 camas + baño",
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: nombre nulo");
    }

    @Test
    public void creation_isIncorrect_2() {
        long rowId = roomsDBHelper.createHabitacion("","2 camas + baño",
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: cadena vacia");
    }

    @Test
    public void creation_isIncorrect_3() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga",null,
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: descripcion nula");
    }

    @Test
    public void creation_isIncorrect_4() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                null,"23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad nula");
    }

    @Test
    public void creation_isIncorrect_5() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "0","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
    }

    @Test
    public void creation_isIncorrect_6() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3",null,"25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio nulo");
    }

    @Test
    public void creation_isIncorrect_7() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","-10","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio negativo");
    }

    @Test
    public void creation_isIncorrect_8() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50",null);
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
    }

    @Test
    public void creation_isIncorrect_9() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","-10");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
    }

    @Test
    public void creation_isIncorrect_10() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","110");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
    }

    // -----------------------------------------------

    @Test
    public void update_isIncorrect_1() {
        Boolean success = roomsDBHelper.updateHabitacion(1, null,"2 camas + baño",
                "3","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: nombre es nulo");
    }

    @Test
    public void update_isIncorrect_2() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "","2 camas + baño",
                "3","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: nombre es cadena vacia");
    }

    @Test
    public void update_isIncorrect_3() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga",null,
                "3","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: descripcion nula");
    }

    @Test
    public void update_isIncorrect_4() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                null,"23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: capacidad nula");
    }

    @Test
    public void update_isIncorrect_5() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "0","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
    }

    @Test
    public void update_isIncorrect_6() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3",null,"25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: precio nulo");
    }

    @Test
    public void update_isIncorrect_7() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","-10","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: precio negativo");
    }

    @Test
    public void update_isIncorrect_8() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50",null);
        assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
    }

    @Test
    public void update_isIncorrect_9() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50","-10");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
    }

    @Test
    public void update_isIncorrect_10() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50","110");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
    }

    // -----------------------------------------------

    @Test
    public void delete_isIncorrect() {
        Boolean success = roomsDBHelper.deleteHabitacion(-1);
        assertFalse(success);
        Log.d(ROOM_TAG,"Fallo al eliminar una habitación");
    }
}