package eina.unizar.ingsoftapp;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RoomUnitTest extends Test {
    private static final String ROOM_TAG    = "Habitacion";
    private RoomsDbAdapter roomsDBHelper;

    public void run(Context ctx) throws Exception {
        roomsDBHelper = new RoomsDbAdapter(ctx);
        roomsDBHelper.open();
        long rowId = 0;

        creation_isCorrect();
        creation_isIncorrect_1();
        creation_isIncorrect_2();
        creation_isIncorrect_3();
        creation_isIncorrect_4();
        creation_isIncorrect_5();
        creation_isIncorrect_6();
        creation_isIncorrect_7();
        creation_isIncorrect_8();
        creation_isIncorrect_9();
        creation_isIncorrect_10();

        rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","25");
        update_isCorrect(rowId);
        update_isIncorrect_1(rowId);
        update_isIncorrect_2(rowId);
        update_isIncorrect_3(rowId);
        update_isIncorrect_4(rowId);
        update_isIncorrect_5(rowId);
        update_isIncorrect_6(rowId);
        update_isIncorrect_7(rowId);
        update_isIncorrect_8(rowId);
        update_isIncorrect_9(rowId);
        update_isIncorrect_10(rowId);

        delete_isCorrect(rowId);
        delete_isIncorrect();
    }

    // ----- Habitaciones ----------------------------

    public void creation_isCorrect() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","25");
        Log.d(ROOM_TAG, "id");
        assertTrue(-1 != rowId);
        Log.d(ROOM_TAG,"Habitacion creada con exito");

        // Elimina la habitacion creada por el test
        roomsDBHelper.deleteHabitacion(rowId);
    }

    public void update_isCorrect(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion actualizada con exito");
    }

    public void delete_isCorrect(long rowId) throws Exception {
        Boolean success = roomsDBHelper.deleteHabitacion(rowId);
        assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion borrada con exito");
    }

    // -----------------------------------------------

    public void creation_isIncorrect_1() throws Exception {
        long rowId = roomsDBHelper.createHabitacion(null,"2 camas + baño",
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: nombre nulo");
    }

    public void creation_isIncorrect_2() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("","2 camas + baño",
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: cadena vacia");
    }

    public void creation_isIncorrect_3() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga",null,
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: descripcion nula");
    }

    public void creation_isIncorrect_4() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                null,"23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad nula");
    }

    public void creation_isIncorrect_5() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "0","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
    }

    public void creation_isIncorrect_6() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3",null,"25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio nulo");
    }

    public void creation_isIncorrect_7() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","-10","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio negativo");
    }

    public void creation_isIncorrect_8() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50",null);
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
    }

    public void creation_isIncorrect_9() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","-10");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
    }

    public void creation_isIncorrect_10() throws Exception {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","110");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
    }

    // -----------------------------------------------

    public void update_isIncorrect_1(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, null,"2 camas + baño",
                "3","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: nombre es nulo");
    }

    public void update_isIncorrect_2(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "","2 camas + baño",
                "3","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: nombre es cadena vacia");
    }

    public void update_isIncorrect_3(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga",null,
                "3","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: descripcion nula");
    }

    public void update_isIncorrect_4(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                null,"23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: capacidad nula");
    }

    public void update_isIncorrect_5(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "0","23.50","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
    }

    public void update_isIncorrect_6(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3",null,"25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: precio nulo");
    }

    public void update_isIncorrect_7(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","-10","25");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: precio negativo");
    }

    public void update_isIncorrect_8(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50",null);
        assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
    }

    public void update_isIncorrect_9(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50","-10");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
    }

    public void update_isIncorrect_10(long rowId) throws Exception {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50","110");
        assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
    }

    // -----------------------------------------------

    public void delete_isIncorrect() throws Exception {
        Boolean success = roomsDBHelper.deleteHabitacion(-1);
        assertFalse(success);
        Log.d(ROOM_TAG,"check: Eliminar habitación fallo por indice negativo.");
    }
}