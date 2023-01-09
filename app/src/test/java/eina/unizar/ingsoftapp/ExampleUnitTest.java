package eina.unizar.ingsoftapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import android.util.Log;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String ROOM_TAG    = "Habitacion";
    private static final String BOOKING_TAG = "Reservas";
    private static final String MIX_TAG     = "HabitacionesReservas";
    private static final String CLEAN_TAG   = "Clean";

    private RoomsDbAdapter                  roomsDBHelper;
    private ReservationDbAdapter            bookingDBHelper;
    private HabitacionesReservasDbAdapter   mixDBHelper;

    // ----- Habitaciones ----------------------------

    @Test
    public void h_creation_isCorrect() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","25");
        assertTrue(-1 != rowId);
        Log.d(ROOM_TAG,"Habitacion creada con exito");
    }

    @Test
    public void h_update_isCorrect() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion actualizada con exito");
    }

    @Test
    public void h_delete_isCorrect() {
        Boolean success = roomsDBHelper.deleteHabitacion(1);
        assertTrue(success);
        Log.d(ROOM_TAG,"Habitacion borrada con exito");
    }

    // -----------------------------------------------

    @Test
    public void h_creation_isIncorrect_1() {
        long rowId = roomsDBHelper.createHabitacion(null,"2 camas + baño",
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: nombre nulo");
    }

    @Test
    public void h_creation_isIncorrect_2() {
        long rowId = roomsDBHelper.createHabitacion("","2 camas + baño",
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: cadena vacia");
    }

    @Test
    public void h_creation_isIncorrect_3() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga",null,
                "3","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: descripcion nula");
    }

    @Test
    public void h_creation_isIncorrect_4() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                null,"23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad nula");
    }

    @Test
    public void h_creation_isIncorrect_5() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "0","23.50","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
    }

    @Test
    public void h_creation_isIncorrect_6() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3",null,"25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio nulo");
    }

    @Test
    public void h_creation_isIncorrect_7() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","-10","25");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: precio negativo");
    }

    @Test
    public void h_creation_isIncorrect_8() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50",null);
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
    }

    @Test
    public void h_creation_isIncorrect_9() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","-10");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
    }

    @Test
    public void h_creation_isIncorrect_10() {
        long rowId = roomsDBHelper.createHabitacion("Luciernaga","2 camas + baño",
                "3","23.50","110");
        assertEquals(-1, rowId);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
    }

    // -----------------------------------------------

    @Test
    public void h_update_isIncorrect_1() {
        Boolean success = roomsDBHelper.updateHabitacion(1, null,"2 camas + baño",
                "3","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: nombre es nulo");
    }

    @Test
    public void h_update_isIncorrect_2() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "","2 camas + baño",
                "3","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: nombre es cadena vacia");
    }

    @Test
    public void h_update_isIncorrect_3() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga",null,
                "3","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: descripcion nula");
    }

    @Test
    public void h_update_isIncorrect_4() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                null,"23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: capacidad nula");
    }

    @Test
    public void h_update_isIncorrect_5() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "0","23.50","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: capacidad menor que 1");
    }

    @Test
    public void h_update_isIncorrect_6() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3",null,"25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: precio nulo");
    }

    @Test
    public void h_update_isIncorrect_7() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","-10","25");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: precio negativo");
    }

    @Test
    public void h_update_isIncorrect_8() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50",null);
        assertTrue(success);
        Log.d(ROOM_TAG,"check: porcentaje nulo");
    }

    @Test
    public void h_update_isIncorrect_9() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50","-10");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: porcentaje negativo");
    }

    @Test
    public void h_update_isIncorrect_10() {
        Boolean success = roomsDBHelper.updateHabitacion(1, "Luciernaga","2 camas + baño",
                "3","23.50","110");
        assertTrue(success);
        Log.d(ROOM_TAG,"check: porcentaje pasa el tope");
    }

    // -----------------------------------------------

    @Test
    public void h_delete_isIncorrect() {
        Boolean success = roomsDBHelper.deleteHabitacion(-1);
        assertTrue(success);
        Log.d(ROOM_TAG,"Fallo al eliminar una habitación");
    }

    // ----- Reservas --------------------------------

    @Test
    public void r_creation_isCorrect() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"Reserva creada con exito");
    }

    @Test
    public void r_update_isCorrect() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva actualizada con exito");
    }

    @Test
    public void r_delete_isCorrect() {
        Boolean success = bookingDBHelper.deleteReserva(1);
        assertTrue(success);
        Log.d(BOOKING_TAG,"Reserva borrada con exito");
    }

    @Test
    public void r_creation_isIncorrect_1() {
        long rowId = bookingDBHelper.createReserva(null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: nombre nulo");
    }

    @Test
    public void r_creation_isIncorrect_2() {
        long rowId = bookingDBHelper.createReserva("","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: nombre es cadena vacia");
    }

    @Test
    public void r_creation_isIncorrect_3() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: telefono es nulo");
    }

    @Test
    public void r_creation_isIncorrect_4() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene menos de 9 digitos");
    }

    @Test
    public void r_creation_isIncorrect_5() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene mas de 9 digitos");
    }

    @Test
    public void r_creation_isIncorrect_6() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: telefono tiene caracteres no numericos");
    }

    @Test
    public void r_creation_isIncorrect_7() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada es nulo");
    }

    @Test
    public void r_creation_isIncorrect_8() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
    }

    @Test
    public void r_creation_isIncorrect_9() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023",null, "23.5");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: fecha salida nulo");
    }

    @Test
    public void r_creation_isIncorrect_10() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: precio nulo");
    }

    @Test
    public void r_creation_isIncorrect_11() {
        long rowId = bookingDBHelper.createReserva("Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        assertTrue(-1 != rowId);
        Log.d(BOOKING_TAG,"check: precio negativo");
    }

    // -----------------------------------------------

    @Test
    public void r_update_isIncorrect_1() {
        Boolean success = bookingDBHelper.updateReserva(1, null,"874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: nombre cliente nulo");
    }

    @Test
    public void r_update_isIncorrect_2() {
        Boolean success = bookingDBHelper.updateReserva(1, "","874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: nombre cliente cadena vacia");
    }

    @Test
    public void r_update_isIncorrect_3() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez",null,
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: telefono nulo");
    }

    @Test
    public void r_update_isIncorrect_4() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","1004",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: telefono con menos de 9 digitos");
    }

    @Test
    public void r_update_isIncorrect_5() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","34874642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: telefono con mas de 9 digitos");
    }

    @Test
    public void r_update_isIncorrect_6() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","a74642093",
                "07/01/2023","08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: telefono con caracteres no numericos");
    }

    @Test
    public void r_update_isIncorrect_7() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                null,"08/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: fecha entrada nula");
    }

    @Test
    public void r_update_isIncorrect_8() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "08/01/2023","07/01/2023","23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: fecha entrada posterior a la de salida");
    }

    @Test
    public void r_update_isIncorrect_9() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023",null,"23.5");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: fecha salida nula");
    }

    @Test
    public void r_update_isIncorrect_10() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023",null);
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: precio nulo");
    }

    @Test
    public void r_update_isIncorrect_11() {
        Boolean success = bookingDBHelper.updateReserva(1, "Federico Jiménez","874642093",
                "07/01/2023","08/01/2023","-10");
        assertTrue(success);
        Log.d(BOOKING_TAG,"check: precio negativo");
    }

    // -----------------------------------------------

    @Test
    public void r_delete_isIncorrect() {
        Boolean success = bookingDBHelper.deleteReserva(-1);
        assertFalse(success);
        Log.d(BOOKING_TAG,"Reserva borrada sin exito");
    }

    // ----- Habitaciones y Reservas -----

    @Test
    public void m_creation_isCorrect() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"2");
        assertTrue(-1 != rowId);
        Log.d(ROOM_TAG,"HabitacionReserva creada con exito");
    }

    @Test
    public void m_update_isCorrect() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,"6");
        assertTrue(success);
        Log.d(ROOM_TAG,"HabitacionReserva actualizada con exito");
    }

    @Test
    public void m_delete_isCorrect() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(1,1);
        assertTrue(success);
        Log.d(ROOM_TAG,"HabitacionReserva borrada con exito");
    }

    // -----------------------------------------------

    @Test
    public void m_creation_isIncorrect() {
        long rowId = mixDBHelper.createHabitacionReserva(1,1,"2");
        assertFalse(-1 != rowId);
        Log.d(ROOM_TAG,"HabitacionReserva creada con exito");
    }

    // -----------------------------------------------

    @Test
    public void m_update_isIncorrect() {
        Boolean success = mixDBHelper.updateHabitacionReserva(1,1,"6");
        assertFalse(success);
        Log.d(ROOM_TAG,"HabitacionReserva actualizada con exito");
    }

    // -----------------------------------------------

    @Test
    public void m_delete_isIncorrect_1() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(-1,1);
        assertFalse(success);
        Log.d(ROOM_TAG,"HabitacionReserva borrada con exito");
    }

    @Test
    public void m_delete_isIncorrect_2() {
        Boolean success = mixDBHelper.deleteHabitacionReserva(1,-1);
        assertFalse(success);
        Log.d(ROOM_TAG,"HabitacionReserva borrada con exito");
    }
}