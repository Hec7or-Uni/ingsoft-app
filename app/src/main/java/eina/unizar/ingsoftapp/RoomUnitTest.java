package eina.unizar.ingsoftapp;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RoomUnitTest extends Test {
    private static final String ROOM_TAG    = "Habitacion";
    private RoomsDbAdapter roomsDBHelper;

    public int run(Context ctx) {
        roomsDBHelper = new RoomsDbAdapter(ctx);
        roomsDBHelper.open();
        long rowId = 0;
        int flags = 0;

        flags += creation_isCorrect();
        flags += creation_isIncorrect_1();
        flags += creation_isIncorrect_2();
        flags += creation_isIncorrect_3();
        flags += creation_isIncorrect_4();
        flags += creation_isIncorrect_5();
        flags += creation_isIncorrect_6();
        flags += creation_isIncorrect_7();
        flags += creation_isIncorrect_8();
        flags +=creation_isIncorrect_9();
        flags +=creation_isIncorrect_10();

        rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","25");
        flags += update_isCorrect(rowId);
        flags += update_isIncorrect_1(rowId);
        flags += update_isIncorrect_2(rowId);
        flags += update_isIncorrect_3(rowId);
        flags += update_isIncorrect_4(rowId);
        flags += update_isIncorrect_5(rowId);
        flags += update_isIncorrect_6(rowId);
        flags += update_isIncorrect_7(rowId);
        flags += update_isIncorrect_8(rowId);
        flags += update_isIncorrect_9(rowId);
        flags += update_isIncorrect_10(rowId);

        flags += delete_isCorrect(rowId);
        flags += delete_isIncorrect();

        return flags;
    }

    // ----- Habitaciones ----------------------------

    public int creation_isCorrect() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","25");
        Log.d(ROOM_TAG, "id");
        int flag = assertTrue(-1 != rowId);
        Log.d(ROOM_TAG,"Habitacion creada con exito");

        // Elimina la habitacion creada por el test
        roomsDBHelper.deleteHabitacion(rowId);
        return flag;
    }

    public int update_isCorrect(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50","25");
        int flag = assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion actualizada con exito");
        return flag;
    }

    public int delete_isCorrect(long rowId) {
        Boolean success = roomsDBHelper.deleteHabitacion(rowId);
        int flag = assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion borrada con exito");
        return flag;
    }

    // -----------------------------------------------

    public int creation_isIncorrect_1() {
        long rowId = roomsDBHelper.createHabitacion(null,"2 camas + baño",
                "3","23.50","25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: nombre nulo");
        return flag;
    }

    public int creation_isIncorrect_2() {
        long rowId = roomsDBHelper.createHabitacion("","2 camas + baño",
                "3","23.50","25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: cadena vacia");
        return flag;
    }

    public int creation_isIncorrect_3() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga",null,
                "3","23.50","25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: descripcion nula");
        return flag;
    }

    public int creation_isIncorrect_4() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                null,"23.50","25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad nula");
        return flag;
    }

    public int creation_isIncorrect_5() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "0","23.50","25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
        return flag;
    }

    public int creation_isIncorrect_6() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3",null,"25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio nulo");
        return flag;
    }

    public int creation_isIncorrect_7() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","-10","25");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio negativo");
        return flag;
    }

    public int creation_isIncorrect_8() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50",null);
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
        return flag;
    }

    public int creation_isIncorrect_9() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","-10");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
        return flag;
    }

    public int creation_isIncorrect_10() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","110");
        int flag = assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
        return flag;
    }

    // -----------------------------------------------

    public int update_isIncorrect_1(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, null,"2 camas + baño",
                "3","23.50","25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: nombre es nulo");
        return flag;
    }

    public int update_isIncorrect_2(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "","2 camas + baño",
                "3","23.50","25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: nombre es cadena vacia");
        return flag;
    }

    public int update_isIncorrect_3(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga",null,
                "3","23.50","25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: descripcion nula");
        return flag;
    }

    public int update_isIncorrect_4(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                null,"23.50","25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: capacidad nula");
        return flag;
    }

    public int update_isIncorrect_5(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "0","23.50","25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
        return flag;
    }

    public int update_isIncorrect_6(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3",null,"25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: precio nulo");
        return flag;
    }

    public int update_isIncorrect_7(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","-10","25");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: precio negativo");
        return flag;
    }

    public int update_isIncorrect_8(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50",null);
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
        return flag;
    }

    public int update_isIncorrect_9(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50","-10");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
        return flag;
    }

    public int update_isIncorrect_10(long rowId) {
        Boolean success = roomsDBHelper.updateHabitacion(rowId, "Luciernaga","2 camas + baño",
                "3","23.50","110");
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
        return flag;
    }

    // -----------------------------------------------

    public int delete_isIncorrect() {
        Boolean success = roomsDBHelper.deleteHabitacion(-1);
        int flag = assertFalse(success);
        Log.d(ROOM_TAG,"check: Eliminar habitación fallo por indice negativo.");
        return flag;
    }
}