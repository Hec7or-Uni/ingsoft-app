package eina.unizar.ingsoftapp;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReservationUnitTest extends Test {
    private static final String BOOKING_TAG = "Reservas";
    private ReservationDbAdapter            bookingDBHelper;

    public int run(Context ctx) {
        bookingDBHelper = new ReservationDbAdapter(ctx);
        bookingDBHelper.open();
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
        flags += creation_isIncorrect_9();
        flags += creation_isIncorrect_10();
        flags += creation_isIncorrect_11();

        rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
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
        flags += update_isIncorrect_11(rowId);

        flags += delete_isCorrect(rowId);
        flags += delete_isIncorrect();

        return flags;
    }

    // ----- Reservas --------------------------------

    public int creation_isCorrect() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"Reserva creada con exito");
        return flag;
    }

    public int update_isCorrect(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva actualizada con exito");
        return flag;
    }

    public int delete_isCorrect(long rowId) {
        Boolean success = bookingDBHelper.deleteReserva(rowId);
        int flag = assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva borrada con exito");
        return flag;
    }

    public int creation_isIncorrect_1() {
        long rowId = bookingDBHelper.createReserva(null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: nombre nulo");
        return flag;
    }

    public int creation_isIncorrect_2() {
        long rowId = bookingDBHelper.createReserva("","874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: nombre es cadena vacia");
        return flag;
    }

    public int creation_isIncorrect_3() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono es nulo");
        return flag;
    }

    public int creation_isIncorrect_4() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene menos de 9 digitos");
        return flag;
    }

    public int creation_isIncorrect_5() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene mas de 9 digitos");
        return flag;
    }

    public int creation_isIncorrect_6() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene caracteres no numericos");
        return flag;
    }

    public int creation_isIncorrect_7() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada es nulo");
        return flag;
    }

    public int creation_isIncorrect_8() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
        return flag;
    }

    public int creation_isIncorrect_9() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023",null, "23.5");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha salida nulo");
        return flag;
    }

    public int creation_isIncorrect_10() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: precio nulo");
        return flag;
    }

    public int creation_isIncorrect_11() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        int flag = assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: precio negativo");
        return flag;
    }

    // -----------------------------------------------

    public int update_isIncorrect_1(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: nombre cliente nulo");
        return flag;
    }

    public int update_isIncorrect_2(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "","874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: nombre cliente cadena vacia");
        return flag;
    }

    public int update_isIncorrect_3(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono nulo");
        return flag;
    }

    public int update_isIncorrect_4(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con menos de 9 digitos");
        return flag;
    }

    public int update_isIncorrect_5(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con mas de 9 digitos");
        return flag;
    }

    public int update_isIncorrect_6(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con caracteres no numericos");
        return flag;
    }

    public int update_isIncorrect_7(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha entrada nula");
        return flag;
    }

    public int update_isIncorrect_8(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
        return flag;
    }

    public int update_isIncorrect_9(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023",null,"23.5");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha salida nula");
        return flag;
    }

    public int update_isIncorrect_10(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: precio nulo");
        return flag;
    }

    public int update_isIncorrect_11(long rowId) {
        Boolean success = bookingDBHelper.updateReserva(rowId, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"check: precio negativo");
        return flag;
    }

    // -----------------------------------------------

    public int delete_isIncorrect() {
        Boolean success = bookingDBHelper.deleteReserva(-1);
        int flag = assertFalse(success);
        Log.d(BOOKING_TAG,"Reserva borrada sin exito");
        return flag;
    }
}