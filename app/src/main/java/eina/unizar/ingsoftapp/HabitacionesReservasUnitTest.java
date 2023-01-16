package eina.unizar.ingsoftapp;

import android.content.Context;
import android.util.Log;

public class HabitacionesReservasUnitTest extends Test {
    private static final String MIX_TAG     = "HabitacionesReservas";
    private HabitacionesReservasDbAdapter   mixDBHelper;

    public int run(Context ctx) {
        mixDBHelper = new HabitacionesReservasDbAdapter(ctx);
        mixDBHelper.open();
        long roomId = 1, resId = 1;
        int flags = 0;

        flags += creation_isCorrect();
        flags += creation_isIncorrect_1();
        flags += creation_isIncorrect_2();
        flags += creation_isIncorrect_3();
        flags += creation_isIncorrect_4();

        mixDBHelper.createHabitacionReserva(roomId,resId,"2");
        flags += update_isCorrect(roomId, resId);
        flags += update_isIncorrect_1();
        flags += update_isIncorrect_2();
        flags += update_isIncorrect_3();
        flags += update_isIncorrect_4();

        flags += delete_isCorrect(roomId, resId);
        flags += delete_isIncorrect_1();
        flags += delete_isIncorrect_2();
        return flags;
    }

    // ----- Habitaciones y Reservas -----

    public int creation_isCorrect() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"2");
        int flag = assertTrue(-1 != rowId);
        Log.d(MIX_TAG,"HabitacionReserva creada con exito");
        mixDBHelper.deleteHabitacionReserva(1, 1);
        return flag;
    }

    public int update_isCorrect(long roomId, long resId) {
        Boolean success = mixDBHelper.updateHabitacionReserva(roomId,resId,"6");
        int flag = assertTrue(success);
        Log.d(MIX_TAG,"HabitacionReserva actualizada con exito");
        return flag;
    }

    public int delete_isCorrect(long roomId, long resId) {
        Boolean success = mixDBHelper.deleteHabitacionReserva(roomId, resId);
        int flag = assertTrue(success);
        Log.d(MIX_TAG,"HabitacionReserva borrada con exito");
        return flag;
    }

    // -----------------------------------------------

    public int creation_isIncorrect_1() {
        long rowId = mixDBHelper.createHabitacionReserva(-1,1,"2");
        int flag = assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: idHabitacion invalido");
        return flag;
    }

    public int creation_isIncorrect_2() {
        long rowId = mixDBHelper.createHabitacionReserva(1,-1,"2");
        int flag = assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: idReserva invalido");
        return flag;
    }

    public int creation_isIncorrect_3() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,null);
        int flag = assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: ocupacion nula");
        return flag;
    }

    public int creation_isIncorrect_4() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"0");
        int flag = assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: ocupacion menor que 1");
        return flag;
    }

    // -----------------------------------------------

    public int update_isIncorrect_1() {
        Boolean success = mixDBHelper.updateHabitacionReserva(-1,1,"6");
        int flag = assertFalse(success);
        Log.d(MIX_TAG,"check: idHabitacion invalido");
        return flag;
    }

    public int update_isIncorrect_2() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,-1,"6");
        int flag = assertFalse(success);
        Log.d(MIX_TAG,"check: idReserva invalido");
        return flag;
    }

    public int update_isIncorrect_3() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,null);
        int flag = assertFalse(success);
        Log.d(MIX_TAG,"check: ocupacion nula");
        return flag;
    }

    public int update_isIncorrect_4() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,"0");
        int flag = assertFalse(success);
        Log.d(MIX_TAG,"check: ocupacion menor que 1");
        return flag;
    }

    // -----------------------------------------------

    public int delete_isIncorrect_1() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(-1,1);
        int flag = assertFalse(success);
        Log.d(MIX_TAG,"check: idhabitacion es negativo");
        return flag;
    }

    public int delete_isIncorrect_2() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(1,-1);
        int flag = assertFalse(success);
        Log.d(MIX_TAG,"check: idreserva es negativo");
        return flag;
    }
}