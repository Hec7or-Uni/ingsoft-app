package eina.unizar.ingsoftapp;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HabitacionesReservasUnitTest extends Test {
    private static final String MIX_TAG     = "HabitacionesReservas";
    private HabitacionesReservasDbAdapter   mixDBHelper;


    public void run() throws InvocationTargetException, IllegalAccessException {
        Class miClase = HabitacionesReservasUnitTest.class;
        Method[] methods = miClase.getDeclaredMethods();
        Object obj = new RoomUnitTest();

        for (Method method : methods) {
            System.out.println(method.getName());

            if (method.getName().startsWith("creation")) {
                method.invoke(obj);
            } else if (method.getName().startsWith("update")) {
//                long rowId = mixDBHelper.createHabitacion("Luciernaga","2 camas + baño",
//                        "3","23.50","25");
//                method.invoke(obj, rowId);
//                mixDBHelper.deleteHabitacion(rowId);
            } else if (method.getName().startsWith("delete")) {
                if (method.getName().contains("Incorrect")) {
                    method.invoke(obj);
                } else {
//                    long rowId = mixDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                            "3","23.50","25");
//                    method.invoke(obj, rowId);
                }
            }
        }
    }

    // ----- Habitaciones y Reservas -----

    public void creation_isCorrect(long roomId, long resId) throws Exception {
        long rowId = mixDBHelper.createHabitacionReserva(roomId,resId,"2");
        assertTrue(-1 != rowId);
        Log.d(MIX_TAG,"HabitacionReserva creada con exito");
    }

    public void update_isCorrect(long roomId, long resId) throws Exception {
        Boolean success = mixDBHelper.updateHabitacionReserva(roomId,resId,"6");
        assertTrue(success);
        Log.d(MIX_TAG,"HabitacionReserva actualizada con exito");
    }

    public void delete_isCorrect(long roomId, long resId) throws Exception {
        Boolean success = mixDBHelper.deleteHabitacionReserva(roomId,resId);
        assertTrue(success);
        Log.d(MIX_TAG,"HabitacionReserva borrada con exito");
    }

    // -----------------------------------------------

    public void creation_isIncorrect_1() throws Exception {
        long rowId = mixDBHelper.createHabitacionReserva(-1,1,"2");
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: idHabitacion invalido");
    }

    public void creation_isIncorrect_2() throws Exception {
        long rowId = mixDBHelper.createHabitacionReserva(1,-1,"2");
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: idReserva invalido");
    }

    public void creation_isIncorrect_3() throws Exception {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,null);
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: ocupacion nula");
    }

    public void creation_isIncorrect_4() throws Exception {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"0");
        assertEquals(-1, rowId);
        Log.d(MIX_TAG,"check: ocupacion menor que 1");
    }

    // -----------------------------------------------

    public void update_isIncorrect_1() throws Exception {
        Boolean success = mixDBHelper.updateHabitacionReserva(-1,1,"6");
        assertFalse(success);
        Log.d(MIX_TAG,"check: idHabitacion invalido");
    }

    public void update_isIncorrect_2() throws Exception {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,-1,"6");
        assertFalse(success);
        Log.d(MIX_TAG,"check: idReserva invalido");
    }

    public void update_isIncorrect_3() throws Exception {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,null);
        assertFalse(success);
        Log.d(MIX_TAG,"check: ocupacion nula");
    }

    public void update_isIncorrect_4() throws Exception {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,"0");
        assertFalse(success);
        Log.d(MIX_TAG,"check: ocupacion menor que 1");
    }

    // -----------------------------------------------

    public void delete_isIncorrect_1() throws Exception {
        Boolean success = mixDBHelper.deleteHabitacionReserva(-1,1);
        assertFalse(success);
        Log.d(MIX_TAG,"check: idhabitacion es negativo");
    }

    public void delete_isIncorrect_2() throws Exception {
        Boolean success = mixDBHelper.deleteHabitacionReserva(1,-1);
        assertFalse(success);
        Log.d(MIX_TAG,"check: idreserva es negativo");
    }
}