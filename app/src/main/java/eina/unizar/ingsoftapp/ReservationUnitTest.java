package eina.unizar.ingsoftapp;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReservationUnitTest extends Test {
    private static final String BOOKING_TAG = "Reservas";
    private ReservationDbAdapter            bookingDBHelper;

    public void run(Context ctx) throws Exception {
        bookingDBHelper = new ReservationDbAdapter(ctx);
        bookingDBHelper.open();
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
        creation_isIncorrect_11();

        rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
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
        update_isIncorrect_11(rowId);

        delete_isCorrect(rowId);
        delete_isIncorrect();
    }

    // ----- Reservas --------------------------------

    public void creation_isCorrect() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"Reserva creada con exito");
    }

    public void update_isCorrect(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva actualizada con exito");
    }

    public void delete_isCorrect(long rowId) throws Exception {
        Boolean success = bookingDBHelper.deleteReserva(rowId);
        assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva borrada con exito");
    }

    public void creation_isIncorrect_1() throws Exception {
        long rowId = bookingDBHelper.createReserva(null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: nombre nulo");
    }

    public void creation_isIncorrect_2() throws Exception {
        long rowId = bookingDBHelper.createReserva("","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: nombre es cadena vacia");
    }

    public void creation_isIncorrect_3() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono es nulo");
    }

    public void creation_isIncorrect_4() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene menos de 9 digitos");
    }

    public void creation_isIncorrect_5() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene mas de 9 digitos");
    }

    public void creation_isIncorrect_6() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene caracteres no numericos");
    }

    public void creation_isIncorrect_7() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada es nulo");
    }

    public void creation_isIncorrect_8() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
    }

    public void creation_isIncorrect_9() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023",null, "23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha salida nulo");
    }

    public void creation_isIncorrect_10() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: precio nulo");
    }

    public void creation_isIncorrect_11() throws Exception {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: precio negativo");
    }

    // -----------------------------------------------

    public void update_isIncorrect_1(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: nombre cliente nulo");
    }

    public void update_isIncorrect_2(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: nombre cliente cadena vacia");
    }

    public void update_isIncorrect_3(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono nulo");
    }

    public void update_isIncorrect_4(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con menos de 9 digitos");
    }

    public void update_isIncorrect_5(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con mas de 9 digitos");
    }

    public void update_isIncorrect_6(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con caracteres no numericos");
    }

    public void update_isIncorrect_7(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha entrada nula");
    }

    public void update_isIncorrect_8(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
    }

    public void update_isIncorrect_9(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023",null,"23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha salida nula");
    }

    public void update_isIncorrect_10(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: precio nulo");
    }

    public void update_isIncorrect_11(long rowId) throws Exception {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: precio negativo");
    }

    // -----------------------------------------------

    public void delete_isIncorrect() throws Exception {
        Boolean success = bookingDBHelper.deleteReserva(-1);
        assertFalse(success);
        Log.d(BOOKING_TAG,"Reserva borrada sin exito");
    }
}