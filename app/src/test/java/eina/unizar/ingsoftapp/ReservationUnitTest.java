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
public class ReservationUnitTest {
    private static final String BOOKING_TAG = "Reservas";
    private static final String CLEAN_TAG   = "Clean";

    private ReservationDbAdapter            bookingDBHelper;

    // ----- Reservas --------------------------------

    @Test
    public void creation_isCorrect() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"Reserva creada con exito");
    }

    @Test
    public void update_isCorrect() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva actualizada con exito");
    }

    @Test
    public void delete_isCorrect() {
        Boolean success = bookingDBHelper.deleteReserva(1);
        assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva borrada con exito");
    }

    @Test
    public void creation_isIncorrect_1() {
        long rowId = bookingDBHelper.createReserva(null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: nombre nulo");
    }

    @Test
    public void creation_isIncorrect_2() {
        long rowId = bookingDBHelper.createReserva("","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: nombre es cadena vacia");
    }

    @Test
    public void creation_isIncorrect_3() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono es nulo");
    }

    @Test
    public void creation_isIncorrect_4() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene menos de 9 digitos");
    }

    @Test
    public void creation_isIncorrect_5() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene mas de 9 digitos");
    }

    @Test
    public void creation_isIncorrect_6() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene caracteres no numericos");
    }

    @Test
    public void creation_isIncorrect_7() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada es nulo");
    }

    @Test
    public void creation_isIncorrect_8() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
    }

    @Test
    public void creation_isIncorrect_9() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023",null, "23.5");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: fecha salida nulo");
    }

    @Test
    public void creation_isIncorrect_10() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: precio nulo");
    }

    @Test
    public void creation_isIncorrect_11() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        assertEquals(-1, rowId);
        Log.d(BOOKING_TAG,"check: precio negativo");
    }

    // -----------------------------------------------

    @Test
    public void update_isIncorrect_1() {
        Boolean success = bookingDBHelper.updateReserva(1, null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: nombre cliente nulo");
    }

    @Test
    public void update_isIncorrect_2() {
        Boolean success = bookingDBHelper.updateReserva(1, "","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: nombre cliente cadena vacia");
    }

    @Test
    public void update_isIncorrect_3() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono nulo");
    }

    @Test
    public void update_isIncorrect_4() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con menos de 9 digitos");
    }

    @Test
    public void update_isIncorrect_5() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con mas de 9 digitos");
    }

    @Test
    public void update_isIncorrect_6() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: telefono con caracteres no numericos");
    }

    @Test
    public void update_isIncorrect_7() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha entrada nula");
    }

    @Test
    public void update_isIncorrect_8() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
    }

    @Test
    public void update_isIncorrect_9() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023",null,"23.5");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: fecha salida nula");
    }

    @Test
    public void update_isIncorrect_10() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: precio nulo");
    }

    @Test
    public void update_isIncorrect_11() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        assertFalse(success);
        Log.d(BOOKING_TAG,"check: precio negativo");
    }

    // -----------------------------------------------

    @Test
    public void delete_isIncorrect() {
        Boolean success = bookingDBHelper.deleteReserva(-1);
        assertFalse(success);
        Log.d(BOOKING_TAG,"Reserva borrada sin exito");
    }
}